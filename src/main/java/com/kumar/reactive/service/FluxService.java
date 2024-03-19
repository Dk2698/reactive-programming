package com.kumar.reactive.service;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

@Service
public class FluxService {

    public void print() {
        System.out.println("flux service");
    }

    public Flux<String> getFlux() {
        return Flux.just("hello");
    }

    public Flux<String> getFruisFlux() {
        final List<String> fruitsFlux = List.of("Mongo", "Apple", "Banana");
        return Flux.fromIterable(fruitsFlux).log();
    }

    public Flux<Void> getBlankFlux() {
        return Flux.empty();
    }

    public Flux<String> mapFlux() {
        return getFlux().map(String::toUpperCase);
    }

    public Flux<String> filterFlux() {
        return getFlux().filter(name -> name.length() > 0).log();
    }

    public Flux<String> flatMap() {
        return getFlux().flatMap(name -> Flux.just(name.split(" ")));
    }

    public Flux<String> transform() {
        Function<Flux<String>, Flux<String>> function = (name) -> name.map(String::toUpperCase);
        return getFlux().transform(function).log();
    }

    public Flux<String> ifExample(int length) {
        return getFlux()
                .filter(name -> name.length() > length)
//                .defaultIfEmpty("hi")
                .switchIfEmpty(getFruisFlux())
                .log();
    }

    public Flux<String> concatExample(int length) {
        return Flux.concat(getFlux().delayElements(Duration.ofMillis(2000)), getFruisFlux())
                .log();
    }
    // merge, zip. zipWith

    public Flux<String> sideEffectFlux() {
        return getFlux().doOnNext(data -> {
                    System.out.println(data + " on next");
                }).doOnSubscribe(data -> {
                    System.out.println(data + " on subscribe");
                }).doOnEach(data -> {
                    System.out.println(data + " on each");
                }).doOnComplete(() -> {
                    System.out.println(" on subscribe");
                })
                .log();
    }


}
