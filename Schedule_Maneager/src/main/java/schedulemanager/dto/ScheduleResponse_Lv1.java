package schedulemanager.dto;

import java.time.LocalDateTime;

public class ScheduleResponse_Lv1 {
    private int scheduleId;
    private String title;
    private String writer;
    private LocalDateTime scheduledDate;     // 추가됨
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponse_Lv1(int scheduleId, String title, String writer, LocalDateTime scheduledDate,
                                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.scheduleId = scheduleId;
        this.title = title;
        this.writer = writer;
        this.scheduledDate = scheduledDate;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return
                "{scheduleId=" + scheduleId +
                        ", title='" + title + '\'' +
                        ", writer='" + writer + '\'' +
                        ", scheduledDate=" + scheduledDate +
                        ", createdAt=" + createdAt +
                        ", modifiedAt=" + modifiedAt +
                        '}';
    }

    public int getScheduleIdId() {
        return scheduleId;
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