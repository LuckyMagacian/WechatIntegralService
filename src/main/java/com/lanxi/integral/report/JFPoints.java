package com.lanxi.integral.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.FileUtil;
import com.lanxi.httpsclient.core.ESignature;
/**
 * 报文内容
 * @author 1
 *
 */
public class JFPoints {
	/**xml节点名称*/
	public static final String NAME="points";
	/**通讯信息*/
	private Original original;	
	/**签名*/
	private String   sign;
	static{
		try{
			Properties properties=new Properties();
			File  file=FileUtil.getFileOppositeClassPath("client.properties");
			FileInputStream fin=new FileInputStream(file);
			properties.load(fin);
			fin.close();
			properties.setProperty("cafile",FileUtil.getFileOppositeClassPath("jks/client_1.jks").getAbsolutePath());
			FileOutputStream fou=new FileOutputStream(file);
			properties.store(fou,null);
			fou.close();
		}catch (Exception e) {
			throw new AppException("设置LanxiClient配置文件异常",e);
		}
	}
	public Original getOriginal() {
		return original;
	}
	public void setOriginal(Original original) {
		this.original = original;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String sign(){
		setSign(ESignature.sign(original.toElement().asXML(), ConfigUtil.get("charset")));
		return sign;
	}
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.add(original.toElement());
		element.addElement("sign").setText(sign);
		return element;
	}
	public static JFPoints fromElement(Element element){
		JFPoints points=null;
		if(element!=null&&element.getName().trim().equals(NAME)){
			points=new JFPoints();
			points.setOriginal(Original.fromElement(element.element(Original.NAME)));
			points.setSign(element.elementText("sign"));
		}
		return points;
	}
}
