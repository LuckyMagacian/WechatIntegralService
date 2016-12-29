package com.lanxi.wechat.manageer;
/**
 * 微信菜单管理类
 * @author 1
 *
 */
import java.util.LinkedHashMap;
import java.util.Map;

import com.lanxi.WechatIntegralService.util.AppException;
import com.lanxi.WechatIntegralService.util.ConfigUtil;
import com.lanxi.WechatIntegralService.util.HttpUtil;
import com.lanxi.wechat.entity.menu.BaseButton;
import com.lanxi.wechat.entity.menu.SubButton;
import com.lanxi.wechat.entity.menu.WechatMenu;

public class MenuManager {
	public  static final WechatMenu menu;			/**微信菜单*/
	private static Map<String, String> menuLog;		/**微信菜单记录*/
	static  int    menuIndex=0;						/**已使用的菜单位置*/
	static{
		menu=new WechatMenu();
		menuLog=new LinkedHashMap<>();
	}
	private MenuManager(){
		
	}
	/**
	 * 添加一级按键
	 * @param button
	 */
	public static void addButton(BaseButton button){
		menu.addButton(button);
		menuLog.put(""+menuIndex, "BaseButton");
		menuIndex++;
	}
	/**
	 * 添加子菜单按键
	 * @param subButton
	 */
	public static void addSubButton(SubButton subButton){
		menu.addButton(subButton);
		menuLog.put(""+menuIndex,"SubButton");
		menuIndex++;
	}
	/**
	 * 添加二级按键
	 * @param index
	 * @param button
	 */
	public static void addToSubButton(Integer index,BaseButton button){
		if("SubButton".equals(menuLog.get(index+""))){
			SubButton subButton=(SubButton) menu.getButton().get(index);
			subButton.addButton(button);
		}else{
			throw new AppException("该索引处不是子菜单");
		}
	}
	/**
	 * 请求创建菜单
	 * @return
	 */
	public static String createMenu(){
		String urlStr=ConfigUtil.get("menuCreateUrl");
		urlStr=urlStr.replace("ACCESS_TOKEN",TokenManager.getAccessToken());
		return HttpUtil.post(menu.toJson(), urlStr, "utf-8", null);
	}
	/**
	 * 删除菜单(关闭菜单)
	 * @return
	 */
	public static String clearMenu(){
		String urlStr=ConfigUtil.get("menuDeleteUrl");
		urlStr=urlStr.replace("ACCESS_TOKEN",TokenManager.getAccessToken());
		return HttpUtil.get(urlStr, "utf-8");
	}
	/**
	 * 获取菜单信息(全部,包括用网页管理创建的菜单)
	 * @return
	 */
	public static String getMenu(){
		String urlStr=ConfigUtil.get("menuGetUrl");
		urlStr=urlStr.replace("ACCESS_TOKEN",TokenManager.getAccessToken());
		return HttpUtil.get(urlStr, "utf-8");
	}
}
