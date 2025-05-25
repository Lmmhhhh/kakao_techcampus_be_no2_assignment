package service;

import dto.ScheduleRequest_Lv3;
import dto.ScheduleResponse_Lv3;
import dto.ScheduleSearchCondition_Lv3;
import dto.ScheduleUpdateRequest_Lv2;

import java.util.List;

public interface ScheduleService {
    int createSchedule(ScheduleRequest_Lv3 request);
    List<ScheduleResponse_Lv3> searchAllSchedules(ScheduleSearchCondition_Lv3 condition);
    ScheduleResponse_Lv3 searchScheduleById(int scheduleId);
    void updateSchedule(int scheduleId, String password, ScheduleUpdateRequest_Lv2 request);
    void deleteSchedule(int scheduleId, String password);
}

