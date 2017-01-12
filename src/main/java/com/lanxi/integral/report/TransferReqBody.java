package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分转赠-请求消息体
 * @author 1
 *
 */
public class TransferReqBody extends Body {
	/**帐号类型*/
	private String idType;
	/**帐号*/
	private String idNo;
	/**接收方帐号类型*/
	private String toIdType;
	/**接收方帐号*/
	private String toIdNo;
	/**赠送积分值*/
	private String transferPoints;
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
	public String getToIdType() {
		return toIdType;
	}
	public void setToIdType(String toIdType) {
		this.toIdType = toIdType;
	}
	public String getToIdNo() {
		return toIdNo;
	}
	public void setToIdNo(String toIdNo) {
		this.toIdNo = toIdNo;
	}
	public String getTransferPoints() {
		return transferPoints;
	}
	public void setTransferPoints(String transferPoints) {
		this.transferPoints = transferPoints;
	}
	@Override
	public Element toElement() {
		DOMElement element=new DOMElement(NAME);
		element.addElement("idType").setText(idType);
		element.addElement("idNo").setText(idNo);
		element.addElement("toIdType").setText(toIdType);
		element.addElement("toIdNo").setText(toIdNo);
		element.addElement("transferPoints").setText(transferPoints);
		return element;
	}
	@Override
	public Body fromElement(Element element) {
		setToIdNo(element.elementText("idType"));
		setIdType(element.elementText("idNo"));
		setIdType(element.elementText("toIdType"));
		setToIdType(element.elementText("toIdNo"));
		setTransferPoints(element.elementText("transferPoints"));
		return this;
	}
	
}
