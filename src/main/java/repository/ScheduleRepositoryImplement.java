package repository;

import dto.ScheduleRequest_Lv1;
import dto.ScheduleResponse_Lv1;
import dto.ScheduleSearchCondition_Lv1;
import dto.ScheduleUpdateRequest_Lv2;
import dto.ScheduleDeleteRequest_Lv2;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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

            bindInsertParameters(pstmt, request);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save schedule", e);
        }
    }

    private void bindInsertParameters(PreparedStatement pstmt, ScheduleRequest_Lv1 req) throws SQLException {
        pstmt.setString(1, req.getTitle());
        pstmt.setString(2, req.getWriter());
        pstmt.setString(3, req.getPassword());
        pstmt.setTimestamp(4, Timestamp.valueOf(req.getScheduledDate()));
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
                    throw new RuntimeException("Schedule with ID " + id + " not found");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to query schedule by ID", e);
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

            bindParameters(pstmt, params);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    results.add(mapToResponse(rs));
                }
            }
            return results;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to query schedules", e);
        }
    }

    private void bindParameters(PreparedStatement pstmt, List<Object> params) throws SQLException {
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
                throw new RuntimeException("No schedule found with the given ID and password.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update schedule", e);
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
                throw new RuntimeException("No schedule found with the given ID and password.");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete schedule", e);
        }
    }

