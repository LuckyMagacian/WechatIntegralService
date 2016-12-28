package com.lanxi.wechat.entity.automessage;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;

public class ImageAutoMessage extends WechatAutoMessage {
	public class Image{
		private String mediaId;

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
		public DOMElement toElement() {
			DOMElement element=new DOMElement("Image");
			element.addElement("MediaId").addCDATA(mediaId);
			return element;
		}
		public void fromElement(Element ele) {
			try{
				setMediaId(ele.elementText("MediaId"));
			}catch (Exception e) {
				throw new AppException("从xml中提取属性异常",e);
			}
		}
	}
	private Image image;
	public ImageAutoMessage() {
		setMsgType(AUTO_MESSAGE_TYPE_IMAGE);
		image=new Image();
	}
	public String getMediaId() {
		return image.mediaId;
	}
	public void setMediaId(String mediaId) {
		image.mediaId = mediaId;
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.add(image.toElement());
		return element;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			image.fromElement(root.element("Image"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
