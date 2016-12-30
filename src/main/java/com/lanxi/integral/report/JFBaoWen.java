package com.lanxi.integral.report;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
/**
 * 积分系统通讯报文
 * @author 1
 *
 */
public class JFBaoWen {
	/**报文根节点*/
	private JFPoints points;

	public JFPoints getPoints() {
		return points;
	}

	public void setPoints(JFPoints points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "JFBaoWen [points=" + points + "]";
	}
	/**
	 * 将报文对象转为xml文档形式
	 * @return
	 */
	public Document toDocument(){
		Document document=DocumentHelper.createDocument();
		document.setRootElement(points.toElement());
		document.setXMLEncoding(ConfigUtil.get("charset"));
		return document;
	}
//	/**
//	 * 用xml文档设置报文对象
//	 * @param document
//	 * @return
//	 */
//	public static JFBaoWen fromDocument(Document document){
//		JFBaoWen baoWen=null;
//		if(document.getRootElement().getName().trim().equals(JFPoints.NAME)){
//			baoWen=new JFBaoWen();
//			baoWen.setPoints(JFPoints.fromElement(document.getRootElement()));
//		}
//		return baoWen;
//	}
//	/**
//	 * 用xml字符串构造报文对象
//	 * @param docStr
//	 * @return
//	 */
//	public static JFBaoWen fromDocStr(String docStr){
//		try {
//			return fromDocument(DocumentHelper.parseText(docStr));
//		} catch (DocumentException e) {
//			throw new AppException("字符串转报文异常",e);
//		}
//	}
}

