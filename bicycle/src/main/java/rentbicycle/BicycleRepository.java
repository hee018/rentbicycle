package rentbicycle;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel="bicycles", path="bicycles")
public interface BicycleRepository extends PagingAndSortingRepository<Bicycle, Long>{


}
