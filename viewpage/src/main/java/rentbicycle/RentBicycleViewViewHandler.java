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
            // view 레파지 토리에 save
            rentBicycleViewRepository.save(rentBicycleView);
        
        }catch (Exception e){
            e.printStackTrace();
        }
    }



}