package com.lanxi.wechat.entity.message;

import org.dom4j.DocumentHelper;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public abstract class WechatMessage extends BaseMessage{
	/**微信消息类型-文本消息*/
	public static final String MESSAGE_TYPE_TEXT		="text";
	/**微信消息类型-图片消息*/
	public static final String MESSAGE_TYPE_IMAGE		="image";
	/**微信消息类型-语音消息*/
	public static final String MESSAGE_TYPE_VOICE		="voice";
	/**微信消息类型-视频消息*/
	public static final String MESSAGE_TYPE_VIDEO		="video";
	/**微信消息类型-小视频消息*/
	public static final String MESSAGE_TYPE_SHORT_VIDEO	="shortvideo";
	/**微信消息类型-位置消息*/
	public static final String MESSAGE_TYPE_LOCATION	="location";
	/**微信消息类型-链接消息*/
	public static final String MESSAGE_TYPE_LINK		="link";
	/**微信消息类型-事件消息*/
	public static final String MESSAGE_TYPE_EVENT		="event";

	private String msgId;
	
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public DOMElement    toElement(){
		DOMElement element=super.toElement();
		element.addElement("MsgId").setText(msgId);
		return element;
	}
	public  void fromString(String str){
		try{
			super.fromString(str);
			setMsgId(DocumentHelper.parseText(str).getRootElement().elementText("MsgId"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
}
