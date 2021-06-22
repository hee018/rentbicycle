package rentbicycle;

import rentbicycle.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverTicketRefunded_CancelPayment(@Payload TicketRefunded ticketRefunded){

        if(!ticketRefunded.validate()) return;

        System.out.println("\n\n##### listener CancelPayment : " + ticketRefunded.toJson() + "\n\n");
     
        Payment payment = paymentRepository.findByTicketId(ticketRefunded.getTicketId());
        payment.setPaymentStatus("paymentCancelled");
        paymentRepository.save(payment);

            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
