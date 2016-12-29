package com.lanxi.wechat.entity.material;

import java.net.HttpURLConnection;
import java.net.URL;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
/**
 * 微信素材类-临时素材
 * @author 1
 *
 */
public class TempMaterial extends WechatMaterial{
	private String accessToken;		/**授权凭证*/
	private String type;			/**素材类型*/
	private WechatMedia media;		/**素材文件*/

	private String createdAt;		/**上传时间*/
	public TempMaterial() {
		media=new WechatMedia();
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public WechatMedia getMedia() {
		return media;
	}
	public void setMedia(WechatMedia media) {
		this.media = media;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	/**
	 * 上传临时素材
	 * @return
	 */
	public String upload(){
		try{
			String rs=null;
			String urlStr=ConfigUtil.get("tempMaterialUploadUrl");
			urlStr.replace("ACCESS_TOKEN",accessToken);
			urlStr.replace("TYPE",type);
			HttpURLConnection conn=(HttpURLConnection) new URL(urlStr).openConnection();
			rs=media.uploadMedia(conn);
			JSONObject jobj=JSONObject.parseObject(rs);
			setMediaId(jobj.getString("media_id"));
			setCreatedAt(jobj.getString("created_at"));
			return rs;
		}catch (Exception e) {
			throw new AppException("上传临时素材异常");
		}
	}
	public TempMaterial getTempMaterial(String mediaId){
		if(mediaId==null)
			return null;
		try{
			String urlStr=ConfigUtil.get("tempMaterialGetUrl");
			urlStr.replace("ACCESS_TOKEN",accessToken);
			urlStr.replace("media_id",mediaId);
			HttpURLConnection conn=(HttpURLConnection) new URL(urlStr).openConnection();
			media.downloadMedia(conn);
			return this;
		}catch (Exception e) {
			throw new AppException("获取临时素材异常",e);
		}
	}
}
