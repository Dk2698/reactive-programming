package com.kumar.reactive;

import com.kumar.reactive.service.FluxService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
public class FluxTest {

    @Autowired
    private FluxService fluxService;

    @Test
    void testFlux(){
        this.fluxService.print();
        this.fluxService.getFlux().subscribe((data->{
            System.out.println(data);
            System.out.println("get data");
        }));

        fluxService.getFruisFlux().subscribe(System.out::println);
    }

    @Test
    public void mapTest(){
        final Flux<String> capFlux = fluxService.mapFlux();
        StepVerifier.create(capFlux)
                .expectNextCount(3)
                .verifyComplete();
    }
}
