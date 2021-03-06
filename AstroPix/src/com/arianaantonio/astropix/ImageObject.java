package com.arianaantonio.astropix;

public class ImageObject {
	private String url;
	private String username;
	private String camera;
	private String description;
	private String telescope;
	private String websiteUrl;
	private String title;
	
	public ImageObject(String url, String username, String camera, String description, String telescope, String websiteUrl, String title) {
		this.camera = camera; 
		this.description = description;
		this.telescope = telescope;
		this.url = url;
		this.username = username;
		this.websiteUrl = websiteUrl;
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getusername() {
		return username;
	}
	public void setusername(String username) {
		this.username = username;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}
	public String getTelescope() {
		return telescope;
	}
	public void setTelescope(String telescope) {
		this.telescope = telescope;
	}
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title; 
	}
}

