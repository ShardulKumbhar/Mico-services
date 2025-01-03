package com.bridgelabz.book.repository;

import java.util.Optional;

import com.bridgelabz.book.model.BookModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookModel, Long>{

    Optional<BookModel> findByBookName(String bookName);

}
