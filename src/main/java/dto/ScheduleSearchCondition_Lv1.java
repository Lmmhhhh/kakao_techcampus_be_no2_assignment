package dto;

import java.time.LocalDate;

public class ScheduleSearchCondition_Lv1 {
    private String writer;
    private LocalDate modifiedDate;

    public ScheduleSearchCondition_Lv1() {
    }

    public ScheduleSearchCondition_Lv1(String writer, LocalDate modifiedDate) {
        this.writer = writer;
        this.modifiedDate = modifiedDate;
    }

    public String getWriter() {
        return writer;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }
}
