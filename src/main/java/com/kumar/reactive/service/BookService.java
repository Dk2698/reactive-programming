package com.kumar.reactive.service;

import com.kumar.reactive.model.Book;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

    public Mono<Book> create(Book book);

    public Flux<Book> getAll();

    public Mono<Book> get(int bookId);

    public Mono<Book> update(Book book, int bookId);

    public Mono<Void> delete(int bookId);

    Flux<Book> searchBooks(String query);

    Flux<Book> search(String query);
}
