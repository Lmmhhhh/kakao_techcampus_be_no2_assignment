package controller;

import dto.ScheduleRequest_Lv1;
import dto.ScheduleResponse_Lv1;
import dto.ScheduleSearchCondition_Lv1;
import dto.ScheduleUpdateRequest_Lv2;
import service.ScheduleService;

import java.util.List;

public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 등록
    public void createSchedule(ScheduleRequest_Lv1 request) {
        scheduleService.createSchedule(request);
        System.out.println("일정이 성공적으로 등록되었습니다.");
    }

    // 단건 조회
    public void findScheduleById(Long id) {
        ScheduleResponse_Lv1 response = scheduleService.searchScheduleById(id);
        if (response != null) {
            System.out.println("일정 조회 결과: " + response);
        } else {
            System.out.println("해당 ID의 일정이 존재하지 않습니다.");
        }
    }

    // 전체 조건 조회
    public void searchSchedules(ScheduleSearchCondition_Lv1 condition) {
        List<ScheduleResponse_Lv1> results = scheduleService.searchAllSchedules(condition);
        if (results.isEmpty()) {
            System.out.println("조건에 맞는 일정이 없습니다.");
        } else {
            System.out.println("조회된 일정 목록:");
            for (ScheduleResponse_Lv1 s : results) {
                System.out.println(s);
            }
        }
    }

    // 일정 수정
    public void updateSchedule(Long id, String password, ScheduleUpdateRequest_Lv2 request) {
        scheduleService.updateSchedule(id, password, request);
        System.out.println("일정이 성공적으로 수정되었습니다.");
    }

    // 일정 삭제
    public void deleteSchedule(Long id, String password) {
        scheduleService.deleteSchedule(id, password);
        System.out.println("일정이 성공적으로 삭제되었습니다.");
    }
}