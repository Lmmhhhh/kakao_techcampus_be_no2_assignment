package service;

import dto.ScheduleRequest_Lv1;
import dto.ScheduleResponse_Lv1;
import dto.ScheduleSearchCondition_Lv1;
import dto.ScheduleUpdateRequest_Lv2;
import java.util.List;

public interface ScheduleService {
    void createSchedule(ScheduleRequest_Lv1 request);
    List<ScheduleResponse_Lv1> searchAllSchedules(ScheduleSearchCondition_Lv1 condition);
    ScheduleResponse_Lv1 searchScheduleById(Long id);
    void updateSchedule(Long id, String password, ScheduleUpdateRequest_Lv2 request);
    void deleteSchedule(Long id, String password);
}
