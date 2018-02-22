package com.contribe.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Scanner;

import com.contribe.object.Book;

public class BookMain {

	public static void main(String[] args) {
		//This is a self destroy application since database was not required for this exercise
		//everytime that we run this application 'Books.txt' will be overwritten with the 
		//initial data provided by contribe.
		BookStore bookStore = new BookStore();
		StringBuilder stringBuilder = null;
		BigDecimal price = null;
		Book[] books = null;
		Book newBook = null;
		Integer quantity = 0;
		int numBuyBook = 0;
		int[] buyResult = null;
		boolean input = false;
		
		
		System.out.println("Getting all the Books from the system...");
		try {
			BookStore.getInitialBookList();		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			e.getMessage();
		}
		
		Scanner reader = new Scanner(System.in);
		
		String userAction = null;
		do{
			System.out.println("Please choose from the options bellow...");
			System.out.println("----------------------------------------");
	        System.out.println("Select (s) to search for a book.");
	        System.out.println("Select (a) to add a book.");
	        System.out.println("Select (b) to buy a book.");
	        System.out.println("Select (q) to quit the application.");
	        userAction = reader.nextLine();
	        
			switch(userAction){
			case "s":
				System.out.println("Please type the search String");
				System.out.println("Type \"All\" if you want to list all the Books!");
				String searchString = reader.nextLine();
				try {
					books = bookStore.list(searchString);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("===Book(s) Found in the Store===");
				for(Book foundBook: books){
					System.out.println("=====================================");
					System.out.println("Book Title - " + foundBook.getTitle());
					System.out.println("Book Author - " + foundBook.getAuthor());
					System.out.println("Book Price - " + foundBook.getPrice());
					System.out.println("=====================================");
				}
				input = true;
				break;
				
			case "a":
				System.out.println("Please enter the title of the book");
				newBook = new Book();
				
				stringBuilder = new StringBuilder(reader.nextLine());
				newBook.setTitle(stringBuilder.toString());
				System.out.println("Please enter the author of the book");
				
				stringBuilder = new StringBuilder(reader.nextLine());
				newBook.setAuthor(stringBuilder.toString());
				System.out.println("Please enter the price of the book");
				
				price = reader.nextBigDecimal();
				newBook.setPrice(price);
				System.out.println("Please enter the quantity of copies");
				
				quantity = reader.nextInt();
				try {
					bookStore.add(newBook, quantity);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				input = true;
				break;
				
			case "b":	
				System.out.println("How many books are you buying?");
				stringBuilder = new StringBuilder(reader.nextLine());
				//I did this silly thing here because I was getting an issue with the int scanner along with strings
				numBuyBook = Integer.parseInt(stringBuilder.toString());
				//initialize the book array to buy
				books = new Book[numBuyBook];//reset the array
				
				for(int i = 0; i < books.length; i++){
					System.out.println("Please enter the title of the book you wish to buy");
					stringBuilder = new StringBuilder(reader.nextLine());
					books[i] = new Book();
					books[i].setTitle(stringBuilder.toString());
					System.out.println("test");
				}
				
				buyResult = bookStore.buy(books);
				for(int i = 0; i < books.length; i++){
					System.out.println("--------------------------------------------------");
					if(buyResult[i] == 0){
						System.out.println("The book title - " +books[i].getTitle()+ " was successfully Bought.");
                                                System.out.println("--------------------------------------------------");
					}else if(buyResult[i] == 1){
						System.out.println("The book title - " +books[i].getTitle()+ " is out of stock.");
                                                System.out.println("--------------------------------------------------");
					}else{
						System.out.println("The book title - " +books[i].getTitle()+ " was not found.");
                                                System.out.println("--------------------------------------------------");
					}
				}
				
				input = true;
				break;
			case "q":
				System.out.println("Quiting the application...");
				reader.close();
				input = false;
				return;
			default:
					System.out.println("Choice is not available... Try again...");
					System.out.println("\n");
					input = true;
					break;
			}
		}while(input);
			
		
	}

}
