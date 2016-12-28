package com.lanxi.wechat.entity.token;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import javax.swing.Timer;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.FileUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.WechatIntegralService.util.TimeUtil;

/**
 * 微信AccessToken类
 * @author 1
 *
 */
public class AccessToken {
	private String grantType;		/**授权类型-获取accesstooken时填写client_credential*/
	private String appid;			/**开发者id*/
	private String secret;			/**开发者密钥*/
	private String accessToken;		/**获取到的凭证串*/
	private String expiresIn;		/**有效时间*/
	private String generatorTime;	/**生成时间*/
	private String overTime;		/**过期时间*/
	private Timer  timer;			/**定时器*/
	public AccessToken(){
		grantType="client_credential";
		appid=ConfigUtil.get("appID");
		secret=ConfigUtil.get("appSecret");
	}
	public String getGrantType() {
		return grantType;
	}
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getSecret() {
		return secret;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getOverTime() {
		return overTime;
	}
	public void setOverTime(String overTime) {
		this.overTime = overTime;
	}
	
	public String getGeneratorTime() {
		return generatorTime;
	}
	public void setGeneratorTime(String generatorTime) {
		this.generatorTime = generatorTime;
	}
	@Override
	public String toString() {
		return "AccessToken [grantType=" + grantType + ", appid=" + appid + ", secret=" + secret + ", accessToken="
				+ accessToken + ", expiresIn=" + expiresIn + ", generatorTime=" + generatorTime + ", overTime="
				+ overTime + "]";
	}	
	public void generatorToken(){
		if(timer!=null)
			timer.stop();
		StringBuffer url=new StringBuffer(ConfigUtil.get("tokenGetUrl"));
		url.append("?").append("grant_type=").append(grantType).append("&");
		url.append("appid=").append(appid).append("&");
		url.append("secret=").append(secret);
		String rs=HttpUtil.get(url.toString(), "utf-8");
		JSONObject jobj=JSONObject.parseObject(rs);
		if(jobj.containsKey("access_token")){
			setGeneratorTime(System.currentTimeMillis()+"");
			setExpiresIn(jobj.getString("expires_in"));
			setAccessToken(jobj.getString("access_token"));
			setOverTime(Long.parseLong(generatorTime)+Long.parseLong(expiresIn)*1000+"");
		}
		timer=new Timer((int) (Integer.parseInt(expiresIn)*1000*0.95),new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				generatorToken();
				System.out.println("generator new Token : "+TimeUtil.getDateTime());
				System.out.println(this.toString());
			}
		});
		timer.start();
	}
	
	public void saveToken(){
		try{
			File file=FileUtil.getFileOppositeClassPath("/properties/token.properties");
			if(!file.exists())
				file.createNewFile();
			FileOutputStream fout=new FileOutputStream(file);
			Properties properties=new Properties();
			properties.setProperty("grantType", grantType);
			properties.setProperty("accessToken", accessToken);
			properties.setProperty("appid", appid);
			properties.setProperty("expiresIn", expiresIn);
			properties.setProperty("generatorTime", generatorTime);
			properties.setProperty("overTime", overTime);
			properties.setProperty("secret", secret);
			properties.store(fout, null);
		}catch (Exception e) {
			throw new AppException("保存token异常",e);
		}
	}
	public static AccessToken loadToken(){
		try{
			File file=FileUtil.getFileOppositeClassPath("/properties/token.properties");
			Properties properties=new Properties();
			properties.load(new FileInputStream(file));
			System.out.println("load token :"+properties);
			AccessToken token=new AccessToken();
			token.setAccessToken(properties.getProperty("accessToken"));
			token.setAppid(properties.getProperty("appid"));
			token.setExpiresIn(properties.getProperty("expiresIn"));
			token.setGeneratorTime(properties.getProperty("generatorTime"));
			token.setGrantType(properties.getProperty("grantType"));
			token.setOverTime(properties.getProperty("overTime"));
			token.setSecret(properties.getProperty("secret"));
			return token;
		}catch (Exception e) {
			throw new AppException("加载token异常",e);
		}
	}
}
