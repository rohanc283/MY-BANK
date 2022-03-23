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
public class MessageStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mess_status_id")
    private int id ;

    @OneToOne
    @JoinColumn(name = "req_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Requests requests;

    @NotNull
    private String status;
}
