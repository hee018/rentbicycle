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
결제 완료된 이후에 고객은 이용권 사용이 가능하다.(Sync)
결제가 완료되지 않은 대기상태의 이용권은 자전거 대여가 불가능 해야 한다 (Sync)

2. 장애격리 
자전거 대여 / 반납 은 24시간 받을 수 있어야 한다. .(Async 호출-event-driven)
결제시스템이 과중되면 결제를 받지 않고 결제를 잠시 후에 하도록 유도한다. (Circuit breaker, fallback)

3. 성능
고객이 자주 남은 대여시간을 확인 가능해야 한다 (CQRS)
자전거 대여 상태 변경 시 고객에게 알림을 줄 수 있어야 한다 (Event Driven)
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
![image](https://user-images.githubusercontent.com/84304021/122719255-e9931000-d2a8-11eb-96b2-db0fc0083a53.png){: width="70%" height="70%"}

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


#### 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증
#### - 기능적 요구사항 검증
![2-1](https://user-images.githubusercontent.com/61194075/122499564-18e91900-d02c-11eb-83ba-0fcf56c2bf4c.PNG)
     
     [빨간라인]
     1. 고객이 이용권을 선택하여 구매한다.(60분 1000원, 120분 2000원) → OK
     2. 고객이 결제한다. → OK
     
     [파란라인] 
     3. 고객은 이용권을 취소할 수 있다. → OK
     
     [초록라인]
     4. 고객은 구매한 이용권을 사용하여 대여가능 상태의 자전거를 대여한다. → 이용권에 대한 차감이 되지 않음(오류 발견)     

##### [수정사항 1] 자전거 대여 시 이용권 차감(ticket → updateTicket) 처리 및 재검증!
![3-2](https://user-images.githubusercontent.com/61194075/122499588-20a8bd80-d02c-11eb-8bf3-faba08179a7d.PNG)

     [초록라인]
     4. 고객은 구매한 이용권을 사용하여 대여가능 상태의 자전거를 대여한다. → 이용권 차감처리 → OK
     
     [빨간라인]
     6. 자전거를 대여한 고객은 자전거 반납이 가능하다. → OK
     7. 자전거 반납시 고객에게 반납 안내 알림을 전송한다. → OK
     ![2-1](https://user-images.githubusercontent.com/61194075/122499552-15ee2880-d02c-11eb-93df-26023e7ca040.PNG)

     [파란라인] 
     8. 자전거 반납후 기본 대여시간 초과 하여 추가요금 발생시 결제 알림을 전송한다. → OK
     
     [노란라인]
     9. 관리자는 자전거를 등록, 삭제할 수 있다. → OK

#### - 비기능적 요구사항 검증
[ISSUE 1] 이용권은 다건을 만들어서 관리하려 했으나 ticket Aggregate에서 수량관리를 할 수 없는 구조이므로 변경필요
![image](https://user-images.githubusercontent.com/61194075/122494190-de7b7e00-d023-11eb-98d3-9f634f43bb81.png)

##### [수정사항 2] 1개의 이용권을 구매해서 대여까지 할 수 있도록 구조변경
- 이용권의 상태만 변경하면 되므로 confirmTicket, confirmCancelTicket은 updateTicket과 동일한 기능이므로 병합
![image](https://user-images.githubusercontent.com/61194075/122495489-1a631300-d025-11eb-8b5b-1eebb709d2a1.png)





### 구현

*****



