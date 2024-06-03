-- 创建 User 表
CREATE TABLE User (
                      id INT(11) NOT NULL AUTO_INCREMENT,
                      username VARCHAR(32) NOT NULL,
                      password VARCHAR(16) NOT NULL,
                      avatar VARCHAR(256),
                      email VARCHAR(64) NOT NULL,
                      PRIMARY KEY (id)
);

-- 创建 Project 表
CREATE TABLE Project (
                         id INT(11) NOT NULL AUTO_INCREMENT,
                         name VARCHAR(32) NOT NULL,
                         scale INT(11) NOT NULL,
                         leader INT(11) NOT NULL,
                         description VARCHAR(4096),
                         PRIMARY KEY (id)
);

-- 创建 Task 表
CREATE TABLE Task (
                      id INT(11) NOT NULL AUTO_INCREMENT,
                      name VARCHAR(32) NOT NULL,
                      description VARCHAR(4096),
                      project_id INT(11) NOT NULL,
                      leader INT(11) NOT NULL,
                      priority INT(11) NOT NULL,
                      status INT(11) NOT NULL,
                      deadline DATETIME NOT NULL,
                      finish_time DATETIME,
                      file VARCHAR(64),
                      PRIMARY KEY (id)
);

-- 创建 File 表
CREATE TABLE File (
                      id INT(11) NOT NULL AUTO_INCREMENT,
                      project_id INT(11) NOT NULL,
                      user_id INT(11) NOT NULL,
                      name VARCHAR(256) NOT NULL,
                      type VARCHAR(32) NOT NULL,
                      size INT(11) NOT NULL,
                      upload_time DATETIME NOT NULL,
                      url VARCHAR(64) NOT NULL,
                      PRIMARY KEY (id)
);

-- 创建 GroupChat 表
CREATE TABLE GroupChat (
                           id INT(11) NOT NULL AUTO_INCREMENT,
                           name VARCHAR(32) NOT NULL,
                           avatar VARCHAR(256),
                           PRIMARY KEY (id)
);

-- 创建 Event 表
CREATE TABLE Event (
                       id INT(11) NOT NULL AUTO_INCREMENT,
                       user_id INT(11) NOT NULL,
                       title VARCHAR(32) NOT NULL,
                       start_time DATETIME NOT NULL,
                       deadline DATETIME NOT NULL,
                       description VARCHAR(4096),
                       priority INT(11) NOT NULL,
                       PRIMARY KEY (id)
);

-- 创建 Meeting 表
CREATE TABLE Meeting (
                         id VARCHAR(20) NOT NULL,
                         title VARCHAR(256) NOT NULL,
                         project_id INT(11) NOT NULL,
                         start_time DATETIME,
                         duration INT NOT NULL,
                         description VARCHAR(1024) NOT NULL,
                         url VARCHAR(1024),
                         book_id VARCHAR(32),
                         PRIMARY KEY (id)
);

-- 创建 Star 表
CREATE TABLE Star (
                      file_id INT(11) NOT NULL,
                      user_id INT(11) NOT NULL,
                      PRIMARY KEY (file_id, user_id)
);

-- 创建 ProjectMember 表
CREATE TABLE ProjectMember (
                               project_id INT(11) NOT NULL,
                               user_id INT(11) NOT NULL,
                               privilege INT(11) NOT NULL,
                               PRIMARY KEY (project_id, user_id)
);

-- 创建 ProjectMemberRequest 表
CREATE TABLE ProjectMemberRequest (
                                      project_id INT(11) NOT NULL,
                                      user_id INT(11) NOT NULL,
                                      status INT(11) NOT NULL,
                                      request_time DATETIME,
                                      PRIMARY KEY (project_id, user_id)
);

-- 创建 GroupChatMember 表
CREATE TABLE GroupMember (
                             group_id INT(11) NOT NULL,
                             user_id INT(11) NOT NULL,
                             PRIMARY KEY (group_id, user_id)
);

-- 创建 Meeting_Participants 表
CREATE TABLE Meeting_Participants (
                                      id INT NOT NULL AUTO_INCREMENT,
                                      meeting_id VARCHAR(20) NOT NULL,
                                      participant_id INT NOT NULL,
                                      role VARCHAR(64),
                                      PRIMARY KEY (id)
);

-- 创建 TaskMember 表
CREATE TABLE TaskMember (
                            task_id INT(11) NOT NULL,
                            user_id INT(11) NOT NULL,
                            score INT(11),
                            PRIMARY KEY (task_id, user_id)
);

-- 修改 Project 表，设置 name 列唯一
ALTER TABLE Project
    ADD CONSTRAINT unique_project_name UNIQUE (name);

-- 修改 GroupChat 表，设置 name 列唯一
ALTER TABLE GroupChat
    ADD CONSTRAINT unique_groupchat_name UNIQUE (name);

-- 修改 ProjectMember 表，确保 (project_id, user_id) 元组唯一
ALTER TABLE ProjectMember
    ADD CONSTRAINT unique_project_member UNIQUE (project_id, user_id);

-- 修改 User 表，设置 username 列唯一
ALTER TABLE User
    ADD CONSTRAINT unique_username UNIQUE (username);

-- 修改 User 表，设置 email 列唯一
ALTER TABLE User
    ADD CONSTRAINT unique_email UNIQUE (email);

-- 修改 Event 表，确保 (user_id, title) 元组唯一
ALTER TABLE Event
    ADD CONSTRAINT unique_event_user_title UNIQUE (user_id, title);

-- 修改 Meeting_Participants 表，确保 (meeting_id, participant_id) 元组唯一
ALTER TABLE Meeting_Participants
    ADD CONSTRAINT unique_meeting_participant UNIQUE (meeting_id, participant_id);

-- 修改 GroupMember 表，确保 (group_id, user_id) 元组唯一
ALTER TABLE GroupMember
    ADD CONSTRAINT unique_group_member UNIQUE (group_id, user_id);

-- 修改 TaskMember 表，确保 (task_id, user_id) 元组唯一
ALTER TABLE TaskMember
    ADD CONSTRAINT unique_task_member UNIQUE (task_id, user_id);



-- 插入 User 表数据
INSERT INTO User (username, password, avatar, email) VALUES
                                                         ('Alice', 'password123', 'http://example.com/avatar1.png', 'alice@example.com'),
                                                         ('Bob', 'password456', 'http://example.com/avatar2.png', 'bob@example.com'),
                                                         ('Charlie', 'password789', 'http://example.com/avatar3.png', 'charlie@example.com'),
                                                         ('David', 'password101', 'http://example.com/avatar4.png', 'david@example.com'),
                                                         ('Eve', 'password202', 'http://example.com/avatar5.png', 'eve@example.com'),
                                                         ('Frank', 'password303', 'http://example.com/avatar6.png', 'frank@example.com'),
                                                         ('Grace', 'password404', 'http://example.com/avatar7.png', 'grace@example.com'),
                                                         ('Heidi', 'password505', 'http://example.com/avatar8.png', 'heidi@example.com'),
                                                         ('Ivan', 'password606', 'http://example.com/avatar9.png', 'ivan@example.com'),
                                                         ('Judy', 'password707', 'http://example.com/avatar10.png', 'judy@example.com');

-- 插入 Project 表数据
INSERT INTO Project (name, scale, leader, description) VALUES
                                                           ('Project A', 10, 1, 'Description for Project A'),
                                                           ('Project B', 20, 2, 'Description for Project B'),
                                                           ('Project C', 15, 3, 'Description for Project C'),
                                                           ('Project D', 25, 4, 'Description for Project D'),
                                                           ('Project E', 30, 5, 'Description for Project E'),
                                                           ('Project F', 18, 6, 'Description for Project F'),
                                                           ('Project G', 22, 7, 'Description for Project G'),
                                                           ('Project H', 12, 8, 'Description for Project H'),
                                                           ('Project I', 27, 9, 'Description for Project I'),
                                                           ('Project J', 14, 10, 'Description for Project J');

-- 插入 Task 表数据
INSERT INTO Task (name, description, project_id, leader, priority, status, deadline, finish_time, file) VALUES
                                                                                                            ('Task 1', 'Description for Task 1', 1, 1, 1, 1, '2024-06-01 12:00:00', NULL, 'file1.txt'),
                                                                                                            ('Task 2', 'Description for Task 2', 2, 2, 2, 2, '2024-06-02 12:00:00', NULL, 'file2.txt'),
                                                                                                            ('Task 3', 'Description for Task 3', 3, 3, 3, 3, '2024-06-03 12:00:00', NULL, 'file3.txt'),
                                                                                                            ('Task 4', 'Description for Task 4', 4, 4, 4, 4, '2024-06-04 12:00:00', NULL, 'file4.txt'),
                                                                                                            ('Task 5', 'Description for Task 5', 5, 5, 5, 5, '2024-06-05 12:00:00', NULL, 'file5.txt'),
                                                                                                            ('Task 6', 'Description for Task 6', 6, 6, 6, 6, '2024-06-06 12:00:00', NULL, 'file6.txt'),
                                                                                                            ('Task 7', 'Description for Task 7', 7, 7, 7, 7, '2024-06-07 12:00:00', NULL, 'file7.txt'),
                                                                                                            ('Task 8', 'Description for Task 8', 8, 8, 8, 8, '2024-06-08 12:00:00', NULL, 'file8.txt'),
                                                                                                            ('Task 9', 'Description for Task 9', 9, 9, 9, 9, '2024-06-09 12:00:00', NULL, 'file9.txt'),
                                                                                                            ('Task 10', 'Description for Task 10', 10, 10, 10, 10, '2024-06-10 12:00:00', NULL, 'file10.txt');

-- 插入 File 表数据
INSERT INTO File (project_id, user_id, name, type, size, upload_time, url) VALUES
                                                                               (1, 1, 'File 1', 'txt', 1024, '2024-05-01 12:00:00', 'http://example.com/file1.txt'),
                                                                               (2, 2, 'File 2', 'txt', 2048, '2024-05-02 12:00:00', 'http://example.com/file2.txt'),
                                                                               (3, 3, 'File 3', 'txt', 3072, '2024-05-03 12:00:00', 'http://example.com/file3.txt'),
                                                                               (4, 4, 'File 4', 'txt', 4096, '2024-05-04 12:00:00', 'http://example.com/file4.txt'),
                                                                               (5, 5, 'File 5', 'txt', 5120, '2024-05-05 12:00:00', 'http://example.com/file5.txt'),
                                                                               (6, 6, 'File 6', 'txt', 6144, '2024-05-06 12:00:00', 'http://example.com/file6.txt'),
                                                                               (7, 7, 'File 7', 'txt', 7168, '2024-05-07 12:00:00', 'http://example.com/file7.txt'),
                                                                               (8, 8, 'File 8', 'txt', 8192, '2024-05-08 12:00:00', 'http://example.com/file8.txt'),
                                                                               (9, 9, 'File 9', 'txt', 9216, '2024-05-09 12:00:00', 'http://example.com/file9.txt'),
                                                                               (10, 10, 'File 10', 'txt', 10240, '2024-05-10 12:00:00', 'http://example.com/file10.txt');

-- 插入 GroupChat 表数据
INSERT INTO GroupChat (name, avatar) VALUES
                                         ('Group A', 'http://example.com/group1.png'),
                                         ('Group B', 'http://example.com/group2.png'),
                                         ('Group C', 'http://example.com/group3.png'),
                                         ('Group D', 'http://example.com/group4.png'),
                                         ('Group E', 'http://example.com/group5.png'),
                                         ('Group F', 'http://example.com/group6.png'),
                                         ('Group G', 'http://example.com/group7.png'),
                                         ('Group H', 'http://example.com/group8.png'),
                                         ('Group I', 'http://example.com/group9.png'),
                                         ('Group J', 'http://example.com/group10.png');

-- 插入 Event 表数据
INSERT INTO Event (user_id, title, start_time, deadline, description, priority) VALUES
                                                                                    (1, 'Event 1', '2024-06-01 09:00:00', '2024-06-01 17:00:00', 'Description for Event 1', 1),
                                                                                    (2, 'Event 2', '2024-06-02 09:00:00', '2024-06-02 17:00:00', 'Description for Event 2', 2),
                                                                                    (3, 'Event 3', '2024-06-03 09:00:00', '2024-06-03 17:00:00', 'Description for Event 3', 3),
                                                                                    (4, 'Event 4', '2024-06-04 09:00:00', '2024-06-04 17:00:00', 'Description for Event 4', 4),
                                                                                    (5, 'Event 5', '2024-06-05 09:00:00', '2024-06-05 17:00:00', 'Description for Event 5', 5),
                                                                                    (6, 'Event 6', '2024-06-06 09:00:00', '2024-06-06 17:00:00', 'Description for Event 6', 6),
                                                                                    (7, 'Event 7', '2024-06-07 09:00:00', '2024-06-07 17:00:00', 'Description for Event 7', 7),
                                                                                    (8, 'Event 8', '2024-06-08 09:00:00', '2024-06-08 17:00:00', 'Description for Event 8', 8),
                                                                                    (9, 'Event 9', '2024-06-09 09:00:00', '2024-06-09 17:00:00', 'Description for Event 9', 9),
                                                                                    (10, 'Event 10', '2024-06-10 09:00:00', '2024-06-10 17:00:00', 'Description for Event 10', 10);

-- 插入 Meeting 表数据
INSERT INTO Meeting (id, title, project_id, start_time, duration, description, url, book_id) VALUES
                                                                                                 ('M001', 'Meeting 1', 1, '2024-06-01 10:00:00', 60, 'Description for Meeting 1', 'http://example.com/meeting1', 'B001'),
                                                                                                 ('M002', 'Meeting 2', 2, '2024-06-02 11:00:00', 90, 'Description for Meeting 2', 'http://example.com/meeting2', 'B002'),
                                                                                                 ('M003', 'Meeting 3', 3, '2024-06-03 12:00:00', 120, 'Description for Meeting 3', 'http://example.com/meeting3', 'B003'),
                                                                                                 ('M004', 'Meeting 4', 4, '2024-06-04 13:00:00', 60, 'Description for Meeting 4', 'http://example.com/meeting4', 'B004'),
                                                                                                 ('M005', 'Meeting 5', 5, '2024-06-05 14:00:00', 90, 'Description for Meeting 5', 'http://example.com/meeting5', 'B005'),
                                                                                                 ('M006', 'Meeting 6', 6, '2024-06-06 15:00:00', 120, 'Description for Meeting 6', 'http://example.com/meeting6', 'B006'),
                                                                                                 ('M007', 'Meeting 7', 7, '2024-06-07 16:00:00', 60, 'Description for Meeting 7', 'http://example.com/meeting7', 'B007'),
                                                                                                 ('M008', 'Meeting 8', 8, '2024-06-08 17:00:00', 90, 'Description for Meeting 8', 'http://example.com/meeting8', 'B008'),
                                                                                                 ('M009', 'Meeting 9', 9, '2024-06-09 18:00:00', 120, 'Description for Meeting 9', 'http://example.com/meeting9', 'B009'),
                                                                                                 ('M010', 'Meeting 10', 10, '2024-06-10 19:00:00', 60, 'Description for Meeting 10', 'http://example.com/meeting10', 'B010');

-- 插入 Star 表数据
INSERT INTO Star (file_id, user_id) VALUES
                                        (1, 1),
                                        (2, 2),
                                        (3, 3),
                                        (4, 4),
                                        (5, 5),
                                        (6, 6),
                                        (7, 7),
                                        (8, 8),
                                        (9, 9),
                                        (10, 10);

-- 插入 ProjectMember 表数据
INSERT INTO ProjectMember (project_id, user_id, privilege) VALUES
                                                               (1, 1, 1),
                                                               (2, 2, 2),
                                                               (3, 3, 3),
                                                               (4, 4, 1),
                                                               (5, 5, 2),
                                                               (6, 6, 3),
                                                               (7, 7, 1),
                                                               (8, 8, 2),
                                                               (9, 9, 3),
                                                               (10, 10, 1);

-- 插入 ProjectMemberRequest 表数据
INSERT INTO ProjectMemberRequest (project_id, user_id, status, request_time) VALUES
                                                                                 (1, 1, 0, '2024-05-01 12:00:00'),
                                                                                 (2, 2, 1, '2024-05-02 12:00:00'),
                                                                                 (3, 3, 0, '2024-05-03 12:00:00'),
                                                                                 (4, 4, 1, '2024-05-04 12:00:00'),
                                                                                 (5, 5, 0, '2024-05-05 12:00:00'),
                                                                                 (6, 6, 1, '2024-05-06 12:00:00'),
                                                                                 (7, 7, 0, '2024-05-07 12:00:00'),
                                                                                 (8, 8, 1, '2024-05-08 12:00:00'),
                                                                                 (9, 9, 0, '2024-05-09 12:00:00'),
                                                                                 (10, 10, 1, '2024-05-10 12:00:00');

-- 插入 GroupMember 表数据
INSERT INTO GroupMember (group_id, user_id) VALUES
                                                (1, 1),
                                                (2, 2),
                                                (3, 3),
                                                (4, 4),
                                                (5, 5),
                                                (6, 6),
                                                (7, 7),
                                                (8, 8),
                                                (9, 9),
                                                (10, 10);

-- 插入 MeetingParticipants 表数据
INSERT INTO Meeting_Participants (meeting_id, participant_id, role) VALUES
                                                                        ('M001', 1, 'Organizer'),
                                                                        ('M002', 2, 'Participant'),
                                                                        ('M003', 3, 'Organizer'),
                                                                        ('M004', 4, 'Participant'),
                                                                        ('M005', 5, 'Organizer'),
                                                                        ('M006', 6, 'Participant'),
                                                                        ('M007', 7, 'Organizer'),
                                                                        ('M008', 8, 'Participant'),
                                                                        ('M009', 9, 'Organizer'),
                                                                        ('M010', 10, 'Participant');

-- 插入 TaskMember 表数据
INSERT INTO TaskMember (task_id, user_id, score) VALUES
                                                     (1, 1, 90),
                                                     (2, 2, 85),
                                                     (3, 3, 88),
                                                     (4, 4, 92),
                                                     (5, 5, 87),
                                                     (6, 6, 90),
                                                     (7, 7, 85),
                                                     (8, 8, 88),
                                                     (9, 9, 92),
                                                     (10, 10, 87);
