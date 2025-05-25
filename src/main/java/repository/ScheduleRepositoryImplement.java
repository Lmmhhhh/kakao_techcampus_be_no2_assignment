package repository;

import dto.ScheduleRequest_Lv3;
import dto.ScheduleResponse_Lv3;
import dto.ScheduleSearchCondition_Lv3;
import dto.ScheduleUpdateRequest_Lv2;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepositoryImplement implements ScheduleRepository {

    @Override
    public int save(int userId, ScheduleRequest_Lv3 request) {
        String sql = "INSERT INTO schedule (user_id, title, password, scheduled_date, created_at, modified_at) "
                + "VALUES (?, ?, ?, ?, NOW(), NOW())";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, request.getTitle());
            pstmt.setString(3, request.getPassword());
            pstmt.setTimestamp(4, Timestamp.valueOf(request.getScheduledDate()));
            pstmt.executeUpdate();
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
                else throw new RuntimeException("일정 ID 생성 실패");
            }
        } catch (SQLException e) {
            throw new RuntimeException("일정 저장 실패", e);
        }
    }

    @Override
    public ScheduleResponse_Lv3 findById(int scheduleId) {
        String sql = "SELECT s.schedule_id AS scheduleId, s.title, u.name AS writer, s.scheduled_date, s.created_at, s.modified_at "
                + "FROM schedule s JOIN user u ON s.user_id = u.user_id WHERE s.schedule_id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, scheduleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return mapToResponse(rs);
                else throw new RuntimeException("해당 일정이 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("일정 조회 실패", e);
        }
    }

    @Override
    public List<ScheduleResponse_Lv3> findAllByCondition(ScheduleSearchCondition_Lv3 c) {
        StringBuilder sql = new StringBuilder(
                "SELECT s.schedule_id AS scheduleId, s.user_id AS userId,  s.title, u.name AS writer, s.scheduled_date, s.created_at, s.modified_at "
                        + "FROM schedule s JOIN user u ON s.user_id = u.user_id WHERE 1=1"
        );
        List<Object> params = new ArrayList<>();
        if (c.getWriterId() != null) {
            sql.append(" AND u.user_id = ?");
            params.add(c.getWriterId());
        }
        if (c.getModifiedDate() != null) {
            sql.append(" AND DATE(s.modified_at) = ?");
            params.add(c.getModifiedDate());
        }
        sql.append(" ORDER BY s.modified_at DESC");

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object p = params.get(i);
                int idx = i + 1;
                if (p instanceof Integer) pstmt.setInt(idx, (Integer)p);
                else if (p instanceof LocalDate) pstmt.setDate(idx, Date.valueOf((LocalDate)p));
                else pstmt.setObject(idx, p);
            }

            List<ScheduleResponse_Lv3> list = new ArrayList<>();
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) list.add(mapToResponse(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("목록 조회 실패", e);
        }
    }

    @Override
    public void update(int scheduleId, ScheduleUpdateRequest_Lv2 req) {
        String sql = "UPDATE schedule SET title = ?, modified_at = NOW() WHERE schedule_id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, req.getTitle());
            pstmt.setInt(2, scheduleId);
            if (pstmt.executeUpdate() == 0) {
                throw new RuntimeException("해당 일정이 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("일정 수정 실패", e);
        }
    }

    @Override
    public void delete(int scheduleId) {
        String sql = "DELETE FROM schedule WHERE schedule_id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, scheduleId);
            if (pstmt.executeUpdate() == 0) {
                throw new RuntimeException("해당 일정이 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("일정 삭제 실패", e);
        }
    }

    @Override
    public String findPasswordById(int scheduleId) {
        String sql = "SELECT password FROM schedule WHERE schedule_id = ?";
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, scheduleId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return rs.getString("password");
                else throw new RuntimeException("해당 일정이 없습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("비밀번호 조회 중 오류 발생", e);
        }
    }

    private ScheduleResponse_Lv3 mapToResponse(ResultSet rs) throws SQLException {
        return new ScheduleResponse_Lv3(
                rs.getInt("scheduleId"),
                rs.getInt("userId"),
                rs.getString("title"),
                rs.getString("writer"),
                rs.getTimestamp("scheduled_date").toLocalDateTime(),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()
        );
    }
}
