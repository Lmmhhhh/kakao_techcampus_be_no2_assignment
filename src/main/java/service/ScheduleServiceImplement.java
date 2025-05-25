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
    public Long createSchedule(ScheduleRequest_Lv1 request) {
        return scheduleRepository.save(request);
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
        String realPassword = scheduleRepository.findPasswordById(id);
        if (!realPassword.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.update(id, request);
    }

    @Override
    public void deleteSchedule(Long id, String password) {
        String realPassword = scheduleRepository.findPasswordById(id);
        if (!realPassword.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.delete(id);
    }
}
