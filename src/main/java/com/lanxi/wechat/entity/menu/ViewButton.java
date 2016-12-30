package com.lanxi.wechat.entity.menu;
/**
 * 微信菜单类-按键类-链接按键
 * @author 1
 *
 */
public class ViewButton extends BaseButton {
	/**链接url*/
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
