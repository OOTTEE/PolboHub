package com.polbohub.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Competition {
    private Long id;
    private String name;
    private String location;
    private String federationReference;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDateTime registrationDeadline;
    private int maxRunsPerUser;
    private boolean paymentRequired;
    private boolean visible;
    private List<Run> runs;
}
