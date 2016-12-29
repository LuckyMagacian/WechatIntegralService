package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-发送拍照和图片按键
 * @author 1
 *
 */
public class SystemCameraAndPhotos extends BaseButton {
	private String key;/**键值*/
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
