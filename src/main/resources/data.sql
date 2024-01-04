-- school
INSERT INTO benchmark_hust.school (id, created_date, updated_date, abbreviations, vn_name, en_name)
VALUES (1, '2023-12-13 01:38:21', '2023-12-13 01:38:21', 'SME', 'Trường Cơ Khí', 'School of Mechanical Engineering'),
       (2, '2023-12-13 01:38:40', '2023-12-13 01:38:40', 'SEEE', 'Trường Điện - Điện tử', 'School of Electrical & Electronic Engineering'),
       (3, '2023-12-13 01:38:49', '2023-12-13 01:38:49', 'SoICT', 'Trường Công nghệ Thông tin và Truyền thông', 'School of Information and Communications Technology'),
       (4, '2023-12-13 01:40:49', '2023-12-13 01:40:49', 'SBFT', 'Trường Hóa và Khoa học sự sống', 'School of Biotechnology and Food Technology'),
       (5, '2023-12-13 01:49:04', '2023-12-13 01:49:04', 'SMSE', 'Trường Vật Liệu', 'School of Materials Science and Engineering');

-- group
INSERT INTO benchmark_hust.`group` (id, created_date, updated_date, code, subject1, subject2, subject3, group_type)
VALUES (1, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'A00', 'Toán', 'Vật lý', 'Hóa học', 'BASIC'),
       (2, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'A01', 'Toán', 'Vật lý', 'Tiếng Anh', 'BASIC'),
       (3, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'A02', 'Toán', 'Vật lý', 'Sinh học', 'BASIC'),
       (4, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'B00', 'Toán', 'Hóa học', 'Sinh học', 'BASIC'),
       (5, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D01', 'Ngữ văn', 'Toán học', 'Tiếng Anh', 'BASIC'),
       (6, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D07', 'Toán học', 'Hóa học', 'Tiếng Anh', 'BASIC'),
       (7, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D26', 'Toán học', 'Vật lý', 'Tiếng Đức', 'BASIC'),
       (8, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D28', 'Toán học', 'Vật lý', 'Tiếng Nhật', 'BASIC'),
       (9, '2023-12-13 01:38:18', '2023-12-13 01:38:18', 'D29', 'Toán học', 'Vật lý', 'Tiếng Pháp', 'BASIC'),
       (10, '2023-12-13 01:38:20', '2023-12-13 01:38:20', 'K00', null, null, null, 'TSA');

INSERT INTO benchmark_hust.faculty (id, created_date, updated_date, code, name, school_id)
VALUES (1, '2023-12-13 23:43:15.132811', '2023-12-13 23:43:15.132811', 'EE-E18', 'Hệ thống điện và năng lượng tái tạo (Chương trình tiên tiến)', 1),
       (2, '2023-12-13 23:43:56.401214', '2023-12-13 23:43:56.401214', 'EE-E8', 'Kỹ thuật Điều khiển - Tự động hóa (Chương trình tiên tiến)', 2),
       (3, '2023-12-13 23:45:27.663163', '2023-12-13 23:45:27.663163', 'EE-EP', 'Tin học công nghiệp và Tự động hóa (Chương trình Việt-Pháp PFIEV)', 3),
       (4, '2023-12-13 23:46:16.529120', '2023-12-13 23:46:16.529120', 'EE1', 'Kỹ thuật điện', 4),
       (5, '2023-12-13 23:46:35.637204', '2023-12-13 23:46:35.637204', 'EE2', 'Kỹ thuật Điều khiển - Tự động hoá', 5),
       (6, '2023-12-13 23:47:22.757060', '2023-12-13 23:47:22.757060', 'ET-E16', 'Truyền thông số và kỹ thuật đa phương tiện (Chương trình tiên tiến)', 1),
       (7, '2023-12-13 23:47:59.629170', '2023-12-13 23:47:59.629170', 'ET-E4', 'Kỹ thuật Điện tử - Viễn thông (Chương trình tiên tiến)', 2),
       (8, '2023-12-13 23:48:35.753245', '2023-12-13 23:48:35.753245', 'ET-E5', 'Kỹ thuật Y sinh (Chương trình tiên tiến)', 3),
       (9, '2023-12-13 23:48:54.777193', '2023-12-13 23:48:54.777193', 'ET-E9', 'Hệ thống nhúng thông minh và IoT (Chương trình tiên tiến)', 4),
       (10, '2023-12-13 23:51:05.734783', '2023-12-13 23:51:05.734783', 'ET-LUH', 'Điện tử viễn thông - ĐH Leibniz Hannover (Đức)', 5),
       (11, '2023-12-13 23:51:58.944683', '2023-12-13 23:51:58.944683', 'ET1', 'Điện tử và viễn thông', 1),
       (12, '2023-12-13 23:52:27.897111', '2023-12-13 23:52:27.897111', 'ET2', 'Kỹ thuật Y sinh', 2);