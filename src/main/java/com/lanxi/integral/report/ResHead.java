package com.lanxi.integral.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
/**
 * 积分系统-响应消息头
 * @author 1
 *
 */
public class ResHead extends ReqHead {
	
	public static String RETCODE_SUCCESS="0000";
	private String retCode;		/**返回码*/
	private String retMsg;		/**返回信息*/
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
	public String toString() {
		return "ResHead [retCode=" + retCode + ", retMsg=" + retMsg + "]";
	}
	public Element toElement(){
		DOMElement element=new DOMElement(NAME);
		element.addElement("businessId").setText(getBusinessId());
		element.addElement("reqDate").setText(getReqDate());
		element.addElement("orgId").setText(getOrgId());
		element.addElement("reserve").setText(getReserve()==null?"":getReserve());
		element.addElement("retCode").setText(retCode);
		element.addElement("retMsg").setText(retMsg);
		return element;
	}
	public static ResHead fromElement(Element element){
		ResHead head=null;
		if(element.getName().trim().equals(NAME)){
			head=new ResHead();
			head.setBusinessId(element.elementText("businessId"));
			head.setOrgId(element.elementText("orgId"));
			head.setReqDate(element.elementText("reqDate"));
			head.setReserve(element.elementText("reserve"));
			head.setRetCode(element.elementText("retCode"));
			head.setRetMsg(element.elementText("retMsg"));
		}
		return head;
	}
}
