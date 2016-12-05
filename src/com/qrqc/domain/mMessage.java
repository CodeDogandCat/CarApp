package com.qrqc.domain;

import java.io.Serializable;
import java.util.List;

import android.graphics.Bitmap;

public class mMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String title;
	private String content;
	private String pictureurl;
	private String date;
	private String replynum;
	private String picnum;
	private long sign;

	public long getSign() {
		return sign;
	}

	public void setSign(long sign) {
		this.sign = sign;
	}

	private List<Bitmap> picList;

	public String getPicnum() {
		return picnum;
	}

	public void setPicnum(String picnum) {
		this.picnum = picnum;
	}

	public List<Bitmap> getPicList() {
		return picList;
	}

	public void setPicList(List<Bitmap> picList) {
		this.picList = picList;
	}

	public String getReplynum() {
		return replynum;
	}

	public void setReplynum(String replynum) {
		this.replynum = replynum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPictureurl() {
		return pictureurl;
	}

	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public mMessage(String id, String username, String title, String content, String pictureurl, String date,
			String replynum, String picnum) {
		super();
		this.id = id;
		this.username = username;
		this.title = title;
		this.content = content;
		this.pictureurl = pictureurl;
		this.date = date;
		this.replynum = replynum;
		this.picnum = picnum;
	}

	@Override
	public String toString() {
		return "mMessage [id=" + id + ", username=" + username + ", title=" + title + ", content=" + content
				+ ", pictureurl=" + pictureurl + ", date=" + date + ", replynum=" + replynum + ", picnum=" + picnum
				+ "]";
	}

}
