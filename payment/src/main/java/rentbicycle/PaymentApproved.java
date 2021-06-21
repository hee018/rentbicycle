
package rentbicycle;



public class PaymentApproved extends AbstractEvent {
// event (주황색)
// Aggregate 의 변화에 의해서 발생하기 때문에, 이벤트를 보내는 로직은 Entity의 lifecycle 에 작성
//메시지 브로커를 kafka 를 사용한다면 topic 을 설정하고, 마지막에 send 하는 형식으로 이벤트를 발행

    private Long paymentId;
    private Long ticketId;
    private Long ticketAmt;
    private Boolean addPaymentYN;
    private String paymentStatus;

    public PaymentApproved(){
    //         this.paymentStatus = this.getClass().getSimpleName();
    }


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
    public Boolean getAddPaymentYn() {
        return addPaymentYN;
    }

    public void setAddPaymentYn(Boolean addPaymentYN) {
        this.addPaymentYN = addPaymentYN;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}

