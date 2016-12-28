package com.lanxi.WechatIntegralService.wechat;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.SignUtil;

public class Arithmetics {
	
	public static String sign(String content){
		try{
			byte[] bytes=SignUtil.sha1En(content.getBytes("utf-8"));
			StringBuffer sign=new StringBuffer();
			for(byte each:bytes){
				String hex=Integer.toHexString(each&0xff);
				sign.append(hex.length()<2?"0":"");
				sign.append(hex);
			}
			return sign.toString();
		}catch (Exception e) {
			throw new AppException("微信签名异常,",e);
		}
	}
}
