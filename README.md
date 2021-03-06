<h1 align="center"> ๐๐พCrash๐๐พ โจRxJava + RxAndroidโจ </h1>

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

๋ฐ์ดํฐ ํ๋ฆ์ ๋ง๊ฒ <b>์๋ฆผ(Based on Observer Pattern)</b>์ ๋ณด๋ด **Subscriber**๊ฐ ๋ฐ์ดํฐ๋ฅผ ์ฒ๋ฆฌํ  ์ ์๋๋ก ํ๋ค

<h3> Observable Class </h3>

Observable์ ์ต์ ๋ฒ ํจํด์ ๊ตฌํํ๋ค. ๊ฐ์ฒด์ ์ํ ๋ณํ๋ฅผ ๊ด์ฐฐํ๋ ๊ด์ฐฐ์ ๋ชฉ๋ก์ ๊ฐ์ฒด์ ๋ฑ๋กํ๊ณ ,<br/>
์ํ ๋ณํ๊ฐ ์์ ๋๋ง๋ค ๋ฉ์๋๋ฅผ ํธ์ถ -> ๊ด์ฐฐ์์๊ฒ ๋ณํ๋ฅผ ํต์ง

<h4> Why Observable? </h4>

> Observed๊ฐ ๊ด์ฐฐ์ ํตํด์ ์ป์ ๊ฒฐ๊ณผ๋ฅผ ์๋ฏธํ๋ค๋ฉด
>
> Observable์ ํ์ฌ๋ ๊ด์ฐฐ๋์ง ์์์ง๋ง ์ด๋ก ์ ํตํด์ ์์ผ๋ก ๊ด์ฐฐํ  ๊ฐ๋ฅ์ฑ์ ์๋ฏธํ๋ค.
> - The Function of Reason(์ด์ฑ์ ๊ธฐ๋ฅ)

<h3> Observable์ ์ข๋ฅ </h3>

- Observable
- Maybe
    - ๋ฐ์ดํฐ๊ฐ ๋ฐํ๋  ์ ์๊ฑฐ๋ ๋ฐํ๋์ง ์์๋ ์๋ฃ๋๋ ๊ฒฝ์ฐ
- Flowable
    - Back Pressure์ ๋์ํ๋ ๊ธฐ๋ฅ์ ์ถ๊ฐ๋ก ์ ๊ณต(Observable์์ ๋ฐ์ดํฐ๋ฅผ ๋ฌด์๋นํ๊ฒ ๋น ๋ฅด๊ฒ ๋ฐํํ  ๋)

<h3> Observable's Notification </h3>

- onNext
    - ๋ฐ์ดํฐ ๋ฐํ์ ์๋ ค์ค๋๋ค
- onComplete
    - ๋ชจ๋  ๋ฐ์ดํฐ์ ๋ฐํ์ด ์๋ฃ๋์์์ ์๋ ค์ค๋๋ค
    - ๋จ ํ๋ฒ๋ง ๋ฐ์
    - ๋ ์ด์ onNext๊ฐ ๋ฐ์์๋๊ฒ ํด์ผ๋จ
- onError
    - ์๋ฌ๊ฐ ๋ฐ์ํ  ๋
    - ์ดํ onNext, onComplete๊ฐ ์คํ์ด ์ ๋จ

<h3> Observable Factory Method </h3>

Observable์ ๊ฐ์ฒด๋ฅผ ์์ฑํ์ง ์๊ณ  Factory ํจํด์ผ๋ก ์์ฑ

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

(์๋ฐ๋ฐ) ์์ ๊ฐ์ด ์์๋๋ก ๋ฐ์ดํฐ๋ค์ ๋ฐ์์ค๊ณ  Observable Timeline(๋ง๋ธ ๋ค์ด์ด๊ทธ๋จ ์์ชฝ ์ )์ผ๋ก ๋ฐํ

<h3> create() vs just() </h3>

- just: onNext, onComplete, onError ์ปค์คํํ  ํ์์์
- create: ๊ฐ๋ฐ์๊ฐ ์ง์  ์ฝ๋ฐฑ์ ์ค์ 

```kotlin
        val taskObservableCreate = Observable
            .create { emitter: ObservableEmitter<Task> ->
                emitter.onNext(Task(description = "", isComplete = false, priority = 0))
                // ๋๋ด๋ ค๋ฉด onComplete ํธ์ถ
                emitter.onComplete()
            }
            .subscribe { Log.d(TAG, "$it") }
```

<h4> ์ฃผ์์  </h4>

- Observable์ด dispose๋  ๋ ์ฝ๋ฐฑ ํด์ง(memory leak)
- ๊ตฌ๋ํ๋ ๋์์๋ง onNext, onComplete ํธ์ถ
- ์๋ฌ๋ ์ค์ง onError์์๋ง
- BackPressure๋ ์ง์  ์ฒ๋ฆฌ

<h4> TMI) RxJava is Declarative </h4>

๋ด๊ฐ ์ฝ๋๋ก ์์ฑํ๋ ๊ฒ์ด ์ด๋ป๊ฒ ์๋๋๋ ์ง๋ฅผ ๋ช์ธํ๋ ๊ฒ์ด ์๋๋ผ,<br/>
๋ฌด์์ธ์ง ๋ช์ธํด์ฃผ๋ ๋ฐฉ์, ์ด ๊ฐ์ฒด๊ฐ ์ด๋ค ๊ฐ์ฒด์ธ์ง ์ ์ํจ

<h3> subscribe() ํจ์ </h3>

Observable์ Factory๋ก ๊ฐ์ฒด๋ฅผ ์ ์ํ๊ณ  subscribe ํจ์๋ก ๋ฐ์ดํฐ๋ฅผ ๋ฐํ์ํจ๋ค

```java
Disposable disposable() -> onError ์ด๋ฒคํธ ๋ฐ์ ์ Exception throw, ๋๋ฒ๊นํ  ๋ ์ฌ์ฉ
Disposable subscribe(
    Consumer <? super T> onNext,
    Consumer <? super java.lang.Throwable> onError,
    Action onComplete
) -> ์ด๋ฒคํธ ์ฒ๋ฆฌ
```

<h3> from~ : Observable Factory Method </h3>

- fromArray, fromIterable
    - Array, ArrayList, Queue ๋ฑ Java(Kotlin) Collections์์ ์ ๊ณตํ๋ ์๋ฃ๊ตฌ์กฐ ํ์ฉ Observable ์์ฑ
- fromCallable
    - ๋น๋๊ธฐ ์ฒ๋ฆฌ Wrapper Class์ธ Callable์ return ๊ฐ์ ํ์ฉํ์ฌ Observable ์์ฑ
- fromFuture
    - future์ return ๊ฐ์ ํ์ฉํ์ฌ Observable ์์ฑ
- fromPublisher
    - publisher(Java 9) ํ์ฉํ์ฌ Observable ์์ฑ

<h2> Disposable </h2>

onComplete ์ด๋ฒคํธ๊ฐ ๋ฐ์๋์๋ค๋ฉด dispose๋ ํธ์ถํ  ํ์๊ฐ ์๋๋ฐ...<br/>
-> ๋ง์ฝ ์ฒ๋ฆฌ๊ฐ ์๋๋ฉด ๋ฉ๋ชจ๋ฆฌ ๋ฆญ ๋ฐ์์ด ๋๋ CompositeDisposable Class ํ์ฉํด์ ๊ฐ์ฒด๊ฐ destroy๋  ๋ ๊ด๊ณ ํด์ 

<h2> Single: Special Type of Observable </h2>

Observable์ ๋ฐ์ดํฐ ๋ฌดํ ๋ฐํ ์๊ฐ๋ฅ,<br/>
But, ``Single``์ ์ค๋ก์ง ํ๋!

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

Maybe ํด๋์ค๋ ๋ฐ์ดํฐ๋ฅผ ํ๋๋ง ๋ฐํํ์ง๋ง 0๊ฐ๋ ๋ฐํํ  ์ ์์!<br/>
์ฆ, onComplete ๋ฉ์๋๋ฅผ ํ๋ ๋ ์ถ๊ฐํด์ ๊ตฌํํ๋ ํ์

<h2> Cold Observavbles vs Hot Observables </h2>

<h4> Cold Observavbles </h4>

- subscribe ํจ์๋ฅผ ํธ์ถํ์ฌ ๊ตฌ๋์ ํด์ผ ๋ฐ์ดํฐ๊ฐ ๋ฐํ๋จ
- ์น/DB/์๋ฒ ์์ฒญ๊ณผ ๊ฐ์ URL(๋ฐ์ดํฐ) ์ง์ ํ๊ณ  ์์ฒญ์ ๋ณด๋ด์ ๊ฒฐ๊ณผ๋ฅผ ๋ฐ์์ค๋ ๋ก์ง์ Cold Observable๋ก ๊ตฌํ

<h4> Hot Observables </h4>

- ๊ตฌ๋์๊ฐ ์๋ ์๋ ๋ฐ์ดํฐ๊ฐ ๊ณ์ ๋ฐํ๋๋ Observable
    - ์ฌ๋ฌ ๊ตฌ๋์๋ฅผ ๋์์ ๊ตฌ๋ํ  ์ ์์
- ์ฃผ์, ๋ง์ฐ์ค(ํค๋ณด๋) Event, ์ผ์(์ฃผ์) ๋ฐ์ดํฐ ๋ฑ
    - ์ค์๊ฐ์ผ๋ก ๊ณ์ ๋ฐ์์์ ํ์ํ  ๋์๋ Hot Observable๋ก ๊ตฌํ
- ๋ฐฐ์(BackPressure)์ ๋ฌด์กฐ๊ฑด ๊ณ ๋ คํด์ผ๋จ
    - ๋ฐํ์๋์ ๊ตฌ๋์๋์ ์ฐจ์ด๊ฐ ํด ๋ ๋ฐ์

<h3> Switch Cold to Hot </h3>

- Subject ํด๋์ค
- ConnectableObservable

<h3> Subject ํด๋์ค </h3>

Subject ํด๋์ค๋ ๊ตฌ๋์์ Cold Observable์ ํน์ฑ์ด ๋ชจ๋ ๊ณต์กดํ๋ค
- ๋ฐ์ดํฐ๋ฅผ ๋ฐํํ  ์๋ ์๊ณ , ๋ฐํ๋ ๋ฐ์ดํฐ๋ฅผ ๋ฐ๋ก ์ฒ๋ฆฌํ  ์๋ ์์

- AsyncySubject
- BehaviorSubject
- PublishSubject
- ReplaySubject

<h4> AsyncSubject </h4>

์๋ฃ๋๊ธฐ ์  **๋ง์ง๋ง** ๋ฐ์ดํฐ์๋ง ๊ด์ฌ, ์ด์  ๋ฐ์ดํฐ๋ ๋ฌด์

```kotlin
// AsyncSubject ๊ฐ์ฒด ์์ฑ
val subject = AsyncSubject.create<String>()

// Subject์ ๊ตฌ๋์ ์ค์ 
subject.subscribe { Log.d(TAG, it) }

// Subject ๋ฐํ(์ด๊ฑด ๋ฌด์)
subject.onNext("Hi")
subject.onNext("Hello")

// Subject์ ๊ตฌ๋์ ์ค์ 
subject.subscribe { Log.d(TAG, "${it + " Second}") }

// Subject ๋ฐํ(์ด๊ฑด ๋ฌด์)
subject.onNext("HyunWoo")
subject.onComplete()
subject.onNext("Fake")
subject.subscribe { Log.d(TAG, "${it + " Third}") }

// ์ต์ข ๊ฒฐ๊ณผ, onComplete๊ฐ ํธ์ถ๋๊ธฐ ์ง์ ์ ๋ง์ง๋ง ๊ฒฐ๊ณผ๋ง ์ฒ๋ฆฌ, ์ดํ onNext๋ Fake
// onComplete๊ฐ ํธ์ถ๋ ์ดํ์ subscriber๋ ์ต์ข๊ฐ๋ง ๊ฐ์ ธ์ด
TAG: HyunWoo
TAG: HyunWoo Second
TAG: HyunWoo Third
```

<h4> BehaviorSubject </h4>

๊ตฌ๋์ ํ๋ฉด ๊ฐ์ฅ ์ต๊ทผ ๊ฐ ํน์ ๊ธฐ๋ณธ ๊ฐ์ ๋๊ฒจ ๋ฐ์

```kotlin
// BehaviorSubject ์์ฑ
val subject = BehaviorSubject.createDefault<String>("DEFAULT")

// ๋ํดํธ ๊ฐ ์ถ๋ ฅ๋จ(์ฒ์์๋)
subject.subscribe { Log.d(TAG, "First Subscriber -> $it") }

// ๋ฐํ
subject.onNext("1")
subject.onNext("3")

// ๊ตฌ๋์ ํ๋ ๋ ์ถ๊ฐ
subject.subscribe { Log.d(TAG, "Second Subscriber -> $it") }

// ๋ฐํ
subject.onNext("5")
subject.onComplete()

// ๊ฒฐ๊ณผ
TAG: First Subscriber -> 6
TAG: First Subscriber -> 1
TAG: First Subscriber -> 3
TAG: Second Subscriber -> 3
TAG: First Subscriber -> 5
TAG: Second Subscriber -> 5
```

<h4> PublisherSubject </h4>

๊ฐ์ฅ ์ผ๋ฐ์ ์ธ Subject ํด๋์ค, ``subscribe()`` ํจ์๋ฅผ ํธ์ถํ๋ฉด ๊ฐ์ ๋ฐํํ๊ธฐ ์์ํ๋ค.
์ดํ์ ๊ตฌ๋์ ํด๋ ์ต๊ทผ์ ๊ฐ์ ๋ฐ์์ค์ง ์์(BehaviorSubject์ ๋ค๋ฅธ ์ )

```kotlin
val subject = PublisherSubject.create<String>()

// ๊ตฌ๋ ์์
subject.subscribe { Log.d(TAG, "First Subscriber -> $it") }

// ๋ฐํ
subject.onNext("1")
subject.onNext("3")

// ๊ตฌ๋์ ํ๋ ๋ ์ถ๊ฐ
subject.subscribe { Log.d(TAG, "Second Subscriber -> $it") }

// ๋ฐํ
subject.onNext("5")
subject.onComplete()

// ๊ฒฐ๊ณผ
TAG: First Subscriber -> 1
TAG: First Subscriber -> 3
TAG: First Subscriber -> 5
TAG: Second Subscriber -> 5
```

<h4> ReplaySubject : ์ทจ๊ธ ์ฃผ์ </h4>

๋ฐ์ดํฐ์ ์ฒ์๋ถํฐ ๋๊ฐ์ง ๋ฐํํ๋ ๊ฒ์ ๋ณด์ฅํด์ค
๋ชจ๋  ๋ฐ์ดํฐ ์ ์ฅํ  ๋ ๋ฉ๋ชจ๋ฆฌ ๋ฆญ์ด ๋๋ ๊ฐ๋ฅ์์ซ ์ผ๋ํด์ผ ๋จ

```kotlin
val subject = ReplaySubject.create<String>()

// ๊ตฌ๋ ์์
subject.subscribe { Log.d(TAG, "First Subscriber -> $it") }

// ๋ฐํ
subject.onNext("1")
subject.onNext("3")

// ๊ตฌ๋์ ํ๋ ๋ ์ถ๊ฐ
subject.subscribe { Log.d(TAG, "Second Subscriber -> $it") }

// ๋ฐํ
subject.onNext("5")
subject.onComplete()

// ๊ฒฐ๊ณผ
TAG: First Subscriber -> 1
TAG: First Subscriber -> 3
TAG: Second Subscriber -> 1
TAG: Second Subscriber -> 3
TAG: First Subscriber -> 5
TAG: Second Subscriber -> 5
```

<h3> ConnectableObservable </h3>

Cold Observable์ด๊ธด ํ๋ฐ ์ฌ๋ฌ๋ช์ ๊ตฌ๋์๋ค์๊ฒ ์ด์ฃผ๊ณ  ์ถ์ ๋!

```
val dataList = listOf("1", "3", "5")

// Cold Observable ๋ง๋ค๊ธฐ
val observableData = Observable.interval(100L, TimeUnit.MILLISECONDS)
    .map(Long::intValue)
    .map { dataList[it] }
    .take(dataList.length)

// ConnectableObservable ๋ง๋ค๊ธฐ
val dataSource = observableData.publish()

// subscribe(ConnectableObservable, ์์๋ค๊ฐ ์ฐ๊ฒฐ๋ง)
dataSource.subscribe { Log.d(TAG, "First Subscriber -> $it") }
dataSource.subscribe { Log.d(TAG, "Second Subscriber -> $it") }

// ConnectableObservable๊ณผ ์ฐ๊ฒฐ
dataSource.connect()

// Hot Observable์ ํน์ฑ -> ์ผ์  ์๊ฐ์ด ์ง๋๋ฉด ๊ทธ ์ดํ๋ก ๋ฐํ๋ ๋ฐ์ดํฐ๋ง ๋ฐ์์ฌ ์ ์์
Thread.sleep(250L)
dataSource.subscribe { Log.d(TAG, "Third Subscriber -> $it") }

// ๊ฒฐ๊ณผ
TAG: First Subscriber -> 1
TAG: Second Subscriber -> 1
TAG: First Subscriber -> 3
TAG: Second Subscriber -> 3
TAG: First Subscriber -> 5
TAG: Second Subscriber -> 5
TAG: Third Subscriber -> 5
```

<h2> ํ ํ ์ ๋ฆฌ </h2>

<h3> ๋ฐ์ดํฐ ๋ฐํ์(DataSource) </h3>

- Observable
- Sigle
- Maybe
- Subject
- Completable

<h3> ๋ฐ์ดํฐ ์์ ์ </h3>

- ๊ตฌ๋์(Subscriber)
- ์ต์ ๋ฒ(Observer)
- ์๋น์(Consumer)
    - Java 8๊ณผ ๊ฐ์ ์ด๋ฆ์ ์ฌ์ฉํ๊ธฐ ์ํด


