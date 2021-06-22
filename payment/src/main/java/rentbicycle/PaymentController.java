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
        Long ticketAmt = Long.valueOf(request.getParameter("ticketAmt"));

        Payment payment = new Payment();
        payment.setPaymentStatus("paymentApprved");
        payment.setTicketId(ticketId);
        payment.setTicketAmt(ticketAmt);
        paymentRepository.save(payment);
        result = true;

        return result;
        }


 }
