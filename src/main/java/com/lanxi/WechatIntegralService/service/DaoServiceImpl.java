package com.lanxi.WechatIntegralService.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lanxi.WechatIntegralService.dao.AccountBindingDao;
import com.lanxi.WechatIntegralService.dao.GameDao;
import com.lanxi.WechatIntegralService.dao.GiftDao;
import com.lanxi.WechatIntegralService.dao.GiftOrderDao;
import com.lanxi.WechatIntegralService.dao.IntegralGameDao;
import com.lanxi.WechatIntegralService.dao.IntegralRedPacketDao;
import com.lanxi.WechatIntegralService.dao.IntegralTransferDao;
import com.lanxi.WechatIntegralService.dao.RedPacketReceiveDao;
import com.lanxi.WechatIntegralService.dao.ValidCodeDao;
import com.lanxi.WechatIntegralService.entity.AccountBinding;
import com.lanxi.WechatIntegralService.entity.Game;
import com.lanxi.WechatIntegralService.entity.Gift;
import com.lanxi.WechatIntegralService.entity.IntegralGame;
import com.lanxi.WechatIntegralService.entity.IntegralRedPacket;
import com.lanxi.WechatIntegralService.entity.IntegralTransfer;
import com.lanxi.WechatIntegralService.entity.RedPacketReceive;
import com.lanxi.WechatIntegralService.entity.ValidCode;
import com.lanxi.WechatIntegralService.util.TimeUtil;
@Service("daoService")
public class DaoServiceImpl implements DaoService {
	@Resource
	private AccountBindingDao accountDao;
	@Resource
	private GameDao gameDao;
	@Resource
	private GiftDao giftDao;
	@Resource
	private GiftOrderDao giftOrderDao;
	@Resource
	private IntegralGameDao integralGameDao;
	@Resource
	private IntegralRedPacketDao redPacketDao;
	@Resource
	private IntegralTransferDao transferDao;
	@Resource
	private RedPacketReceiveDao receiveDao;
	@Resource
	private ValidCodeDao codeDao;
	@Override
	public void addAccountBinding(AccountBinding accountBinding) {
		accountDao.addAccountBinding(accountBinding);
	}

	@Override
	public void addAccountBindingDefault(AccountBinding accountBinding) {
		accountDao.addAccountBindingDefault(accountBinding);
	}

	@Override
	public void deleteAccountBinding(AccountBinding accountBinding) {
		accountDao.deleteAccountBinding(accountBinding);
	}

	@Override
	public void updateAccountBinding(AccountBinding accountBinding) {
		accountDao.updateAccountBinding(accountBinding);
	}

	@Override
	public List<AccountBinding> selectAccountBinding(AccountBinding accountBinding) {
		return accountDao.selectAccountBinding(accountBinding);
	}

	@Override
	public void addGame(Game game) {
		gameDao.addGame(game);
	}

	@Override
	public void addGameDefault(Game game) {
		gameDao.addGameDefault(game);
	}

	@Override
	public void deleteGame(Game game) {
		gameDao.deleteGame(game);
	}

	@Override
	public void updateGame(Game game) {
		gameDao.updateGame(game);
	}

	@Override
	public List<Game> selectGame(Game game) {
		return gameDao.selectGame(game);
	}

	@Override
	public void addGift(Gift gift) {
		giftDao.addGift(gift);
	}

	@Override
	public void addGiftDefault(Gift gift) {
		giftDao.addGiftDefault(gift);
	}

	@Override
	public void deleteGift(Gift gift) {
		giftDao.deleteGift(gift);
	}

	@Override
	public void updateGift(Gift gift) {
		giftDao.updateGift(gift);
	}

	@Override
	public List<Gift> selectGift(Gift gift) {
		return giftDao.selectGift(gift);
	}

	@Override
	public void addIntegralGame(IntegralGame integralGame) {
		integralGameDao.addIntegralGame(integralGame);
	}

	@Override
	public void addIntegralGameDefault(IntegralGame integralGame) {
		integralGameDao.addIntegralGameDefault(integralGame);
	}

	@Override
	public void deleteIntegralGame(IntegralGame integralGame) {
		integralGameDao.deleteIntegralGame(integralGame);
	}

	@Override
	public void updateIntegralGame(IntegralGame integralGame) {
		integralGameDao.updateIntegralGame(integralGame);
	}

	@Override
	public List<IntegralGame> selectIntegralGame(IntegralGame integralGame) {
		return integralGameDao.selectIntegralGame(integralGame);
	}

	@Override
	public void addIntegralRedPacket(IntegralRedPacket integralRedPacket) {
		redPacketDao.addIntegralRedPacket(integralRedPacket);
	}

	@Override
	public void addIntegralRedPacketDefault(IntegralRedPacket integralRedPacket) {
		redPacketDao.addIntegralRedPacketDefault(integralRedPacket);
	}

	@Override
	public void deleteIntegralRedPacket(IntegralRedPacket integralRedPacket) {
		redPacketDao.deleteIntegralRedPacket(integralRedPacket);
	}

	@Override
	public void updateIntegralRedPacket(IntegralRedPacket integralRedPacket) {
		redPacketDao.updateIntegralRedPacket(integralRedPacket);
	}

	@Override
	public List<IntegralRedPacket> selectIntegralRedPacket(IntegralRedPacket integralRedPacket) {
		return redPacketDao.selectIntegralRedPacket(integralRedPacket);
	}

	@Override
	public void addIntegralTransfer(IntegralTransfer integralTransfer) {
		transferDao.addIntegralTransfer(integralTransfer);
	}

	@Override
	public void addIntegralTransferDefault(IntegralTransfer integralTransfer) {
		transferDao.addIntegralTransferDefault(integralTransfer);
	}

	@Override
	public void deleteIntegralTransfer(IntegralTransfer integralTransfer) {
		transferDao.deleteIntegralTransfer(integralTransfer);
	}

	@Override
	public void updateIntegralTransfer(IntegralTransfer integralTransfer) {
		transferDao.updateIntegralTransfer(integralTransfer);
	}

	@Override
	public List<IntegralTransfer> selectIntegralTransfer(IntegralTransfer integralTransfer) {
		return transferDao.selectIntegralTransfer(integralTransfer);
	}

	@Override
	public void addRedPacketReceive(RedPacketReceive redPacketReceive) {
		receiveDao.addRedPacketReceive(redPacketReceive);
	}

	@Override
	public void addRedPacketReceiveDefault(RedPacketReceive redPacketReceive) {
		receiveDao.addRedPacketReceiveDefault(redPacketReceive);
	}

	@Override
	public void deleteRedPacketReceive(RedPacketReceive redPacketReceive) {
		receiveDao.deleteRedPacketReceive(redPacketReceive);
	}

	@Override
	public void updateRedPacketReceive(RedPacketReceive redPacketReceive) {
		receiveDao.updateRedPacketReceive(redPacketReceive);
	}

	@Override
	public List<RedPacketReceive> selectRedPacketReceive(RedPacketReceive redPacketReceive) {
		return receiveDao.selectRedPacketReceive(redPacketReceive);
	}

	@Override
	public AccountBinding getAccount(String wechatId) {
		AccountBinding accountBinding=new AccountBinding();
		accountBinding.setOpenId(wechatId);
		List<AccountBinding> result=selectAccountBinding(accountBinding);
		return result.isEmpty()?null:result.get(0);
	}

	@Override
	public Game getGame(String gameId) {
		Game game=new Game();
		game.setId(gameId);
		List<Game> result=gameDao.selectGame(game);
		return result.isEmpty()?null:result.get(0);
	}

	@Override
	public List<Gift> getGifts(String gameId) {
		Gift gift=new Gift();
		gift.setGameId(gameId);
		List<Gift> result=giftDao.selectGift(gift);
		return result;
	}

	@Override
	public Gift getGift(String gameId, Integer level) {
		Gift gift=new Gift();
		gift.setGameId(gameId);
		gift.setPrizeLevel(level);
		List<Gift> result=giftDao.selectGift(gift);
		return result.isEmpty()?null:result.get(0);
	}

	@Override
	public IntegralRedPacket getRedPacket(String redPacketId) {
		IntegralRedPacket redPacket=new IntegralRedPacket();
		redPacket.setRedPacketId(redPacketId);
		List<IntegralRedPacket> result=redPacketDao.selectIntegralRedPacket(redPacket);
		return result.isEmpty()?null:result.get(0);
	}

	@Override
	public List<IntegralRedPacket> getRedPacketsByStatus(String status) {
		IntegralRedPacket redPacket=new IntegralRedPacket();
		redPacket.setRedPacketStatus(status);;
		List<IntegralRedPacket> result=redPacketDao.selectIntegralRedPacket(redPacket);
		return result.isEmpty()?null:result;
	}

	@Override
	public List<IntegralRedPacket> getNormlRedPacket() {
		return getRedPacketsByStatus(IntegralRedPacket.RED_PACKET_STATUS_NORML);
	}

	@Override
	public List<IntegralRedPacket> getNoneRedPacket() {
		return getRedPacketsByStatus(IntegralRedPacket.RED_PACKET_STATUS_NONE);
	}

	@Override
	public List<IntegralRedPacket> getOverTimeRedPacket() {
		return getRedPacketsByStatus(IntegralRedPacket.RED_PACKET_STATUS_OVERTIME);
	}

	@Override
	public RedPacketReceive getReceiveRecord(String redPacketId, String wechatId) {
		RedPacketReceive receive=new RedPacketReceive();
		receive.setRedPacketId(redPacketId);
		receive.setOpenId(wechatId);
		List<RedPacketReceive> result=receiveDao.selectRedPacketReceive(receive);
		return result.isEmpty()?null:result.get(0);
	}

	@Override
	public void addValidCode(ValidCode validCode) {
		codeDao.addValidCode(validCode);	
	}

	@Override
	public void addValidCodeDefault(ValidCode validCode) {
		codeDao.addValidCodeDefault(validCode);
	}

	@Override
	public void deleteValidCode(ValidCode validCode) {
		codeDao.deleteValidCode(validCode);
	}

	@Override
	public void updateValidCode(ValidCode validCode) {
		codeDao.updateValidCode(validCode);
	}

	@Override
	public List<ValidCode> selectValidCode(ValidCode validCode) {
		return codeDao.selectValidCode(validCode);
	}

	@Override
	public ValidCode getValidCode(String phone) {
		ValidCode code=new ValidCode();
		code.setPhone(phone);
		List<ValidCode> result=selectValidCode(code);
		return result.isEmpty()?null:result.get(0);
	}
	@Override
	public void insert(ValidCode validCode) {
		codeDao.insert(validCode);
	}

	@Override
	public String getCodeByPhone(String phone) {
		return codeDao.getCodeByPhone(phone);
	}

	@Override
	public void updateStatusByPhone(String phone) {
		codeDao.updateStatusByPhone(phone);
	}

	@Override
	public String getStatusByPhone(String phone) {
		return codeDao.getStatusByPhone(phone);
	}

	@Override
	public void updateStatusByTime() {
		codeDao.updateStatusByTime(TimeUtil.getDateTime());
	}

	@Override
	public void deleteCode() {
		codeDao.deleteCode();
	}
	@Override
	public int getCountByPhone(String phone) {
		return codeDao.getCountByPhone(phone);
	}

	@Override
	public void updateCode(ValidCode validCode) {
		codeDao.updateCode(validCode);
	}
}
