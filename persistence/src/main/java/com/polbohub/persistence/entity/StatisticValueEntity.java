package com.polbohub.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "statics_values")
public class StatisticValueEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "static_id", columnDefinition = "UUID")
    private UUID staticId;

    @Column(name = "value", nullable = false, length = 100)
    private String value;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    private PlayerEntity player;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getStaticId() {
        return staticId;
    }

    public void setStaticId(UUID staticId) {
        this.staticId = staticId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setDate(LocalDate date) {
    }

    public LocalDate getDate() {
        return date;
    }
}
