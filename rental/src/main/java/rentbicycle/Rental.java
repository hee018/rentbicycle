package rentbicycle;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Rental_table")
public class Rental {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long bicycleId;
    private Long ticketId;
    private String rentalStatus;

    @PostPersist
    public void onPostPersist(){

        // mappings goes here
        boolean rslt = RentalApplication.applicationContext.getBean(rentbicycle.external.BicycleService.class)
            .checkBicycleStatus(this.getBicycleId());

        if(rslt) {
            boolean rslt2 = RentalApplication.applicationContext.getBean(rentbicycle.external.TicketService.class)
                    .chkTicketStatus(this.getTicketId());

            if (rslt2) {
                BicycleRented bicycleRented = new BicycleRented();
                bicycleRented.setBicycleId(this.getBicycleId());
                bicycleRented.setTicketId(this.getTicketId());
                bicycleRented.setBicycleStatus("Rented");
                BeanUtils.copyProperties(this, bicycleRented);
                bicycleRented.publishAfterCommit();
            }
        }   

    }

    @PostUpdate
    public void onPostUpdate(){

        String rentalStatus  = this.getRentalStatus()+"";
        if(rentalStatus.equals("Returned")) {
            BicycleReturned bicycleReturned = new BicycleReturned();
            bicycleReturned.setBicycleId(this.getBicycleId());
            bicycleReturned.setBicycleStatus(this.getRentalStatus());
            BeanUtils.copyProperties(this, bicycleReturned);
            bicycleReturned.publishAfterCommit();
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(Long bicycleId) {
        this.bicycleId = bicycleId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getRentalStatus() {
        return rentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }


}
