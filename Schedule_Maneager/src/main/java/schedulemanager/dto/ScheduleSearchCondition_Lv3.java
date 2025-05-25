package schedulemanager.dto;

import java.time.LocalDate;

public class ScheduleSearchCondition_Lv3 {
    private Integer writerId;
    private LocalDate modifiedDate;

    public ScheduleSearchCondition_Lv3() {}

    public ScheduleSearchCondition_Lv3(Integer writerId, LocalDate modifiedDate) {
        this.writerId = writerId;
        this.modifiedDate = modifiedDate;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public LocalDate getModifiedDate() {
        return modifiedDate;
    }
}
