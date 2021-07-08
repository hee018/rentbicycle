
package rentbicycle.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@FeignClient(name="bicycle", url="http://localhost:8083")
public interface BicycleService {

    @RequestMapping(method= RequestMethod.GET, path="/checkBicycleStatus")
    public boolean checkBicycleStatus(@RequestParam("bicycleId") Long bicycleId);

}