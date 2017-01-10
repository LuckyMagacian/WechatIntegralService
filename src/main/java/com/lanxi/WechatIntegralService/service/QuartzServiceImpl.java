package com.lanxi.WechatIntegralService.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.IntegralRedPacket;
import com.lanxi.WechatIntegralService.util.TimeUtil;
import com.lanxi.integral.report.ReturnMessage;
import com.lanxi.integral.service.IntegralService;
@Service("quartzService")
public class QuartzServiceImpl implements QuartzService{
	@Resource
	private DaoService dao;
	private static Logger logger=Logger.getLogger(QuartzServiceImpl.class);
	@Override
	public void redPacketOverTime() {
		List<IntegralRedPacket> redPackets=dao.getNormlRedPacket();
		redPackets.addAll(dao.getNoneRedPacket());
		for(IntegralRedPacket each:redPackets){
			if(each.getOverTime().compareTo(TimeUtil.getDateTime())<1){
				logger.info("红包过期:"+each);
				each.setRedPacketStatus(IntegralRedPacket.RED_PACKET_STATUS_OVERTIME);
				if(IntegralRedPacket.RED_PACKET_STATUS_NORML.equals(each.getRedPacketStatus())){
					Integer returnIntegral=each.getLessIntegeral();
					logger.info("红包过期返还积分:"+returnIntegral);
					String  userId=each.getOpenId();
					AccountBinding account=dao.getAccount(userId);
					ReturnMessage returnMessage=IntegralService.addIntegral(account.getIntegralAccount(),returnIntegral+"");
					logger.info("积分返还结果:"+returnMessage);
					if(!returnMessage.getRetCode().trim().equals("0000")){
						returnMessage.setRetMsg("积分增加失败!");
						returnMessage.setObj("积分增加失败!");
						logger.info("积分增加失败!");
					}
				}
				dao.updateIntegralRedPacket(each);
				logger.info("更新红包信息!");
			}
		}
	}

}
