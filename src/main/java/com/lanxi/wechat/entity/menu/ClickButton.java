package com.lanxi.wechat.entity.menu;

public class ClickButton extends BaseButton {
	private String key;
	
	public ClickButton() {
		setType(BUTTON_TYPE_CLICK);
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
