package com.lanxi.gift.report;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.CheckReplaceUtil;

/**
 * 响应消息头
 * @author 1
 *
 */
public class ResHead implements Head{
	/**消息版本 默认1.0*/
	private String 	ver;		
	/**消息编号-请求类型*/
	private String 	msgNo;		
	/**清空日期 一般为交易日期*/
	private String  chkDate;	
	/**交易日期 */
	private String  workDate;	
	/**交易时间*/
	private String  workTime;	
	/**地址*/
	private String 	add;		
	/**交易发起方 蓝喜1000000000000000*/
	private String 	src;		
	/**交易接收放 默认蓝喜1000000000000000*/
	private String	des;		
	/**应用平台 默认 蓝喜电子礼品营销平台*/
	private String  app;		
	/**消息id*/
	private String	msgId;		
	/**备注*/
	private String  reserve;	
	/**签名 参数值拼接加上密钥 结果为小写*/
	private String  sign;		
	/**返回码 */
	private String 	resCode;	
	/**返回消息 */
	private String 	resMsg;		
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
	public String getResCode() {
		return resCode;
	}
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}
	public String getResMsg() {
		return resMsg;
	}
	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}
	@Override
	public String toString() {
		return "ResHeadBean [ver=" + ver + ", msgNo=" + msgNo + ", chkDate=" + chkDate + ", workDate=" + workDate
				+ ", workTime=" + workTime + ", add=" + add + ", src=" + src + ", des=" + des + ", app=" + app
				+ ", msgId=" + msgId + ", reserve=" + reserve + ", sign=" + sign + ", resCode=" + resCode + ", resMsg="
				+ resMsg + "]";
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=new DOMElement(CheckReplaceUtil.nullAsSpace(NAME));
		element.addElement("VER").setText(CheckReplaceUtil.nullAsSpace(ver));
		element.addElement("MsgNo").setText(CheckReplaceUtil.nullAsSpace(msgNo));
		element.addElement("CHKDate").setText(CheckReplaceUtil.nullAsSpace(chkDate));
		element.addElement("WorkDate").setText(CheckReplaceUtil.nullAsSpace(workDate));
		element.addElement("WorkTime").setText(CheckReplaceUtil.nullAsSpace(workTime));
		element.addElement("ADD").setText(CheckReplaceUtil.nullAsSpace(add));
		element.addElement("SRC").setText(CheckReplaceUtil.nullAsSpace(src));
		element.addElement("DES").setText(CheckReplaceUtil.nullAsSpace(des));
		element.addElement("APP").setText(CheckReplaceUtil.nullAsSpace(app));
		element.addElement("MsgID").setText(CheckReplaceUtil.nullAsSpace(msgId));
		element.addElement("Reserve").setText(CheckReplaceUtil.nullAsSpace(reserve));
		element.addElement("Sign").setText(CheckReplaceUtil.nullAsSpace(sign));
		element.addElement("ResCode").setText(CheckReplaceUtil.nullAsSpace(resCode));
		element.addElement("ResMsg").setText(resMsg);
		return element;
	}
//	@Override
	public static Head fromElement(Element element) {
		Head head=null;
		if(element!=null&&element.getName().equals(NAME)){
			head=new ResHead();
			ResHead resHead=(ResHead)head;
			resHead.setVer		(element.element("VER").getText());
			resHead.setMsgNo	(element.element("MsgNo").getText());
			resHead.setChkDate	(element.element("CHKDate").getText());
			resHead.setWorkDate	(element.element("WorkDate").getText());
			resHead.setWorkTime	(element.element("WorkTime").getText());
			resHead.setAdd		(element.element("ADD").getText());
			resHead.setSrc		(element.element("SRC").getText());
			resHead.setDes		(element.element("DES").getText());
			resHead.setApp		(element.element("APP").getText());
			resHead.setMsgId	(element.element("MsgID").getText());
			resHead.setReserve	(element.element("Reserve").getText());
			resHead.setSign		(element.element("Sign").getText());
			resHead.setResCode	(element.element("ResCode").getText());
			Element res=element.element("ResMsg");
			Element des=element.element("ResDesc");
			resHead.setResMsg	(res!=null?res.getText():des!=null?des.getText():"");
		}
		return head;
	}
	
}

//public class ResHeadBean implements Head{
//	private String 	VER;		/**消息版本 */
//	private String 	MsgNo;		/**消息编号 */
//	private String  CHKDate;	/**清空日期 */
//	private String  WorkDate;	/**交易日期 */
//	private String  WorkTime;	/**交易时间 */
//	private String 	ADD;		/**地址 */
//	private String 	SRC;		/**交易发起方 */
//	private String	DES;		/**交易接收放 */
//	private String  APP;		/**应用平台 */
//	private String	MsgID;		/**消息id */
//	private String  Reserve;	/**备注 */
//	private String  Sign;		/**签名 */
//	private String ResCode;		/**返回码 */
//	private String ResMsg;		/**返回消息 */
//	
//	/**
//	 * Head接口方法
//	 * 返回自身类对象
//	 * @return 自身类对象
//	 */
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
//	public String getResCode() {
//		return ResCode;
//	}
//
//	public void setResCode(String resCode) {
//		ResCode = resCode;
//	}
//
//	public String getResMsg() {
//		return ResMsg;
//	}
//	
//	public ReturnMsg getResMsgBean(){
//		ReturnMsg msg=new ReturnMsg();
//		msg.setResCode(getResCode());
//		msg.setResMsg(getResMsg());
//		return msg;
//	}
//	
//	public void setResMsg(String resMsg) {
//		ResMsg = resMsg;
//	}
//
//	public void setResMsg(ReturnMsg msg){
//		setResCode(msg.getResCode());
//		setResMsg(msg.getResMsg());
//	}
//	
//	@Override
//	public String toString() {
//		return "ResHeadBean [VER=" + VER + ", MsgNo=" + MsgNo + ", CHKDate=" + CHKDate + ", WorkDate=" + WorkDate
//				+ ", WorkTime=" + WorkTime + ", ADD=" + ADD + ", SRC=" + SRC + ", DES=" + DES + ", APP=" + APP
//				+ ", MsgID=" + MsgID + ", Reserve=" + Reserve + ", Sign=" + Sign + ", ResCode=" + ResCode + ", ResMsg="
//				+ ResMsg + "]";
//	}
//	
//	
//}
