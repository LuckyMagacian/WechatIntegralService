package com.lanxi.wechat.entity.menu;

public class ScanPushButton extends BaseButton{
	private String key;
	public ScanPushButton() {
		setType(BUTTON_TYPE_SCANCODE_PUSH);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	
}
