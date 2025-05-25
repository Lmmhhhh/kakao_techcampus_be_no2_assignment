package repository;

import dto.ScheduleRequest_Lv1;
import dto.ScheduleResponse_Lv1;
import dto.ScheduleSearchCondition_Lv1;
import dto.ScheduleUpdateRequest_Lv2;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ScheduleRepositoryImplement implements ScheduleRepository {

    @Override
    public void save(ScheduleRequest_Lv1 request) {
        String sql = "INSERT INTO schedule (title, writer, password, scheduled_date, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, NOW(), NOW())";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getWriter());
            pstmt.setString(3, request.getPassword());
            pstmt.setTimestamp(4, Timestamp.valueOf(request.getScheduledDate()));
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("일정 저장 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public ScheduleResponse_Lv1 findById(Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapToResponse(rs);
                } else {
                    throw new RuntimeException("해당 ID의 일정을 찾을 수 없습니다.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("ID로 일정 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public List<ScheduleResponse_Lv1> findAllByCondition(ScheduleSearchCondition_Lv1 condition) {
        StringBuilder sql = new StringBuilder("SELECT * FROM schedule WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (condition.getWriter() != null && !condition.getWriter().isBlank()) {
            sql.append(" AND writer = ?");
            params.add(condition.getWriter());
        }
        LocalDate modifiedDate = condition.getModifiedDate();
        if (modifiedDate != null) {
            sql.append(" AND DATE(modified_at) = ?");
            params.add(modifiedDate);
        }
        sql.append(" ORDER BY modified_at DESC");

        List<ScheduleResponse_Lv1> results = new ArrayList<>();
        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                int index = i + 1;
                if (param instanceof String) {
                    pstmt.setString(index, (String) param);
                } else if (param instanceof LocalDate) {
                    pstmt.setDate(index, Date.valueOf((LocalDate) param));
                } else if (param instanceof Long) {
                    pstmt.setLong(index, (Long) param);
                } else {
                    pstmt.setObject(index, param);
                }
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapToResponse(rs));
                }
            }
            return results;

        } catch (SQLException e) {
            throw new RuntimeException("일정 목록 조회 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void update(Long id, String password, ScheduleUpdateRequest_Lv2 request) {
        String sql = "UPDATE schedule SET title = ?, writer = ?, modified_at = NOW() " +
                "WHERE id = ? AND password = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getWriter());
            pstmt.setLong(3, id);
            pstmt.setString(4, password);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("ID와 비밀번호가 일치하는 일정을 찾을 수 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("일정 수정 중 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void delete(Long id, String password) {
        String sql = "DELETE FROM schedule WHERE id = ? AND password = ?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.setString(2, password);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new RuntimeException("ID와 비밀번호가 일치하는 일정을 찾을 수 없습니다.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("일정 삭제 중 오류가 발생했습니다.", e);
        }
    }

    private ScheduleResponse_Lv1 mapToResponse(ResultSet rs) throws SQLException {
        return new ScheduleResponse_Lv1(
                rs.getLong("id"),
                rs.getString("title"),
                rs.getString("writer"),
                rs.getTimestamp("scheduled_date").toLocalDateTime(),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("modified_at").toLocalDateTime()
        );
    }
}

