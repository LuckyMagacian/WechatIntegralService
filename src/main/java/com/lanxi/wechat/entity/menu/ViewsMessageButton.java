package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-获取图文消息按键
 * @author 1
 *
 */
public class ViewsMessageButton extends BaseButton {
	/**图文素材编号*/
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
