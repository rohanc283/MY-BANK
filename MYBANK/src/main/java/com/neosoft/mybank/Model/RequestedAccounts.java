package com.neosoft.mybank.Model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RequestedAccounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "req_cus_id")
    private int id;

    @NotNull
    private String name;

    @NotNull
    private long phoneNo;

    @NotNull
    private String emailId;

    @NotNull
    private String address;

    @NotNull
    private long adhaarNo;

    @NotNull
    private String panNo;

    @OneToOne
    @JoinColumn(name = "branch_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @NotNull
    private float balance;

    @NotNull
    private String accType;

    @NotNull
    private String dob;

}