package com.lanxi.wechat.entity.event;
/**
 * 微信消息类-事件消息类-用户关注消息类
 * @author 1
 *
 */
public class SubscribeEvent extends WechatEventMessage {
	public SubscribeEvent() {
		setEvent(EVENT_SUBSCRIBE);
	}
}
