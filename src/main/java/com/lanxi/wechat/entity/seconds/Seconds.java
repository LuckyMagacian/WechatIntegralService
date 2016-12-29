package com.lanxi.wechat.entity.seconds;

import java.io.InputStream;
/**
 * 微信二维码类
 * @author 1
 *
 */
public class Seconds {
	/**临时二维码最大有效期*/
	public static final String SECONDS_MAX_EXPIRESECONDS="604800";
	/**临时二维码默认有效期*/
	public static final String SECONDS_DEFAULT_EXPIRESECONDS="30";
	/**二维码类型-临时二维码*/
	public static final String SECONDS_ACTION_NAME_QRTYPE_TEMP="QR_SCENE";
	/**二维码类型-永久二维码*/
	public static final String SECONDS_ACTION_NAME_QRTYPE_FOREVER="QR_LIMIT_SCENE";
	/**二维码类型-永久字符串二维码*/
	public static final String SECONDS_ACTION_NAME_QRTYPE_FOREVER_STR="QR_LIMIT_STR_SCENE";

	//------------------------------req---------------------------------
	private String expireSeconds;	/**临时二维码请求有效期*/
	private String actionName;		/**二维码类型*/
	private String actionInfo;		/**二维码详细信息*/
	private String sceneId;			/**二维码场景编号1-100000*/
	private String sceneStr;		/**场景值id,1-64位,限永久二维码*/
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
