package com.lanxi.wechat.entity.menu;

public class MediaMessageButton extends BaseButton {
	private String mediaId;
	public MediaMessageButton() {
		setType(BUTTON_TYPE_MEDIA_ID);
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

}
