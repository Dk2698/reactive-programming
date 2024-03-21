package com.kumar.reactive;

import com.kumar.reactive.model.Book;
import com.kumar.reactive.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void findMethodTest(){

        final Mono<Book> bookMono = bookRepository.findByName("messi4");
        StepVerifier.create(bookMono)
                .expectNextCount(0)
                .verifyComplete();
    }
}
