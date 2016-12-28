package com.lanxi.wechat.entity.menu;

import com.alibaba.fastjson.JSONObject;

public abstract class BaseButton implements WechatButton {
	/**菜单按键类型-点击推*/
	public static final String BUTTON_TYPE_CLICK="click";
	/**菜单按键类型-跳转URL*/
	public static final String BUTTON_TYPE_VIEW="view";
	/**菜单按键类型-扫码推事件*/
	public static final String BUTTON_TYPE_SCANCODE_PUSH="scancode_push";
	/**菜单按键类型-扫码推事件且弹出“消息接收中”提示框*/
	public static final String BUTTON_TYPE_SCANCODE_WAITMSG="scancode_waitmsg";
	/**菜单按键类型-弹出系统拍照发图*/
	public static final String BUTTON_TYPE_PIC_SYSPHOTO="pic_sysphoto";
	/**菜单按键类型-弹出拍照或者相册发图*/
	public static final String BUTTON_TYPE_PIC_PHOTO_OR_ALBUM="pic_photo_or_album";
	/**菜单按键类型-弹出微信相册发图器*/
	public static final String BUTTON_TYPE_PIC_WEIXIN="pic_weixin";
	/**菜单按键类型-弹出地理位置选择器*/
	public static final String BUTTON_TYPE_LOCATION_SELECT="location_select";
	/**菜单按键类型-下发消息（除文本消息）*/
	public static final String BUTTON_TYPE_MEDIA_ID="media_id";
	/**菜单按键类型-跳转图文消息URL*/
	public static final String BUTTON_TYPE_VIEW_LIMITED="view_limited";

	private String type;
	private String name;
	public String getType() {
		return type;
	}
	protected void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "MenuInfo [type=" + type + ", name=" + name + "]";
	}
	public  String toJson(){
		return JSONObject.toJSONString(this);
	}
}
