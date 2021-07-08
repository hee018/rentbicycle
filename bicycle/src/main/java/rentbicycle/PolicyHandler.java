package rentbicycle;

import rentbicycle.config.kafka.KafkaProcessor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PolicyHandler{

    @Autowired BicycleRepository bicycleRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whateverBicycleRented_updateBicycleStatus(@Payload BicycleRented bicycleRented){

        if(!bicycleRented.validate()) return;

        System.out.println("\n\n##### listener UpdateBicycleStatus : " + bicycleRented.toJson() + "\n\n");

        Bicycle bicycle = bicycleRepository.findById(bicycleRented.getBicycleId()).get();
        bicycle.setBicycleStatus("Rented");
        bicycleRepository.save(bicycle);
    }


       @StreamListener(KafkaProcessor.INPUT)
    public void whateverBicycleReturned_updateBicycleStatus(@Payload BicycleReturned bicycleReturned){

        if(!bicycleReturned.validate()) return;

        System.out.println("\n\n##### listener UpdateBicycleStatus : " + bicycleReturned.toJson() + "\n\n");

        Bicycle bicycle = bicycleRepository.findById(bicycleReturned.getBicycleId()).get();
        bicycle.setBicycleStatus("Available");
        bicycleRepository.save(bicycle);
    }


}
