package com.lanxi.wechat.entity.event;

public class UnsubscribeEvent extends WechatEventMessage {
	public UnsubscribeEvent() {
		setEvent(EVENT_UNSUBSCRIBE);
	}
}
