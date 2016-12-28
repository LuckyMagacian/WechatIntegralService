package com.lanxi.wechat.entity.menu;

public class ViewButton extends BaseButton {
	private String url;
	
	public ViewButton() {
		setType(BUTTON_TYPE_VIEW);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	
}
