package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

public class ModifyPhoneResBody extends Body {

	@Override
	public Element toElement() {
		return new DOMElement(NAME);
	}

	@Override
	public Body fromElement(Element element) {
		return this;
	}

}
