package rentbicycle;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;

@Entity
@Table(name="Ticket_table")
public class Ticket {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ticketId;
    private String ticketStatus;
    private String ticketType;
    private String buyerPhoneNum;

    @PostPersist
    public void onPostPersist(){
        TicketPurchased ticketPurchased = new TicketPurchased();
        ticketPurchased.setTicketId(this.getTicketId());
        ticketPurchased.setTicketStatus("ticketPurchased");
        ticketPurchased.setTicketType(this.getTicketType());
        ticketPurchased.setBuyerPhoneNum(this.getBuyerPhoneNum());

        BeanUtils.copyProperties(this, ticketPurchased);
        ticketPurchased.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        /*
        rentbicycle.external.Payment payment = new rentbicycle.external.Payment();
        payment.setTicketId(this.getTicketId());
        Long ticketAmount = Long.decode(this.getTicketType() == "1"?"1000":"2000");
        payment.setTicketAmt(ticketAmount);
        // mappings goes here
        TicketApplication.applicationContext.getBean(rentbicycle.external.PaymentService.class)
            .payTicket(payment);
        */

        TicketRefunded ticketRefunded = new TicketRefunded();
        ticketRefunded.setTicketId(this.getTicketId());
        ticketRefunded.setTicketType(this.getTicketType());
        ticketRefunded.setTicketStatus("ticketRefunded");

        BeanUtils.copyProperties(this, ticketRefunded);
        ticketRefunded.publishAfterCommit();


        TicketModified ticketModified = new TicketModified();
        ticketModified.setTicketId(this.getTicketId());
        ticketModified.setTicketType(this.getTicketType());
        ticketModified.setTicketStatus("ticketModified");

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
