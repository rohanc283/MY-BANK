package com.neosoft.mybank.Model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MoneyReceived {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @ManyToOne
    @JoinColumn(name = "cus_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Customers customers_money_received;

    @NotNull
    private String senderAccNo;

    @NotNull
    private String receiversAccNo;

    @NotNull
    private String senderIfscCode;

    @NotNull
    private String receiverIfscCode;

    @NotNull
    private float amount;


}
