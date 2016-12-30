package com.lanxi.wechat.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.wechat.entity.token.WebAccessToken;
import com.lanxi.wechat.manageer.TokenManager;

public class WebTokenService {
	/**
	 * 获取网页token
	 * @param req
	 * @param res
	 */
	public void getWebToken(HttpServletRequest req, HttpServletResponse res){
		try {
			req.setCharacterEncoding("utf-8");
			res.setCharacterEncoding("utf-8");
			String code=req.getParameter("code");
			WebAccessToken token=TokenManager.generatorWebAccessTokenMetadata(code);
		} catch (Exception e) {
			throw new AppException("获取code异常", e);
		}

	}
}
