package rentbicycle;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="payments", path="payments")
public interface PaymentRepository extends PagingAndSortingRepository<Payment, Long>{
/*
Command 
> 구현관점으로 보았을 때 외부로부터 들어오는 API 
해당 Aggregate 를 변화시키는 커맨드를 작성

 Aggregate 를 변화시키는 채널을 Repository
Spring-Data-Rest 를 사용하여 Repository Pattern 으로 프로그램을 구현하면 Entity 의
lifecycle 에 해당하는 기본적인 CRUD 가 바로 생성이 되고, 해당 CRUD 에 해당하는 API (커맨드) 가 자동으로 생성
*/
    Payment findByTicketId(Long ticketId);
}
