-- school
INSERT INTO benchmark_hust.school (id, created_date, updated_date, abbreviations, vn_name, en_name)
VALUES (1, '2023-12-13 01:38:21', '2023-12-13 01:38:21', 'SME', 'Trường Cơ Khí', 'School of Mechanical Engineering'),
       (2, '2023-12-13 01:38:40', '2023-12-13 01:38:40', 'SEEE', 'Trường Điện - Điện tử', 'School of Electrical & Electronic Engineering'),
       (3, '2023-12-13 01:38:49', '2023-12-13 01:38:49', 'SoICT', 'Trường Công nghệ Thông tin và Truyền thông', 'School of Information and Communications Technology'),
       (4, '2023-12-13 01:40:49', '2023-12-13 01:40:49', 'SBFT', 'Trường Hóa và Khoa học sự sống', 'School of Biotechnology and Food Technology'),
       (5, '2023-12-13 01:49:04', '2023-12-13 01:49:04', 'SMSE', 'Trường Vật Liệu', 'School of Materials Science and Engineering');

-- group
INSERT INTO benchmark_hust.`group` (id, created_date, updated_date, code, subject1, subject2, subject3)
VALUES (1, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'A00', 'Toán', 'Vật lý', 'Hóa học'),
       (2, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'A01', 'Toán', 'Vật lý', 'Tiếng Anh'),
       (3, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'A02', 'Toán', 'Vật lý', 'Sinh học'),
       (4, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'B00', 'Toán', 'Hóa học', 'Sinh học'),
       (5, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D01', 'Ngữ văn', 'Toán học', 'Tiếng Anh'),
       (6, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D07', 'Toán học', 'Hóa học', 'Tiếng Anh'),
       (7, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D26', 'Toán học', 'Vật lý', 'Tiếng Đức'),
       (8, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D28', 'Toán học', 'Vật lý', 'Tiếng Nhật'),
       (9, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D29', 'Toán học', 'Vật lý', 'Tiếng Pháp'),
       (10, '2023-12-13 01:38:20', '2023-12-13 01:38:20', 'K00', null, null, null);
