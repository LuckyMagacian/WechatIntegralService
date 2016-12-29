package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-微信拍照按键
 * @author 1
 *
 */
public class WechatCameraButton extends BaseButton {
	private String key;/**键值*/
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
