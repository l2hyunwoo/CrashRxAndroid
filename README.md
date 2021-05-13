<h1 align="center"> CrashRxAndroid </h1>

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

데이터 흐름에 맞게 **알림(Based on Observer Pattern)**을 보내 **Subscriber**가 데이터를 처리할 수 있도록 한다

<h3> Observable Class </h3>

Observable은 옵저버 패턴을 구현한다. 객체의 상태 변화를 관찰하는 관찰자 목록을 객체에 등록하고,
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

<h3> ``just()`` </h3>

```java
    public static <T> Observable<T> just(T item1, ...,  T item10) {
        ObjectHelper.requireNonNull(item1, "item1 is null");
        ...
        ObjectHelper.requireNonNull(item10, "item10 is null");

        return fromArray(item1, item2, item3, item4, item5, item6, item7, item8, item9, item10);
    }
````

(에반데) 위와 같이 순서대로 데이터들을 받아오고 Observable Timeline(마블 다이어그램 위쪽 선)으로 발행

<h3> ``create()`` vs ``just()`` </h3>

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

<h4> RxJava is Declarative </h4>

내가 코드로 작성하는 것이 어떻게 작동되는 지를 명세하는 것이 아니라,
무엇인지 명세해주는 방식, 이 객체가 어떤 객체인지 정의함

<h3> ``subscribe()`` 함수 </h3>

Observable은 Factory로 객체를 정의하고 subscribe 함수로 데이터를 발행시킨다

```java
Disposable disposable() -> onError 이벤트 발생 시 Exception throw, 디버깅할 때 사용
Disposable subscribe(
    Consumer <? super T> onNext,
    Consumer <? super java.lang.Throwable> onError,
    Action onComplete
) -> 이벤트 처리
```

<h3> Disposable </h3>

onComplete 이벤트가 발생되었다면 dispose는 호출할 필요가 없는데...
-> 만약 처리가 안되면 메모리 릭 발생이 되니 CompositeDisposable Class 활용해서 객체가 destroy될 때 관계 해제




