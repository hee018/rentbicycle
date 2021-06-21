package rentbicycle;

public class BicycleRegistered extends AbstractEvent {

    private Long bicycleId;
    private Long bicycleStatus;

    public BicycleRegistered(){
        super();
    }

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
}
