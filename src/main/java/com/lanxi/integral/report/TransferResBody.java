package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分转赠-响应消息体
 * @author 1
 *
 */
public class TransferResBody implements Body {

	@Override
	public Element toElement() {
		return new DOMElement(NAME);
	}

	@Override
	public Body fromElement(Element element) {
		return this;
	}

}
