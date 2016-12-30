package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class AddResBody implements Body {
	/**平台流水号*/
	private String serialNo;
	/**增加的积分值*/
	private String points;
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
	@Override
	public Element toElement() {
		DOMElement element=new DOMElement(NAME);
		element.addElement("serialNo").setText(serialNo);
		element.addElement("points").setText(points);
		return element;
	}
	@Override
	public Body fromElement(Element element) {
		setPoints(element.elementText("points"));
		setSerialNo(element.elementText("serialNo"));
		return this;
	}
	
}
