package com.lanxi.wechat.entity.seconds;

import java.io.InputStream;

public class Seconds {
	public static final String SECONDS_MAX_EXPIRESECONDS="604800";
	//------------------------------req---------------------------------
	private String expireSeconds;
	private String actionName;
	private String actionInfo;
	private String sceneId;
	private String sceneStr;
	//-------------------------------res--------------------------------
	private String ticket;
	private String url;
	public String getExpireSeconds() {
		return expireSeconds;
	}
	public void setExpireSeconds(String expireSeconds) {
		this.expireSeconds = expireSeconds;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionInfo() {
		return actionInfo;
	}
	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}
	public String getSceneId() {
		return sceneId;
	}
	public void setSceneId(String sceneId) {
		this.sceneId = sceneId;
	}
	public String getSceneStr() {
		return sceneStr;
	}
	public void setSceneStr(String sceneStr) {
		this.sceneStr = sceneStr;
	}
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String toJson(){
		String jsonStr=null;
		// TODO tojson
		return jsonStr;
	}
	public void requestSeconds(){
		// TODO make seconds
	}
	public InputStream getSecondsPicture(){
		InputStream in=null;
		// TODO get seconds picture
		return in;
	}
	public static String longUrlToShortUrl(String longUrl){
		// TODO longurl 2 short url
		String shortUrl=null;
		
		return shortUrl;
	}
}
