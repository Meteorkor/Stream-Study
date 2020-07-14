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
* StreamSupport(추후)

# 가공(작성중)
* Filtering
* Mapping
* Sorting
* Iterating

# 결과
* Calculating
* Reduction
* Collecting
* Matching
* Iterating
