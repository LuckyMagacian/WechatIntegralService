package com.lanxi.wechat.manageer;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.wechat.entity.token.AccessToken;
import com.lanxi.wechat.entity.token.JSSign;
import com.lanxi.wechat.entity.token.JSTicket;
import com.lanxi.wechat.entity.token.WebAccessToken;
import com.lanxi.wechat.entity.token.WebAccessTokenRequst;

/**
 * 微信凭证管理类
 * @author 1
 *
 */
public class TokenManager {
	/**开发授权凭证*/
	private static AccessToken accessToken;						

	/**网页授权请求*/
	private static WebAccessTokenRequst webAccessTokenRequst;	
	
	/**Js凭证*/
	private static JSTicket jsTicket;
	
	/**Js签名*/
	private static JSSign jsSign;
	
	/**网页凭证列表*/
	private static Map<String, WebAccessToken> webAccessTokens;	
	
	static{
		webAccessTokenRequst=new WebAccessTokenRequst();
		webAccessTokens=new HashMap<>();
		loadAccessToken();
	}
	
	private TokenManager(){
		
	}
	/**
	 * 获取所有网页凭证
	 * @return
	 */
	public static Map<String, WebAccessToken> getWebAccessTokens(){
		return webAccessTokens;
	}
	/**
	 * 生成网页凭证获取code
	 * @param redirectUrl	重定向url
	 * @param codeScope     凭证权限(WebAccessTokenRequst中的常量字段)
	 * @return
	 */
	public static String generatorWebTokenCodeUrl(String redirectUrl,String codeScope){
		if(redirectUrl!=null){	
			redirectUrl=redirectUrl.startsWith("http")?redirectUrl:"http://"+redirectUrl;
			webAccessTokenRequst.setRedirectUri(redirectUrl);
		}
		if(codeScope==null||!(codeScope.trim().equals(WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_BASE)||codeScope.trim().equals(WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_DETAIL)))
			codeScope=WebAccessTokenRequst.WEB_ACCESS_TOOKEN_SCOPE_BASE;	
		webAccessTokenRequst.setScope(codeScope);
		return webAccessTokenRequst.generatorCodeUrl();
	}
	/**
	 * 通过用户id获取网页凭证字符串
	 * @param userId
	 * @return
	 */
	public static WebAccessToken getWebAccessTokenMetadata(String userId){
		WebAccessToken token=webAccessTokens.get(userId);
		if(token==null)
			return null;
		if(token.getOverTime().compareTo(System.currentTimeMillis()+"")<1){
			if(token.getRefreshToken()==null)
				return null;
			if(token.getRefreshToken().getOverTime().compareTo(System.currentTimeMillis()+"")<1)
				return null;
			return token.refreshMetadata();
		}
		return token;
	}
	/**
	 * 通过用户id获取网页凭证字符串
	 * @param userId
	 * @return
	 */
	public static String getWebAccessToken(String userId){
//		System.err.println(getWebAccessTokenMetadata(userId));
		return getWebAccessTokenMetadata(userId).getAccessToken();
	}
	/**
	 * 通过code生成网页凭证并返回字符串信息
	 * @param code
	 * @return
	 */
	public static String generatorWebAccessToken(String code){
		return generatorWebAccessTokenMetadata(code).getAccessToken();
	}
	/**
	 * 通过code生成网页凭证并返回原型
	 * @param code
	 * @return
	 */
	public static WebAccessToken generatorWebAccessTokenMetadata(String code){
		String urlStr=ConfigUtil.get("webTokenGetUrl");
		urlStr=urlStr.replace("APPID",ConfigUtil.get("appID"));
		urlStr=urlStr.replace("SECRET",ConfigUtil.get("appSecret"));
		urlStr=urlStr.replace("CODE",code);
		String rs=HttpUtil.get(urlStr,"utf-8");
		JSONObject jobj=JSONObject.parseObject(rs);
		if(jobj.getString("errcode")!=null)
			return null;

		WebAccessToken token=new WebAccessToken();
		token.setAccessToken(jobj.getString("access_token"));
		token.setExpiresIn(jobj.getString("expires_in"));
		token.setOpenId(jobj.getString("openid"));
		token.setScope(jobj.getString("scope"));
		token.setGeneratorTime(System.currentTimeMillis()+"");
		token.setExpiresIn(jobj.getString("expires_in"));
		token.setOverTime(Long.parseLong(token.getGeneratorTime())+Long.parseLong(token.getExpiresIn())*1000+"");
		token.getRefreshToken().setRefreshToken(jobj.getString("refresh_token"));
		token.getRefreshToken().setGeneratorTime(token.getGeneratorTime());
		token.getRefreshToken().setExpiresIn(30*24*60*60+"");
		token.getRefreshToken().setOverTime(Long.parseLong(token.getGeneratorTime())+30*24*60*60*1000+"");
		webAccessTokens.put(token.getOpenId(), token);
		return token;
	}
	/**
	 * 获取开发授权凭证字符串
	 * @return
	 */
	public static String getAccessToken(){
		return getAccessTokenMetadata().getAccessToken();
	}
	/**
	 * 获取开发授权凭证字符串
	 * @return
	 */
	public static AccessToken getAccessTokenMetadata(){
		if(accessToken==null){
			accessToken=new AccessToken();
			accessToken.generatorToken();
		}
		if(accessToken.getOverTime().compareTo(System.currentTimeMillis()+"")<1){
			System.out.println("token over time !");
			accessToken=new AccessToken();
			accessToken.generatorToken();
			// TODO  删除保存token
			saveAccessToken();
		}
		return accessToken;
	}
	/**
	 * 保存授权凭证到资源目录
	 */
	public static void saveAccessToken(){
		if(accessToken==null){
			accessToken=new AccessToken();
			accessToken.generatorToken();
		}
		accessToken.saveToken();
	}
	/**
	 * 从资源目录加载凭证
	 * @return
	 */
	public static String loadAccessToken(){
		accessToken=AccessToken.loadToken();
		return accessToken.getAccessToken();
	}
	
	/**
	 * 获取jsTicket元数据
	 * @return
	 */
	public static JSTicket getJsTicketMetaData(){
		if(jsTicket==null){
			jsTicket=new JSTicket();
			jsTicket.generatorTicket();
		}
		if(jsTicket.getOverTime().compareTo(System.currentTimeMillis()+"")<1){
			jsTicket.generatorTicket();
		}
		return jsTicket;
	}
	/**
	 * 获取jsTicket
	 * @return
	 */
	public static String getJsTicket(){
		return getJsTicketMetaData().getTicket();
	}
	/**
	 * jsTicket签名
	 * @param nonceStr	随机字符串
	 * @param timestamp	时间戳
	 * @param url 		链接
	 * @return
	 */
	public static JSSign getJsSign(String nonceStr,String timestamp,String url){
		if(jsSign==null){
			jsSign=new JSSign();
		}
		jsSign.setNonce(nonceStr);
		jsSign.setTimeStamp(timestamp);
		jsSign.setUrl(url);
		jsSign.setTicket(getJsTicketMetaData());
		jsSign.generatorSign();
		return jsSign;
	}
}
