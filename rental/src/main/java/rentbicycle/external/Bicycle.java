package rentbicycle.external;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;


public class Bicycle {

    private Long bicycleId;
    private String bicycleStatus;
    private Long ticketId;
    private Long usingTime;
    private Date startTime;

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
