package com.polbohub.api.v1.statisticvalue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public class StatisticValueDto {
    private UUID id;

    @NotNull
    private UUID staticId;

    @NotBlank
    private String value;

    @NotBlank
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
