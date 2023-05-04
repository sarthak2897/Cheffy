package com.cooks.demo.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.print.attribute.standard.OutputDeviceAssigned;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private Order order;
    @NonNull
    private int amount;
    private Date date;
    public enum Status{
        FAILED,SUCCESS
    }
    @Column(name = "transaction_confirmation_id")
    private String transactionConfirmationId;
    private Status status;

}
