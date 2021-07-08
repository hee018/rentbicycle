package rentbicycle;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Rental_table")
public class Rental {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @PostPersist
    public void onPostPersist(){
        BicycleRented bicycleRented = new BicycleRented();
        BeanUtils.copyProperties(this, bicycleRented);
        bicycleRented.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        rentbicycle.external.Bicycle bicycle = new rentbicycle.external.Bicycle();
        // mappings goes here
        Application.applicationContext.getBean(rentbicycle.external.BicycleService.class)
            .checkBicycleStatus(bicycle);


        BicycleReturned bicycleReturned = new BicycleReturned();
        BeanUtils.copyProperties(this, bicycleReturned);
        bicycleReturned.publishAfterCommit();


    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
