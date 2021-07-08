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
    public void whenBicycleRented_then_CREATE_1 (@Payload BicycleRented bicycleRented) {
        try {

            if (!bicycleRented.validate()) return;

            // view 객체 생성
            RentBicycleView rentBicycleView = new RentBicycleView();
            // view 객체에 이벤트의 Value 를 set 함
            rentBicycleView.setStartTime(bicycleRented.getStartTime());
            rentBicycleView.setUsingTime(bicycleRented.getUsingTime());
            rentBicycleView.setTicketId(bicycleRented.getTicketId());
            rentBicycleView.setBicycleId(bicycleRented.getBicycleId());
            rentBicycleView.setBicycleStatus(bicycleRented.getBicycleStatus());
            // view 레파지 토리에 save
            rentBicycleViewRepository.save(rentBicycleView);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenBicycleReturned_then_CREATE_2 (@Payload BicycleReturned bicycleReturned) {
        try {

            if (!bicycleReturned.validate()) return;

            // view 객체 생성
            RentBicycleView rentBicycleView = new RentBicycleView();
            // view 객체에 이벤트의 Value 를 set 함
            rentBicycleView.setBicycleId(bicycleReturned.getBicycleId());
            rentBicycleView.setTicketId(bicycleReturned.getTicketId());
            rentBicycleView.setBicycleStatus(bicycleReturned.getBicycleStatus());
            rentBicycleView.setUsingTime(bicycleReturned.getUsingTime());
            // view 레파지 토리에 save
            rentBicycleViewRepository.save(rentBicycleView);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentApproved_then_CREATE_3 (@Payload PaymentApproved paymentApproved) {
        try {

            if (!paymentApproved.validate()) return;

            // view 객체 생성
            RentBicycleView rentBicycleView = new RentBicycleView();
            // view 객체에 이벤트의 Value 를 set 함
            rentBicycleView.setTicketId(paymentApproved.getTicketId());
            rentBicycleView.setTicketType(paymentApproved.getTicketAmt());
            rentBicycleView.setPaymentId(paymentApproved.getPaymentId());
            rentBicycleView.setPaymentStatus(paymentApproved.getPaymentStatus());
            // view 레파지 토리에 save
            rentBicycleViewRepository.save(rentBicycleView);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void whenPaymentCancelled_then_CREATE_4 (@Payload PaymentCancelled paymentCancelled) {
        try {

            if (!paymentCancelled.validate()) return;

            // view 객체 생성
            RentBicycleView rentBicycleView = new RentBicycleView();
            // view 객체에 이벤트의 Value 를 set 함
            rentBicycleView.setPaymentId(paymentCancelled.getPaymentId());
            rentBicycleView.setTicketId(paymentCancelled.getTicketId());
            rentBicycleView.setTicketType(paymentCancelled.getTicketAmt());
            rentBicycleView.setPaymentStatus(paymentCancelled.getPaymentStatus());
            // view 레파지 토리에 save
            rentBicycleViewRepository.save(rentBicycleView);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}