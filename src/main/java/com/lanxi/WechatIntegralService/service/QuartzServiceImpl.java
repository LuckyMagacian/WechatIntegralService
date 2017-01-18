package com.lanxi.WechatIntegralService.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.ElectronicCoupon;
import com.lanxi.WechatIntegralService.entity.IntegralRedPacket;
import com.lanxi.WechatIntegralService.util.AppException;
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
		try {
			List<IntegralRedPacket> redPackets=dao.getNormlRedPacket();
			List<IntegralRedPacket> temp=dao.getNoneRedPacket();
			if(temp!=null&&!temp.isEmpty())
				redPackets.addAll(temp);
			if(redPackets!=null&&!redPackets.isEmpty())
			for(IntegralRedPacket each:redPackets){
				if(each.getOverTime().compareTo(TimeUtil.getDateTime())<1){
					logger.info("红包过期:"+each);
					if(IntegralRedPacket.RED_PACKET_STATUS_NORML.equals(each.getRedPacketStatus())){
						Integer returnIntegral=each.getLessIntegral();
						logger.info("红包过期返还积分:"+returnIntegral);
						String  userId=each.getOpenId();
						AccountBinding account=dao.getAccount(userId);
						ReturnMessage returnMessage;
							returnMessage = IntegralService.addIntegral(account.getIntegralAccount(),returnIntegral+"");
						logger.info("积分返还结果:"+returnMessage);
						if(!returnMessage.getRetCode().trim().equals("0000")){
							returnMessage.setRetMsg("积分增加失败!");
							returnMessage.setObj("积分增加失败!");
							logger.info("积分增加失败!");
						}
					}
					each.setRedPacketStatus(IntegralRedPacket.RED_PACKET_STATUS_OVERTIME);
					dao.updateIntegralRedPacket(each);
					logger.info("更新红包信息:"+each);
				}
			}
		} catch (Exception e) {
			throw new AppException("紅包過期異常！",e);
		}
	}
	
	
	public void updateStatusByTime(){
		dao.updateStatusByTime();
	}
	
	public void deleteCode(){
		dao.deleteCode();
	}


	@Override
	public void couponOverTime() {
		try {
			ElectronicCoupon coupon=new ElectronicCoupon();
			coupon.setStatus(ElectronicCoupon.ELECTRONIC_COUPON_STATUS_NORMAL);
			List<ElectronicCoupon> coupons=dao.selectElectronicCoupon(coupon);
			coupon.setStatus(ElectronicCoupon.ELECTRONIC_COUPON_STATUS_USED);
			List<ElectronicCoupon> temp=dao.selectElectronicCoupon(coupon);
			if(temp!=null&&!temp.isEmpty())
				coupons.addAll(temp);
			if(coupons!=null&&!coupons.isEmpty())
				for(ElectronicCoupon each:coupons){
					if(each.getOverTime().compareTo(TimeUtil.getDateTime())<1){
						logger.info("卡券过期:"+each);
						each.setStatus(ElectronicCoupon.ELECTRONIC_COUPON_STATUS_OVERTIME);
						dao.updateElectronicCoupon(each);
						logger.info("更新卡券信息:"+each);
					}
				}
		} catch (Exception e) {
			throw new AppException("卡券过期任务异常",e);
		}
	}

}
