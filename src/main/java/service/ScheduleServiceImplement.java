package service;

import dto.ScheduleRequest_Lv1;
import dto.ScheduleResponse_Lv1;
import dto.ScheduleSearchCondition_Lv1;
import dto.ScheduleUpdateRequest_Lv2;
import dto.ScheduleDeleteRequest_Lv2;
import repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class ScheduleServiceImplement implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final Scanner scanner = new Scanner(System.in);

    public ScheduleServiceImplement(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void createSchedule() {
        System.out.print("제목: ");
        String title = scanner.nextLine();
        System.out.print("작성자명: ");
        String writer = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();
        System.out.print("일정 날짜와 시간 (예: 2024-06-01T14:00): ");
        LocalDateTime scheduledDate = LocalDateTime.parse(scanner.nextLine());

        ScheduleRequest_Lv1 request = new ScheduleRequest_Lv1(title, writer, password, scheduledDate);
        scheduleRepository.save(request);
        System.out.println("일정이 성공적으로 등록되었습니다.");
    }

    @Override
    public void searchAllSchedules() {
        System.out.print("수정일(예: 2024-06-01) 또는 Enter 생략: ");
        String modifiedDateInput = scanner.nextLine();
        System.out.print("작성자명 또는 Enter 생략: ");
        String writer = scanner.nextLine();

        ScheduleSearchCondition_Lv1 condition = new ScheduleSearchCondition_Lv1(
                modifiedDateInput.isBlank() ? null : modifiedDateInput,
                writer.isBlank() ? null : writer
        );

        List<ScheduleResponse_Lv1> schedules = scheduleRepository.findAllByCondition(condition);
        for (ScheduleResponse_Lv1 s : schedules) {
            System.out.println(s);
        }
    }

    @Override
    public void searchScheduleById() {
        System.out.print("조회할 일정 ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        ScheduleResponse_Lv1 schedule = scheduleRepository.findById(id);
        System.out.println(schedule);
    }

    @Override
    public void updateSchedule() {
        System.out.print("수정할 일정 ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        System.out.print("새 제목: ");
        String title = scanner.nextLine();
        System.out.print("새 작성자명: ");
        String writer = scanner.nextLine();
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        ScheduleUpdateRequest_Lv2 req = new ScheduleUpdateRequest_Lv2(id, title, writer, password);
        scheduleRepository.update(req);
        System.out.println("일정이 수정되었습니다.");
    }

    @Override
    public void deleteSchedule() {
        System.out.print("삭제할 일정 ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("비밀번호: ");
        String password = scanner.nextLine();

        ScheduleDeleteRequest_Lv2 req = new ScheduleDeleteRequest_Lv2(id, password);
        scheduleRepository.delete(req);
        System.out.println("일정이 삭제되었습니다.");
    }

    @Override
    public void save(ScheduleRequest_Lv1 request) {
        scheduleRepository.save(request);
    }

    @Override
    public ScheduleResponse_Lv1 findById(Long id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public List<ScheduleResponse_Lv1> findAllByCondition(ScheduleSearchCondition_Lv1 condition) {
        return scheduleRepository.findAllByCondition(condition);
    }
}
