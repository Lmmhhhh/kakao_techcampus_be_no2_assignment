package dto;

import java.time.LocalDateTime;

public class ScheduleRequest_Lv1 {
    private String title;
    private String writer;
    private LocalDateTime scheduledDate;
    private String password;

    public ScheduleRequest_Lv1() {
    }

    public ScheduleRequest_Lv1(String title, String writer, String password, LocalDateTime scheduledDate) {
        this.title = title;
        this.writer = writer;
        this.password = password;
        this.scheduledDate = scheduledDate;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getPassword() {
        return password;
    }

    public LocalDateTime getScheduledDate() {
        return scheduledDate;
    }
}
