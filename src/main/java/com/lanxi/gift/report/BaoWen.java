package com.lanxi.gift.report;

import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.BeanUtil;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.SignUtil;

/**
 * 报文bean
 * @author 1
 *
 */
public class BaoWen {
	public static final String NAME="JFDH";
	private Head head;	/**消息头*/
	private Msg  msg;	/**业务信息*/
	public Head getHead() {
		return head;
	}
	public void setHead(Head head) {
		this.head = head;
	}
	public Msg getMsg() {
		return msg;
	}
	public void setMsg(Msg msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "BaoWen [head=" + head.toString() + ", msg=" + msg.toString() + "]";
	}
	public Document toDocument(){
		Document document=DocumentHelper.createDocument();
		document.setXMLEncoding(ConfigUtil.get("charset"));
		DOMElement root=new DOMElement("JFDH");
		if(head!=null)
			root.add(head.toElement());
		else
			root.addElement(Head.NAME);
		if(msg!=null)
			root.add(msg.toElement());
		else
			root.addElement(Msg.NAME);
		document.setRootElement(root);
		return document;
	}
	public static BaoWen fromDocument(Document document){
		BaoWen baoWen=null;
		if(document!=null&&document.getRootElement().getName().equals("JFDH")){
			Element root=document.getRootElement();
			baoWen=new BaoWen();
			if(root.selectSingleNode("//ResCode")==null){
				baoWen.setHead(ReqHead.fromElement(root.element(Head.NAME)));
				baoWen.setMsg(ReqMsg.fromElement(root.element(Msg.NAME)));
			}else{
				baoWen.setHead(ResHead.fromElement(root.element(Head.NAME)));
				if(root.selectSingleNode("//ResCode").getText().equals("0000")){
					baoWen.setMsg(ResMsg.fromElement(root.element(Msg.NAME)));
				}
			}
		}
		return baoWen;
	}
	public static BaoWen formDocumentStr(String documentStr){
		BaoWen baoWen=null;
		Document document=null;
		if(documentStr!=null){
			try {
				document=DocumentHelper.parseText(documentStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(document!=null&&document.getRootElement().getName().equals("JFDH")){
			Element root=document.getRootElement();
			baoWen=new BaoWen();
			if(root.selectSingleNode("//ResCode")==null){
				baoWen.setHead(ReqHead.fromElement(root.element(Head.NAME)));
				baoWen.setMsg(ReqMsg.fromElement(root.element(Msg.NAME)));
			}else{
				baoWen.setHead(ResHead.fromElement(root.element(Head.NAME)));
				if(root.selectSingleNode("//ResCode").getText().equals("0000")){
					baoWen.setMsg(ResMsg.fromElement(root.element(Msg.NAME)));
				}
			}
		}
		return baoWen;
	}
	
	public String sign(){
		Map<String, String> params=BeanUtil.getParamMap(head);
		params.putAll(BeanUtil.getParamMap(msg));
		params.remove("sign");
		String tempSign=SignUtil.md5LowerCase(SignUtil.mapToValueString(params)+ConfigUtil.get("giftKey"),ConfigUtil.get("charset"));
		head.setSign(tempSign);
		return head.getSign();
	}
}
