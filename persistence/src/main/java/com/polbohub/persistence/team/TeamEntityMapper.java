package com.polbohub.persistence.team;

import com.polbohub.domain.team.Team;
import com.polbohub.persistence.Sport;

public class TeamEntityMapper {

    static public TeamEntity toEntity(Team teamDto) {
        TeamEntity teamEntity = new TeamEntity();
        teamEntity.setId(teamDto.id());
        teamEntity.setName(teamDto.name());
        teamEntity.setSport(Sport.valueOf(teamDto.sport().name()));
        return teamEntity;
    }

    static public Team toDomain(TeamEntity teamEntity) {
        return new Team(
                teamEntity.getId(),
                teamEntity.getName(),
                com.polbohub.domain.Sport.valueOf(teamEntity.getSport().name())
        );
    }

}
