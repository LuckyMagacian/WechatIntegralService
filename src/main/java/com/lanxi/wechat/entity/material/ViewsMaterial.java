package com.lanxi.wechat.entity.material;

import java.util.List;
/**
 * 微信素材类-永久素材-图文素材
 * @author 1
 *
 */
public class ViewsMaterial extends ForeverMaterial {
	/**
	 * 微信素材类-永久素材-图文素材内部类-文章
	 * @author 1
	 *
	 */
	public static class Article{
		/**文章封面-显示*/
		public static final String ARTICLE_SHOWCOVERPIC_ON ="1";
		/**文章封面-不显示*/
		public static final String ARTICLE_SHOWCOVERPIC_OFF="0";
		private String title;			/**文章标题*/
		private String thumbMediaId;	/**文章缩略图*/
		private String author;			/**文章作者*/
		private String showCoverPic;	/**文章是否显示封面*/
		private String content;			/**文章内容*/
		private String contentSourceUrl;/**文章原文链接*/
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getThumbMediaId() {
			return thumbMediaId;
		}
		public void setThumbMediaId(String thumbMediaId) {
			this.thumbMediaId = thumbMediaId;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getShowCoverPic() {
			return showCoverPic;
		}
		public void setShowCoverPic(String showCoverPic) {
			this.showCoverPic = showCoverPic;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getContentSourceUrl() {
			return contentSourceUrl;
		}
		public void setContentSourceUrl(String contentSourceUrl) {
			this.contentSourceUrl = contentSourceUrl;
		}
	}
	
	private List<Article> articles;

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	
}
