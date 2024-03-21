package com.kumar.reactive.repository;

import com.kumar.reactive.model.Book;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface BookRepository extends ReactiveCrudRepository<Book, String> {


    Mono<Book> findByName(String name);
    Flux<Book> findByAuthor(String author);
    Flux<Book> findByPublisher(String publisher);

    Flux<Book> findByNameAndAuthor(String name, String author);

    @Query("select * from book_details where name=:name")
    Flux<Book> getAllBooksByAuthor(String name);

    @Query("select * from book_details where name=:name AND author author=:author")
    Flux<Book> getAllBooksByAuthorAndName(String name, String author);

    @Query("select * from book_details where name LIKE:title")
    Flux<Book> searchBookByName(String title);
}
