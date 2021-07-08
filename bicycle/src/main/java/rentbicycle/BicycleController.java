package rentbicycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

 @RestController
 public class BicycleController {

    @Autowired
    BicycleRepository bicycleRepository;

    @RequestMapping(value = "/checkBicycleStatus", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")

    public boolean checkBicycleStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("##### /bicycle/checkBicycleStatus  called #####");

        boolean result = false;
        Long bicycleId = Long.valueOf(request.getParameter("bicycleId"));

        Bicycle bicycle = bicycleRepository.findById(bicycleId).get();

        if(bicycle.getBicycleStatus().equals("Registered") || bicycle.getBicycleStatus().equals("Available")) {
            result = true;
        }

        return result;
    }

 }
