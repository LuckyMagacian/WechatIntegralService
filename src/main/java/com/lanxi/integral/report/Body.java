package com.lanxi.integral.report;

import org.dom4j.Element;
/**
 * 报文体接口
 * @author 1
 *
 */
public abstract class Body {
	/**报文体xml名称*/
	public static final String NAME="body";
	/**账户类型-帐号*/
	public static final String CUST_ID_TYPE_ACCOUNT="0";
	/**账户类型-卡号*/
	public static final String CUST_ID_TYPE_CART="1";
	/**
	 * 将报文体转为Element
	 * @return
	 */
	public abstract Element toElement();
	/**
	 * 根据报文元素设置body
	 * @param element
	 * @return
	 */
	public abstract Body fromElement(Element element);
	
	public String toString(){
		return toElement().asXML();
	}
}
