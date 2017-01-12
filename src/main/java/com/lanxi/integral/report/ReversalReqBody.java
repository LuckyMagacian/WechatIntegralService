package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 冲正-请求消息体
 * @author 1
 *
 */
public class ReversalReqBody extends Body {
	/**平台流水号*/
	private String serialNo;

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Override
	public Element toElement() {
		DOMElement element=new DOMElement(NAME);
		element.element("serialNo").setText(serialNo);
		return element;
	}

	@Override
	public Body fromElement(Element element) {
		setSerialNo(element.elementText("serialNo"));
		return this;
	}
	
}
