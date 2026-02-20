package com.polbohub.model;

import java.time.LocalDateTime;
import java.util.List;


public class Inscription {
    private Long id;
    private Long competitionId;
    private Long userId;
    private LocalDateTime inscriptionDate;
    private InscriptionStatus status;
    private PaymentStatus paymentStatus;
    private List<InscriptionEntry> entries;

    public enum InscriptionStatus {
        PENDING, APPROVED, REJECTED
    }

    public enum PaymentStatus {
        PENDING, PAID, WAIVED
    }
}
