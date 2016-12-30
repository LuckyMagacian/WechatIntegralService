package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-获取媒体消息按键
 * @author 1
 *
 */
public class MediaMessageButton extends BaseButton {
	/**媒体素材编号*/
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
