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
/*
이벤트에 반응하여 작동하는 Policy

이벤트에 반응하기 때문에 이벤트를 수신하는 리스너가 필요
 Spring-cloud-Stream 을 사용시 아래처럼 @StreamListener 어노테이션으로 선언을 하여 주면, 
이벤트가 생성될 때마다 INPUT 으로 들어오는 데이터를 한 개씩 수신
*/
    @Autowired PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverTicketRefunded_CancelPayment(@Payload TicketRefunded ticketRefunded){

        if(!ticketRefunded.validate()) return;

        System.out.println("\n\n##### listener CancelPayment : " + ticketRefunded.toJson() + "\n\n");

        // Sample Logic //
        Payment payment = new Payment();
        paymentRepository.save(payment);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
