package com.lanxi.wechat.entity.automessage;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;

import com.lanxi.WechatIntegralService.util.AppException;
/**
 * 微信消息类-自动消息类-自动回复音乐消息类
 * @author 1
 *
 */
public class MusicAutoMessage extends WechatAutoMessage {
	/**
	 * 自动回复音乐消息类-内部类-音乐
	 * @author 1
	 *
	 */
	public class Music{
		private String title;		/**音乐标题*/
		private String description; /**音乐描述*/
		private String musicUrl;	/**音乐链接*/		//=>MusicURL	
		private String hqMusicUrl;	/**高清链接*/			//=>HQMusicUrl
		private String thumbMediaId;/**缩略图素材编号*/
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getMusicUrl() {
			return musicUrl;
		}
		public void setMusicUrl(String musicUrl) {
			this.musicUrl = musicUrl;
		}
		public String getHqMusicUrl() {
			return hqMusicUrl;
		}
		public void setHqMusicUrl(String hqMusicUrl) {
			this.hqMusicUrl = hqMusicUrl;
		}
		public String getThumbMediaId() {
			return thumbMediaId;
		}
		public void setThumbMediaId(String thumbMediaId) {
			this.thumbMediaId = thumbMediaId;
		}
		public DOMElement toElement() {
			DOMElement element=new DOMElement("Music");
			element.addElement("Title").addCDATA(title);
			element.addElement("Description").addCDATA(description);
			element.addElement("MusicURL").addCDATA(musicUrl);
			element.addElement("HQMusicUrl").addCDATA(hqMusicUrl==null?"":hqMusicUrl);;
			element.addElement("ThumbMediaId").addCDATA(thumbMediaId);
			return element;
		}
		public void fromElement(Element ele){
			setTitle(ele.elementText("Title"));
			setDescription(ele.elementText("Description"));
			setMusicUrl(ele.elementText("MusicURL"));
			setHqMusicUrl(ele.elementText("HQMusicUrl"));
			setThumbMediaId(ele.elementText("ThumbMediaId"));
		}
	}
	private Music music;
	public MusicAutoMessage() {
		setMsgType(AUTO_MESSAGE_TYPE_MUSIC);
		music=new Music();
	}
	public String getTitle() {
		return music.title;
	}
	public void setTitle(String title) {
		music.title = title;
	}
	public String getDescription() {
		return music.description;
	}
	public void setDescription(String description) {
		music.description = description;
	}
	public String getMusicUrl() {
		return music.musicUrl;
	}
	public void setMusicUrl(String musicUrl) {
		music.musicUrl = musicUrl;
	}
	public String getHqMusicUrl() {
		return music.hqMusicUrl;
	}
	public void setHqMusicUrl(String hqMusicUrl) {
		music.hqMusicUrl = hqMusicUrl;
	}
	public String getThumbMediaId() {
		return music.thumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		music.thumbMediaId = thumbMediaId;
	}
	@Override
	public DOMElement toElement() {
		DOMElement element=super.toElement();
		element.add(music.toElement());
		return element;
	}
	@Override
	public void  fromString(String str) {
		try{
			super.fromString(str);
			Element root=DocumentHelper.parseText(str).getRootElement();
			music.fromElement(root.element("Music"));
		}catch (Exception e) {
			throw new AppException("从xml中提取属性异常",e);
		}
	}
	
}
