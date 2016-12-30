package com.lanxi.wechat.entity.automessage;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-自动消息类-自动回复图片消息类
 * @author 1
 *
 */
public class ImageAutoMessage extends WechatAutoMessage {
	/**
	 * 自动回复图片消息类-内部类-图片
	 * @author 1
	 *
	 */
	public class Image{
		/**微信服务器上图片素材对应的编号*/
		private String mediaId;

		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
		/**
		 * 将当前类转为xml元素
		 * @return
		 */
		public DOMElement toElement() {
			DOMElement element=new DOMElement("Image");
			element.addElement("MediaId").addCDATA(mediaId);
			return element;
		}
		/**
		 * 用户xml属性设置当前类
		 * @param ele
		 */
		public void fromElement(Element ele) {
			try{
				setMediaId(ele.elementText("MediaId"));
			}catch (Exception e) {
				throw new AppException("从xml中提取属性异常",e);
			}
		}
	}
	/**图片*/
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
	/**
	 * 将当前类转为xml元素
	 * @return
	 */
	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.add(image.toElement());
		return element;
	}
	/**
	 * 用户xml属性设置当前类
	 * @param ele
	 */
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
