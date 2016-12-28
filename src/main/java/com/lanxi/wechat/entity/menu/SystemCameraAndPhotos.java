package com.lanxi.wechat.entity.menu;

public class SystemCameraAndPhotos extends BaseButton {
	private String key;
	public SystemCameraAndPhotos() {
		setType(BUTTON_TYPE_PIC_PHOTO_OR_ALBUM);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
