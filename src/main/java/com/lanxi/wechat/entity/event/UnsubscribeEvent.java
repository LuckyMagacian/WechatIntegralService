package com.lanxi.wechat.entity.event;
/**
 * 微信消息类-事件消息类-用户取消关注
 * @author 1
 *
 */
public class UnsubscribeEvent extends WechatEventMessage {
	public UnsubscribeEvent() {
		setEvent(EVENT_UNSUBSCRIBE);
	}
}
