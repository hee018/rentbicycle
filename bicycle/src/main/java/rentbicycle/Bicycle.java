package rentbicycle;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

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

            BicycleRegistered bicycleRegistered = new BicycleRegistered();
            BeanUtils.copyProperties(this, bicycleRegistered);
            bicycleRegistered.publishAfterCommit();

    }

    @PostUpdate
    public void onPostUpdate(){

        if( this.getBicycleStatus().equals("Registered") || this.getBicycleStatus().equals("Returned") ) {
            //Following code causes dependency to external APIs
            // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.
            System.out.println("############### onPostUpdate #################");
            if(this.getTicketId() != null) {

                // mappings goes here
                //boolean rslt = true;
                boolean rslt = BicycleApplication.applicationContext.getBean(rentbicycle.external.TicketService.class)
                    .chkTicketStatus(this.getTicketId());

                if (rslt) {
                    BicycleRented bicycleRented = new BicycleRented();
                    BeanUtils.copyProperties(this, bicycleRented);
                    bicycleRented.publishAfterCommit();
                }
            }
        }

        if( this.getBicycleStatus().equals("Rented") ) {
            BicycleReturned bicycleReturned = new BicycleReturned();
            BeanUtils.copyProperties(this, bicycleReturned);
            bicycleReturned.publishAfterCommit(); 
        }
    }

    @PreRemove
    public void onPreRemove(){

        // 렌트중인 자전거는 삭제 불가함
        if ( !this.getBicycleStatus().equals("Rented") )
        {
            // 이벤트 발생 > BicycleDeleted
            BicycleDeleted bicycleDeleted = new BicycleDeleted();
            BeanUtils.copyProperties(this, bicycleDeleted);
            bicycleDeleted.publishAfterCommit();
        }

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
