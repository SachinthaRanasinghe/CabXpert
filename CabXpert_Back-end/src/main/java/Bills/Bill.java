package Bills;

import java.math.BigDecimal;

public class Bill {

    private int BookingID;
    private BigDecimal TotalFare;
    private BigDecimal Tax;
    private BigDecimal Discount;
    private BigDecimal FinalAmount;
    private String PaymentStatus;

    // Getters and Setters
    public int getBookingID() {
        return BookingID;
    }

    public void setBookingID(int bookingID) {
        this.BookingID = bookingID;
    }

    public BigDecimal getTotalFare() {
        return TotalFare;
    }

    public void setTotalFare(BigDecimal totalFare) {
        this.TotalFare = totalFare;
    }

    public BigDecimal getTax() {
        return Tax;
    }

    public void setTax(BigDecimal tax) {
        this.Tax = tax;
    }

    public BigDecimal getDiscount() {
        return Discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.Discount = discount;
    }

    public BigDecimal getFinalAmount() {
        return FinalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.FinalAmount = finalAmount;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.PaymentStatus = paymentStatus;
    }
}
