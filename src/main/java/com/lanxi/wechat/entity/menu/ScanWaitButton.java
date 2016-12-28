package com.lanxi.wechat.entity.menu;

public class ScanWaitButton extends BaseButton{
	private String key;
	public ScanWaitButton() {
		setType(BUTTON_TYPE_SCANCODE_WAITMSG);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	
}
