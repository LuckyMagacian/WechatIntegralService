package com.lanxi.wechat.entity.event;

public class SubscribeEvent extends WechatEventMessage {
	public SubscribeEvent() {
		setEvent(EVENT_SUBSCRIBE);
	}
}
