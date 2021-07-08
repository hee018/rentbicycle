package rentbicycle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import java.util.List;

public interface RentBicycleViewRepository extends CrudRepository<RentBicycleView, Long> {

    List<RentBicycleView> findByBicycleId(Long bicycleId);
    List<RentBicycleView> findByPaymentId(Long paymentId);
    Optional<RentBicycleView> findByTicketId(Long ticketId);

}