package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.GiftOrder;

public interface GiftOrderDao {
	public void addGiftOrder(GiftOrder giftorder);
	public void addGiftOrderDefault(GiftOrder giftorder);
	public void deleteGiftOrder(GiftOrder giftorder);
	public void updateGiftOrder(GiftOrder giftorder);
	public List<GiftOrder> selectGiftOrder(GiftOrder giftorder);
	
}
