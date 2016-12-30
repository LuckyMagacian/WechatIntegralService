package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.Gift;

public interface GiftDao {
	public void addGift(Gift gift);
	public void addGiftDefault(Gift gift);
	public void deleteGift(Gift gift);
	public void updateGift(Gift gift);
	public List<Gift>selectGift(Gift gift);
	
}
