# Schedule Manager API

The Schedule Manager API provides core endpoints and tools that enable users to **create**, **view**, **update**, and **delete** schedules through a simple and secure interface.

---

## Overview

- All requests and responses use **JSON** format.
- When creating a schedule, users must provide a **password**, which is:
  - **Included only in the request** (never in response).
  - Used for verifying identity during **update** and **delete**.
- No login or API key is required.

---

## Getting Started

### 🔗 Base Endpoints

| Method | URL                     | Description                     |
|--------|--------------------------|----------------------------------|
| POST   | `/api/schedules`         | Create a new schedule            |
| GET    | `/api/schedules/{id}`    | Get a specific schedule          |
| GET    | `/api/schedules`         | Get all schedules                |
| PUT    | `/api/schedules/{id}`    | Update a schedule                |
| DELETE | `/api/schedules/{id}`    | Delete a schedule                |

### Required Fields

- **Create:** `title`, `content`, `password`
- **Update/Delete:** `password`

All operations use `application/json` format.

---

## API Specification

| 기능             | Method | URL                    | request      | response          | 상태 코드                     |
|------------------|--------|-------------------------|--------------|-------------------|------------------------------|
| 일정 등록         | POST   | `/api/schedules`        | 요청 body     | 등록 정보          | 200 : 정상등록               |
| 특정 일정 조회     | GET    | `/api/schedules/{id}`   | 요청 param    | 단건 일정 정보      | 200 : 정상조회               |
| 전체 일정 조회     | GET    | `/api/schedules`        | 요청 param    | 다건 일정 정보      | 200 : 정상조회               |
| 일정 수정         | PUT    | `/api/schedules/{id}`   | 요청 body     | 수정된 일정 정보    | 200 : 정상수정               |
| 일정 삭제         | DELETE | `/api/schedules/{id}`   | 요청 body     | 성공/실패 메시지    | 200(삭제) or 400(삭제 실패) |

---

## Authentication

This API does **not use login tokens or API keys**.

Instead, it uses a **password-based** verification method:

- The user must include the correct `password` in the body when updating or deleting a schedule.
- Passwords are never returned in API responses.

### Authentication Error Response

```json
// 잘못된 비밀번호
{
  "error": "Password does not match."
}

// 존재하지 않는 일정
{
  "error": "Schedule not found."
}
