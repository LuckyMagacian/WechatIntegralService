package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分明细查询-请求消息体
 * @author 1
 *
 */
public class HistoryReqBody extends Body {
	/**请求时间，格式yyyymmddhhmmss*/
	private String startDate;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
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
		element.addElement("startDate").setText(startDate);
		return element;
	}
	public Body fromElement(Element element){
		if(element.getName().trim().equals(NAME)){
			setIdType(element.elementText("idType"));
			setIdNo(element.elementText("idNo"));
			setStartDate(element.elementText("startDate"));
		}
		return this;
	}

}
