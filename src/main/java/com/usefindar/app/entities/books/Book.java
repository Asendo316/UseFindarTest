package com.usefindar.app.entities.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Table(name = "books")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private boolean available;
}
