package repository;

import dto.ScheduleRequest_Lv1;
import dto.ScheduleResponse_Lv1;
import dto.ScheduleSearchCondition_Lv1;
import dto.ScheduleUpdateRequest_Lv2;
import java.util.List;

public interface ScheduleRepository {
    Long save(ScheduleRequest_Lv1 request);
    ScheduleResponse_Lv1 findById(Long id);
    List<ScheduleResponse_Lv1> findAllByCondition(ScheduleSearchCondition_Lv1 condition);
    void update(Long id, String password, ScheduleUpdateRequest_Lv2 request);
    void delete(Long id, String password);
}