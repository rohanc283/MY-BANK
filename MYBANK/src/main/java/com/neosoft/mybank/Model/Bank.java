package com.neosoft.mybank.Model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bank_id")
    private int id ;

    @NotNull
    private String bankName;

    @NotNull
    private int noOfBranches;

    @NotNull
    private int noOfCustomers;

    @NotNull
    private String mainOffice;

    @NotNull
    private String emailId;

    @NotNull
    private String achievements;

}
