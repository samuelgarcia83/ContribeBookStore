package com.contribe.controller;

import java.io.IOException;

import com.contribe.object.Book;

public interface BookList {
	public Book[] list(String searchString) throws IOException;
    public boolean add(Book book, int quantity) throws IOException;
    public int[] buy(Book... books);
}
