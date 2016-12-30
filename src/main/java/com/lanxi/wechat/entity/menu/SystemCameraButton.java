package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-系统拍照按键
 * @author 1
 *
 */
public class SystemCameraButton extends BaseButton {
	/**键值*/
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
