package com.lanxi.WechatIntegralService.dao;

import java.util.List;

import com.lanxi.WechatIntegralService.entity.Game;

public interface GameDao {
	public void addGame(Game game);
	public void addGameDefault(Game game);
	public void deleteGame(Game game);
	public void updateGame(Game game);
	public List<Game>selectGame(Game game);
	
}
