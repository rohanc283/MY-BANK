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
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private int id ;

    @NotNull
    private String branchName;

    @NotNull
    @Column(unique = true)
    private String branchIfscCode;

    @NotNull
    private String branchManager;

    @NotNull
    private long helpLineNo;

    @NotNull
    private String branchAddress;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Bank bank;

    @NotNull
    private String activeState;
}
