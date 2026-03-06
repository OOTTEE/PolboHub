package com.polbohub.persistence.team;

import com.polbohub.domain.team.TeamId;
import com.polbohub.persistence.Sport;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "teams")
public class TeamEntity {

    @Id
    @Column(columnDefinition = "UUID")
    private TeamId id;

    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "sport", nullable = false, columnDefinition = "sport")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Sport sport;

    public TeamId getId() {
        return id;
    }

    public void setId(TeamId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sport getSport() {
        return sport;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }
}
