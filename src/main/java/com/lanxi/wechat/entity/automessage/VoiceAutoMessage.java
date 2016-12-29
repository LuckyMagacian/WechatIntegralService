package com.lanxi.wechat.entity.automessage;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-自动消息类-自动回复语音消息类
 * @author 1
 *
 */
public class VoiceAutoMessage extends WechatAutoMessage {
	/**
	 * 自动回复语音消息类-内部类-语音
	 * @author 1
	 *
	 */
	public class Voice{
		private String mediaId;		/**语音素材编号*/
		public String getMediaId() {
			return mediaId;
		}

		public void setMediaId(String mediaId) {
			this.mediaId = mediaId;
		}
		public DOMElement toElement() {
			DOMElement element=new DOMElement("Music");
			element.addElement("MediaId").addCDATA(mediaId);
			return element;
		}
		public void fromElement(Element ele){
			setMediaId(ele.elementText("MediaId"));
		}
	}
	private Voice voice;
	public VoiceAutoMessage() {
		setMsgType(AUTO_MESSAGE_TYPE_VOICE);
		voice=new Voice();
	}
	public String getMediaId() {
		return voice.mediaId;
	}
	public void setMediaId(String mediaId) {
		voice.mediaId = mediaId;
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.add(voice.toElement());
		return element;
	}
	@Override
	public void fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			voice.fromElement(root.element("Voice"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
