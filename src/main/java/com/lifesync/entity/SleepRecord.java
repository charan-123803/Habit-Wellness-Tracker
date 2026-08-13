package com.lifesync.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "sleep_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SleepRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "record_date", nullable = false)
    private LocalDate recordDate;

    @Column(name = "sleep_time", length = 20)
    private String sleepTime;

    @Column(name = "wake_time", length = 20)
    private String wakeTime;

    @Column(name = "total_hours", nullable = false)
    private Double totalHours;

    @Column(length = 20)
    private String quality; // POOR, FAIR, GOOD, EXCELLENT

    @Column(name = "deep_sleep_hours")
    private Double deepSleepHours;

    @Column(name = "light_sleep_hours")
    private Double lightSleepHours;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
