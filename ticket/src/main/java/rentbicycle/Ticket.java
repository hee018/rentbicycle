package rentbicycle;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Ticket_table")
public class Ticket {

    private Long ticketId;
    private String ticketStatus;
    private String ticketType;
    private String buyerPhoneNum;

    @PostPersist
    public void onPostPersist(){
        TicketPurchased ticketPurchased = new TicketPurchased();
        BeanUtils.copyProperties(this, ticketPurchased);
        ticketPurchased.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        rentbicycle.external.Payment payment = new rentbicycle.external.Payment();
        // mappings goes here
        Application.applicationContext.getBean(rentbicycle.external.PaymentService.class)
            .payTicket(payment);


        TicketRefunded ticketRefunded = new TicketRefunded();
        BeanUtils.copyProperties(this, ticketRefunded);
        ticketRefunded.publishAfterCommit();


        TicketModified ticketModified = new TicketModified();
        BeanUtils.copyProperties(this, ticketModified);
        ticketModified.publishAfterCommit();


    }


    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }
    public String getBuyerPhoneNum() {
        return buyerPhoneNum;
    }

    public void setBuyerPhoneNum(String buyerPhoneNum) {
        this.buyerPhoneNum = buyerPhoneNum;
    }




}
