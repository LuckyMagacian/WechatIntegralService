package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 冲正-响应消息头
 * @author 1
 *
 */
public class ReversalResBody extends Body {
	/**平台流水号*/
	private String serialNo;
	/**冲正积分值*/
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
		element.element("serialNo").setText(serialNo);
		element.element("points").setText(points);
		return element;
	}
	@Override
	public Body fromElement(Element element) {
		setSerialNo(element.elementText("serialNo"));
		setPoints(element.elementText("points"));
		return this;
	}
	
}
