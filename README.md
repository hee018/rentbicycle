# 공유 자전거 플랫폼(따릉이)

## 서비스 시나리오

#### 기능적 요구사항
1. 고객이 이용권을 선택하여 구매한다. (60분 1000원, 120분 2000원)
2. 고객이 결제한다.
3. 고객은 이용권을 취소할 수 있다.
4. 고객은 구매한 이용권을 사용하여 대여가능 상태의 자전거를 대여한다.
5. 자전거 대여시 고객에게 대여 안내 알림을 전송한다.
6. 자전거를 대여한 고객은 자전거 반납이 가능하다.
7. 자전거 반납시 고객에게 반납 안내 알림을 전송한다.
9. 관리자는 자전거를 등록, 삭제할 수 있다.
*****

#### 비기능적 요구사항    
1. 트랜잭션 
  - 결제 완료된 이후에 고객은 이용권 사용이 가능하다.(Sync)
  - 결제가 완료되지 않은 대기상태의 이용권은 자전거 대여가 불가능 해야 한다 (Sync)

2. 장애격리 
  - 자전거 대여 / 반납 은 24시간 받을 수 있어야 한다. .(Async 호출-event-driven)
  - 결제시스템이 과중되면 결제를 받지 않고 결제를 잠시 후에 하도록 유도한다. (Circuit breaker, fallback)

3. 성능
  - 고객이 자주 남은 대여시간을 확인 가능해야 한다 (CQRS)
  - 자전거 대여 상태 변경 시 고객에게 알림을 줄 수 있어야 한다 (Event Driven)
*****

#### 체크포인트
- 분석설계 (40)
  - 이벤트스토밍: 
    - 스티커 색상별 객체의 의미를 제대로 이해하여 헥사고날 아키텍처와의 연계 설계에 적절히 반영하고 있는가?
    - 각 도메인 이벤트가 의미있는 수준으로 정의되었는가?
    - 어그리게잇: Command와 Event 들을 ACID 트랜잭션 단위의 Aggregate 로 제대로 묶었는가?
    - 기능적 요구사항과 비기능적 요구사항을 누락 없이 반영하였는가?    

  - 서브 도메인, 바운디드 컨텍스트 분리
    - 팀별 KPI 와 관심사, 상이한 배포주기 등에 따른  Sub-domain 이나 Bounded Context 를 적절히 분리하였고 그 분리 기준의 합리성이 충분히 설명되는가?
      - 적어도 3개 이상 서비스 분리
    - 폴리글랏 설계: 각 마이크로 서비스들의 구현 목표와 기능 특성에 따른 각자의 기술 Stack 과 저장소 구조를 다양하게 채택하여 설계하였는가?
    - 서비스 시나리오 중 ACID 트랜잭션이 크리티컬한 Use 케이스에 대하여 무리하게 서비스가 과다하게 조밀히 분리되지 않았는가?
  - 컨텍스트 매핑 / 이벤트 드리븐 아키텍처 
    - 업무 중요성과  도메인간 서열을 구분할 수 있는가? (Core, Supporting, General Domain)
    - Request-Response 방식과 이벤트 드리븐 방식을 구분하여 설계할 수 있는가?
    - 장애격리: 서포팅 서비스를 제거 하여도 기존 서비스에 영향이 없도록 설계하였는가?
    - 신규 서비스를 추가 하였을때 기존 서비스의 데이터베이스에 영향이 없도록 설계(열려있는 아키택처)할 수 있는가?
    - 이벤트와 폴리시를 연결하기 위한 Correlation-key 연결을 제대로 설계하였는가?

  - 헥사고날 아키텍처
    - 설계 결과에 따른 헥사고날 아키텍처 다이어그램을 제대로 그렸는가?
    
- 구현 (35)
  - [DDD] 분석단계에서의 스티커별 색상과 헥사고날 아키텍처에 따라 구현체가 매핑되게 개발되었는가?
    - Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 데이터 접근 어댑터를 개발하였는가
    - [헥사고날 아키텍처] REST Inbound adaptor 이외에 gRPC 등의 Inbound Adaptor 를 추가함에 있어서 도메인 모델의 손상을 주지 않고 새로운 프로토콜에 기존 구현체를 적응시킬 수 있는가?
    - 분석단계에서의 유비쿼터스 랭귀지 (업무현장에서 쓰는 용어) 를 사용하여 소스코드가 서술되었는가?
  - Request-Response 방식의 서비스 중심 아키텍처 구현
    - 마이크로 서비스간 Request-Response 호출에 있어 대상 서비스를 어떠한 방식으로 찾아서 호출 하였는가? (Service Discovery, REST, FeignClient)
    - 서킷브레이커를 통하여  장애를 격리시킬 수 있는가?
  - 이벤트 드리븐 아키텍처의 구현
    - 카프카를 이용하여 PubSub 으로 하나 이상의 서비스가 연동되었는가?
    - Correlation-key:  각 이벤트 건 (메시지)가 어떠한 폴리시를 처리할때 어떤 건에 연결된 처리건인지를 구별하기 위한 Correlation-key 연결을 제대로 구현 하였는가?
    - Message Consumer 마이크로서비스가 장애상황에서 수신받지 못했던 기존 이벤트들을 다시 수신받아 처리하는가?
    - Scaling-out: Message Consumer 마이크로서비스의 Replica 를 추가했을때 중복없이 이벤트를 수신할 수 있는가
    - CQRS: Materialized View 를 구현하여, 타 마이크로서비스의 데이터 원본에 접근없이(Composite 서비스나 조인SQL 등 없이) 도 내 서비스의 화면 구성과 잦은 조회가 가능한가?

  - 폴리글랏 플로그래밍
    - 각 마이크로 서비스들이 하나이상의 각자의 기술 Stack 으로 구성되었는가?
    - 각 마이크로 서비스들이 각자의 저장소 구조를 자율적으로 채택하고 각자의 저장소 유형 (RDB, NoSQL, File System 등)을 선택하여 구현하였는가?
  - API 게이트웨이
    - API GW를 통하여 마이크로 서비스들의 집입점을 통일할 수 있는가?
    - 게이트웨이와 인증서버(OAuth), JWT 토큰 인증을 통하여 마이크로서비스들을 보호할 수 있는가?
- 운영 (25)
  - SLA 준수
    - 셀프힐링: Liveness Probe 를 통하여 어떠한 서비스의 health 상태가 지속적으로 저하됨에 따라 어떠한 임계치에서 pod 가 재생되는 것을 증명할 수 있는가?
    - 서킷브레이커, 레이트리밋 등을 통한 장애격리와 성능효율을 높힐 수 있는가?
    - 오토스케일러 (HPA) 를 설정하여 확장적 운영이 가능한가?
    - 모니터링, 앨럿팅: 
  - 무정지 운영 CI/CD (10)
    - Readiness Probe 의 설정과 Rolling update을 통하여 신규 버전이 완전히 서비스를 받을 수 있는 상태일때 신규버전의 서비스로 전환됨을 siege 등으로 증명 
    - Contract Test :  자동화된 경계 테스트를 통하여 구현 오류나 API 계약위반를 미리 차단 가능한가?
*****

# 분석/설계

## 
![image](https://user-images.githubusercontent.com/84304021/122719255-e9931000-d2a8-11eb-96b2-db0fc0083a53.png)

##
![image](https://user-images.githubusercontent.com/84304021/122719352-07f90b80-d2a9-11eb-9c7f-3816fa32bfff.png)

## Event Storming 결과

### MSAEz 로 모델링한 이벤트스토밍 결과:
http://www.msaez.io/#/storming/XPdLMjnOsOeUkU6QKEL93BKtOZw2/share/364ee9e4f63281fbf85e19d33157d892

### Event
![image](https://user-images.githubusercontent.com/84304021/122719840-b4d38880-d2a9-11eb-8168-93e454a0d896.png)

### 비적격 이벤트 제거
![image](https://user-images.githubusercontent.com/84304021/122719995-e187a000-d2a9-11eb-81b2-8559fd0fcf8e.png)

### Actor, Command
![image](https://user-images.githubusercontent.com/84304021/122720056-f2d0ac80-d2a9-11eb-95f1-4247caaf9d3e.png)

### Aggregate
![image](https://user-images.githubusercontent.com/84304021/122720147-0e3bb780-d2aa-11eb-94d6-3adf9b642202.png)

### Bounded Context
![image](https://user-images.githubusercontent.com/84304021/122720201-20b5f100-d2aa-11eb-961c-107936065980.png)

### message기능, Policy 추가
![image](https://user-images.githubusercontent.com/84304021/122720429-6ffc2180-d2aa-11eb-867e-42cf1b840cc7.png)

### Policy이동, 컨텍스트 매핑 
![image](https://user-images.githubusercontent.com/84304021/122720498-82765b00-d2aa-11eb-9500-27b99c162f6f.png)

### 완성된 모형
![image](https://user-images.githubusercontent.com/84304021/122720538-90c47700-d2aa-11eb-9792-b7ba12a72848.png)


### 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증
#### - 기능적 요구사항 검증
![image](https://user-images.githubusercontent.com/84304021/122721541-c322a400-d2ab-11eb-9c54-eaf10d159413.png)
     
     [빨간라인]
     1. 고객이 이용권을 선택하여 구매한다.(60분 1000원, 120분 2000원) → OK
     2. 고객이 결제한다. → OK
     
     [파란라인] 
     3. 고객은 이용권을 취소할 수 있다. → OK
     
     [초록라인]
     4. 고객은 구매한 이용권을 사용하여 대여 가능한 상태의 자전거를 대여한다 : 이용권 차감처리 -> OK
  
![image](https://user-images.githubusercontent.com/84304021/122721586-cf0e6600-d2ab-11eb-9975-96cbc1d8742e.png)

     [노란라인]
     1. 관리자는 자전거를 등록, 삭제할 수 있다 : OK
     
     [파란라인] 
     2. 자전거를 대여한 고객은 반납이 가능하다 : OK
     3. 자전거 반납시 고객에게 반납 완료 알림을 전송한다 : OK
     
     [빨간라인]
     4. 자전거 대여시 고객에게 자전거 대여 알림을 전송한다 : OK

#### - 비기능적 요구사항 검증
![image](https://user-images.githubusercontent.com/84304021/122722168-5e1b7e00-d2ac-11eb-86c1-0d0fd8f7e5ed.png)

    1. 티켓 구매는 결제가 처리되어야만 이용권 사용이 가능하고, 장애격리를 위해 CB를 설치함 (트랜잭션, 장애격리) -> 오류발견

    2. 고객이 자전거 대여 잔여 시간을 수시로 확인하도록 View Table 을 구성 (CQRS) (성능)

#### - [수정1] 자전거 대여 시 이용권 구매 여부 체크!
![image](https://user-images.githubusercontent.com/84304021/122722562-d6823f00-d2ac-11eb-9a5a-99bfa84bcae9.png)

    1. 티켓구매는 결제가 처리되어야만 이용권 사용이 가능하고, 장애격리를 위해 CB를 설치함 (트랜잭션, 장애격리)

    2. 구매된 티켓인지 체크하여 자전거 대여 할 수 있도록 수행함 (장애전파)

    3. 고객이 자전거 대여 잔여 시간을 수시로 확인하도록 View Table 을 구성 (CQRS) (성능)

*****

### 헥사고날 아키텍처 다이어그램 도출

![image](https://user-images.githubusercontent.com/84304021/122846201-4ee61000-d340-11eb-88a8-4f09c439b1b8.png)

```
- Chris Richardson, MSA Patterns 참고하여 Inbound adaptor와 Outbound adaptor를 구분함
- 호출관계에서 PubSub 과 Req/Resp 를 구분함
- 서브 도메인과 바운디드 컨텍스트의 분리:  각 팀의 KPI 별로 아래와 같이 관심 구현 스토리를 나눠가짐
```

*****
# 구현

분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트로 구현하였다. 

구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 8081 ~ 808n 이다)

```
cd ticket
mvn spring-boot:run

cd bicycle
mvn spring-boot:run 

cd payment
mvn spring-boot:run  

cd message
mvn spring-boot:run
```
## 동기식 호출 과 Fallback 처리
분석단계에서의 조건 중 하나로 ticket->payment 간의 호출은 동기식 일관성을 유지하는 트랜잭션으로 처리하기로 하였다. 
호출 프로토콜은 이미 앞서 Rest Repository 에 의해 노출되어있는 REST 서비스를 FeignClient 를 이용하여 호출하도록 한다.

- 결제서비스를 호출하기 위하여 FeignClient 를 이용하여 Service 대행 인터페이스 구현

```
# PaymentService.java

import org.springframework.cloud.openfeign.FeignClient;
....
@FeignClient(name="payment", url="http://localhost:8082")
public interface PaymentService {

    @RequestMapping(method= RequestMethod.GET, path="/payTicket")
    public boolean payTicket(@RequestParam("ticketId") Long ticketId, 
                             @RequestParam("ticketAmt") Long ticketAmt);

}
```
- ticket 구매 직후 (@PostPersist) 결제를 요청하도록 처리
```
# ticket.java

    @PostPersist
    public void onPostPersist(){
        Long ticketAmount = Long.decode(this.getTicketType() == "1"?"1000":"2000");

        boolean result = TicketApplication.applicationContext.getBean(rentbicycle.external.PaymentService.class)
            .payTicket(this.getTicketId(), ticketAmount);
        
        if(result) {
            TicketPurchased ticketPurchased = new TicketPurchased();
            ticketPurchased.setTicketId(this.getTicketId());
            ticketPurchased.setTicketStatus("ticketPurchased");
            ticketPurchased.setTicketType(this.getTicketType());
            ticketPurchased.setBuyerPhoneNum(this.getBuyerPhoneNum());

            BeanUtils.copyProperties(this, ticketPurchased);
            ticketPurchased.publishAfterCommit();
        }
    }
```
## DDD 의 적용
- 각 서비스내에 도출된 핵심 Aggregate Root 객체를 Entity 로 선언하였다 (예시는 ticket 마이크로 서비스)
```
# Ticket.java

@Entity
@Table(name="Ticket_table")
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ticketId;
    private String ticketStatus;
    private String ticketType;
    private String buyerPhoneNum;

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
    public String getBuyerPhoneNum() {
        return buyerPhoneNum;
    }

    public void setBuyerPhoneNum(String buyerPhoneNum) {
        this.buyerPhoneNum = buyerPhoneNum;
    }
}

```
- Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 별도 처리 없이 데이터 접근 어댑터를 자동 생성하기 위하여 Spring Data REST 의 RestRepository 를 적용하였다
```
# TicketRepository.java

@RepositoryRestResource(collectionResourceRel="tickets", path="tickets")
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long>{
}
```
- 적용 후 REST API 의 테스트
```
1. 티켓 구매
http http://localhost:8081/tickets ticketType=1 ticketStatus="ReadyForPay"

2. 자전거 등록
http http://localhost:8083/bicycles bicycleStatus="Registered"

3. 자전거 렌탈(bicycleId가 1이라고 가정)
http PATCH http://localhost:8083/bicycles/1 ticketId=1 usingTime=60

4. 티켓 상태 확인(ticketStatus가 ticketUsed로 변경되었는지 확인)
http GET http://localhost:8081/tickets/1
```
## Correlation

- PolicyHandler에서 처리 시 어떤 건에 대한 처리인지를 구별하기 위한 Correlation-key 구현을 이벤트 클래스 안의 변수로 전달받아 서비스간 연관 처리를 구현 (티켓 생성 시 구매, 자전거 렌탈시 티켓상태 변경, 환불 시 티켓 상태 변경 등)

- 티켓구매

- 자전거등록

- 자전거렌탈

- 티켓2 구매

- 티켓환불
- 
*****
# 운영

## CI/CD 설정

각 구현체들은 각자의 source repository 에 구성되었고, 사용한 CI/CD는 buildspec.yml을 이용한 AWS codebuild를 사용하였습니다.

- CodeBuild 프로젝트를 생성하고 AWS_ACCOUNT_ID, KUBE_URL, KUBE_TOKEN 환경 변수 세팅을 한다

```
SA 생성
```
![7](https://user-images.githubusercontent.com/61194075/122781158-80cc8780-d2ea-11eb-89f3-2db33dbcff53.PNG)

```
Role 생성
```
![8](https://user-images.githubusercontent.com/61194075/122781199-8aee8600-d2ea-11eb-95fb-1894547a2943.PNG)

```
Token 확인
kubectl -n kube-system get secret
```
![9](https://user-images.githubusercontent.com/61194075/122781247-9477ee00-d2ea-11eb-8ec1-471858998d9a.PNG)

```
buildspec.yml 파일 
마이크로 서비스 room의 yml 파일 이용하도록 세팅
```
![10](https://user-images.githubusercontent.com/61194075/122781658-03554700-d2eb-11eb-8c76-19110cb0bcd7.PNG)

- codebuild 실행
```
codebuild 프로젝트 및 빌드 이력
```
![image](https://user-images.githubusercontent.com/61194075/122769143-41e50480-d2df-11eb-9f51-ec33ba88613d.png)

![image](https://user-images.githubusercontent.com/61194075/122769003-224ddc00-d2df-11eb-9f19-2bf16ca3f5b4.png)

## 서킷브레이킹
서킷 브레이킹 : Spring FeignClient + Hystrix 옵션
- 시나리오는 ticket → payment가 RESTful Request/Response 로 연동하여 구현이 되어있고, 요청이 과도할 경우 CB 를 통하여 장애격리.

- Hystrix 설정: thread에서 600 밀리가 넘어서 유지되면 CB 회로가 닫히도록 (요청을 빠르게 실패처리, 차단) 설정
![image](https://user-images.githubusercontent.com/61194075/122858111-b017de80-d354-11eb-88bd-2c8249d7d7c2.png)

