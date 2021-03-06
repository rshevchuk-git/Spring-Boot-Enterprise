package com.ordersmanagement.crm.models.entities;

import com.ordersmanagement.crm.models.pojos.Payment;
import com.ordersmanagement.crm.utils.PaymentUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "city")
    private String city;

    @Column(name = "info")
    private String info;

    @Column(name = "money")
    private int money;

    @Column(name = "customer_pay_log")
    private String payLog;

    @Column(name = "is_debtor")
    private boolean isDebtor;

    @Column(name = "customer_group")
    private String customerGroup;

    @Column(name = "phone_1")
    private String firstPhone;

    @Column(name = "email_1")
    private String firstEmail;

    @Column(name = "person_1")
    private String firstPerson;

    @Column(name = "phone_2")
    private String secondPhone;

    @Column(name = "email_2")
    private String secondEmail;

    @Column(name = "person_2")
    private String secondPerson;

    @Column(name = "phone_3")
    private String thirdPhone;

    @Column(name = "email_3")
    private String thirdEmail;

    @Column(name = "person_3")
    private String thirdPerson;

    public void appendPayLog(String appendix) {
        if (this.payLog == null || this.payLog.isEmpty()) {
            this.payLog = appendix;
        } else {
            this.payLog += appendix;
        }
    }

    public void putOnBalance(Payment payment) {
        if (payment.getSum() <= 0) return;
        this.money += payment.getSum();
        appendPayLog(PaymentUtils.formatPayLog(payment.getDateTime(), payment.getSum(), payment.getReceiver()));
    }

    public void removeAllPayments() {
        this.money = 0;
        this.payLog = "";
    }
}
