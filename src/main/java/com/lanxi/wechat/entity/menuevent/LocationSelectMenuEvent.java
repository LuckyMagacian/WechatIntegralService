package com.lanxi.wechat.entity.menuevent;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public class LocationSelectMenuEvent extends WechatMenuEvent {
	public class SendLocationInfo{
		private String locationX;
		private String locationY;
		private String scale;
		private String label;
		private String poiname;
		public String getLocationX() {
			return locationX;
		}
		public void setLocationX(String locationX) {
			this.locationX = locationX;
		}
		public String getLocationY() {
			return locationY;
		}
		public void setLocationY(String locationY) {
			this.locationY = locationY;
		}
		public String getScale() {
			return scale;
		}
		public void setScale(String scale) {
			this.scale = scale;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getPoiname() {
			return poiname;
		}
		public void setPoiname(String poiname) {
			this.poiname = poiname;
		}
		public DOMElement toElement(){
			DOMElement element=new DOMElement("SendLocationInfo");
			element.element("Location_X").addCDATA(locationX);
			element.element("Location_Y").addCDATA(locationY);
			element.element("Scale").addCDATA(scale);
			element.element("Label").addCDATA(label);
			element.element("Poiname").addCDATA(poiname==null?"":poiname);
			return element;
		}
		public void fromElement(Element ele){
			setLocationX(ele.elementText("Location_X"));
			setLocationY(ele.elementText("Location_Y"));
			setScale(ele.elementText("Scale"));
			setLabel(ele.elementText("Label"));
			setPoiname(ele.elementText("Poiname"));
		}
	}
	private SendLocationInfo locationInfo;
	
	
	public LocationSelectMenuEvent() {
		setMsgType(MENU_EVENT_LOCATION_SELECT);
		locationInfo=new SendLocationInfo();
	}
	
	public SendLocationInfo getLocationInfo() {
		return locationInfo;
	}
	public void setLocationInfo(SendLocationInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.add(locationInfo.toElement());
		return element;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			locationInfo.fromElement(root.element("SendLocationInfo"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
