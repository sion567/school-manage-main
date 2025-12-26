INSERT INTO tbl_user_account
    (id, created_at, updated_at, username, password, is_account_non_expired, is_account_non_locked, is_credentials_non_expired, is_enabled)
VALUES
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin@example.com', '$2a$10$zGp91DzVYBmv42B9QJ6FE.iOBKAtyiKP.KqZZmWOIx3r9iOT6nVqi', true, true, true, true);

INSERT INTO sys_role (id, role_name, created_at, updated_at, user_account_id) VALUES
    (default, 'ADMIN', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

INSERT INTO sys_authority
    (id, created_at, updated_at, authority_name, role_id)
VALUES
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'school:read', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'school:write', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'school:delete', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'grade:read', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'grade:write', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'grade:delete', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'classroom:read', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'classroom:write', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'classroom:delete', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'teacher:read', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'teacher:write', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'teacher:delete', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'course:read', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'course:write', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'course:delete', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'student:read', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'student:write', 1),
    (default, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'student:delete', 1);


INSERT INTO tbl_schools
(city, detail_address, district, postal_code, province, street, email, phone_number, created_at, name, updated_at, id)
values
    ('重庆市', '建外大街', '', 100000, '', '', 'test@school1.com', '011-111111', CURRENT_TIMESTAMP, 'A小学', CURRENT_TIMESTAMP, default),
    ('重庆市', '建外大街', '', 200000, '', '', 'test@school2.cn', '022-222222', CURRENT_TIMESTAMP, '二中', CURRENT_TIMESTAMP, default),
    ('重庆市', '建外大街', '', 300000, '', '', 'test@school3.org', '033-333333', CURRENT_TIMESTAMP, '三高', CURRENT_TIMESTAMP, default);


INSERT INTO tbl_grades (id, created_at, name, school_id, updated_at)
VALUES
    (default, CURRENT_TIMESTAMP, '小班', 1, CURRENT_TIMESTAMP),
    (default, CURRENT_TIMESTAMP, '中班', 2, CURRENT_TIMESTAMP),
    (default, CURRENT_TIMESTAMP, '大班', 3, CURRENT_TIMESTAMP);


INSERT INTO sys_user
(first_name, last_name, email, phone_number, created_at, updated_at, id)
values
    ('张', '老师', 'zhang.teacher@aa.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
    ('李', '老师', 'li.teacher@aa.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 2),
    ('刘', '老师', 'liu.teacher@aa.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 3),
    ('郑', '老师', 'zheng.teacher@aa.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 4),
    ('邓', '老师', 'deng.teacher@aa.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 5),
    ('朱', '老师', 'zhu.teacher@aa.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 6),
    ('张', '老师', 'zhang.teacher@bb.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 7),
    ('李', '老师', 'li.teacher@bb.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 8),
    ('赵', '老师', 'zhao.teacher@bb.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 9),
    ('徐', '老师', 'xu.teacher@bb.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 10),
    ('杨', '老师', 'yang.teacher@bb.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 11),
    ('朱', '老师', 'zhu.teacher@bb.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 12),
    ('张', '老师', 'zhang.teacher@cc.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 13),
    ('李', '老师', 'li.teacher@cc.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 14),
    ('马', '老师', 'ma.teacher@cc.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 15),
    ('徐', '老师', 'xu.teacher@cc.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 16),
    ('杨', '老师', 'yang.teacher@cc.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 17),
    ('王', '老师', 'wang.teacher@cc.com', '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 18);


INSERT INTO tbl_teachers
(id, hire_date, age, gender_code, school_id,
 home_province, home_city, home_district, home_street, home_detail_address, home_postal_code, home_email, home_phone_number,
 work_province, work_city, work_district, work_street, work_detail_address, work_postal_code, work_email, work_phone_number)
values
    (1, '2000-09-10', 30, 'FEMALE', 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111111'),
    (2, '2000-09-10', 22, 'MALE', 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111112'),
    (3, '2000-09-10', 40, 'FEMALE', 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111113'),
    (4, '2000-09-10', 35, 'FEMALE', 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111114'),
    (5, '2000-09-10', 45, 'MALE', 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111115'),
    (6, '2000-09-10', 29, 'MALE', 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111116'),
    (7, '2000-09-10', 30, 'FEMALE', 2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111117'),
    (8, '2000-09-10', 22, 'MALE', 2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111118'),
    (9, '2000-09-10', 40, 'FEMALE', 2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111119'),
    (10, '2000-09-10', 35, 'FEMALE', 2,  null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111120'),
    (11, '2000-09-10', 45, 'MALE', 2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111121'),
    (12, '2000-09-10', 29, 'MALE', 2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111122'),
    (13, '2000-09-10', 30, 'FEMALE', 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111123'),
    (14, '2000-09-10', 22, 'MALE', 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111124'),
    (15, '2000-09-10', 40, 'FEMALE', 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111125'),
    (16, '2000-09-10', 35, 'FEMALE', 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111126'),
    (17, '2000-09-10', 45, 'MALE', 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111127'),
    (18, '2000-09-10', 29, 'MALE', 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '021-111128');

INSERT INTO tbl_classrooms
(created_at, grade_id, name, teacher_id, updated_at, id)
values
    (CURRENT_TIMESTAMP, 1, '小一班', 1, CURRENT_TIMESTAMP, default),
    (CURRENT_TIMESTAMP, 1, '小二班', 2, CURRENT_TIMESTAMP, default),
    (CURRENT_TIMESTAMP, 1, '小三班', 3, CURRENT_TIMESTAMP, default),
    (CURRENT_TIMESTAMP, 2, '中班', 4, CURRENT_TIMESTAMP, default),
    (CURRENT_TIMESTAMP, 2, '大班', 4, CURRENT_TIMESTAMP, default),
    (CURRENT_TIMESTAMP, 3, '超大班', 5, CURRENT_TIMESTAMP, default);

