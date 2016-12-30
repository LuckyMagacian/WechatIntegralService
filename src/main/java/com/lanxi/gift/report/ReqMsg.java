package com.lanxi.gift.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.CheckReplaceUtil;

/**
 * 流量兑换业务请求信息
 * @author 1
 *
 */
public class ReqMsg implements Msg{
	private String phone="";     /**手机号码*/
	private String type="";      /**商品类型*/
	private String skuCode="";   /**商品编号*/
	private String count="";     /**商品数量*/
	private String needSend="";  /**是否蓝喜下发短信*/
	private String remark="";    /**备注*/
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSkuCode() {
		return skuCode;
	}
	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getNeedSend() {
		return needSend;
	}
	public void setNeedSend(String needSend) {
		this.needSend = needSend;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "ReqMsgBean [phone=" + phone + ", type=" + type + ", skuCode=" + skuCode + ", count=" + count
				+ ", needSend=" + needSend + ", remark=" + remark + "]";
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=new DOMElement(CheckReplaceUtil.nullAsSpace(NAME));
		element.addElement("Phone").setText(CheckReplaceUtil.nullAsSpace(phone));
		element.addElement("Type").setText(CheckReplaceUtil.nullAsSpace(type));
		element.addElement("SkuCode").setText(CheckReplaceUtil.nullAsSpace(skuCode));
		element.addElement("Count").setText(CheckReplaceUtil.nullAsSpace(count));
		element.addElement("NeedSend").setText(CheckReplaceUtil.nullAsSpace(needSend));
		element.addElement("Remark").setText(CheckReplaceUtil.nullAsSpace(remark));
		return element;
	}
//	@Override
	public static Msg fromElement(Element element) {
		Msg msg=null;
		if(element!=null&&element.getName().equals(NAME)){
			msg=new ReqMsg();
			ReqMsg reqMsg=(ReqMsg)msg;
			reqMsg.setPhone		(element.elementText("Phone"));
			reqMsg.setType		(element.elementText("Type"));
			reqMsg.setSkuCode	(element.elementText("SkuCode"));
			reqMsg.setCount		(element.elementText("Count"));
			reqMsg.setNeedSend	(element.elementText("NeedSend"));
			reqMsg.setRemark	(element.elementText("Remark"));
		}
		return msg;
	}
	
}
//public class ReqMsgBean implements Msg{
//	
//	private String Phone="";     /**手机号码*/
//	private String Type="";      /**商品类型*/
//	private String SkuCode="";   /**商品编号*/
//	private String Count="";     /**商品数量*/
//	private String NeedSend="";  /**是否蓝喜下发短信*/
//	private String Remark="";    /**备注*/
//	
//
//	public String getPhone() {
//		return Phone;
//	}
//
//	public void setPhone(String phone) {
//		Phone = phone;
//	}
//
//	public String getType() {
//		return Type;
//	}
//
//	public void setType(String type) {
//		Type = type;
//	}
//
//	public String getSkuCode() {
//		return SkuCode;
//	}
//
//	public void setSkuCode(String skuCode) {
//		SkuCode = skuCode;
//	}
//
//	public String getCount() {
//		return Count;
//	}
//
//	public void setCount(String count) {
//		Count = count;
//	}
//
//	public String getNeedSend() {
//		return NeedSend;
//	}
//
//	public void setNeedSend(String needSend) {
//		NeedSend = needSend;
//	}
//
//	public String getRemark() {
//		return Remark;
//	}
//
//	public void setRemark(String remark) {
//		Remark = remark;
//	}
//
//	@Override
//	public String toString() {
//		return "TrafficReqMsgBean [Phone=" + Phone + ", Type=" + Type + ", SkuCode=" + SkuCode + ", Count=" + Count
//				+ ", NeedSend=" + NeedSend + ", Remark=" + Remark + "]";
//	}
//	
//	
//}
