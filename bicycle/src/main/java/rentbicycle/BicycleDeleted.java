package rentbicycle;

public class BicycleDeleted extends AbstractEvent {

    private Long bicycleId;
    private String bicycleStatus;

    public BicycleDeleted(){
        super();
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
}
