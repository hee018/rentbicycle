package rentbicycle;

import rentbicycle.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RentBicycleViewViewHandler {


    @Autowired
    private RentBicycleViewRepository rentBicycleViewRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketPurchased_then_CREATE_1 (@Payload TicketPurchased ticketPurchased) {
        try {

            if (!ticketPurchased.validate()) return;

            // view 객체 생성
            RentBicycleView rentBicycleView = new RentBicycleView();
            // view 객체에 이벤트의 Value 를 set 함
            rentBicycleView.setTicketId(ticketPurchased.getTicketId());
            rentBicycleView.setTicketType(ticketPurchased.getTicketType());
            rentBicycleView.setTicketStatus(ticketPurchased.getTicketStatus());
            rentBicycleView.setBuyerPhoneNum(ticketPurchased.getBuyerPhoneNum());
            // view 레파지 토리에 save
            rentBicycleViewRepository.save(rentBicycleView);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentApproved_then_CREATE_2 (@Payload PaymentApproved paymentApproved) {
        
        try {

            System.out.println("\n\n##### listener paymentApproved : " + paymentApproved.toJson() + "\n\n");

            if (!paymentApproved.validate()) return;

            // view 객체 생성
            RentBicycleView rentBicycleView = new RentBicycleView();
            // view 객체에 이벤트의 Value 를 set 함
            rentBicycleView.setPaymentId(paymentApproved.getPaymentId());
            rentBicycleView.setTicketId(paymentApproved.getTicketId());
            rentBicycleView.setTicketAmt(paymentApproved.getTicketAmt());
            rentBicycleView.setTicketStatus("ticketAvailable");
            rentBicycleView.setPaymentStatus(paymentApproved.getPaymentStatus());
            // view 레파지 토리에 save
            rentBicycleViewRepository.save(rentBicycleView);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenBicycleReturned_then_UPDATE_1(@Payload BicycleReturned bicycleReturned) {
        try {
            if (!bicycleReturned.validate()) return;
                // view 객체 조회
            Optional<RentBicycleView> rentBicycleViewOptional = rentBicycleViewRepository.findByTicketId(bicycleReturned.getTicketId());
            if( rentBicycleViewOptional.isPresent()) {
                RentBicycleView rentBicycleView = rentBicycleViewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                    rentBicycleView.setBicycleStatus(bicycleReturned.getBicycleStatus());
                // view 레파지 토리에 save
                rentBicycleViewRepository.save(rentBicycleView);
            }
            List<RentBicycleView> rentBicycleViewList = rentBicycleViewRepository.findByBicycleId(bicycleReturned.getBicycleId());
            for(RentBicycleView rentBicycleView : rentBicycleViewList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                rentBicycleView.setBicycleStatus(bicycleReturned.getBicycleStatus());
                // view 레파지 토리에 save
                rentBicycleViewRepository.save(rentBicycleView);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketModified_then_UPDATE_2(@Payload TicketModified ticketModified) {
        try {
            if (!ticketModified.validate()) return;
                // view 객체 조회
            Optional<RentBicycleView> rentBicycleViewOptional = rentBicycleViewRepository.findByTicketId(ticketModified.getTicketId());
            if( rentBicycleViewOptional.isPresent()) {
                RentBicycleView rentBicycleView = rentBicycleViewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                    rentBicycleView.setTicketStatus(ticketModified.getTicketStatus());
                // view 레파지 토리에 save
                rentBicycleViewRepository.save(rentBicycleView);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentCancelled_then_UPDATE_3(@Payload PaymentCancelled paymentCancelled) {
        try {

            System.out.println("\n\n##### listener paymentCancelled : " + paymentCancelled.toJson() + "\n\n");

            if (!paymentCancelled.validate()) return;
                // view 객체 조회
            List<RentBicycleView> rentBicycleViewList = rentBicycleViewRepository.findByPaymentId(paymentCancelled.getPaymentId());
            for(RentBicycleView rentBicycleView : rentBicycleViewList){
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                rentBicycleView.setPaymentStatus(paymentCancelled.getPaymentStatus());
                // view 레파지 토리에 save
                rentBicycleViewRepository.save(rentBicycleView);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenBicycleRented_then_UPDATE_4(@Payload BicycleRented bicycleRented) {
        try {

            System.out.println("\n\n##### listener bicycleRented : " + bicycleRented.toJson() + "\n\n");

            //if (!bicycleRented.validate()) return;
                // view 객체 조회
            Optional<RentBicycleView> rentBicycleViewOptional = rentBicycleViewRepository.findByTicketId(bicycleRented.getTicketId());
            if( rentBicycleViewOptional.isPresent()) {
                RentBicycleView rentBicycleView = rentBicycleViewOptional.get();
                rentBicycleView.setBicycleId(bicycleRented.getBicycleId());
                rentBicycleView.setBicycleStatus(bicycleRented.getBicycleStatus());
                rentBicycleView.setTicketStatus("ticketUsed");
                // view 레파지 토리에 save
                rentBicycleViewRepository.save(rentBicycleView);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenBicycleReturned_then_UPDATE_5(@Payload BicycleReturned bicycleReturned) {
        try {

            System.out.println("\n\n##### listener bicycleReturned : " + bicycleReturned.toJson() + "\n\n");

            // if (!bicycleReturned.validate()) return;
                // view 객체 조회
            Optional<RentBicycleView> rentBicycleViewOptional = rentBicycleViewRepository.findByTicketId(bicycleReturned.getTicketId());
            if( rentBicycleViewOptional.isPresent()) {
                RentBicycleView rentBicycleView = rentBicycleViewOptional.get();
                // view 객체에 이벤트의 eventDirectValue 를 set 함
                rentBicycleView.setBicycleStatus(bicycleReturned.getBicycleStatus());
                // view 레파지 토리에 save
                rentBicycleViewRepository.save(rentBicycleView);
            }
            
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}