package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分系统-请求消息头
 * @author 1
 *
 */
public class ReqHead {
	public static final String NAME="head";
	/**业务流水号*/
	private String businessId;		
	/**时间按yyyyMMddHHmmss*/
	private String reqDate;			
	/**发起机构号*/
	private String orgId;			
	/**备用*/
	private String reserve;			
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
	@Override
	public String toString() {
		return "Head [businessId=" + businessId + ", reqDate=" + reqDate + ", orgId=" + orgId + ", reserve=" + reserve
				+ "]";
	}
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("businessId").setText(businessId);
		element.addElement("reqDate").setText(reqDate);
		element.addElement("orgId").setText(orgId);
		element.addElement("reserve").setText(reserve==null?"":reserve);
		return element;
	}
	public static ReqHead fromElement(Element element){
		ReqHead head=null;
		if(element.getName().trim().equals(NAME)){
			head=new ReqHead();
			head.setBusinessId(element.elementText("businessId"));
			head.setOrgId(element.elementText("orgId"));
			head.setReqDate(element.elementText("reqDate"));
			head.setReserve(element.elementText("reserve"));
		}
		return head;
	}
}
