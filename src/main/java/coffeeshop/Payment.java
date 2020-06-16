package coffeeshop;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name="Payment_table")
public class Payment {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long paymentId;
    private Long orderId;
    private Long coffeeId;
    private Float price;
    private Integer qty;
    private Float totalAmount;
    private String status;
    private String coffeeName;

    @PostPersist
    public void onPostPersist(){
        Paid paid = new Paid();
        BeanUtils.copyProperties(this, paid);
        paid.publishAfterCommit();

        //서킷브레이커를 위한 설정
        // 1. order에서 Hystrix 를 설정 함:
        //    요청처리 쓰레드에서 처리시간이 610 밀리가 넘어서기 시작하여 어느정도 유지되면 CB 회로가 닫히도록 (요청을 빠르게 실패처리, 차단) 설정
        // 2. payment를 저장한 후 적당한 시간 끌기
//        try {
//            Thread.currentThread().sleep((long) (400 + Math.random() * 220));
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    @PreRemove
    public void onPreRemove(){
        PayCanceled payCanceled = new PayCanceled();
        BeanUtils.copyProperties(this, payCanceled);
        payCanceled.setStatus("Pay Canceled");
        payCanceled.publishAfterCommit();

    }


    public Long getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCoffeeId() {
        return coffeeId;
    }
    public void setCoffeeId(Long coffeeId) {
        this.coffeeId = coffeeId;
    }

    public Float getPrice() {
        return price;
    }
    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Float getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getCoffeeName() {
        return coffeeName;
    }
    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }


}
