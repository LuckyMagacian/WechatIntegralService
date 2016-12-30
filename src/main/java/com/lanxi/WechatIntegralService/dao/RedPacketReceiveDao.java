package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.RedPacketReceive;

public interface RedPacketReceiveDao {
	public void addRedPacketReceive(RedPacketReceive redPacketReceive);
	public void addRedPacketReceiveDefault(RedPacketReceive redPacketReceive);
	public void deleteRedPacketReceive(RedPacketReceive redPacketReceive);
	public void updateRedPacketReceive(RedPacketReceive redPacketReceive);
	public List<RedPacketReceive> selectRedPacketReceive(RedPacketReceive redPacketReceive);
	
}
