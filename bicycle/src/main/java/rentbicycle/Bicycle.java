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

        // 이벤트 발생 --> 자전거등록 RegisterBicycle
        BicycleRegistered bicycleRegistered = new BicycleRegistered();
        BeanUtils.copyProperties(this, bicycleRegistered);
        bicycleRegistered.publishAfterCommit();

    }

    @PostUpdate
    public void onPostUpdate(){

        // 이벤트 발생 --> 자전거렌트 RegisterBicycle
        // 등록 / 반납 상태의 유휴 자전거만 렌트 가능
        if( this.getBicycleStatus().equals("Registerd") || this.getBicycleStatus().equals("Returned") ) {
            boolean rslt = BicycleApplication.applicationContext.getBean(rentbicycle.external.TicketService.class)
                .chkTicketStatus(this.getTicketId(), this.getUsingTime());

            if (rslt) {
                BicycleRented bicycleRented = new BicycleRented();
                BeanUtils.copyProperties(this, bicycleRented);
                bicycleRented.publishAfterCommit();
            }
        }

        // 이벤트 발생 --> 자전거반납 ReturnBicycle
        // 렌트 상태의 자전거만 반납 가능
        if( this.getBicycleStatus().equals("Rented") ) {
            BicycleReturned bicycleReturned = new BicycleReturned();
            BeanUtils.copyProperties(this, bicycleReturned);
            bicycleReturned.publishAfterCommit(); 
        }
    }

    @PreRemove
    public void onPreRemove(){

        // 이벤트 발생 --> 자전거삭제 DeleteBicycle
        // 렌트 상태의 자전거는 삭제 불가 
        if ( !this.getBicycleStatus().equals("Rented") )
        {
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
