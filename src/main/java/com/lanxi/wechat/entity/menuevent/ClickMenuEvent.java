package com.lanxi.wechat.entity.menuevent;
/**
 * 微信消息类-微信事件类-微信菜单事件类-点击菜单下拉消息
 * @author 1
 *
 */
public class ClickMenuEvent extends WechatMenuEvent {
	public ClickMenuEvent() {
		setMsgType(MENU_EVENT_CLICK);
	}
}
