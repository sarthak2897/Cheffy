package com.cooks.demo.model;

import lombok.*;
import org.hibernate.engine.internal.Cascade;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chef")
@EntityListeners(AuditingEntityListener.class)
public class Chef extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NonNull
    private String qualification;

    @NonNull
    private Double experience;

    private Integer tier;

    private Boolean isActive;

    @OneToOne(cascade = CascadeType.PERSIST)
    private User user;
}
