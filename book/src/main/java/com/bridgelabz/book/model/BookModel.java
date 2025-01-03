package com.bridgelabz.book.model;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "books")
public class BookModel {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @Column(name = "bookName")
    private String bookName;

    @Column(name = "bookAuthor")
    private String author;

    @Column(name = "bookDescription")
    private String description;

    @Column(name = "bookLogo")
    private String logo;

    @Column(name = "bookPrice")
    private double price;

    @Column(name = "bookQuantity")
    private int quantity;

}


