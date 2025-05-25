package schedulemanager.dto;

import java.time.LocalDateTime;

public class ScheduleRequest_Lv3 {
    private String title;
    private String writer;
    private String email;
    private String password;
    private LocalDateTime scheduledDate;

    public ScheduleRequest_Lv3() {}

    public ScheduleRequest_Lv3(
            String title,
            String writer,
            String email,
            String password,
            LocalDateTime scheduledDate
    ) {
        this.title = title;
        this.writer = writer;
        this.email = email;
        this.password = password;
        this.scheduledDate = scheduledDate;
    }

    public String getTitle() { return title; }
    public String getWriter() { return writer; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public LocalDateTime getScheduledDate() { return scheduledDate; }
}