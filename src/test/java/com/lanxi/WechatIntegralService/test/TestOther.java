package com.lanxi.WechatIntegralService.test;

import java.util.Arrays;
import java.util.Date;

import com.lanxi.integral.report.ReturnMessage;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.dom.DOMElement;
import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.RandomUtil;
import com.lanxi.WechatIntegralService.util.TimeUtil;
import com.lanxi.gift.report.BaoWen;
import com.lanxi.gift.report.ReqHead;
import com.lanxi.gift.report.ReqMsg;
import com.lanxi.integral.service.IntegralService;
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
		String overTime="2017-03-18 23:59:59";
		Date date=TimeUtil.parse("yyyy-MM-dd HH:mm:ss",overTime);
		System.out.println(TimeUtil.formatDateTime(date));
	}
	@Test
	public void testKey() throws Exception{
		ReturnMessage returnMessage= null;
		try {
			returnMessage = IntegralService.historyIntegral("101330326199412256115","20161225");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("数据"+returnMessage.getObj());
	}
	@Test
	public void testHis() throws Exception{
		System.out.println(IntegralService.historyIntegral("101331082199311236217",""));
		System.err.println(IntegralService.transferIntegral("101331082199311236217","101330501199309081314", "8"));
		System.out.println(IntegralService.historyIntegral("101331082199311236217",""));
	}
	@Test
	public void testToken(){
        EasyToken.setPrivateKey(ConfigUtil.get("easyTokenKey"));
		String token="y8KcKMpmRnTstQMptik7TASEhmUSMEMsptULhoujs9dPKpIXBhHDKzOiLkrjEIZkIZKkPms6rR9kKetudBKhhyhwP3HAHelXD1nzM3ddoEP5OSbGKIA/l2/G9E83ru7N.0b6b45e2637fbf45dd0c24bfea635628";
		System.out.println(EasyToken.flipToken(token));
	}
	
	@Test
	public void test1(){
		ReqHead head=new ReqHead();
		head.setMsgId("1001");
		head.setWorkDate("1001");
		head.setWorkTime("1001");
		head.setChkDate("1001");
		ReqMsg  reqMsg=new ReqMsg();
		reqMsg.setPhone("15068610940");
		reqMsg.setSkuCode("1001");
		reqMsg.setCount("1");
		reqMsg.setType("10");
		BaoWen  baoWen=new BaoWen();
		baoWen.setHead(head);
		baoWen.setMsg(reqMsg);
		baoWen.sign();
		System.out.println(baoWen.getHead().getSign());
		System.out.println(baoWen.toDocument().asXML());
	}
}
