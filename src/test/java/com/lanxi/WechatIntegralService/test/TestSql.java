package com.lanxi.WechatIntegralService.test;

import org.junit.Test;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.Game;
import com.lanxi.WechatIntegralService.entity.Gift;
import com.lanxi.WechatIntegralService.entity.GiftOrder;
import com.lanxi.WechatIntegralService.entity.IntegralGame;
import com.lanxi.WechatIntegralService.entity.IntegralRedPacket;
import com.lanxi.WechatIntegralService.entity.IntegralTransfer;
import com.lanxi.WechatIntegralService.entity.RedPacketReceive;
import com.lanxi.WechatIntegralService.entity.ValidCode;
import com.lanxi.WechatIntegralService.util.CheckReplaceUtil;
import com.lanxi.WechatIntegralService.util.SqlUtil;

public class TestSql {
	
	@Test
	public void test(){
		Class[] classes=new Class[]{IntegralRedPacket.class,RedPacketReceive.class};
		for(Class<?> each:classes)
			SqlUtil.createMapperFile(each, "wechat_integral_service_"+CheckReplaceUtil.upcaseToUnderlineLowcaser(each.getSimpleName()));
			//SqlUtil.createAll(each, "wechat_integral_service_"+CheckReplaceUtil.upcaseToUnderlineLowcaser(each.getSimpleName()));
	}
}
