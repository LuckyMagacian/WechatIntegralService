package com.lanxi.gift.report;

import org.dom4j.dom.DOMElement;

/**
 * 业务信息接口
 * @author 1
 *
 */
public interface Msg {
	public static final String NAME="MSG";
	public DOMElement toElement();
//	public Msg fromElement(Element element);
}
