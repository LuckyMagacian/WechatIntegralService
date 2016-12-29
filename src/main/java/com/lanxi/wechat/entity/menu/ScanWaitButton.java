package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-扫描二维码按键-显示正在扫描
 * @author 1
 *
 */
public class ScanWaitButton extends BaseButton{
	private String key;/**键值*/
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
