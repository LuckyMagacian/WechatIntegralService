package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.IntegralRedPacket;

public interface IntegralRedPacketDao {
	public void addIntegralRedPacket(IntegralRedPacket integralRedPacket);
	public void addIntegralRedPacketDefault(IntegralRedPacket integralRedPacket);
	public void deleteIntegralRedPacket(IntegralRedPacket integralRedPacket);
	public void updateIntegralRedPacket(IntegralRedPacket integralRedPacket);
	public List<IntegralRedPacket> selectIntegralRedPacket(IntegralRedPacket integralRedPacket);
}
