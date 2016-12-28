package com.lanxi.wechat.entity.menu;

public class WechatCameraButton extends BaseButton {
	private String key;
	public WechatCameraButton() {
		setType(BUTTON_TYPE_PIC_WEIXIN);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
