package schedulemanager.dto;

import java.time.LocalDateTime;

public class ScheduleResponse_Lv3 {
    private int scheduleId;
    private int userId;
    private String title;
    private String writer;
    private LocalDateTime scheduledDate;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public ScheduleResponse_Lv3(int scheduleId, int userId, String title, String writer, LocalDateTime scheduledDate,
                                LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.scheduleId = scheduleId;
        this.userId = userId;
        this.title = title;
        this.writer = writer;
        this.scheduledDate = scheduledDate;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "{scheduleId=" + scheduleId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", writer='" + writer + '\'' +
                ", scheduledDate=" + scheduledDate +
                ", createdAt=" + createdAt +
                ", modifiedAt=" + modifiedAt +
                '}';
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public int getUserId() {
        return userId;
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