package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-点击下拉消息按键
 * @author 1
 *
 */
public class ClickButton extends BaseButton {
	/**按键key值*/
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
