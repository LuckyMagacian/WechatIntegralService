package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-扫描二维码按键
 * @author 1
 *
 */
public class ScanPushButton extends BaseButton{
	/**键值*/
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
