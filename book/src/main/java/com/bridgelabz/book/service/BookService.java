package com.bridgelabz.book.service;

import com.bridgelabz.book.dto.BookDTO;
import com.bridgelabz.book.exception.BookStoreException;
import com.bridgelabz.book.model.BookModel;
import com.bridgelabz.book.model.UserModel;
import com.bridgelabz.book.repository.BookRepository;
import com.bridgelabz.book.util.Response;
import com.bridgelabz.book.util.TokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookService implements IBookService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RestTemplate restTemplate;

     Object object;

    @Override
    public Response addNewBook(String token, BookDTO bookDto) {
        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        if(isUserPresent != null) {
        Optional<BookModel> isBookPresent = bookRepository.findByBookName(bookDto.getBookName());
        if (isBookPresent.isPresent()) {
            throw new BookStoreException(400, "Book already exists.");
        } else {
            BookModel bookModel = modelMapper.map(bookDto, BookModel.class);
            BookModel data = bookRepository.save(bookModel);
            return new Response(200, "New Book Added.",data);
        }
    }
        else {
            throw new BookStoreException(404,"User Not found");
        }
    }

    @Override
    public List<BookModel> getAllBooks(String token)
    {
        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        if(isUserPresent != null) {
            List<BookModel> books = bookRepository.findAll();
            return books;
        }
        else {
            log.error("User not found.");
            throw new BookStoreException(404,"User Not found");
        }
    }

    @Override
    public Response updateBookPrice(String token, String bookName, Double price)
    {

        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        if(isUserPresent != null)
        {

            Optional<BookModel> isBookPresent = bookRepository.findByBookName(bookName);
            if(isBookPresent.isPresent())
            {
                isBookPresent.get().setPrice(price);
                BookModel updatedPrice = bookRepository.save(isBookPresent.get());
                return new Response(200, "Book price updated.", updatedPrice);
            }
            else
            {
                throw new BookStoreException(404,"Book Not found");
            }
        }
        else
        {
            throw new BookStoreException(404,"User Not found");
        }

    }

    @Override
    public Response updateBookQuantity(String token, String bookName, int newQuantity) {

        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        if(isUserPresent != null) {
            Optional<BookModel> isBookPresent = bookRepository.findByBookName(bookName);
            if(isBookPresent.isPresent()) {
                isBookPresent.get().setQuantity(isBookPresent.get().getQuantity()+newQuantity);
                BookModel updatedQuantity = bookRepository.save(isBookPresent.get());
                return new Response(200, "Book quantity updated.", updatedQuantity);
            }
            else {
                throw new BookStoreException(404,"Book Not found");
            }
        }
        else {
            throw new BookStoreException(404,"User Not found");
        }
    }

    @Override
    public Response updateBook(String token, BookDTO bookDto)
    {

        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        if(isUserPresent != null) {
            Optional<BookModel> isBookPresent = bookRepository.findByBookName(bookDto.getBookName());
            if(isBookPresent.isPresent()) {
                isBookPresent.get().setAuthor(bookDto.getAuthor());
                isBookPresent.get().setDescription(bookDto.getDescription());
                isBookPresent.get().setLogo(bookDto.getLogo());
                isBookPresent.get().setPrice(bookDto.getPrice());
                isBookPresent.get().setQuantity(bookDto.getQuantity());
                BookModel updatedBook = bookRepository.save(isBookPresent.get());
                return new Response(200, "Book Updated.", updatedBook);
            }
            else
            {
                throw new BookStoreException(404,"Book Not found");
            }
        }
        else
        {
            throw new BookStoreException(404,"User Not found");
        }
    }


    @Override
    public Response deleteBook(String token, String bookName)
    {

        long id = tokenUtil.decodeToken(token);
        UserModel isUserPresent = restTemplate.getForObject("http://localhost:8081/user/getUserById/" + id, UserModel.class);
        if(isUserPresent != null) {

            Optional<BookModel> isBookPresent = bookRepository.findByBookName(bookName);
            if(isBookPresent.isPresent())
            {
                bookRepository.delete(isBookPresent.get());
                return new Response(200, "Book Deleted.", null);
            }
            else
            {
                throw new BookStoreException(404,"Book Not found");
            }
        }
        else
        {
            throw new BookStoreException(404,"User Not found");
        }
    }

//    public Object getAllUser() {
//        Object user = restTemplate.getForObject("http://localhost:8081/user/getUser", Object.class);
//        System.out.println(user);
//        return user;
//    }

    @Override
    public String welcome() {
        String msg = restTemplate.getForObject("http://localhost:8081/user/welcome",String.class) ;
        System.out.println(msg);
        return msg;
    }

}

