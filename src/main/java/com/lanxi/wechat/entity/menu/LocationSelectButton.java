package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-上报地理位置按键
 * @author 1
 *
 */
public class LocationSelectButton extends BaseButton {
	/**按键key值*/
	private String key;	
	public LocationSelectButton() {
		setType(BUTTON_TYPE_LOCATION_SELECT);
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

}
