package com.lanxi.wechat.entity.message;

import java.lang.reflect.Field;
import java.util.Map;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.BeanUtil;
/**
 * 微信消息类
 * @author 1
 *
 */
public abstract class BaseMessage {
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
	
	/**微信自动回复消息类型-文本消息*/
	public static final String AUTO_MESSAGE_TYPE_TEXT		="text";
	/**微信自动回复消息类型-图片消息*/
	public static final String AUTO_MESSAGE_TYPE_IMAGE		="image";
	/**微信自动回复消息类型-语音消息*/
	public static final String AUTO_MESSAGE_TYPE_VOICE		="voice";
	/**微信自动回复消息类型-视频消息*/
	public static final String AUTO_MESSAGE_TYPE_VIDEO		="video";
	/**微信自动回复消息类型-音乐消息*/
	public static final String AUTO_MESSAGE_TYPE_MUSIC		="music";
	/**微信自动回复消息类型-图文消息*/
	public static final String AUTO_MESSAGE_TYPE_NEWS		="news";
	/**接收方微信号*/
	private String toUserName;
	/**发送方微信号*/
	private String fromUserName;
	/**消息创建时间*/
	private String createTime;
	/**消息类型*/
	private String msgType;
	public BaseMessage(){
		setCreateTime(System.currentTimeMillis()+"");
	}
	public String getToUserName() {
		return toUserName;
	}
	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}
	public String getFromUserName() {
		return fromUserName;
	}
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/**
	 * 将当前对象转为一个domElement并返回
	 * @return domElment
	 */
	public  DOMElement    toElement(){
		DOMElement element=new DOMElement("xml");
		element.addElement("ToUserName").addCDATA(toUserName);
		element.addElement("FromUserName").addCDATA(fromUserName);
		element.addElement("CreateTime").setText(createTime);
		element.addElement("MsgType").addCDATA(msgType);
		return element;
	}
	/**
	 * 用xml字符串中配置当前对象
	 * @param str xml字符串
	 */
	public void fromString(String str){
		try {
			Element root=DocumentHelper.parseText(str).getRootElement();
			setCreateTime(root.elementText("CreateTime"));
			setFromUserName(root.elementText("FromUserName"));
			setMsgType(root.elementText("CreateTime"));
			setToUserName(root.elementText("ToUserName"));
		} catch (Exception e) {
			throw new AppException("字符串转xml异常",e);
		}
	}
	/**
	 * 从父类消息中获取信息
	 * @param fatherObj
	 */
	public void fromSuper(Object superObj){
		try{
			System.out.println(this.getClass());
			System.out.println(superObj.getClass());
			if(!superObj.getClass().isAssignableFrom(this.getClass()))
				throw new AppException("连个类之间不存在继承关系");
			Map<String , Field> map=BeanUtil.getFieldsNoStatic(superObj.getClass());
			for(Map.Entry<String , Field> each:map.entrySet()){
				Field  field=each.getValue();
				field.setAccessible(true);
				field.set(this, field.get(superObj));
			}
		}catch (Exception e) {
			throw new AppException("从父类对象中获取属性异常",e);
		}
	}
	
}
