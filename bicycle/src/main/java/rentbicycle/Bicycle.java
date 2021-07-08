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

    @PostPersist
    public void onPostPersist(){
        BicycleRegistered bicycleRegistered = new BicycleRegistered();
        bicycleRegistered.setBicycleId(this.getBicycleId());
        bicycleRegistered.setBicycleStatus("Registered");
        BeanUtils.copyProperties(this, bicycleRegistered);
        bicycleRegistered.publishAfterCommit();


        BicycleDeleted bicycleDeleted = new BicycleDeleted();
        bicycleRegistered.setBicycleId(this.getBicycleId());
        bicycleRegistered.setBicycleStatus("Deleted");

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

}
