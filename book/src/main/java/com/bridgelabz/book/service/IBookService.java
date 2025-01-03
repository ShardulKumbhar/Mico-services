package com.bridgelabz.book.service;

import com.bridgelabz.book.dto.BookDTO;
import com.bridgelabz.book.model.BookModel;
import com.bridgelabz.book.util.Response;

import java.util.List;

public interface IBookService {

    Response addNewBook(String token, BookDTO bookDto);

    List<BookModel> getAllBooks(String token);

    Response updateBookPrice(String token, String bookName, Double price);

    Response updateBookQuantity(String token, String bookName, int quantity);

    Response updateBook(String token, BookDTO bookDto);

    Response deleteBook(String token, String bookName);

//    Object getAllUser();

    String welcome();
}