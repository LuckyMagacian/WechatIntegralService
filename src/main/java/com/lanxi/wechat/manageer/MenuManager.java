package com.lanxi.wechat.manageer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.wechat.entity.menu.BaseButton;
import com.lanxi.wechat.entity.menu.SubButton;
import com.lanxi.wechat.entity.menu.WechatMenu;

public class MenuManager {
	public  static final WechatMenu menu;
	private static Map<String, String> menuLog;
	static  int    menuIndex=0;
	static{
		menu=new WechatMenu();
		menuLog=new LinkedHashMap<>();
	}
	private MenuManager(){
		
	}
	public static void addButton(BaseButton button){
		menu.addButton(button);
		menuLog.put(""+menuIndex, "BaseButton");
		menuIndex++;
	}
	public static void addSubButton(SubButton subButton){
		menu.addButton(subButton);
		menuLog.put(""+menuIndex,"SubButton");
		menuIndex++;
	}
	public static void addToSubButton(Integer index,BaseButton button){
		if("SubButton".equals(menuLog.get(index+""))){
			SubButton subButton=(SubButton) menu.getButton().get(index);
			subButton.addButton(button);
		}else{
			throw new AppException("该索引处不是子菜单");
		}
	}
	public static String createMenu(){
		String urlStr=ConfigUtil.get("menuCreateUrl");
		urlStr=urlStr.replace("ACCESS_TOKEN",TokenManager.getAccessToken());
		return HttpUtil.post(menu.toJson(), urlStr, "utf-8", null);
	}
	public static String clearMenu(){
		String urlStr=ConfigUtil.get("menuDeleteUrl");
		urlStr=urlStr.replace("ACCESS_TOKEN",TokenManager.getAccessToken());
		return HttpUtil.get(urlStr, "utf-8");
	}
	public static String getMenu(){
		String urlStr=ConfigUtil.get("menuGetUrl");
		urlStr=urlStr.replace("ACCESS_TOKEN",TokenManager.getAccessToken());
		return HttpUtil.get(urlStr, "utf-8");
	}
}
