package com.kumar.reactive.controller;

import com.kumar.reactive.model.Book;
import com.kumar.reactive.service.BookService;
import jakarta.annotation.Nonnull;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public Mono<Book> create(@RequestBody Book book){
        return bookService.create(book);
    }

    @GetMapping
    public Flux<Book> getAll(){
        return bookService.getAll();
    }

    @GetMapping("/{bid}")
    public Mono<Book> get(@PathVariable("bid") int bookId){
        return bookService.get(bookId);
    }

    @PutMapping("/{bookId}")
    public Mono<Book> get(@RequestBody Book book, @PathVariable int bookId){
        return bookService.update(book, bookId);
    }

    @DeleteMapping("/{bookId}")
    public Mono<Void> delete(@PathVariable int bookId){
        return bookService.delete(bookId);
    }

    @GetMapping("/search")
    public Flux<Book> searchBooks(@RequestParam("query") String query){
        return bookService.searchBooks(query);
    }
}
