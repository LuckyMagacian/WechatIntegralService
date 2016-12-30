package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分明细查询-响应消息体
 * @author 1
 *
 */
public class HistoryResBody implements Body{
	/**业务流水号*/
	private String businessId;
	/**请求时间，格式yyyymmddhhmmss*/
	private String reqDate;
	/**发起机构号*/
	private String orgId;
	/**备用字段*/
	private String reserve;
	/**返回码*/
	private String retCode;
	/**返回信息*/
	private String retMsg;
	public String getBusinessId() {
		return businessId;
	}
	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}
	public String getReqDate() {
		return reqDate;
	}
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	@Override
	public Element toElement() {
		DOMElement element=new DOMElement(NAME);
		element.addElement("businessId").setText(businessId);
		element.addElement("reqDate").setText(reqDate);
		element.addElement("orgId").setText(orgId);
		element.addElement("retCode").setText(retCode);
		element.addElement("retMsg").setText(retMsg);
		element.addElement("reserve").setText(reserve);
		return element;
	}
	@Override
	public Body fromElement(Element element) {
		setBusinessId(element.elementText("businessId"));
		setReqDate(element.elementText("reqDate"));
		setOrgId(element.elementText("orgId"));
		setReserve(element.elementText("reserve"));
		setRetCode(element.elementText("retCode"));
		setRetMsg(element.elementText("retMsg"));
		return this;
	}
}
