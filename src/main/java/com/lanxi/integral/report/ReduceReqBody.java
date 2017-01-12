package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分扣除-请求消息体
 * @author 1
 *
 */
public class ReduceReqBody extends Body{
	/**账户类型-帐号*/
	public static final String CUST_ID_TYPE_ACCOUNT="0";
	/**账户类型-卡号*/
	public static final String CUST_ID_TYPE_CART="1";
	/**xml节点名称*/
	public static final String NAME="body";
	/**帐号类型 0-账户号 1-卡号*/
	private String idType;		
	/**号码*/
	private String idNo;	
	/**扣除积分值*/
	private String reducePoints;
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
	
	public String getReducePoints() {
		return reducePoints;
	}
	public void setReducePoints(String reducePoints) {
		this.reducePoints = reducePoints;
	}
	@Override
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("idType").setText(idType);
		element.addElement("idNo").setText(idNo);
		element.addElement("reducePoints").setText(reducePoints);
		return element;
	}
	@Override
	public Body fromElement(Element element){
		if(element.getName().trim().equals(NAME)){
			setIdType(element.elementText("idType"));
			setIdNo(element.elementText("idNo"));
			setReducePoints(element.elementText("reducePoints"));
		}
		return this;
	}
}
