package com.lanxi.wechat.entity.event;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-事件消息类-上报地理位置消息类
 * @author 1
 *
 */
public class LocationEvent extends WechatEventMessage {
	/**经度*/
	private String latitude;		
	/**纬度*/
	private String longitude;		
	/**位置精度*/
	private String precision;		
	public LocationEvent() {
		setEvent(EVENT_LOCATION);
	}
	
	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getPrecision() {
		return precision;
	}

	public void setPrecision(String precision) {
		this.precision = precision;
	}

	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.addElement("Latitude").setText(latitude);;
		element.addElement("Longitude").setText(longitude);;
		element.addElement("Precision").setText(precision);;
		return element;
	}

	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			setLatitude(root.elementText("Latitude"));
			setLongitude(root.elementText("Longitude"));
			setPrecision(root.elementText("Precision"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}

}
