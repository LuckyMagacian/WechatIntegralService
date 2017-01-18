package com.lanxi.WechatIntegralService.service;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.ElectronicCoupon;
import com.lanxi.WechatIntegralService.entity.Game;
import com.lanxi.WechatIntegralService.entity.Gift;
import com.lanxi.WechatIntegralService.entity.GiftOrder;
import com.lanxi.WechatIntegralService.entity.IntegralGame;
import com.lanxi.WechatIntegralService.entity.IntegralRedPacket;
import com.lanxi.WechatIntegralService.entity.IntegralTransfer;
import com.lanxi.WechatIntegralService.entity.RedPacketReceive;
import com.lanxi.WechatIntegralService.entity.ValidCode;

public interface DaoService {
	// TODO----------------------------account---------------------------------------------------
	public void addAccountBinding(AccountBinding accountBinding);
	public void addAccountBindingDefault(AccountBinding accountBinding);
	public void deleteAccountBinding(AccountBinding accountBinding);
	public void updateAccountBinding(AccountBinding accountBinding);
	public List<AccountBinding> selectAccountBinding(AccountBinding accountBinding);
	public AccountBinding getAccount(String wechatId);
	// TODO----------------------------game---------------------------------------------------
	public void addGame(Game game);
	public void addGameDefault(Game game);
	public void deleteGame(Game game);
	public void updateGame(Game game);
	public List<Game>selectGame(Game game);
	public Game getGame(String gameId);
	// TODO----------------------------gift---------------------------------------------------
	public void addGift(Gift gift);
	public void addGiftDefault(Gift gift);
	public void deleteGift(Gift gift);
	public void updateGift(Gift gift);
	public List<Gift>selectGift(Gift gift);
	public List<Gift>getGifts(String gameId);
	public Gift getGift(String gameId,Integer level);
	public Gift getGift(String giftId);
	// TODO----------------------------integralGame---------------------------------------------------
	public void addIntegralGame(IntegralGame integralGame);
	public void addIntegralGameDefault(IntegralGame integralGame);
	public void deleteIntegralGame(IntegralGame integralGame);
	public void updateIntegralGame(IntegralGame integralGame);
	public List<IntegralGame> selectIntegralGame(IntegralGame integralGame);
	// TODO----------------------------integralRedPacket---------------------------------------------------
	public void addIntegralRedPacket(IntegralRedPacket integralRedPacket);
	public void addIntegralRedPacketDefault(IntegralRedPacket integralRedPacket);
	public void deleteIntegralRedPacket(IntegralRedPacket integralRedPacket);
	public void updateIntegralRedPacket(IntegralRedPacket integralRedPacket);
	public List<IntegralRedPacket> selectIntegralRedPacket(IntegralRedPacket integralRedPacket);
	public IntegralRedPacket getRedPacket(String redPacketId);
	public List<IntegralRedPacket> getRedPacketsByStatus(String status);
	public List<IntegralRedPacket> getNormlRedPacket();
	public List<IntegralRedPacket> getNoneRedPacket();
	public List<IntegralRedPacket> getOverTimeRedPacket();
	// TODO----------------------------integralTransfer---------------------------------------------------
	public void addIntegralTransfer(IntegralTransfer integralTransfer);
	public void addIntegralTransferDefault(IntegralTransfer integralTransfer);
	public void deleteIntegralTransfer(IntegralTransfer integralTransfer);
	public void updateIntegralTransfer(IntegralTransfer integralTransfer);
	public List<IntegralTransfer> selectIntegralTransfer(IntegralTransfer integralTransfer);
	// TODO----------------------------redPacketReceive---------------------------------------------------
	public void addRedPacketReceive(RedPacketReceive redPacketReceive);
	public void addRedPacketReceiveDefault(RedPacketReceive redPacketReceive);
	public void deleteRedPacketReceive(RedPacketReceive redPacketReceive);
	public void updateRedPacketReceive(RedPacketReceive redPacketReceive);
	public List<RedPacketReceive> selectRedPacketReceive(RedPacketReceive redPacketReceive);
	public RedPacketReceive getReceiveRecord(String redPacketId,String wechatId);
	public List<RedPacketReceive> getReceives(String redPacketId);
	//TODO-----------------------------validCode------------------------------------------------------------
	public void addValidCode(ValidCode validCode);
	public void addValidCodeDefault(ValidCode validCode);
	public void deleteValidCode(ValidCode validCode);
	public void updateValidCode(ValidCode validCode);
	public List<ValidCode> selectValidCode(ValidCode validCode);
	public ValidCode getValidCode(String phone);
	void insert(ValidCode validCode);
	String getCodeByPhone(String phone);
	void updateStatusByPhone(String phone);
	String getStatusByPhone(String phone);
	void updateStatusByTime();
	void deleteCode();
	/**
	 * 查询数据库中是否有该手机号
	 */
	int getCountByPhone(String phone);
	/**
	 * 修改验证码
	 */
	void updateCode(ValidCode validCode);
	//TODO----------------------------giftOrder------------------------------------------------------
	public void addGiftOrder(GiftOrder giftorder);
	public void addGiftOrderDefault(GiftOrder giftorder);
	public void deleteGiftOrder(GiftOrder giftorder);
	public void updateGiftOrder(GiftOrder giftorder);
	public List<GiftOrder> selectGiftOrder(GiftOrder giftorder);
	//TODO----------------------------electronicCoupon------------------------------------------------------
	public void addElectronicCoupon(ElectronicCoupon electronicCoupon);
	public void addElectronicCouponDefault(ElectronicCoupon electronicCoupon);
	public void deleteElectronicCoupon(ElectronicCoupon electronicCoupon);
	public void updateElectronicCoupon(ElectronicCoupon electronicCoupon);
	public List<ElectronicCoupon> selectElectronicCoupon(ElectronicCoupon electronicCoupon);
	public ElectronicCoupon getElectronicCoupon(String id);
	public List<ElectronicCoupon> getElectronicCoupons(String openId);
	public void deleteElectronicCoupon(String id);
}
