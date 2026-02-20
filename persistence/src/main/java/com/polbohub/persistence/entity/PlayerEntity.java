package com.polbohub.persistence.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "players")
public class PlayerEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private UUID id;

    @Column(name = "license_number", nullable = false, unique = true, length = 100)
    private String licenseNumber;

    @Column(name = "team_id", columnDefinition = "UUID")
    private UUID team;

    @Column(name = "user_id", columnDefinition = "UUID")
    private UUID user;

    @OneToMany(mappedBy = "player", fetch = FetchType.LAZY)
    private List<StatisticValueEntity> statistics;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public UUID getTeam() {
        return team;
    }

    public void setTeam(UUID team) {
        this.team = team;
    }

    public UUID getUser() {
        return user;
    }

    public void setUser(UUID user) {
        this.user = user;
    }
}
