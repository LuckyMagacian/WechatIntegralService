package com.lanxi.gift.report;

import org.dom4j.dom.DOMElement;

/**
 * 消息头接口
 * @author 1
 *
 */
public interface Head {
	/**消息头xml节点名称*/
	public static final String NAME="HEAD";
	/**
	 * 将head对象转为XMl元素形式
	 * @return
	 */
	public DOMElement toElement();
	/**
	 * 获取签名
	 * @return
	 */
	public String getSign();
	/**
	 * 设置签名
	 * @param sign
	 */
	public void   setSign(String sign);
//	public Head fromElement(Element element);
}
