package com.bridgelabz.book.controller;

import com.bridgelabz.book.dto.BookDTO;
import com.bridgelabz.book.model.BookModel;
import com.bridgelabz.book.service.IBookService;
import com.bridgelabz.book.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

    @Autowired
    IBookService bookService;

    @GetMapping("/welcome")
    public String welcomeBook() {
        String welMsg = bookService.welcome();
        return welMsg;
    }

//    @GetMapping("/get")
//    public <object> object getAllUser(){
//        object user = (object) bookService.getAllUser();
//        return user;
//    }

    @PostMapping("/addBook")
    public ResponseEntity<Response> addBook(@RequestBody BookDTO bookDto,@RequestHeader String token){
        Response response = bookService.addNewBook(token,bookDto);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @GetMapping("/getBooks")
    public ResponseEntity<List<?>> getAllBooks(@RequestHeader String token)
    {
        List<BookModel> books = bookService.getAllBooks(token);
        return new ResponseEntity<List<?>>(books, HttpStatus.OK);
    }

    @PutMapping("/updateBookPrice")
    public ResponseEntity<Response> updateBookPrice(@RequestHeader String token,@RequestParam("bookName") String bookName,
                                                    @RequestParam("quantity") Double price){
        Response response = bookService.updateBookPrice(token,bookName,price);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PutMapping("/updateBookQuantity")
    public ResponseEntity<Response> updateBookQuantity(@RequestHeader String token,@RequestParam("bookName") String bookName,
                                                       @RequestParam("quantity") int newQuantity){
        Response response = bookService.updateBookQuantity(token,bookName,newQuantity);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @PutMapping("/updateBook")
    public ResponseEntity<Response> updateBook(@RequestBody BookDTO bookDto,@PathVariable String token)
    {
        Response response = bookService.updateBook(token,bookDto);
        return new ResponseEntity<Response>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deleteBook")
    public ResponseEntity<Response> deleteBook(@RequestHeader String token,@RequestParam("name") String bookName){
        Response response = bookService.deleteBook(token,bookName);
        return new ResponseEntity<Response>(response, HttpStatus.OK);

    }
}

