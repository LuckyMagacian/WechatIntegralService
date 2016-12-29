package com.lanxi.wechat.entity.event;

import org.dom4j.DocumentHelper;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.wechat.entity.message.BaseMessage;
import com.lanxi.wechat.entity.message.WechatMessage;
/**
 * 微信消息类-事件消息类
 * @author 1
 *
 */
public abstract class WechatEventMessage extends BaseMessage{
	/**事件消息类型-用户关注*/
	public static final String EVENT_SUBSCRIBE="subscribe";
	/**事件消息类型-用户取消关注*/
	public static final String EVENT_UNSUBSCRIBE="unsubscribe";
	/**事件消息类型-扫描带参数的二维码*/
	public static final String EVENT_SCAN="SCAN";
	/**事件消息类型-上报地理位置*/
	public static final String EVENT_LOCATION="LOCATION";
	/**事件消息类型-点击菜单拉取消息*/
	public static final String EVENT_CLICK="CLICK";
	/**事件消息类型-点击菜单跳转链接*/
	public static final String EVENT_VIEW="VIEW";
	
	/**菜单事件消息类型-点击菜单拉取消息*/
	public static final String MENU_EVENT_CLICK="click";
	/**菜单事件消息类型-点击菜单跳转链接*/
	public static final String MENU_EVENT_VIEW="view";
	/**菜单事件消息类型-扫码推事件*/
	public static final String MENU_EVENT_SCANCODE_PUSH="scancode_push";
	/**菜单事件消息类型-扫码推事件且弹出“消息接收中”*/
	public static final String MENU_EVENT_SCANCODE_WAITMSG="scancode_waitmsg";
	/**菜单事件消息类型-弹出系统拍照发图*/
	public static final String MENU_EVENT_PIC_SYSPHOTO="pic_sysphoto";
	/**菜单事件消息类型-弹出拍照或者相册发图*/
	public static final String MENU_EVENT_PIC_PHOTO_OR_ALBUM="pic_photo_or_album";
	/**菜单事件消息类型-弹出微信相册发图器*/
	public static final String MENU_EVENT_PIC_WEIXIN="pic_weixin";
	/**菜单事件消息类型-弹出地理位置选择器*/
	public static final String MENU_EVENT_LOCATION_SELECT="location_select";

	private String event;	/**事件类型*/
	public WechatEventMessage() {
		setMsgType(WechatMessage.MESSAGE_TYPE_EVENT);
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public  DOMElement    toElement(){
		DOMElement element=super.toElement();
		element.addElement("Event").addCDATA(event);
		return element;
	}
	public void fromString(String str){
		try{
			super.fromString(str);
			setEvent(DocumentHelper.parseText(str).getRootElement().elementText("Event"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
}
