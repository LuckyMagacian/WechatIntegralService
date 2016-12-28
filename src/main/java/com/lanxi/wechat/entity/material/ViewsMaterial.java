package com.lanxi.wechat.entity.material;

import java.util.List;

public class ViewsMaterial extends ForeverMaterial {
	public static class Article{
		private String title;
		private String thumbMediaId;
		private String author;
		private String showCoverPic;
		private String content;
		private String contentSourceUrl;
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
