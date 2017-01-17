package com.lanxi.WechatIntegralService.test;

import java.util.Arrays;
import java.util.Date;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.dom.DOMElement;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.RandomUtil;
import com.lanxi.token.EasyToken;
import com.lanxi.token.SignUtil;
import com.lanxi.wechat.entity.automessage.VoiceAutoMessage;
import com.lanxi.wechat.entity.message.BaseMessage;
import com.lanxi.wechat.entity.message.TextMessage;
import com.lanxi.wechat.entity.message.WechatMessage;
import com.lanxi.wechat.manageer.TokenManager;

public class TestOther {

	@Test
	public void testCDATA(){
		DOMElement ele=new DOMElement("ToUserName");
		ele.addCDATA("老司机");
		System.out.println(ele.asXML());
	} 
	@Test
	public void testParseElement() throws DocumentException{
		String xml="<ToUserName><![CDATA[老司机]]></ToUserName>";
		System.out.println(DocumentHelper.parseText(xml));
	}
	@Test
	public void testFromSuper(){
		BaseMessage message=new BaseMessage() {
		};
		message.setCreateTime(System.currentTimeMillis()+"");
		message.setFromUserName("老司机");
		message.setMsgType(BaseMessage.AUTO_MESSAGE_TYPE_VOICE);
		message.setToUserName("开火车");
		System.out.println(message);
		VoiceAutoMessage voiceMessage=new VoiceAutoMessage();
		voiceMessage.fromSuper(message);
		System.out.println(voiceMessage);
	}
	@Test
	public void testExtends(){
		System.out.println(WechatMessage.class.isAssignableFrom(BaseMessage.class));
		System.out.println(BaseMessage.class.isAssignableFrom(WechatMessage.class));
	}
	@Test
	public void testToFrom(){
		TextMessage text=new TextMessage();
		text.setContent("老司机发车了~");
		text.setCreateTime(System.currentTimeMillis()+"");
		text.setFromUserName("老司机");
		text.setMsgId("110");
		text.setToUserName("乘客");
		System.out.println(text.toElement().asXML());
		TextMessage message=new TextMessage();
		message.fromString(text.toElement().asXML());
		System.out.println(message.toElement().asXML());
	}
	@Test
	public void testJsonParse(){
		String str="{\"privilege\":[ \"PRIVILEGE1\",\"PRIVILEGE2\"     ]}";
		System.out.println(Arrays.asList(JSONObject.parseObject(str).getJSONArray("privilege").toArray()));
	}
	@Test
	public void testGetJsTicket(){
		System.out.println(TokenManager.getJsSign(null, null, null).toJson());
	}
	@Test
	public void testRR(){
		System.out.println(testReturn());
	}
	public String testReturn(){
		String str="110";
		try {
			if(str.equals("110"))
				return null;
			throw new AppException("");
		} catch (Exception e) {
			str="120";
		}
		finally {
			return str;
		}
	}
	
	@Test
	public void testEat(){
		String string="CC0wVOr04h4m7mj9NuuoLnc0UT5WcUoCTfSGyqnzM0d69lzGyzW7XbQq62TmGJ9+n+UsBU5JzesbeoDyS/9uwM/nCvk+4JL1tZpXSFK9mBsLst3CPMfCQ9lfsqjWxv6s.ada588a17157f56d50cb28aca9831798";
		System.out.println(EasyToken.flipToken(string));
		string="1485276806518";
		System.out.println(new Date(Long.parseLong(string)));
	}
	@Test
	public void testKey(){
		System.out.println(RandomUtil.getRandomChar(64));
	}
}
