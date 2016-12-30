package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class ModifyPhoneReqBody implements Body {
	/**编号类型*/
	private String 	idType;
	/**客户号/卡号*/
	private String 	idNo;
	/**新手机号*/
	private String	phoneNo;
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
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public Element toElement() {
		DOMElement element=new DOMElement(NAME);
		element.addElement("idType").setText(idType);
		element.addElement("idNo").setText(idNo);
		element.addElement("phoneNo").setText(phoneNo);
		return element;
	}
	@Override
	public Body fromElement(Element element) {
		setPhoneNo(element.elementText("phoneNo"));
		setIdNo(element.elementText("idNo"));
		setIdType(element.elementText("idType"));
		return this;
	}
}
