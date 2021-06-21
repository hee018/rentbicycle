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
        Long ticketAmount = Long.decode(this.getTicketType() == "1"?"1000":"2000");

        boolean result = TicketApplication.applicationContext.getBean(rentbicycle.external.PaymentService.class)
            .payTicket(this.getTicketId(), ticketAmount);
        
        if(result) {
            TicketPurchased ticketPurchased = new TicketPurchased();
            ticketPurchased.setTicketId(this.getTicketId());
            ticketPurchased.setTicketStatus("ticketPurchased");
            ticketPurchased.setTicketType(this.getTicketType());
            ticketPurchased.setBuyerPhoneNum(this.getBuyerPhoneNum());

            BeanUtils.copyProperties(this, ticketPurchased);
            ticketPurchased.publishAfterCommit();
        }
    }

    @PreRemove
    public void onPreRemove(){
        TicketRefunded ticketRefunded = new TicketRefunded();
        ticketRefunded.setTicketId(this.getTicketId());
        ticketRefunded.setTicketType(this.getTicketType());
        ticketRefunded.setTicketStatus("ticketRefunded");

        BeanUtils.copyProperties(this, ticketRefunded);
        ticketRefunded.publishAfterCommit();

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
