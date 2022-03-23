package com.neosoft.mybank.Model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Customers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cus_id")
    private int id ;

    @NotNull
    private String name;

    @NotNull
    private long phoneNo;

    @NotNull
    @Column(unique = true)
    private String accNO;

    @NotNull
    private String pinNo;

    @NotNull
    private String  emailId;

    @NotNull
    private String address;

    @NotNull
    @Column(unique = true)
    private long adhaarNo;

    @NotNull
    @Column(unique = true)
    private String panNo;

    @NotNull
    private float balance;

    @NotNull
    private String accType;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Branch branch;

    @NotNull
    private String dob;

}
