package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分查询-请求消息体
 * @author 1
 *
 */
public class QueryReqBody extends Body{
	/**帐号类型 0-账户号 1-卡号*/
	private String idType;		
	/**号码*/
	private String idNo;		
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
	@Override
	public String toString() {
		return "Body [idType=" + idType + ", idNo=" + idNo +"]";
	}
	@Override
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("idType").setText(idType);
		element.addElement("idNo").setText(idNo);
		return element;
	}
	public Body fromElement(Element element){
		if(element.getName().trim().equals(NAME)){
			setIdType(element.elementText("idType"));
			setIdNo(element.elementText("idNo"));
		}
		return this;
	}
}
