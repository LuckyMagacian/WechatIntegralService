package com.lanxi.gift.report;

import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.TimeUtil;

/**
 * 请求消息头
 * @author 1
 *
 */

public class ReqHead implements Head{
	private String 	ver;		/**消息版本 默认1.0*/
	private String 	msgNo;		/**消息编号-请求类型*/
	private String  chkDate;	/**清空日期 一般为交易日期*/
	private String  workDate;	/**交易日期 */
	private String  workTime;	/**交易时间*/
	private String 	add;		/**地址*/
	private String 	src;		/**交易发起方 蓝喜1000000000000000*/
	private String	des;		/**交易接收放 默认蓝喜1000000000000000*/
	private String  app;		/**应用平台 默认 蓝喜电子礼品营销平台*/
	private String	msgId;		/**消息id*/
	private String  reserve;	/**备注*/
	private String  sign;		/**签名 参数值拼接加上密钥 结果为小写*/
	public ReqHead(){
		ver="1.0";
		chkDate=TimeUtil.getDate();
		workDate=TimeUtil.getDate();
		workTime=TimeUtil.getTime();
		add="浙江省杭州市";
		src="1000000000000000";
		des="1000000000000000";
		app="蓝喜电子礼品营销平台";
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getMsgNo() {
		return msgNo;
	}
	public void setMsgNo(String msgNo) {
		this.msgNo = msgNo;
	}
	public String getChkDate() {
		return chkDate;
	}
	public void setChkDate(String chkDate) {
		this.chkDate = chkDate;
	}
	public String getWorkDate() {
		return workDate;
	}
	public void setWorkDate(String workDate) {
		this.workDate = workDate;
	}
	public String getWorkTime() {
		return workTime;
	}
	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}
	public String getAdd() {
		return add;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	@Override
	public String toString() {
		return "ReqHeadBean [ver=" + ver + ", msgNo=" + msgNo + ", chkDate=" + chkDate + ", workDate=" + workDate
				+ ", workTime=" + workTime + ", add=" + add + ", src=" + src + ", des=" + des + ", app=" + app
				+ ", msgId=" + msgId + ", reserve=" + reserve + ", sign=" + sign + "]";
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=new DOMElement(NAME);
		element.addElement("VER").setText(ver==null?"":ver);
		element.addElement("MsgNo").setText(msgNo==null?"":msgNo);
		element.addElement("CHKDate").setText(chkDate==null?"":chkDate);
		element.addElement("WorkDate").setText(workDate==null?"":workDate);
		element.addElement("WorkTime").setText(workTime==null?"":workTime);
		element.addElement("ADD").setText(add==null?"":add);
		element.addElement("SRC").setText(src==null?"":src);
		element.addElement("DES").setText(des==null?"":des);
		element.addElement("APP").setText(app==null?"":app);
		element.addElement("MsgID").setText(msgId==null?"":msgId);
		element.addElement("Reserve").setText(reserve==null?"":reserve);
		element.addElement("Sign").setText(sign==null?"":sign);
		return element;
	}
	public static Head fromElement(Element element) {
		Head head=null;
		if(element!=null&&element.getName().equals(NAME)){
			head=new ReqHead();
			ReqHead reqHead=(ReqHead)head;
			reqHead.setVer		(element.element("VER").getText());
			reqHead.setMsgNo	(element.element("MsgNo").getText());
			reqHead.setChkDate	(element.element("CHKDate").getText());
			reqHead.setWorkDate	(element.element("WorkDate").getText());
			reqHead.setWorkTime	(element.element("WorkTime").getText());
			reqHead.setAdd		(element.element("ADD").getText());
			reqHead.setSrc		(element.element("SRC").getText());
			reqHead.setDes		(element.element("DES").getText());
			reqHead.setApp		(element.element("APP").getText());
			reqHead.setMsgId	(element.element("MsgID").getText());
			reqHead.setReserve	(element.element("Reserve").getText());
			reqHead.setSign		(element.element("Sign").getText());
		}
		return head;
	}
}
//public class ReqHeadBean implements Head{
//	private String 	VER;		/**消息版本 默认1.0*/
//	private String 	MsgNo;		/**消息编号*/
//	private String  CHKDate;	/**清空日期 一般为交易日期*/
//	private String  WorkDate;	/**交易日期 */
//	private String  WorkTime;	/**交易时间*/
//	private String 	ADD;		/**地址*/
//	private String 	SRC;		/**交易发起方 蓝喜1000000000000000*/
//	private String	DES;		/**交易接收放 默认蓝喜1000000000000000*/
//	private String  APP;		/**应用平台 默认 蓝喜电子礼品营销平台*/
//	private String	MsgID;		/**消息id*/
//	private String  Reserve;	/**备注*/
//	private String  Sign;		/**签名 参数值拼接加上密钥 结果为小写*/
//	
//	public String getVER() {
//		return VER;
//	}
//
//	public void setVER(String vER) {
//		VER = vER;
//	}
//
//	public String getMsgNo() {
//		return MsgNo;
//	}
//
//	public void setMsgNo(String msgNo) {
//		MsgNo = msgNo;
//	}
//
//	public String getCHKDate() {
//		return CHKDate;
//	}
//
//	public void setCHKDate(String cHKDate) {
//		CHKDate = cHKDate;
//	}
//
//	public String getWorkDate() {
//		return WorkDate;
//	}
//
//	public void setWorkDate(String workDate) {
//		WorkDate = workDate;
//	}
//
//	public String getWorkTime() {
//		return WorkTime;
//	}
//
//	public void setWorkTime(String workTime) {
//		WorkTime = workTime;
//	}
//
//	public String getADD() {
//		return ADD;
//	}
//
//	public void setADD(String aDD) {
//		ADD = aDD;
//	}
//
//	public String getSRC() {
//		return SRC;
//	}
//
//	public void setSRC(String sRC) {
//		SRC = sRC;
//	}
//
//	public String getDES() {
//		return DES;
//	}
//
//	public void setDES(String dES) {
//		DES = dES;
//	}
//
//	public String getAPP() {
//		return APP;
//	}
//
//	public void setAPP(String aPP) {
//		APP = aPP;
//	}
//
//	public String getMsgID() {
//		return MsgID;
//	}
//
//	public void setMsgID(String msgID) {
//		MsgID = msgID;
//	}
//
//	public String getReserve() {
//		return Reserve;
//	}
//
//	public void setReserve(String reserve) {
//		Reserve = reserve;
//	}
//
//	public String getSign() {
//		return Sign;
//	}
//
//	public void setSign(String sign) {
//		Sign = sign;
//	}
//
//	@Override
//	public String toString() {
//		return "ReqHeadBean [VER=" + VER + ", MsgNo=" + MsgNo + ", CHKDate=" + CHKDate + ", WorkDate=" + WorkDate
//				+ ", WorkTime=" + WorkTime + ", ADD=" + ADD + ", SRC=" + SRC + ", DES=" + DES + ", APP=" + APP
//				+ ", MsgID=" + MsgID + ", Reserve=" + Reserve + ", Sign=" + Sign + "]";
//	}
	
	
	
//}
