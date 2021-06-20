package rentbicycle;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="RentBicycleView_table")
public class RentBicycleView {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long ticketId;
        private String ticketStatus;
        private String ticketType;
        private String buyerPhoneNum;
        private Long paymentId;
        private String paymentStatus;
        private Boolean addPaymentYn;
        private Long bicycleId;
        private String bicycleStatus;
        private Long usingTime;


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
        public Long getPaymentId() {
            return paymentId;
        }

        public void setPaymentId(Long paymentId) {
            this.paymentId = paymentId;
        }
        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }
        public Boolean getAddPaymentYn() {
            return addPaymentYn;
        }

        public void setAddPaymentYn(Boolean addPaymentYn) {
            this.addPaymentYn = addPaymentYn;
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
        public Long getUsingTime() {
            return usingTime;
        }

        public void setUsingTime(Long usingTime) {
            this.usingTime = usingTime;
        }

}
