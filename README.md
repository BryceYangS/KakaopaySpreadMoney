# 카카오페이 뿌리기 기능 API 구현

### 1. 환경구성
- Spring Boot 2.3.1.RELEASE
- Java 1.8
- Gradle
- Mongo DB
- JUnit 5


### 2. 설계

#### 2.1. API 설계
##### 2.1.1. 뿌리기 API
 - 뿌리기 위해 토큰 문자열을 랜덤으로 발행하며, `같은 채팅방에 동일 토큰이 입력되는 경우`를 막기 위해 유효성을 확인한다. 같은 채팅방 내 동일 한 토큰이 있을 경우 중복 토큰값이 없을 때까지 토큰을 랜덤 생성한다.
    - 토큰의 경우 영어대소문자 및 숫자를 조합해 생성한다.
 - 총 금액과 뿌릴 인원을 통해 뿌리는 금액에 대한 정보 entity를 생성한다.
 - 이러한 과정을 거쳐 아래와 같은 데이터를 생성한다.  
 - 토큰 id를 리턴한다.


 ```json
 {
     "token":"hgq",
     "roomId":"TEST",
     "spreadId":1234,
     "spreadStTime":"2020-06-27 15:05:51",
     "totalMoney":2000,
     "getterNum":10,
     "spreadDetailInfo":[
         {"no":0,"getterId":"none","spreadMoney":200},
         {"no":1,"getterId":"none","spreadMoney":200},
         {"no":2,"getterId":"none","spreadMoney":200},
         {"no":3,"getterId":"none","spreadMoney":200},
         {"no":4,"getterId":"none","spreadMoney":200},
         {"no":5,"getterId":"none","spreadMoney":200},
         {"no":6,"getterId":"none","spreadMoney":200},
         {"no":7,"getterId":"none","spreadMoney":200},
         {"no":8,"getterId":"none","spreadMoney":200},
         {"no":9,"getterId":"none","spreadMoney":200}
         ]
}
 ```
  
##### 2.1.2. 받기 API
 - 채팅방 id와 토큰값을 이용해 해당 뿌리기 정보를 불러오며, 받기를 위한 유효성 체크를 수행한다.
 - 유효성 체크 후 뿌린 돈을 순서대로 받도록 한다.
 - 받은 사람 id를 getterId에 입력하며 해당 정보를 DB에 업데이트한다. (아래의 경우 3456 아이디 회원이 200원을 받은 예시)
 - 받게 되는 돈을 리턴한다.
  
 ```json
 {
     "token":"hgq",
     "roomId":"TEST",
     "spreadId":1234,
     "spreadStTime":"2020-06-27 15:05:51",
     "totalMoney":2000,
     "getterNum":10,
     "spreadDetailInfo":[
         {"no":0,"getterId":"3456","spreadMoney":200},
         {"no":1,"getterId":"none","spreadMoney":200},
         {"no":2,"getterId":"none","spreadMoney":200},
         {"no":3,"getterId":"none","spreadMoney":200},
         {"no":4,"getterId":"none","spreadMoney":200},
         {"no":5,"getterId":"none","spreadMoney":200},
         {"no":6,"getterId":"none","spreadMoney":200},
         {"no":7,"getterId":"none","spreadMoney":200},
         {"no":8,"getterId":"none","spreadMoney":200},
         {"no":9,"getterId":"none","spreadMoney":200}
         ]
}
 ```

##### 2.1.3. 조회 API
 - 채팅방 id와 토큰값을 이용해 뿌리기 정보를 불러오며, 조회를 위한 유효성 체크를 수행한다.
 - 유효성 체크 후 조회 결과를 리턴한다.