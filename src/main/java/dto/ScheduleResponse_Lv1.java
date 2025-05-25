package dto;

import java.time.LocalDateTime;

public class ScheduleResponse_Lv1 {
    private Long id;
    private String title;
    private String writer;
    private LocalDateTime scheduledDate;     // 추가됨
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponse_Lv1(Long id, String title, String writer, LocalDateTime scheduledDate,
                                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.scheduledDate = scheduledDate;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }
}