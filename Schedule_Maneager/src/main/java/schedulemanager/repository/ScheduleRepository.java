package schedulemanager.repository;

import schedulemanager.dto.ScheduleRequest_Lv3;
import schedulemanager.dto.ScheduleResponse_Lv3;
import schedulemanager.dto.ScheduleSearchCondition_Lv3;
import schedulemanager.dto.ScheduleUpdateRequest_Lv2;

import java.util.List;

public interface ScheduleRepository {
    int save(int userId, ScheduleRequest_Lv3 request);
    ScheduleResponse_Lv3 findById(int scheduleId);
    List<ScheduleResponse_Lv3> findAllByCondition(ScheduleSearchCondition_Lv3 condition);
    void update(int scheduleId, ScheduleUpdateRequest_Lv2 request);
    void delete(int scheduleId);
    String findPasswordById(int scheduleId);
}