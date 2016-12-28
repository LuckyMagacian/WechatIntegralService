package com.lanxi.wechat.entity.material;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import com.lanxi.WechatIntegralService.util.AppException;

public class WechatMedia {
	public static final String BOUNDARY="----------"+System.currentTimeMillis();;
	private File file;
	private String connection;
	private String charset;
	private String contentType;
	private String contentDisposition;
	private String contentLenght;
	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public String getContentLenght() {
		return contentLenght;
	}

	public void setContentLenght(String contentLenght) {
		this.contentLenght = contentLenght;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	public void initConnection(HttpURLConnection conn){
		try {
			conn.setRequestMethod("POST");
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			setConnection("Keep-Alive");
			conn.setRequestProperty("Connection", connection);
			setCharset("UTF-8");
			conn.setRequestProperty("Charset", charset);
			setContentType("multipart/form-data; boundary="+ BOUNDARY);
			conn.setRequestProperty("Content-Type",contentType);
		} catch (Exception e) {
			throw new AppException("设置链接异常",e);
		}
	}
	
	public  void postHead(HttpURLConnection conn){
		try{
			initConnection(conn);
			StringBuffer head=new StringBuffer();
			head.append("--");// 必须多两道线
			head.append(BOUNDARY);
			head.append("\r\n");
			setContentDisposition("form-data;name=\"file\";filename=\""+file.getName()+"\"\r\n");
			head.append("Content-Disposition: "+getContentDisposition());
			setContentType("application/octet-stream\r\n\r\n");
			head.append("Content-Type:"+contentType);
			conn.getOutputStream().write(head.toString().getBytes("utf-8"));
		}catch (Exception e) {
			throw new AppException("发送头部消息异常",e);
		}
	}
	
	public void postFile(HttpURLConnection conn){
		try {
			if(file==null){
				throw new AppException("被上传的文件不存在");
			}
			FileInputStream fin=new FileInputStream(file);
			OutputStream out=conn.getOutputStream();
			byte[] buff=new byte[1024];
			while(fin.read(buff)!=-1)
				out.write(buff);
			fin.close();
		} catch (Exception e) {
			throw new AppException("发送文件异常",e);
		}
	}
	
	public void postTail(HttpURLConnection conn){
		try{
			StringBuffer foot=new StringBuffer();
			foot.append("\r\n--" + BOUNDARY + "--\r\n");
			OutputStream out=conn.getOutputStream();
			out.write(foot.toString().getBytes("utf-8"));
			out.flush();
			out.close();
		}catch (Exception e) {
			throw new AppException("发送消息尾异常",e);
		}
	}
	
	public String readResponse(HttpURLConnection conn){
		try{
			StringBuffer buffer=new StringBuffer();
			BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String temp=null;
			while((temp=reader.readLine())!=null)
				buffer.append(temp);
			return buffer.toString();
		}catch (Exception e) {
			throw new AppException("读取响应消息异常",e);
		}
	}
	
	public String uploadMedia(HttpURLConnection conn){
		postHead(conn);
		postFile(conn);
		postTail(conn);
		return readResponse(conn);
	}
	public WechatMedia downloadMedia(HttpURLConnection conn){
		try{
			setConnection(conn.getHeaderField("Connection"));
			setContentType(conn.getHeaderField("Content-Type"));
			setContentDisposition(conn.getHeaderField("Content-disposition"));
			setContentLenght(conn.getHeaderField("Content-Length"));
			InputStream in=conn.getInputStream();
			String fileName=contentDisposition.substring(contentDisposition.indexOf('"')+1,contentDisposition.lastIndexOf('"'));
			file=new File(fileName);
			FileOutputStream fout=new FileOutputStream(file);
			byte[] buffer=new byte[1024];
			int temp=-1;
			while((temp=in.read(buffer))!=-1)
				fout.write(buffer);
			in.close();
			fout.close();
			return this;
		}catch (Exception e) {
			throw new AppException("读取响应异常",e);
		}
	}
}
