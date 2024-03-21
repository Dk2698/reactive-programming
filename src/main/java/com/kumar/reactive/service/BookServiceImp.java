package com.kumar.reactive.service;

import com.kumar.reactive.model.Book;
import com.kumar.reactive.repository.BookRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImp(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Mono<Book> create(Book book) {
        System.out.println(Thread.currentThread().getName());
        final Mono<Book> bookMono = bookRepository.save(book).doOnNext(data->{
            System.out.println(Thread.currentThread().getName());
        });
        return bookMono;
    }

    @Override
    public Flux<Book> getAll() {
        return bookRepository.findAll().delayElements(Duration.ofSeconds(2)).log();
    }

    @Override
    public Mono<Book> get(int bookId) {
        return bookRepository.findById(String.valueOf((Integer) bookId));
    }

    @Override
    public Mono<Book> update(Book book, int bookId) {
        final Mono<Book> oldBook = bookRepository.findById(String.valueOf((Integer) bookId));
        return oldBook.flatMap(book1 -> {
            book1.setName(book.getName());
            book1.setPublisher(book.getPublisher());
            book1.setAuthor(book1.getAuthor());
            book1.setDescription(book.getDescription());
            return bookRepository.save(book1);
        });
    }

    @Override
    public Mono<Void> delete(int bookId) {
        return bookRepository.findById(String.valueOf(bookId))
                .flatMap(book -> bookRepository.delete(book));
    }

    @Override
    public Flux<Book> searchBooks(String titleKeyword) {
        return this.bookRepository.searchBookByName(titleKeyword);
    }

    @Override
    public Flux<Book> search(String query) {
        return null;
    }
}
