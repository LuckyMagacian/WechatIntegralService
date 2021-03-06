package com.lanxi.WechatIntegralService.util;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUtil {
	/** 默认编码字符�? */
	public static final String defEnCharset = "utf-8";
	/** 默认解码字符�? */
	public static final String defDeCharset = "utf-8";
	/** 默认超时时间 */
	public static final Integer defTimeout = 10000;

	/**
	 * 私有化构造方�? 避免被实例化
	 */
	private HttpUtil() {

	};
	/**
	 * 对输入的url进行url编码处理
	 * @param urlStr	输入的url
	 * @return	url编码后的url
	 */
	public static String urlEncode(String urlStr){
		try{
			return URLEncoder.encode(urlStr,"utf-8");
		}catch (Exception e) {
			throw new AppException("url编码异常",e);
		}
	}
	/**
	 * 发�?�请�?
	 * 
	 * @param content
	 *            请求内容
	 * @param outStream
	 *            输出�?
	 * @param charset
	 *            编码字符�? 默认utf-8
	 */
	private static void post(String content, OutputStream outStream, String charset) {
		try {
			charset = charset == null ? defEnCharset : charset;
			OutputStreamWriter writer;
			writer = new OutputStreamWriter(outStream, charset);
			PrintWriter printer = new PrintWriter(writer);
			printer.println(content);
			printer.close();
		} catch (Exception e) {
			throw new AppException("发�?�post请求异常", e);
		}
	}

	/**
	 * 接受请求
	 * 
	 * @param inStream
	 *            输入�?
	 * @param charset
	 *            解码字符�? 默认utf-8
	 * @return 请求内容
	 */
	private static String receive(InputStream inStream, String charset) {
		try {
			charset = charset == null ? defDeCharset : charset;
			InputStreamReader reader = new InputStreamReader(inStream, charset);
			BufferedReader buffReader = new BufferedReader(reader);
			String temp;
			StringBuffer reply = new StringBuffer();
			while ((temp = buffReader.readLine()) != null)
				reply.append(temp);
			buffReader.close();
			return reply.toString();
		} catch (Exception e) {
			throw new AppException("接收数据异常", e);
		}
	}

	/**
	 * 通过给定的url发�?�内�?,并返回接收方返回的内�?
	 *
	 * @param content
	 *            发�?�的内容
	 * @param Url
	 *            接收方地�?
	 * @param charset
	 *            编码解码字符�? 默认utf-8
	 * @param type
	 *            发�?�内容格�?
	 * @param timeout
	 *            超时时间
	 * @return 接收方返回的内容
	 */
	public static String post(String content, String Url, String charset, String type, Integer timeout) {
		try {
			charset = charset == null ? defEnCharset : charset;
			timeout = timeout == null ? defTimeout : timeout;
			URL url = new URL(Url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			// TODO 超时时间10分钟
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			if (type != null)
				conn.setRequestProperty("Content-Type", type + ";Charset=" + charset);
			conn.connect();
			post(content, conn.getOutputStream(), charset);
			if (conn.getResponseCode() == 200)
				return receive(conn.getInputStream(), charset);
		} catch (Exception e) {
			throw new AppException("发�?�post请求异常", e);
		}
		return null;
	}

	/**
	 * 通过给定的url发�?�内�?,并返回接收方返回的内�?
	 * 
	 * @param content
	 *            发�?�的内容
	 * @param Url
	 *            接收方地�?
	 * @param charset
	 *            编码解码字符�? 默认utf-8
	 * @param type
	 *            发�?�内容格�?
	 * @return 接收方返回的内容
	 */
	public static String post(String content, String Url, String charset, String type) {
		return post(content, Url, charset, type, null);
	}

	/**
	 * 通过给定的url发�?�内�?,并返回接收方返回的内�?
	 * 
	 * @param content
	 *            发�?�的内容
	 * @param res
	 *            Servlet的响�?
	 * @param charset
	 *            编码解码字符�? 默认utf-8
	 * @param type
	 *            发�?�内容格�?
	 * @return 接收方返回的内容
	 */
	public static String post(String content, HttpServletResponse res, String charset, String type) {
		try {
			charset = charset == null ? defEnCharset : charset;
			res.setCharacterEncoding(charset);
			res.setContentType(type + ";charset=" + charset);
			post(content, res.getOutputStream(), charset);
//			if (res.getStatus() == 200)
//				return "0";
		} catch (Exception e) {
			throw new AppException("发�?�xml文档异常", e);
		}
		return "1";
	}

	/**
	 * 发�?�字符串信息
	 * 
	 * @param str
	 *            字符串内�?
	 * @param url
	 *            接收方地�?
	 * @param charset
	 *            编码字符�? 默认 utf-8
	 * @param timeout
	 *            超时时间
	 * @return 接收方返回的内容
	 *
	 * @throws AppException
	 */
	public static String postStr(String str, String url, String charset, Integer timeout) {
		return post(str, url, charset, null, timeout);
	}

	/**
	 * 发�?�字符串信息
	 *
	 * @param str
	 *            字符串内�?
	 * @param url
	 *            接收方地�?
	 * @param charset
	 *            编码字符�? 默认 utf-8
	 * @return 接收方返回的内容
	 * @throws AppException
	 */
	public static String postStr(String str, String url, String charset) {
		return postStr(str, url, charset, null);
	}

	/**
	 * 发�?�字符串信息
	 * 
	 * @param str
	 *            字符串内�?
	 * @param res
	 *            servelet的响�?
	 * @param charset
	 *            编码字符�? 默认 utf-8
	 * @return 接收方返回的内容
	 * @throws AppException
	 */
	public static String postStr(String str, HttpServletResponse res, String charset) {
		return post(str, res, charset, "txt/html");
	}

	/**
	 * 发�?�xml文档数据
	 * 
	 * @param xml
	 *            xml字符�?
	 * @param url
	 *            接收方的地址
	 * @param charset
	 *            编码字符�? 默认utf-8
	 * @param timeout
	 *            超时时间
	 * @return
	 */
	public static String postXml(String xml, String url, String charset, Integer timeout) {
		return post(xml, url, charset, "txt/html", timeout);
	}

	/**
	 * 发�?�xml文档数据
	 *
	 * @param xml
	 *            xml字符�?
	 * @param url
	 *            接收方的地址
	 * @param charset
	 *            编码字符�? 默认utf-8
	 * @return
	 */
	public static String postXml(String xml, String url, String charset) {
		return postXml(xml, url, charset, null);
	}

	/**
	 * 发�?�xml文档数据
	 * 
	 * @param xml
	 *            xml字符�?
	 * @param res
	 *            servelet的响�?
	 * @param charset
	 *            编码字符�? 默认utf-8
	 * @return 0->发�?�成�? 1->发�?�失�?
	 */
	public static String postXml(String xml, HttpServletResponse res, String charset) {
		return post(xml, res, charset, "txt/html");
	}

	/**
	 * 发�?�键值对
	 * 
	 * @param params
	 *            键�?�对
	 * @param url
	 *            目标url
	 * @param charset
	 *            编解码字符集
	 * @return
	 */
	public static String postKeyValue(Map<String, String> params, String url, String charset) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(url);
			List<NameValuePair> keyValue = new ArrayList<>();
			for (Map.Entry<String, String> each : params.entrySet())
				keyValue.add(new BasicNameValuePair(each.getKey(), each.getValue()));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(keyValue);
			entity.setContentEncoding(charset);
			post.setEntity(entity);
			HttpResponse res = client.execute(post);

			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(res.getEntity().getContent(), charset));
			StringBuffer strBuff = new StringBuffer();
			String temp = null;
			while ((temp = buffReader.readLine()) != null)
				strBuff.append(temp);
			return strBuff.toString();

		} catch (Exception e) {
			throw new AppException("发�?�键值对异常", e);
		}
	}

	/**
	 * 发�?�get 请求
	 * 
	 * @param url
	 *            地址+直接参数跟随
	 * @param charset
	 *            字符�?
	 * @return
	 */
	public static String get(String url, String charset) {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet get = new HttpGet();
			get.setURI(new URI(url));
			HttpResponse res = client.execute(get);
			BufferedReader buffReader = new BufferedReader(
					new InputStreamReader(res.getEntity().getContent(), charset));
			StringBuffer strBuff = new StringBuffer();
			String temp = null;
			while ((temp = buffReader.readLine()) != null)
				strBuff.append(temp);
			return strBuff.toString();
		} catch (Exception e) {
			throw new AppException("发�?�get请求异常", e);
		}
	}

	/**
	 * 发�?�get请求
	 * 
	 * @param url
	 *            目标url
	 * @param param
	 *            参数map
	 * @param charset
	 *            字符�?
	 * @return
	 */
	public static String get(String url, Map<String, Object> param, String charset) {
		StringBuffer params = new StringBuffer("?");
		for (Map.Entry<String, Object> each : param.entrySet())
			params.append(each.getKey() + "=" + each.getValue() + "&");
		return get(url + params.substring(0, params.length()), charset);
	}
	/**
	 *
	 * 通过URL POST的方式获取JSON数据
	 * @param _url	接口地址
	 * @param _params	传递参数
	 * @param _charSet	字符编码
	 * @return jsonData
	 */
	public static final String getJsonByPost(String _url, Map<String, String> _params, String _charSet) {
		if (_charSet == null || _charSet.length() == 0){
			_charSet = "utf-8";
		}
		String jsonData = "";
		try {
			URL httpurl = new URL(_url);
			//打开连接
			HttpURLConnection conn = (HttpURLConnection) httpurl.openConnection();
			if(_params!=null){
				conn.setDoOutput(true);//设置连接允许发送数据
				conn.setDoInput(true);//设置连接允许接收数据
				conn.setRequestMethod("POST");
				// Post 请求不能使用缓存
//				conn.setUseCaches(false);
				PrintWriter out = new PrintWriter((new OutputStreamWriter(conn.getOutputStream(), _charSet)));
				int i = 0;
				//处理post中发送的参数
				Set<Map.Entry<String, String>> set = _params.entrySet();
				for (Map.Entry<String, String> entry:set){
					out.print(entry.getKey());
					out.print("=");
					out.print(entry.getValue());
					if (i!=set.size()-1){
						out.print("&");
					}
					i++;
				}
				out.flush();
				out.close();
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), _charSet));
			StringBuffer result = new StringBuffer();//存放结果
			String line;//存放读取单行拿到的数据
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			jsonData = result.toString();
			in.close();
		} catch (Exception e) {
			throw new RuntimeException("发送post请求IO出错,url:" + _url,e);
		}
		return jsonData ;
	}
}
