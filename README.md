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

## Example
 이미지로 대체 
 ![스크린샷 2025-05-25 234015](https://github.com/user-attachments/assets/7d141d12-f61e-438b-b1ad-c045237c85c6)
![스크린샷 2025-05-25 234031](https://github.com/user-attachments/assets/58e62bc9-4697-4e31-a8f0-99ab0add5dbf)
![스크린샷 2![스크린샷 2025-05-25 234045](https://github.com/user-attachments/assets/034ad813-3ce1-414e-835f-1a1dc7107cd6)
025-05-25 234045](https://github.com/user-attachments/assets/210fc7b8-3c96-4fb3-98b1-6b10de6a28e5)
![스크린샷 2025-05-25 234131](https://github.com/user-attachments/assets/954b1308-41dc-4fa6-8649-e56455777f57)


## ERD
![Image](https://github.com/user-attachments/assets/25f308d6-bdb6-4345-b631-da3b8ead65af)



