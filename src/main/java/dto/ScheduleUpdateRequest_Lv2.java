package dto;

public class ScheduleUpdateRequest_Lv2 {
    private String title;
    private String writer;
    private String password;

    public ScheduleUpdateRequest_Lv2() {
    }

    public ScheduleUpdateRequest_Lv2(String title, String writer, String password) {
        this.title = title;
        this.writer = writer;
        this.password = password;
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
}
