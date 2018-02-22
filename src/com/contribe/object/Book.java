package com.contribe.object;

import java.io.Serializable;
import java.math.BigDecimal;

public class Book implements Serializable {
	private String title;
	private String author;
	private BigDecimal price;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
