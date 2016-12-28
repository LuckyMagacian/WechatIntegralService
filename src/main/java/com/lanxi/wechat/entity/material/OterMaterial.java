package com.lanxi.wechat.entity.material;

import java.net.HttpURLConnection;

public class OterMaterial extends ForeverMaterial {
	private String type;
	private String accessToken;
	private WechatMedia media;
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
