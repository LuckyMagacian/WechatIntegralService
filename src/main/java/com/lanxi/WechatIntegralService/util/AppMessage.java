package com.lanxi.WechatIntegralService.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 应用响应信息�?
 * 用于与前端交�?
 * @author 1
 */
public class AppMessage {
	private String errCode;		/**错误代码 0000->成功*/
	private String errMsg;		/**错误信息*/
	private Object content;		/**回复内容*/
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return "AppMessage [errCode=" + errCode + ", errMsg=" + errMsg + ", content=" + content + "]";
	}
	public String toJson(){
		return JSONObject.toJSONString(this);
	}
}
