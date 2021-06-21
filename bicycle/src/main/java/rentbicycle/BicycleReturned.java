
package rentbicycle;

public class BicycleReturned extends AbstractEvent {

    private Long bicycleId;
    private Long bicycleStatus;
    private Long ticketId;
    private Long usingTime;

    public Long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(Long bicycleId) {
        this.bicycleId = bicycleId;
    }
    public Long getBicycleStatus() {
        return bicycleStatus;
    }

    public void setBicycleStatus(Long bicycleStatus) {
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

