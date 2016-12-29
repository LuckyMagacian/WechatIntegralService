package com.lanxi.wechat.entity.menuevent;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-微信事件类-微信菜单事件类-点击菜单拍照发送
 * @author 1
 *
 */
public class SystemCameraMenuEvent extends WechatMenuEvent {
	/**
	 * 微信消息类-微信事件类-微信菜单事件类-点击菜单拍照发送类-内部类-图片
	 * @author 1
	 *
	 */
	public static class Item{
		private String picMd5Sum;/**图片md5摘要*/
		public String getPicMd5Sum() {
			return picMd5Sum;
		}


		public void setPicMd5Sum(String picMd5Sum) {
			this.picMd5Sum = picMd5Sum;
		}


		public DOMElement toElement(){
			DOMElement element=new DOMElement("Item");
			element.addElement("PicMd5Sum").addCDATA(picMd5Sum);
			return element;
		}
		public void fromElement(Element element){
			setPicMd5Sum(element.elementText("PicMd5Sum"));
		}
	}
	/**
	 * 微信消息类-微信事件类-微信菜单事件类-点击菜单拍照发送类-图片列表
	 * @author 1
	 *
	 */
	public static class PicList{
		private List<Item> picList;
		
		public PicList() {
			picList=new ArrayList<>();
		}
		
		public List<Item> getPicList() {
			return picList;
		}

		public void setPicList(List<Item> picList) {
			this.picList = picList;
		}
		public DOMElement toElement(){
			DOMElement element=new DOMElement("PicList");
			for(Item each:picList)
				element.add(each.toElement());
			return element;
		}
		public void fromElement(Element element){
			for(Object each:element.elements()){
				Item one=new Item();
				one.fromElement((Element) each);
				picList.add(one);
			}
		}
	}
	/**
	 * 微信消息类-微信事件类-微信菜单事件类-点击菜单拍照发送类-内部类-发送图片信息
	 * @author 1
	 *
	 */
	public static class SendPicsInfo{
		private String  count;
		private PicList picList;
		public SendPicsInfo() {
			picList=new PicList();
		}
		public String getCount() {
			return count;
		}
		public void setCount(String count) {
			this.count = count;
		}
		public PicList getPicList() {
			return picList;
		}
		public void setPicList(PicList picList) {
			this.picList = picList;
		}
		public DOMElement toElement(){
			DOMElement element=new DOMElement("SendPicsInfo");
			element.element("Count").setText(count);
			element.add(picList.toElement());
			return element;
		}
		public void fromElement(Element element){
			setCount(element.elementText("Count"));
			picList.fromElement(element.element("PicList"));
		}
	}
	
	
	private SendPicsInfo sendPicsInfo;
	public SystemCameraMenuEvent() {
		setMsgType(MENU_EVENT_PIC_SYSPHOTO);
		sendPicsInfo=new SendPicsInfo();
	}
	
	public SendPicsInfo getSendPicsInfo() {
		return sendPicsInfo;
	}
	public void setSendPicsInfo(SendPicsInfo sendPicsInfo) {
		this.sendPicsInfo = sendPicsInfo;
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.add(sendPicsInfo.toElement());
		return element;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			sendPicsInfo.fromElement(root.element("SendPicsInfo"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
