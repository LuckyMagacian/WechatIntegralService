package com.lanxi.wechat.entity.menuevent;

import com.lanxi.wechat.entity.event.WechatEventMessage;
/**
 * 微信消息类-微信事件类-微信菜单事件类-点击菜单访问链接
 * @author 1
 *
 */
public class ViewMenuEvent extends WechatEventMessage {
	public ViewMenuEvent() {
		setMsgType(MENU_EVENT_VIEW);
	}
}
