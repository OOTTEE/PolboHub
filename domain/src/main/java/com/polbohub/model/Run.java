package com.polbohub.model;

import java.time.LocalDateTime;


public class Run {
    private Long id;
    private Long competitionId;
    private String style;
    private int distance;
    private RunCategory category;
    private LocalDateTime scheduledTime;
    private boolean relay;
}
