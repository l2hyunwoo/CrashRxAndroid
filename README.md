<h1 align="center"> 👊🏾Crash👊🏾 ✨RxJava + RxAndroid✨ </h1>

<h2> Study On My Way (feat. Marble Diagram) </h2>

- Obervable Class
    - Hot Observable
    - Cold Observable
- Rx Function
- Operator
- Scheduler
    - ``subscribeOn``
    - ``observeOn``

<h2> Observable </h2>

데이터 흐름에 맞게 <b>알림(Based on Observer Pattern)</b>을 보내 **Subscriber**가 데이터를 처리할 수 있도록 한다

<h3> Observable Class </h3>

Observable은 옵저버 패턴을 구현한다. 객체의 상태 변화를 관찰하는 관찰자 목록을 객체에 등록하고,<br/>
상태 변화가 있을 때마다 메서드를 호출 -> 관찰자에게 변화를 통지

<h4> Why Observable? </h4>

> Observed가 관찰을 통해서 얻은 결과를 의미한다면
>
> Observable은 현재는 관찰되지 않았지만 이론을 통해서 앞으로 관찰할 가능성을 의미한다.
> - The Function of Reason(이성의 기능)

<h3> Observable의 종류 </h3>

- Observable
- Maybe
    - 데이터가 발행될 수 있거나 발행되지 않아도 완료되는 경우
- Flowable
    - Back Pressure에 대응하는 기능을 추가로 제공(Observable에서 데이터를 무자비하게 빠르게 발행할 때)

<h3> Observable's Notification </h3>

- onNext
    - 데이터 발행을 알려줍니다
- onComplete
    - 모든 데이터의 발행이 완료되었음을 알려줍니다
    - 단 한번만 발생
    - 더 이상 onNext가 발생안되게 해야됨
- onError
    - 에러가 발생할 때
    - 이후 onNext, onComplete가 실행이 안 됨

<h3> Observable Factory Method </h3>

Observable은 객체를 생성하지 않고 Factory 패턴으로 생성

- create(), just(), (Deprecated) from()
- fromArray(), fromIterable(), fromCallable(), fromFuture(), fromPublisher()
- interval(), range(), timer(), defer()

<h3> just() </h3>

```java
    public static <T> Observable<T> just(T item1, ...,  T item10) {
        ObjectHelper.requireNonNull(item1, "item1 is null");
        ...
        ObjectHelper.requireNonNull(item10, "item10 is null");

        return fromArray(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10);
    }
````

(에반데) 위와 같이 순서대로 데이터들을 받아오고 Observable Timeline(마블 다이어그램 위쪽 선)으로 발행

<h3> create() vs just() </h3>

- just: onNext, onComplete, onError 커스텀할 필요없음
- create: 개발자가 직접 콜백을 설정

```kotlin
        val taskObservableCreate = Observable
            .create { emitter: ObservableEmitter<Task> ->
                emitter.onNext(Task(description = "", isComplete = false, priority = 0))
                // 끝내려면 onComplete 호출
                emitter.onComplete()
            }
            .subscribe { Log.d(TAG, "$it") }
```

<h4> 주의점 </h4>

- Observable이 dispose될 대 콜백 해지(memory leak)
- 구독하는 동안에만 onNext, onComplete 호출
- 에러는 오직 onError에서만
- BackPressure는 직접 처리

<h4> TMI) RxJava is Declarative </h4>

내가 코드로 작성하는 것이 어떻게 작동되는 지를 명세하는 것이 아니라,<br/>
무엇인지 명세해주는 방식, 이 객체가 어떤 객체인지 정의함

<h3> subscribe() 함수 </h3>

Observable은 Factory로 객체를 정의하고 subscribe 함수로 데이터를 발행시킨다

```java
Disposable disposable() -> onError 이벤트 발생 시 Exception throw, 디버깅할 때 사용
Disposable subscribe(
    Consumer <? super T> onNext,
    Consumer <? super java.lang.Throwable> onError,
    Action onComplete
) -> 이벤트 처리
```

<h3> from~ : Observable Factory Method </h3>

- fromArray, fromIterable
    - Array, ArrayList, Queue 등 Java(Kotlin) Collections에서 제공하는 자료구조 활용 Observable 생성
- fromCallable
    - 비동기 처리 Wrapper Class인 Callable의 return 값을 활용하여 Observable 생성
- fromFuture
    - future의 return 값을 활용하여 Observable 생성
- fromPublisher
    - publisher(Java 9) 활용하여 Observable 생성

<h2> Disposable </h2>

onComplete 이벤트가 발생되었다면 dispose는 호출할 필요가 없는데...<br/>
-> 만약 처리가 안되면 메모리 릭 발생이 되니 CompositeDisposable Class 활용해서 객체가 destroy될 때 관계 해제

<h2> Single: Special Type of Observable </h2>

Observable은 데이터 무한 발행 쌉가능,<br/>
But, ``Single``은 오로지 하나!

<h3> How to create it </h3>

```kotlin
// 1. Observable -> Single
Single.fromObservable(source)
    // Single<T>
    .subscrbe(...)

// 2. single()
Observable.just("Hello! World!")
    .single("Default value")
    // Single<T>
    .subscribe(...)

// 3. first()
Observable.fromArray(source)
    .first("default")
    // Single<T>
    .subscribe(...)

// 4. empty() -> single()
Observable.empty()
    .single("default")
    // Single<T>
    .subscribe(...)

// 5. take(1) -> single()
Observable.fromArray(source)
    .take(1)
    .single("default")
    // Single<T>
    .subscribe(...)
```

<h2> Maybe = Single + onComplete </h2>

Maybe 클래스는 데이터를 하나만 발행하지만 0개도 발행할 수 있음!<br/>
즉, onComplete 메서드를 하나 더 추가해서 구현하는 형식

<h2> Cold Observavbles vs Hot Observables </h2>

<h4> Cold Observavbles </h4>

- subscribe 함수를 호출하여 구독을 해야 데이터가 발행됨
- 웹/DB/서버 요청과 같은 URL(데이터) 지정하고 요청을 보내서 결과를 받아오는 로직은 Cold Observable로 구현

<h4> Hot Observables </h4>

- 구독자가 있던 없던 데이터가 계속 발행되는 Observable
    - 여러 구독자를 동시에 구독할 수 있음
- 주식, 마우스(키보드) Event, 센서(주식) 데이터 등
    - 실시간으로 계속 받아와서 표시할 때에는 Hot Observable로 구현
- 배압(BackPressure)을 무조건 고려해야됨
    - 발행속도와 구독속도의 차이가 클 때 발생

<h3> Switch Cold to Hot </h3>

- Subject 클래스
- ConnectableObservable

<h3> Subject 클래스 </h3>

Subject 클래스는 구독자와 Cold Observable의 특성이 모두 공존한다
- 데이터를 발행할 수도 있고, 발행된 데이터를 바로 처리할 수도 있음

- AsyncySubject
- BehaviorSubject
- PublishSubject
- ReplaySubject

<h4> AsyncSubject </h4>

완료되기 전 **마지막** 데이터에만 관심, 이전 데이터는 무시

```kotlin
// AsyncSubject 객체 생성
val subject = AsyncSubject.create<String>()

// Subject의 구독자 설정
subject.subscribe { Log.d(TAG, it) }

// Subject 발행(이건 무시)
subject.onNext("Hi")
subject.onNext("Hello")

// Subject의 구독자 설정
subject.subscribe { Log.d(TAG, "${it + " Second}") }

// Subject 발행(이건 무시)
subject.onNext("HyunWoo")
subject.onComplete()
subject.onNext("Fake")
subject.subscribe { Log.d(TAG, "${it + " Third}") }

// 최종 결과, onComplete가 호출되기 직전의 마지막 결과만 처리, 이후 onNext는 Fake
// onComplete가 호출된 이후의 subscriber는 최종값만 가져옴
TAG: HyunWoo
TAG: HyunWoo Second
TAG: HyunWoo Third
```

<h4> BehaviorSubject </h4>

구독을 하면 가장 최근 값 혹은 기본 값을 넘겨 받음

```kotlin
// BehaviorSubject 생성
val subject = BehaviorSubject.createDefault<String>("DEFAULT")

// 디폴트 값 출력됨(처음에는)
subject.subscribe { Log.d(TAG, "First Subscriber -> $it") }

// 발행
subject.onNext("1")
subject.onNext("3")

// 구독자 하나 더 추가
subject.subscribe { Log.d(TAG, "Second Subscriber -> $it") }

// 발행
subject.onNext("5")
subject.onComplete()

// 결과
TAG: First Subscriber -> 6
TAG: First Subscriber -> 1
TAG: First Subscriber -> 3
TAG: Second Subscriber -> 3
TAG: First Subscriber -> 5
TAG: Second Subscriber -> 5
```

<h4> PublisherSubject </h4>

가장 일반적인 Subject 클래스, ``subscribe()`` 함수를 호출하면 값을 발행하기 시작한다.
이후에 구독을 해도 최근의 값을 받아오지 않음(BehaviorSubject와 다른 점)

```kotlin
val subject = PublisherSubject.create<String>()

// 구독 시작
subject.subscribe { Log.d(TAG, "First Subscriber -> $it") }

// 발행
subject.onNext("1")
subject.onNext("3")

// 구독자 하나 더 추가
subject.subscribe { Log.d(TAG, "Second Subscriber -> $it") }

// 발행
subject.onNext("5")
subject.onComplete()

// 결과
TAG: First Subscriber -> 1
TAG: First Subscriber -> 3
TAG: First Subscriber -> 5
TAG: Second Subscriber -> 5
```
