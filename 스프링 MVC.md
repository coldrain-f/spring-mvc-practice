#### MVC 기본 개념

**Model**: 평범한 자바 객체(POJO 객체)

화면에서 전달 받은 데이터를 담고 있는 객체 또는

화면에 전달할 데이터를 담고 있는 객체

**View**: HTML, JSP, Thymeleaf 등 전달받은 모델에서 데이터를 꺼내 화면을 보여주는 역할

**Controller**: 사용자의 요청을 처리하는 역할

- 화면에서 입력하고 전달한 데이터를 검증 또는 처리
- 모델 객체를 View 에 전달



MVC 패턴의 장점

- **동시 다발적 개발**: 백엔드 개발자와 프론트엔드 개발자가 독립적으로 개발을 진행할 수 있다.

- **높은 결합도**: 관련있는 기능을 하나의 컨트롤러로 묶을 수 있다.

  관련있는 View들을 그륩화 할 수 있다. 

  (Event 모델과 관련된 View 들은 View 디렉토리 하위에 그륩화해서 관리 등)

- **낮은 의존도**: 모델, 뷰, 컨트롤러는 각각 독립적이다. (서로에게 의존관계가 없다)

  만들어진 모델은 반드시 Thymeleaf 탬플릿으로 전달되지 않아도 된다.

- **개발 용이성**: 책임이 구분되어 있어 코드를 수정하는 것이 용이하다.



#### MVC 간단한 예제

**Model**

```java
@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class Event {

    //이벤트 이름
    private String name;

    //참가 인원수
    private int limitOfEnrollment;

    //시작일
    private LocalDateTime startDateTime;

    //종료일
    private LocalDateTime endDateTime;
}
```



**Service**

```java
@Service
public class EventService {

    public List<Event> getEvents() {
        Event event1 = Event.builder()
                .name("스프링 웹 MVC 스터디 1차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2021, 1, 10, 10, 0))
                .endDateTime(LocalDateTime.of(2021, 1, 17, 12, 0))
                .build();

        Event event2 = Event.builder()
                .name("스프링 웹 MVC 스터디 2차")
                .limitOfEnrollment(5)
                .startDateTime(LocalDateTime.of(2021, 1, 10, 10, 0))
                .endDateTime(LocalDateTime.of(2021, 1, 17, 12, 0))
                .build();

        return List.of(event1, event2);
    }
}
```

간단하게 두 개의 이벤트 List 를 반환해 주는 getEvents() 메서드를 정의한다.



**Controller**

```java
@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventservice;

    @RequestMapping(value = "/events", method = RequestMethod.GET)
    public String events(Model model) {
        model.addAttribute("events", eventservice.getEvents());

        return "events";
    }
}
```

EventService 를 생성자 주입으로 주입받고

웹 브라우저에서 "/events" 를 GET 방식으로 요청하면 

Model 에 `key = "events", value = 이벤트 목록`을 담아서

"events" 라는 이름의 View 에게 전달한다.



```html
<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>이벤트 목록</h1>
    <table>
        <thead>
        <tr>
            <th>이름</th>
            <th>참가 인원</th>
            <th>시작</th>
            <th>종료</th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="event : ${events}">
            <td th:text="${event.name}">이벤트 이름</td>
            <td th:text="${event.limitOfEnrollment}">100</td>
            <td th:text="${event.startDateTime}">2021년 1월 10일 오전 10시</td>
            <td th:text="${event.endDateTime}">2021년 1월 10일 오전 12시</td>
        </tr>
        </tbody>
    </table>
</body>
</html>
```

View 는 전달받은 Model로 화면을 구성한다.
