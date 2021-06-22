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
@StreamListener 어노테이션으로 선언을 하여 주면, 이벤트가 생성될 때마다 INPUT 으로 들어오는 데이터를 한 개씩 수신

Porcessor.INPUT 은 메시지를 수신하는 채널, kafka 의 구현체를 가진다면 topic 을 의미
만약 Topic 을 여러 이벤트에서 공유를 한다면 아래 리스너에서 내가 원하는 이벤트만 선별하여 작업을 해야하기 때문에
 이벤트의 속성값에서 정해진 이벤트 명을 찾던가, header 에서 찾는 등 이벤트를 구분하는 로직이 필요
*/
    @Autowired PaymentRepository paymentRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverTicketRefunded_CancelPayment(@Payload TicketRefunded ticketRefunded){

        if(!ticketRefunded.validate()) return;

        System.out.println("\n\n##### listener CancelPayment : " + ticketRefunded.toJson() + "\n\n");

        // Payment payment = new Payment();
        // ticketRefunded 에 payment 관련 정보..paymentId 같은 필드가 있어야 할 것 같다..아닌가 findByTicketId하면 되나
        // Payment payment = paymentRepository.findById(ticketRefunded.getTicketId()).get();
        Payment payment = paymentRepository.findByTicketId(ticketRefunded.getTicketId());
        
        // payment.setTicketId(ticketRefunded.getTicketId());
        payment.setPaymentStatus("paymentCancelled");
        paymentRepository.save(payment);

            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
