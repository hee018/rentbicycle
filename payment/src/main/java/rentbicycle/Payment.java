package rentbicycle;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

@Entity
@Table(name="Payment_table")
public class Payment {

/*  Entity 의 lifecycle에 해당하는 listener  
    > @PostPersist (저장후) @PrePersist(저장전) @PostUpdate (업데이트후) 
*/
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long paymentId;
    private Long ticketId;
    private Long ticketAmt;
    // private Boolean addPaymentYn;
    private String paymentStatus;

    @PostPersist
    public void onPostPersist(){
        // boolean rslt =  PaymentApplication.applicationContext.getBean(rentbicycle.external.PaymentService.class)
        // .modifyStock(this.getPaymentId(), this.getTicketAmt());

        PaymentApproved paymentApproved = new PaymentApproved();
        paymentApproved.setPaymentId(this.getPaymentId());
        paymentApproved.setPaymentId(this.getTicketId());
        paymentApproved.setTicketAmt(this.getTicketAmt());
        // paymentApproved.setAddPaymentYn(this.getAddPaymentYn());
        paymentApproved.setPaymentStatus("paymentApproved");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(paymentApproved);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        System.out.println("================paymentApproved========================");
        System.out.println(json);


        // Processor processor = PaymentApplication.applicationContext.getBean(Processor.class);
        // MessageChannel outputChannel = processor.output();

        // outputChannel.send(MessageBuilder
        // .withPayload(json)
        // .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        // .build());
        
        BeanUtils.copyProperties(this, paymentApproved);
        paymentApproved.publishAfterCommit();


    }

    // // @PreRemove
    // @PostRemove
    // public void onPostRemove(){
    //     if(!this.getPaymentStatus().equals("paymentCancelled")){ // 이미 환불 처리된 건인지 확인
    //         PaymentCancelled paymentCancelled = new PaymentCancelled();        
    //         paymentCancelled.setPaymentId(this.getPaymentId());
    //         paymentCancelled.setPaymentId(this.getTicketId());
    //         paymentCancelled.setPaymentStatus("paymentCancelled");

    //         ObjectMapper objectMapper = new ObjectMapper();
    //         String json = null;

    //         try {
    //             json = objectMapper.writeValueAsString(paymentCancelled);
    //         } catch (JsonProcessingException e) {
    //             throw new RuntimeException("JSON format exception", e);
    //         }
    //         System.out.println("================paymentCancelled========================");
    //         System.out.println(json);

    //         BeanUtils.copyProperties(this, paymentCancelled);
    //         paymentCancelled.publishAfterCommit();
    //     }
    // }


    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public Long getTicketAmt() {
        return ticketAmt;
    }

    public void setTicketAmt(Long ticketAmt) {
        this.ticketAmt = ticketAmt;
    }
    // public Boolean getAddPaymentYn() {
    //     return addPaymentYn;
    // }

    // public void setAddPaymentYn(Boolean addPaymentYn) {
    //     this.addPaymentYn = addPaymentYn;
    // }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }




}
