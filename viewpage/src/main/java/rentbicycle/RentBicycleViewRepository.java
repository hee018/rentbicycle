package rentbicycle;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentBicycleViewRepository extends CrudRepository<RentBicycleView, Long> {


}