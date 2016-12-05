package com.qrqc.domain;

import java.io.Serializable;

public class mComment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String content;
	private String date;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public mComment(String id, String username, String content, String date) {
		super();
		this.id = id;
		this.username = username;
		this.content = content;
		this.date = date;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", username=" + username + ", content=" + content + ", date=" + date + "]";
	}

}
