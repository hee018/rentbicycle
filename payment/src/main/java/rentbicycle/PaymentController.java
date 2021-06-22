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
 public class PaymentController {

    @Autowired
    PaymentRepository paymentRepository;
    
     @RequestMapping(value = "/payTicket", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
     public boolean payTicket(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("=========payTicket============");

        boolean result = false;
        Long ticketId = Long.valueOf(request.getParameter("ticketId"));

        // ticket Type에 따라 ticketAmt 결정??
        // Payment payment = paymentRepository.findByTicketId(ticketId);

        // 요청온 티켓 ID로 이미 결제 내역이 있는지 확인.. 없으면 뭘 리턴해주지..null?
        if(paymentRepository.findByTicketId(ticketId) == null){
             result = true;
        }

        return result;
        }


 }
