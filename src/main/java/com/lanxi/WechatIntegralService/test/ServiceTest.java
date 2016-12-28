package com.lanxi.WechatIntegralService.test;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.SignUtil;
import com.lanxi.WechatIntegralService.wechat.Arithmetics;
import com.mysql.jdbc.jdbc2.optional.SuspendableXAConnection;
@Service
public class ServiceTest {
	
	public String validService(HttpServletRequest req,HttpServletResponse res){
		try {
			req.setCharacterEncoding(ConfigUtil.get("charset"));
			String signature=req.getParameter("signature");
			String timestamp=req.getParameter("timestamp");
			String nonce	=req.getParameter("nonce");
			String echostr	=req.getParameter("echostr");
			System.out.println("signature:"+signature);
			System.out.println("timestamp:"+signature);
			System.out.println("nonce	 :"+nonce);
			System.out.println("echostr	 :"+echostr);
			List<String> list=new ArrayList<>();
			list.add(timestamp);
			list.add(nonce);
			list.add(ConfigUtil.get("firstToken"));
			System.out.println(list);
			Collections.sort(list);
			System.out.println(list);
			StringBuffer temp=new StringBuffer();
			for(String each:list)
				temp.append(each);
			String sign=Arithmetics.sign(temp.toString());
			System.out.println("sign:"+sign);
			if(sign!=null&&signature!=null&&sign.equals(signature))
				return echostr;
			return null;
		} catch (UnsupportedEncodingException e) {
			throw new AppException("测试微信服务器异常",e);
		}
	}
}
