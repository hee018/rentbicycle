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
    @Autowired TicketRepository ticketRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBicycleRented_UpdateTicket(@Payload BicycleRented bicycleRented){

        if(!bicycleRented.validate()) return;

        System.out.println("\n\n##### listener UpdateTicket : " + bicycleRented.toJson() + "\n\n");

        // Sample Logic //
        Ticket ticket = new Ticket();
        ticketRepository.save(ticket);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_UpdateTicket(@Payload PaymentCancelled paymentCancelled){

        if(!paymentCancelled.validate()) return;

        System.out.println("\n\n##### listener UpdateTicket : " + paymentCancelled.toJson() + "\n\n");

        // Sample Logic //
        Ticket ticket = new Ticket();
        ticketRepository.save(ticket);
            
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentApproved_UpdateTicket(@Payload PaymentApproved paymentApproved){

        if(!paymentApproved.validate()) return;

        System.out.println("\n\n##### listener UpdateTicket : " + paymentApproved.toJson() + "\n\n");

        // Sample Logic //
        Ticket ticket = new Ticket();
        ticketRepository.save(ticket);
            
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString){}


}
