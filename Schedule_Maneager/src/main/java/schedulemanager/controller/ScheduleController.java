package schedulemanager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import schedulemanager.dto.ScheduleRequest_Lv3;
import schedulemanager.dto.ScheduleResponse_Lv3;
import schedulemanager.dto.ScheduleSearchCondition_Lv3;
import schedulemanager.dto.ScheduleUpdateRequest_Lv2;
import schedulemanager.dto.ScheduleDeleteRequest_Lv2;
import schedulemanager.service.ScheduleService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    // 일정 등록
    @PostMapping
    public ResponseEntity<Integer> createSchedule(@RequestBody ScheduleRequest_Lv3 request) {
        try {
            System.out.println("요청 JSON = " + objectMapper.writeValueAsString(request));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        int scheduleId = scheduleService.createSchedule(request);
        return ResponseEntity.ok(scheduleId);
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponse_Lv3> findScheduleById(@PathVariable int id) {
        ScheduleResponse_Lv3 response = scheduleService.searchScheduleById(id);
        return ResponseEntity.ok(response);
    }

    // 전체 조건 조회 (userId, modifiedDate)
    @GetMapping
    public ResponseEntity<List<ScheduleResponse_Lv3>> searchSchedules(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) LocalDate modifiedDate
    ) {
        ScheduleSearchCondition_Lv3 condition = new ScheduleSearchCondition_Lv3(userId, modifiedDate);
        List<ScheduleResponse_Lv3> results = scheduleService.searchAllSchedules(condition);
        return ResponseEntity.ok(results);
    }

    // 일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSchedule(@PathVariable int id,
                                               @RequestParam String password,
                                               @RequestBody ScheduleUpdateRequest_Lv2 request) {
        scheduleService.updateSchedule(id, password, request);
        return ResponseEntity.ok().build();
    }

    // 일정 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable int id,
                                               @RequestParam String password) {
        scheduleService.deleteSchedule(id, password);
        return ResponseEntity.ok().build();
    }
}

