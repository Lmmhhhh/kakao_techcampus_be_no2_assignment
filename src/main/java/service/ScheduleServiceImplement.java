package service;

import dto.ScheduleRequest_Lv3;
import dto.ScheduleResponse_Lv3;
import dto.ScheduleSearchCondition_Lv3;
import dto.ScheduleUpdateRequest_Lv2;
import repository.ScheduleRepository;
import repository.UserRepository_Lv3;

import java.util.List;

public class ScheduleServiceImplement implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository_Lv3 userRepository;

    public ScheduleServiceImplement(ScheduleRepository scheduleRepository,
                                    UserRepository_Lv3 userRepository) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public int createSchedule(ScheduleRequest_Lv3 request) {
        var existing = userRepository.findByEmail(request.getEmail());
        int userId;

        if (existing == null) {
            var newUser = new repository.User_Lv3(request.getWriter(), request.getEmail());
            userId = userRepository.save(newUser);
        } else {
            userId = existing.getUserId();
        }

        return scheduleRepository.save(userId, request);
    }

    @Override
    public List<ScheduleResponse_Lv3> searchAllSchedules(ScheduleSearchCondition_Lv3 c) {
        return scheduleRepository.findAllByCondition(c);
    }

    @Override
    public ScheduleResponse_Lv3 searchScheduleById(int scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }

    @Override
    public void updateSchedule(int scheduleId, String password, ScheduleUpdateRequest_Lv2 req) {
        String realPassword = scheduleRepository.findPasswordById(scheduleId);
        if (!realPassword.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.update(scheduleId, req);
    }

    @Override
    public void deleteSchedule(int scheduleId, String password) {
        String realPassword = scheduleRepository.findPasswordById(scheduleId);
        if (!realPassword.equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        scheduleRepository.delete(scheduleId);
    }
}
