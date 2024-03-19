package com.kumar.reactive;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;

@SpringBootTest
class ReactiveProgrammingApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void workingWithMono(){
		System.out.println("test started");
//		Mono -- Publisher ->0/1
		final Mono<String> monoPublisher = Mono.just("testing");
		monoPublisher.subscribe(new CoreSubscriber<String>() {
			@Override
			public void onSubscribe(Subscription subscription) {
				System.out.println("subscription done");
				subscription.request(1);
			}

			@Override
			public void onNext(String data) {
				System.out.println("data : "+ data );
			}

			@Override
			public void onError(Throwable throwable) {
				System.out.println("error :"+ throwable.getMessage());
			}

			@Override
			public void onComplete() {
				System.out.println("completed");
			}
		});
		//create mono
		final Mono<Object> errorMono = Mono.error(new RuntimeException("Error !"));

		final Mono<Object> justLoadingData = Mono.just("just loading data")
				.log()
				.then(errorMono);
		// consume the mono by subscribing
		justLoadingData.subscribe(data ->{
			System.out.println("data is "+data);
		});

		errorMono.subscribe(System.out::println);

	}

	@Test
	public void workingWithMonoZip(){
		final Mono<String> mono1 = Mono.just("mono1 mono");
		final Mono<String> mono2 = Mono.just("mono2");
		final Mono<Integer> mono3 = Mono.just(123344);

//		final Mono<Tuple2<String, String>> combineMono = Mono.zip(mono1, mono2);
		final Mono<Tuple3<String, String, Integer>> combineMono = Mono.zip(mono1, mono2, mono3);
		combineMono.subscribe(data->{
			System.out.println(data.getT1());
			System.out.println(data.getT2());
			System.out.println(data.getT3());
		});

		final Mono<Tuple2<String, String>> tuple2Mono = mono1.zipWith(mono2);
		tuple2Mono.subscribe(data->{
			System.out.println(data.getT1());
			System.out.println(data.getT2());
		});

		// synchronous function
		final Mono<String> resultMapMono = mono1.map(String::toUpperCase);
		resultMapMono.subscribe(System.out::println);

		final Mono<String[]> resultFlatMap = mono1.flatMap(value -> Mono.just(value.split(" ")));
		resultFlatMap.subscribe(data->{
			for (String s:data){
				System.out.println(s);
			}
		});


	}

	@Test
	public void workingWithFlux(){
		//		Flux -- Publisher --> 0...n
		final Mono<String> mono1 = Mono.just("mono1 mono");
//		final Flux<String> flatMapMany = mono1.flatMapMany(value -> Flux.just(value.split(" ")));
//
//		flatMapMany.subscribe(System.out::println);

		System.out.println(Thread.currentThread().getName());
		final Flux<String> stringFlux = mono1.concatWith(Mono.just("hello"))
				.log()
				.delayElements(Duration.ofMillis(2000));
		stringFlux.subscribe(data->{
			System.out.println(Thread.currentThread().getName());
			System.out.println(data);
		});

		System.out.println("terminated main thread");
	}
}
