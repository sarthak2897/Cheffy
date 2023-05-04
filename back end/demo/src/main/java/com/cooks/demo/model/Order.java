package com.cooks.demo.model;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "customer_order")
@EntityListeners(AuditingEntityListener.class)
public class Order extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NonNull
    private String mealType;
    @NonNull
    private String foodPreference;
    @NonNull
    private String genderPreference;
    @NonNull
    private String cuisine;
    @NonNull
    private String occasion;

    private int noOfPeople;
    @NonNull
    private String typeOfHob;

    private int noOfHobs;

    private boolean isOven;
    @NonNull
    private int chefTier;

    private boolean isIngredients;

    private LocalDate dateOfSupply;

    private LocalDateTime dateOfOrder;

    private boolean isClosed;

    @OneToOne(cascade = CascadeType.ALL)
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    private Chef chef;

    @NonNull
    private String status;

    @NonNull
    private Long amount;
}
