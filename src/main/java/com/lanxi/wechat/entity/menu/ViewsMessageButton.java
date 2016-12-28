package com.lanxi.wechat.entity.menu;

public class ViewsMessageButton extends BaseButton {
	private String mediaId;
	public ViewsMessageButton() {
		setType(BUTTON_TYPE_VIEW_LIMITED);
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
