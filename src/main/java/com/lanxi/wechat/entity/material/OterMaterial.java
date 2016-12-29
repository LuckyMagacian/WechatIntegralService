package com.lanxi.wechat.entity.material;

import java.net.HttpURLConnection;
/**
 * 微信素材类-永久素材-其他素材
 * @author 1
 *
 */
public class OterMaterial extends ForeverMaterial {
	private String type;		/**素材类型*/
	private String accessToken;	/**授权凭证*/
	private WechatMedia media;	/**媒体文件*/
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public WechatMedia getMedia() {
		return media;
	}
	public void setMedia(WechatMedia media) {
		this.media = media;
	}
	public String upload(HttpURLConnection conn){
		return media.uploadMedia(conn);
	}
}
