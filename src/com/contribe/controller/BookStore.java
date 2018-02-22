package com.contribe.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.contribe.object.Book;

public class BookStore implements BookList {

    public static LinkedHashMap<Book, Integer> getInitialBookList() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/contribe/contribe/dev/bookstoredata/bookstoredata.txt");
        //Had to put UTF-8 to read special swedish chars.
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
        String inputLine;
        Book book = null;
        LinkedHashMap<Book, Integer> books = new LinkedHashMap<Book, Integer>();

        //Create a Book Object and store them and finally add it into a LinkedList
        try {
        	System.out.println("All the books downloaded from the url...");
            while ((inputLine = in.readLine()) != null) {
                book = new Book();
                System.err.println(inputLine);
                String[] tempArray = inputLine.split(";");
                book.setTitle(tempArray[0]);
                book.setAuthor(tempArray[1]);
                book.setPrice(new BigDecimal(tempArray[2].replaceAll("\\D", "")));
                books.put(book, Integer.parseInt(tempArray[3]));
            }
            System.out.println("\n");
        } catch (NumberFormatException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.getMessage();
        } finally {
        	if(in != null){
        		in.close();
        	}
        }
        //Store the list on a txt file in our application
        storeInitialBookList(books);
        //then return the list
        return books;
    }

    @Override
    public Book[] list(String searchString) throws IOException {//I would've have a LinkedHashMap return for this to also return the quantity
        //But since the specs forces me to use an array we will have to build another function listQuantity to return quantity
        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {
            fin = new FileInputStream("Books.txt");
            ois = new ObjectInputStream(fin);
            LinkedHashMap<Book, Integer> bookMapList = (LinkedHashMap<Book, Integer>) ois.readObject();
            LinkedList<Book> tempBook = new LinkedList<Book>();
            Book[] books = null;
            for (Map.Entry<Book, Integer> mapEntry : bookMapList.entrySet()) {
                //if it is a specific search or all?
                if (!searchString.equalsIgnoreCase("all")) {
                    if (mapEntry.getKey().getAuthor().matches("(?i:.*" + searchString + ".*)")
                            || mapEntry.getKey().getTitle().matches("(?i:.*" + searchString + ".*)")) {
                        tempBook.add(mapEntry.getKey());
                    }
                } else {
                    tempBook.add(mapEntry.getKey());
                }
            }
            books = tempBook.toArray(new Book[tempBook.size()]);
            return books;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } catch (IOException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (ois != null) {
                ois.close();
            }
        }

        return null;
    }
    
    public LinkedHashMap<Book, Integer> list(Book bookSearch) throws IOException {//I would've have a LinkedHashMap return for this to also return the quantity
        //But since the specs forces me to use an array we will have to build another function listQuantity to return quantity
        FileInputStream fin = null;
        ObjectInputStream ois = null;

        try {
            fin = new FileInputStream("Books.txt");
            ois = new ObjectInputStream(fin);
            LinkedHashMap<Book, Integer> bookList = (LinkedHashMap<Book, Integer>) ois.readObject();
            LinkedHashMap<Book, Integer> tempBook = new LinkedHashMap<Book, Integer>();
            for (Map.Entry<Book, Integer> mapEntry : bookList.entrySet()) {
            		//is the book in our system?
                    if (mapEntry.getKey().getTitle().equalsIgnoreCase(bookSearch.getTitle())) {
                        tempBook.put(mapEntry.getKey(), mapEntry.getValue());
                    }
            }
            return tempBook;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } catch (IOException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (ois != null) {
                ois.close();
            }
        }

        return null;
    }

    public LinkedHashMap<Book, Integer> list() throws IOException {
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            fin = new FileInputStream("Books.txt");
            ois = new ObjectInputStream(fin);
            LinkedHashMap<Book, Integer> theMap = (LinkedHashMap<Book, Integer>) ois.readObject();
            ois.close();
            return theMap;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } catch (IOException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(BookStore.class.getName()).log(Level.SEVERE, null, ex);
            ex.getMessage();
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (ois != null) {
                ois.close();
            }
        }
        return null;
    }

    @Override
    public boolean add(Book book, int quantity) throws IOException {
        //In a real world scenario I would check if that book already exists in the txt file, but for the
        //sake of this exercise I am just going to add the new book without checking the existence\
        FileInputStream fin = null;
        ObjectInputStream ois = null;
        try {
            fin = new FileInputStream("Books.txt");
            ois = new ObjectInputStream(fin);
            LinkedHashMap<Book, Integer> allBooks = (LinkedHashMap<Book, Integer>) ois.readObject();
            allBooks.put(book, quantity);
            storeInitialBookList(allBooks);
            fin.close();
            ois.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.getMessage();
	            if (fin != null) {
	                fin.close();
	            }
	            if (ois != null) {
	                ois.close();
	            }
            return false;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.getMessage();
	            if (fin != null) {
	                fin.close();
	            }
	            if (ois != null) {
	                ois.close();
	            }
            return false;
        } finally {
            if (fin != null) {
                fin.close();
            }
            if (ois != null) {
                ois.close();
            }
        }

        return true;
    }

    @Override
    public int[] buy(Book... books) {
        LinkedHashMap<Book, Integer> allBooks = null;
        LinkedHashMap<Book, Integer> tempBooks = new LinkedHashMap<Book, Integer>();
        int[] output = new int[books.length]; //0 (OK)    // 1 (NOT_IN_STOCK)  // 2 (Does_not_exist)
        boolean found = false;
        try {
            allBooks = list();
            for (int i = 0; i < books.length; i++) {
                for (Map.Entry<Book, Integer> mapEntry : allBooks.entrySet()) {
                    found = false;
                    //have to match the title, otherwise it won't work
                    if (mapEntry.getKey().getTitle().equalsIgnoreCase(books[i].getTitle())) {
                        found = true; //sign to show the book exists in our system
                        if(mapEntry.getValue() <= 1){
                           tempBooks.put(mapEntry.getKey(), mapEntry.getValue());
                           output[i] = 1; 
                        }else{
                            //only allowing one item at a time since the specs does not say otherwise
                           tempBooks.put(mapEntry.getKey(), mapEntry.getValue() - 1);
                           output[i] = 0;
                        }
                    }
                }
               if (!found){
                   output[i] = 2;
               } 
            }
            allBooks.putAll(tempBooks);
            //I kinda made this a bit complicated since I am not using database, so I have to overwrite the txt with the new merged Map
            storeInitialBookList(allBooks);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            e.getMessage();
        }
        return output;
    }

    public static void storeInitialBookList(LinkedHashMap<Book, Integer> bookList) throws FileNotFoundException, IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Books.txt"));
        out.writeObject(bookList);
        if (out != null) {
            out.close();
        }
    }

}
