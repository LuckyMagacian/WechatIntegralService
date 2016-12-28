package com.lanxi.wechat.entity.menu;

public class SystemCameraButton extends BaseButton {
	private String key;
	public SystemCameraButton() {
		setType(BUTTON_TYPE_PIC_SYSPHOTO);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
