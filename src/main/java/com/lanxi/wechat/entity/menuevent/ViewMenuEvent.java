package com.lanxi.wechat.entity.menuevent;

import com.lanxi.wechat.entity.event.WechatEventMessage;

public class ViewMenuEvent extends WechatEventMessage {
	public ViewMenuEvent() {
		setMsgType(MENU_EVENT_VIEW);
	}
}
