package service;

import dto.ScheduleRequest_Lv1;
import dto.ScheduleResponse_Lv1;
import dto.ScheduleSearchCondition_Lv1;
import dto.ScheduleUpdateRequest_Lv2;
import repository.ScheduleRepository;
import java.util.List;

public class ScheduleServiceImplement implements ScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImplement(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void createSchedule(ScheduleRequest_Lv1 request) {
        scheduleRepository.save(request);
    }

    @Override
    public List<ScheduleResponse_Lv1> searchAllSchedules(ScheduleSearchCondition_Lv1 condition) {
        return scheduleRepository.findAllByCondition(condition);
    }

    @Override
    public ScheduleResponse_Lv1 searchScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public void updateSchedule(Long id, String password, ScheduleUpdateRequest_Lv2 request) {
        scheduleRepository.update(id, password, request);
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        scheduleRepository.delete(id, password);
    }
}