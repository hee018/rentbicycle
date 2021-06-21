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
    @Autowired MessageRepository messageRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBicycleRented_RentMessage(@Payload BicycleRented bicycleRented){

        if(!bicycleRented.validate()) return;

        System.out.println("\n\n##### listener RentMessage : " + bicycleRented.toJson() + "\n\n");

        // 메세지는 계속 신규 생성..? findbyId를 하려면..
        Message message = new Message(); 
        // message.setMessageId(messageId); //messageId??
        message.setBicycleId(bicycleRented.getBicycleId());
        message.setBycycleStatus(bicycleRented.getBicycleStatus());
        message.setTicketId(bicycleRented.getTicketId());
        messageRepository.save(message);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBicycleReturned_ReturnMessage(@Payload BicycleReturned bicycleReturned){

        if(!bicycleReturned.validate()) return;

        System.out.println("\n\n##### listener ReturnMessage : " + bicycleReturned.toJson() + "\n\n");

        Message message = new Message();
        message.setBicycleId(bicycleReturned.getBicycleId());
        message.setBycycleStatus(bicycleReturned.getBicycleStatus());
        message.setTicketId(bicycleReturned.getTicketId());
        messageRepository.save(message);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentApproved_BuyTicketMessage(@Payload PaymentApproved paymentApproved){

        if(!paymentApproved.validate()) return;

        System.out.println("\n\n##### listener BuyTicketMessage : " + paymentApproved.toJson() + "\n\n");

        Message message = new Message();
        message.setPaymentId(paymentApproved.getPaymentId());
        message.setPaymentStatus(paymentApproved.getPaymentStatus());
        message.setTicketId(paymentApproved.getTicketId());
        messageRepository.save(message);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_RefundTicketMessage(@Payload PaymentCancelled paymentCancelled){

        if(!paymentCancelled.validate()) return;

        System.out.println("\n\n##### listener RefundTicketMessage : " + paymentCancelled.toJson() + "\n\n");

        Message message = new Message();
        message.setPaymentId(paymentCancelled.getPaymentId());
        message.setPaymentStatus(paymentCancelled.getPaymentStatus());
        message.setTicketId(paymentCancelled.getTicketId());
        messageRepository.save(message);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
