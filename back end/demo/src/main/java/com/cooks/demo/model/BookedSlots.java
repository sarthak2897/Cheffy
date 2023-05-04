package com.cooks.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "booked_slots")
public class BookedSlots {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    private Slot slot;

    @OneToOne
    private Chef chef;

    private boolean isBooked;

}
