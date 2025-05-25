import controller.ScheduleController;
import dto.ScheduleRequest_Lv3;
import dto.ScheduleSearchCondition_Lv3;
import dto.ScheduleUpdateRequest_Lv2;
import repository.ScheduleRepository;
import repository.ScheduleRepositoryImplement;
import repository.UserRepository_Lv3;
import repository.UserRepositoryImplement_Lv3;
import service.ScheduleService;
import service.ScheduleServiceImplement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ScheduleManager {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ScheduleRepository scheduleRepo = new ScheduleRepositoryImplement();
        UserRepository_Lv3 userRepo = new UserRepositoryImplement_Lv3();
        ScheduleService scheduleService =
                new ScheduleServiceImplement(scheduleRepo, userRepo);
        ScheduleController controller = new ScheduleController(scheduleService);

        while (true) {
            System.out.println("\n==== 일정 관리 프로그램 ====");
            System.out.println("1. 일정 등록");
            System.out.println("2. 전체 일정 조회");
            System.out.println("3. 단건 일정 조회");
            System.out.println("4. 일정 수정");
            System.out.println("5. 일정 삭제");
            System.out.println("0. 종료");
            System.out.print("원하는 작업을 선택하세요: ");

            String input = scanner.nextLine();

            try {
                switch (input) {
                    case "1":
                        // 일정 등록
                        System.out.print("할일: ");
                        String title = scanner.nextLine();
                        System.out.print("작성자명: ");
                        String writer = scanner.nextLine();
                        System.out.print("이메일: ");
                        String email = scanner.nextLine();
                        System.out.print("비밀번호: ");
                        String password = scanner.nextLine();
                        System.out.print("일정 날짜 (yyyy-MM-dd HH:mm): ");
                        LocalDateTime scheduledDate = LocalDateTime.parse(
                                scanner.nextLine(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                        );

                        ScheduleRequest_Lv3 request = new ScheduleRequest_Lv3(
                                title, writer, email, password, scheduledDate
                        );
                        controller.createSchedule(request);
                        break;

                    case "2":
                        // 전체 일정 조회
                        System.out.print("작성자 ID (생략 가능): ");
                        String userIdInput = scanner.nextLine();
                        System.out.print("수정일 (yyyy-MM-dd, 생략 가능): ");
                        String modifiedDateInput = scanner.nextLine();

                        ScheduleSearchCondition_Lv3 condition;
                        Integer userId = userIdInput.isBlank() ? null : Integer.parseInt(userIdInput);

                        if (modifiedDateInput.isBlank()) {
                            condition = new ScheduleSearchCondition_Lv3(userId, null);
                        } else {
                            LocalDate modifiedDate = LocalDate.parse(
                                    modifiedDateInput,
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            );
                            condition = new ScheduleSearchCondition_Lv3(userId, modifiedDate);
                        }

                        controller.searchSchedules(condition);
                        break;

                    case "3":
                        // 단건 일정 조회
                        System.out.print("조회할 일정 ID: ");
                        int id = Integer.parseInt(scanner.nextLine());
                        controller.findScheduleById(id);
                        break;

                    case "4":
                        // 일정 수정
                        System.out.print("수정할 일정 ID: ");
                        int updateId = Integer.parseInt(scanner.nextLine());
                        System.out.print("비밀번호: ");
                        String updatePassword = scanner.nextLine();
                        System.out.print("새 제목: ");
                        String newTitle = scanner.nextLine();
                        System.out.print("새 작성자명: ");
                        String newWriter = scanner.nextLine();

                        ScheduleUpdateRequest_Lv2 updateRequest = new ScheduleUpdateRequest_Lv2(
                                newTitle, newWriter, updatePassword
                        );
                        controller.updateSchedule(updateId, updatePassword, updateRequest);
                        break;

                    case "5":
                        // 일정 삭제
                        System.out.print("삭제할 일정 ID: ");
                        int deleteId = Integer.parseInt(scanner.nextLine());
                        System.out.print("비밀번호: ");
                        String deletePassword = scanner.nextLine();

                        controller.deleteSchedule(deleteId, deletePassword);
                        break;

                    case "0":
                        System.out.println("프로그램을 종료합니다.");
                        return;

                    default:
                        System.out.println("잘못된 입력입니다. 다시 선택해주세요.");
                }
            } catch (Exception e) {
                System.out.println("오류 발생: " + e.getMessage());
            }
        }
    }
}
