package com.lanxi.wechat.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.SignUtil;
/**
 * 微信服务器验证服务类
 * @author 1
 *
 */
@Service
public class ValidServerService {
	private static Logger logger=Logger.getLogger(ValidServerService.class);
	/**
	 * 校验服务器
	 * @param req
	 * @param res
	 * @return
	 */
	public String validService(HttpServletRequest req,HttpServletResponse res){
		try {
			req.setCharacterEncoding(ConfigUtil.get("charset"));
			String signature=req.getParameter("signature");
			String timestamp=req.getParameter("timestamp");
			String nonce	=req.getParameter("nonce");
			String echostr	=req.getParameter("echostr");
			logger.info("signature:"+signature);
			logger.info("timestamp:"+timestamp);
			logger.info("nonce	 :"+nonce);
			logger.info("echostr	 :"+echostr);
			List<String> list=new ArrayList<>();
			list.add(timestamp);
			list.add(nonce);
			list.add(ConfigUtil.get("firstToken"));
			logger.info(list);
			Collections.sort(list);
			logger.info(list);
			StringBuffer temp=new StringBuffer();
			for(String each:list)
				temp.append(each);
			String sign=sign(temp.toString());
			logger.info("sign:"+sign);
			if(sign!=null&&signature!=null&&sign.equals(signature))
				return echostr;
			return null;
		} catch (UnsupportedEncodingException e) {
			throw new AppException("测试微信服务器异常",e);
		}
	}
	/**
	 * 服务器校验签名算法
	 * @param content
	 * @return
	 */
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
