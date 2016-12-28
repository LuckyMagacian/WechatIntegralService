package com.lanxi.wechat.entity.menuevent;

public class ClickMenuEvent extends WechatMenuEvent {
	public ClickMenuEvent() {
		setMsgType(MENU_EVENT_CLICK);
	}
}
