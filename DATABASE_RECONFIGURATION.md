## Database Schema Reconfiguration Summary

This document outlines all changes made to align the Enterprise Manager application with the Vietnamese database specification.

### 1. Entity Changes

#### Account Entity (Table: accounts)
- **Primary Key Change**: Changed from `username` (varchar 50) to `email` (varchar 100)
- **Field Updates**:
  - `email` (varchar 100) - now the primary key
  - `password` (varchar 50) - unchanged

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/entity/Account.java`

#### New: EmployeeDetail Entity (Table: users)
- Created new entity `EmployeeDetail` to represent the "users" table in the database specification
- **Fields**:
  - `employeeId` (Integer, PK) - Khóa chính
  - `name` (nvarchar 50) - Tên nhân viên
  - `id_department` (FK to departments) - Khóa tham chiếu từ bảng departments
  - `address` (nvarchar 100) - Địa chỉ
  - `birth` (Datetime) - Ngày sinh
  - `email` (varchar 100, FK to accounts) - Khóa tham chiếu từ accounts
  - `gender` (nvarchar 5) - Giới tính
  - `numberphone` (nvarchar 10) - Số điện thoại
  - `joinDate` (Datetime) - Ngày vào công ty
  - `role` (nvarchar 100) - Vai trò

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/entity/EmployeeDetail.java`

**Note**: The existing `User` entity remains for Spring Security authentication purposes.

#### Department Entity (Table: departments)
- **Field Updates**:
  - Removed: `manageId` (Người quản lý phòng ban)
  - Added: `id_department` (Integer) - Self-reference for parent department
- **Fields** now match specification:
  - `id` (int, PK)
  - `logo` (varchar 30)
  - `id_department` (int) - Self-reference
  - `name` (nvarchar 30)

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/entity/Department.java`

#### Attendance Entity (Table: attendances)
- **Foreign Key Update**: Changed reference from generic `id` to `employee_id` (EmployeeDetail.employeeId)
- **Fields**:
  - `id` (int, PK)
  - `employee_id` (FK to users/EmployeeDetail)
  - `date` (Datetime)
  - `start_time` (Datetime)
  - `end_time` (Datetime)
  - `status` (nvarchar 50)

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/entity/Attendance.java`

#### OvertimeRecord Entity (Table: overtime_records)
- **Foreign Key Update**: Changed reference from generic `id` to `employee_id` (EmployeeDetail.employeeId)
- **Fields**:
  - `id` (int, PK)
  - `employee_id` (FK to users/EmployeeDetail)
  - `type` (nvarchar 50)
  - `date` (Datetime)
  - `start_time` (Datetime)
  - `end_time` (Datetime)

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/entity/OvertimeRecord.java`

#### Payroll Entity (Table: payrolls)
- **Composite Key Update**: Uses `PayrollId` with `employee_id` (FK to users/EmployeeDetail) and `month` (thang)
- **Field Updates**:
  - Fixed `id_department` foreign key to reference `Department.id`
  - **Fields** now match specification:
    - `id` (Composite: employee_id + thang)
    - `employee_id` (FK to users/EmployeeDetail)
    - `id_department` (FK to departments) - nvarchar 30
    - `thang` (nvarchar 15) - Tháng
    - `ngay_cong` (int) - Số ngày công
    - `thuong` (int) - Tiền thưởng
    - `phat` (int) - Tiền phạt
    - `thue` (int) - Tiền thuế
    - `bao_hiem` (int) - Tiền bảo hiểm
    - `tong_cong` (int) - Tổng lương thực lĩnh

**Files**: 
- `src/main/java/io/github/ngthduongg623/enterprise_manager/entity/Payroll.java`
- `src/main/java/io/github/ngthduongg623/enterprise_manager/entity/PayrollId.java` (Updated column name from `id` to `employee_id`)

### 2. Repository Changes

#### AccountRepository
- Updated primary key type and methods to use `email` instead of `username`
- **Methods updated**:
  - `findByEmail(String email)` - replaces `findByUsername(String username)`

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/dao/AccountRepository.java`

#### New: EmployeeDetailRepository
- Created new repository for `EmployeeDetail` entity
- **Methods**:
  - `findByEmail(String email)`
  - `findByDepartmentId(Integer departmentId)`
  - `findByEmployeeId(Integer employeeId)`

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/dao/EmployeeDetailRepository.java`

#### AttendanceRepository
- **Query methods updated** to use nested property access:
  - `findByEmployeeEmployeeId(Integer employeeId)` - replaces `findByEmployeeId()`
  - `findByEmployeeEmployeeIdAndDate(Integer employeeId, LocalDate date)` - replaces `findByEmployeeIdAndDate()`

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/dao/AttendanceRepository.java`

#### OvertimeRecordRepository
- **Query methods updated** to use nested property access:
  - `findByEmployeeEmployeeId(Integer employeeId)` - replaces `findByEmployeeId()`
  - `findByEmployeeEmployeeIdAndDate(Integer employeeId, LocalDate date)` - replaces `findByEmployeeIdAndDate()`

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/dao/OvertimeRecordRepository.java`

#### PayrollRepository
- **Added method**: `findByEmployeeEmployeeId(Integer employeeId)` for querying by employee via nested reference
- **Existing methods maintained**: `findByIdEmployeeId()` and `findByIdMonth()` for composite key queries

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/dao/PayrollRepository.java`

### 3. Service Changes

#### AccountService & AccountServiceImpl
- Updated all methods to use `email` instead of `username`
- **Methods updated**:
  - `findByEmail(String email)` - replaces `findByUsername(String username)`
  - `deleteByEmail(String email)` - replaces `deleteByUsername(String username)`
  - `existsByEmail(String email)` - replaces `existsByUsername(String username)`

**Files**:
- `src/main/java/io/github/ngthduongg623/enterprise_manager/service/AccountService.java`
- `src/main/java/io/github/ngthduongg623/enterprise_manager/service/AccountServiceImpl.java`

#### New: EmployeeDetailService & EmployeeDetailServiceImpl
- Created new service layer for `EmployeeDetail` entity management
- **Methods**:
  - `findAll()`, `findByEmployeeId()`, `findByEmail()`, `findByDepartmentId()`
  - `save()`, `deleteByEmployeeId()`, `existsByEmployeeId()`

**Files**:
- `src/main/java/io/github/ngthduongg623/enterprise_manager/service/EmployeeDetailService.java`
- `src/main/java/io/github/ngthduongg623/enterprise_manager/service/EmployeeDetailServiceImpl.java`

#### AttendanceServiceImpl
- Updated to use new repository method names:
  - `findByEmployeeEmployeeId()` instead of `findByEmployeeId()`
  - `findByEmployeeEmployeeIdAndDate()` instead of `findByEmployeeIdAndDate()`

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/service/AttendanceServiceImpl.java`

#### OvertimeRecordServiceImpl
- Updated to use new repository method names:
  - `findByEmployeeEmployeeId()` instead of `findByEmployeeId()`
  - `findByEmployeeEmployeeIdAndDate()` instead of `findByEmployeeIdAndDate()`

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/service/OvertimeRecordServiceImpl.java`

#### PayrollServiceImpl
- Updated to use new repository method:
  - `findByEmployeeEmployeeId()` for employee-based queries
- Added new method: `findByEmployeeId()` that delegates to repository

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/service/PayrollServiceImpl.java`

### 4. Controller Changes

#### AccountRestController
- Updated all REST endpoints to use `email` as the path parameter instead of `username`
- **Endpoint updates**:
  - `GET /api/accounts/{email}` - replaces `/api/accounts/{username}`
  - `POST /api/accounts` - creates with email
  - `PUT /api/accounts/{email}` - replaces `/api/accounts/{username}`
  - `DELETE /api/accounts/{email}` - replaces `/api/accounts/{username}`

**File**: `src/main/java/io/github/ngthduongg623/enterprise_manager/controller/AccountRestController.java`

### 5. Compilation Status

✅ **Build Status**: SUCCESS
- All 61 source files compiled successfully (Java 21)
- No compilation errors
- Build time: ~4.3 seconds

### Notes for Next Steps

1. **Database Migration**: Create migration scripts to:
   - Rename existing tables if necessary
   - Update column definitions to match VARCHAR/NVARCHAR sizes
   - Add the `id_department` self-reference column to `departments` table
   - Add new fields to `users` table (if migrating from existing schema)
   - Update foreign key relationships

2. **Data Loading**: Update [DataLoader.java](src/main/java/io/github/ngthduongg623/enterprise_manager/config/DataLoader.java) to:
   - Use `EmployeeDetail` for employee data
   - Update account creation to use email as primary key
   - Populate new fields (birth, joinDate, gender, numberphone, etc.)

3. **Integration Tests**: Update any existing tests to:
   - Use new repository method names
   - Reference EmployeeDetail instead of generic Employee
   - Test with new email-based Account primary key

4. **Controller Updates**: Review other controllers (not REST) to:
   - Use EmployeeDetailService where needed
   - Update any views/forms that reference old structure

5. **Application Properties**: Verify `application.properties` database connection:
   - Confirm MSSQL Server datasource is configured correctly
   - Verify proper table/schema naming matches specification
