package rentbicycle;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Bicycle_table")
public class Bicycle {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long bicycleId;
    private String bicycleStatus;
    private Long ticketId;
    private Long usingTime;

    @PostPersist
    public void onPostPersist(){
        BicycleRented bicycleRented = new BicycleRented();
        BeanUtils.copyProperties(this, bicycleRented);
        bicycleRented.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        rentbicycle.external.Ticket ticket = new rentbicycle.external.Ticket();
        // mappings goes here
        Application.applicationContext.getBean(rentbicycle.external.TicketService.class)
            .chkTicketStatus(ticket);


        BicycleReturned bicycleReturned = new BicycleReturned();
        BeanUtils.copyProperties(this, bicycleReturned);
        bicycleReturned.publishAfterCommit();


        BicycleRegistered bicycleRegistered = new BicycleRegistered();
        BeanUtils.copyProperties(this, bicycleRegistered);
        bicycleRegistered.publishAfterCommit();


        BicycleDeleted bicycleDeleted = new BicycleDeleted();
        BeanUtils.copyProperties(this, bicycleDeleted);
        bicycleDeleted.publishAfterCommit();


    }


    public Long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(Long bicycleId) {
        this.bicycleId = bicycleId;
    }
    public String getBicycleStatus() {
        return bicycleStatus;
    }

    public void setBicycleStatus(String bicycleStatus) {
        this.bicycleStatus = bicycleStatus;
    }
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public Long getUsingTime() {
        return usingTime;
    }

    public void setUsingTime(Long usingTime) {
        this.usingTime = usingTime;
    }




}
