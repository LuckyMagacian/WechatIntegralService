package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 增加积分-请求消息体
 * @author 1
 *
 */
public class AddReqBody implements Body {
	/**编号类型*/
	private String idType;
	/**客户号/卡号*/
	private String idNo;
	/**增加积分值，正整数*/
	private String addPoints;
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getAddPoints() {
		return addPoints;
	}
	public void setAddPoints(String addPoints) {
		this.addPoints = addPoints;
	}
	@Override
	public Element toElement() {
		DOMElement element=new DOMElement(NAME);
		element.addElement("idType").setText(idType);
		element.addElement("idNo").setText(idNo);
		element.addElement("addPoints").setText(addPoints);
		return element;
	}
	@Override
	public Body fromElement(Element element) {
		setAddPoints(element.elementText("addPoints"));
		setIdNo(element.elementText("idNo"));
		setIdType(element.elementText("idType"));
		return this;
	}
	
	
}
