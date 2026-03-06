package com.polbohub.persistence.player;

import com.polbohub.domain.player.PlayerId;
import com.polbohub.domain.team.TeamId;
import com.polbohub.domain.user.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "players")
public class PlayerEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private PlayerId id;

    @Column(name = "license_number", nullable = false, unique = true, length = 100)
    private String licenseNumber;

    @Column(name = "team_id", columnDefinition = "UUID")
    private TeamId team;

    @Column(name = "user_id", columnDefinition = "UUID")
    private UserId user;

    public PlayerId getId() {
        return id;
    }

    public void setId(PlayerId id) {
        this.id = id;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public TeamId getTeam() {
        return team;
    }

    public void setTeam(TeamId team) {
        this.team = team;
    }

    public UserId getUser() {
        return user;
    }

    public void setUser(UserId user) {
        this.user = user;
    }
}
