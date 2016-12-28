package com.lanxi.wechat.entity.menu;

public class LocationSelectButton extends BaseButton {
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
