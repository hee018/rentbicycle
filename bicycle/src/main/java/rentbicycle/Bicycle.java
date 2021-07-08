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
    private Date startTime;

    @PostPersist
    public void onPostPersist(){
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
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }




}
