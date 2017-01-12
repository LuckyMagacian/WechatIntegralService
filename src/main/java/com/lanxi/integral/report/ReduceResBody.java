package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分扣除-响应消息体
 * @author 1
 *
 */
public class ReduceResBody extends Body {
	/**平台流水号*/
	private String serialNo;	
	/**扣除的积分值*/
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
	public String toString() {
		return "ResBody [serialNo=" + serialNo + ", points=" + points + "]";
	}
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("serialNo").setText(serialNo);
		element.addElement("points").setText(points);
		return element;
	}
	public Body fromElement(Element element){
		if(element.getName().trim().equals(NAME)){
			setSerialNo(element.elementText("serialNo"));
			setPoints(element.elementText("points"));
		}
		return this;
	}
}
