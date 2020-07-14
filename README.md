[![Build Status](https://travis-ci.org/Meteorkor/Stream-Study.svg?branch=master)](https://travis-ci.org/Meteorkor/Stream-Study)
[![Coverage Status](https://coveralls.io/repos/github/Meteorkor/Stream-Study/badge.svg?branch=master)](https://coveralls.io/github/Meteorkor/Stream-Study?branch=master)
# Stream-Study

# 스트림, 데이터의 흐름

* 배열 또는 컬렉션, 또는 여러개의 조합으로 가공 및 필터링을 수행할 수 있도록 기능 제공
* for, foreach에 비해 코드 복잡도를 줄일 수 있음

* 람다를 활용하여 코드의 양을 줄이고 간결하게 표현 가능
* 간단하게 병렬처리 가능(parallel())
* primitive의 경우 IntStream이나 LongStream을 사용하지 않는다면, Boxing으로 인해 성능 저하가 발생할 수 있으니 주의 필요

# Stream 생성
* Array, Collection
* Stream
  * Stream.builder()
  * Stream.of()
  * Stream.empty()
  * Stream.generate()
  * Stream.iterate()
    * (final T seed, final UnaryOperator<T> f)
    * (T seed, Predicate<? super T> hasNext, UnaryOperator<T> next)
      * JDK9
  * Stream.concat()
* 기본타입(IntStream, LongStream), String, 파일 스트림(BytesReader)
* 병렬스트림

# 가공(transformer)
* Filtering
* Mapping
* Sorting
* Iterating

# 결과(terminal)
* Calculating
* Reduction
* Collecting
* Matching
* Iterating

# 주의 사항
* primitive
  * primitive 타입을 boxed 된 Stream을 사용하게 된다면 boxing unboxing이 반복되어 나타날수 있음
  * 필요에 따라 IntStream, LongStream, DoubleStream 을 잘 구분하여 사용할것
    * mapToInt, mapToLong, mapToDouble 도 마찬가지
* 병렬 스트림
  * 메인 스레드 혹은 일반 스레드에서 parallel()의 경우 ForkJoinPool.common을 사용
    * ForkJoinPool.commonPool()은 보통 Runtime.getRuntime().availableProcessors() 만큼의 스레드를 가지고 있음
    * 다른 각각 스레드에서 parallel()을 사용하는 경우에도 같은 ForkJoinPool.commonPool() 공유해서 사용하기 때문에 Blocking 작업으로 인해 전체적으로 성능이 떨어질수 있음
      * 방지하기 위해서는 ForkJoinPool을 신규로 만들고 신규로 만든 ForkJoinPool에 submit을 하면 ForkJoinPool.commonPool() 이 아닌 신규로 만든 Pool에서 병렬처리로 동작
  
* 파이프라이닝
  * Stream은 하나의 데이터를 파이프라이닝 방식으로 처리
    * {A,B,C}.map(변환1).map(변환2)... 를 수행할때, A,B,C 변환1 을 수행하는것이 아니라 A에 대해 변환1, 변환2, B에 대해 변환1, 변환2 방식으로 동작
      * 물론 병렬의 경우 이 작업들을 서로 다른 스레드에서 처리 할 수 있음
  * 흐름 정의에 따라 처리내용이 수행되지 않을 수 있음
    * {A,B,C}.peek().map().count() 의 경우 peek와 map이 count()에 영향을 주지 않기 때문에 동작하지 않을 수 있음
      * unit-test에서는 동작하지 않았으나 travis-ci에서는 동작하던...
    * {A,B,C}.peek().map() , terminal(결과처리들, calc, reduce, collect 등) 호출이 없을 경우 결국 peek나 map나 의미가 없기 때문에 수행되지 않음

# 더 살펴볼 내용
* StreamSupport(추후)
* Iterator to Stream
* Spliterator

