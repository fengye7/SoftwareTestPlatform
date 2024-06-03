package com.tongji.microservices.integrationtestserver;

import com.example.projecttestserver.ProjectTestServerApplication;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.GroupMemberResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileData;
import com.tongji.microservice.teamsphere.dto.fileservice.FileDataResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingListResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.MeetingResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.ParticipantListResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.*;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventIdResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventInfoResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.EventsResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.*;
import com.tongji.microservice.teamsphere.dto.userservice.*;
import com.tongji.microservice.teamsphere.dubbo.api.*;
//import com.tongji.microservices.configs.TestContainerConfig;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

//import static com.tongji.microservices.configs.TestContainerConfig.mysqlContainer;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {ProjectTestServerApplication.class/*, TestContainerConfig.class*/})
@ActiveProfiles("test")
//@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ServicesIntegrationTest {

    @DubboReference
    private ChatService chatService;

    @DubboReference
    private FileService fileService;

    @DubboReference
    private MeetingService meetingService;

    @DubboReference
    private ProjectService projectService;

    @DubboReference
    private ScheduleService scheduleService;

    @DubboReference
    private TaskService taskService;

    @DubboReference
    private UserService userService;

    // 避免唯一性检查错误
    private static String randomString;

    @BeforeAll
    public void setUp() throws IOException {
        // 生成一个随机字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // 年月日时分秒毫秒
        // 获取当前时间的字符串表示-作为唯一标识
        randomString = sdf.format(new Date());

//        // 初始化数据库数据
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(mysqlContainer.getDriverClassName());
//        dataSource.setUrl(mysqlContainer.getJdbcUrl());
//        dataSource.setUsername(mysqlContainer.getUsername());
//        dataSource.setPassword(mysqlContainer.getPassword());
//
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
//
//        // 读取并执行初始化脚本
////        Resource initScript = new ClassPathResource("sqls/init.sql");
////        String initSql = new String(Files.readAllBytes(Paths.get(initScript.getURI())));
////        jdbcTemplate.update(initSql);
//        jdbcTemplate.update("CREATE TABLE User (\n" +
//                "                      id INT(11) NOT NULL AUTO_INCREMENT,\n" +
//                "                      username VARCHAR(256) NOT NULL,\n" +
//                "                      password VARCHAR(256) NOT NULL,\n" +
//                "                      avatar VARCHAR(256),\n" +
//                "                      email VARCHAR(256) NOT NULL,\n" +
//                "                      PRIMARY KEY (id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE Project (\n" +
//                "                         id INT(11) NOT NULL AUTO_INCREMENT,\n" +
//                "                         name VARCHAR(256) NOT NULL,\n" +
//                "                         scale INT(11) NOT NULL,\n" +
//                "                         leader INT(11) NOT NULL,\n" +
//                "                         description VARCHAR(4096),\n" +
//                "                         PRIMARY KEY (id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE Task (\n" +
//                "                      id INT(11) NOT NULL AUTO_INCREMENT,\n" +
//                "                      name VARCHAR(256) NOT NULL,\n" +
//                "                      description VARCHAR(4096),\n" +
//                "                      project_id INT(11) NOT NULL,\n" +
//                "                      leader INT(11) NOT NULL,\n" +
//                "                      priority INT(11) NOT NULL,\n" +
//                "                      status INT(11) NOT NULL,\n" +
//                "                      deadline DATETIME NOT NULL,\n" +
//                "                      finish_time DATETIME,\n" +
//                "                      file VARCHAR(256),\n" +
//                "                      PRIMARY KEY (id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE File (\n" +
//                "                      id INT(11) NOT NULL AUTO_INCREMENT,\n" +
//                "                      project_id INT(11) NOT NULL,\n" +
//                "                      user_id INT(11) NOT NULL,\n" +
//                "                      name VARCHAR(256) NOT NULL,\n" +
//                "                      type VARCHAR(256) NOT NULL,\n" +
//                "                      size INT(11) NOT NULL,\n" +
//                "                      upload_time DATETIME NOT NULL,\n" +
//                "                      url VARCHAR(256) NOT NULL,\n" +
//                "                      PRIMARY KEY (id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE GroupChat (\n" +
//                "                           id INT(11) NOT NULL AUTO_INCREMENT,\n" +
//                "                           name VARCHAR(256) NOT NULL,\n" +
//                "                           avatar VARCHAR(256),\n" +
//                "                           PRIMARY KEY (id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE Event (\n" +
//                "                       id INT(11) NOT NULL AUTO_INCREMENT,\n" +
//                "                       user_id INT(11) NOT NULL,\n" +
//                "                       title VARCHAR(256) NOT NULL,\n" +
//                "                       start_time DATETIME NOT NULL,\n" +
//                "                       deadline DATETIME NOT NULL,\n" +
//                "                       description VARCHAR(4096),\n" +
//                "                       priority INT(11) NOT NULL,\n" +
//                "                       PRIMARY KEY (id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE Meeting (\n" +
//                "                         id VARCHAR(256) NOT NULL,\n" +
//                "                         title VARCHAR(256) NOT NULL,\n" +
//                "                         project_id INT(11) NOT NULL,\n" +
//                "                         start_time DATETIME,\n" +
//                "                         duration INT NOT NULL,\n" +
//                "                         description VARCHAR(1024) NOT NULL,\n" +
//                "                         url VARCHAR(1024),\n" +
//                "                         book_id VARCHAR(256),\n" +
//                "                         PRIMARY KEY (id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE Star (\n" +
//                "                      file_id INT(11) NOT NULL,\n" +
//                "                      user_id INT(11) NOT NULL,\n" +
//                "                      PRIMARY KEY (file_id, user_id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE ProjectMember (\n" +
//                "                               project_id INT(11) NOT NULL,\n" +
//                "                               user_id INT(11) NOT NULL,\n" +
//                "                               privilege INT(11) NOT NULL,\n" +
//                "                               PRIMARY KEY (project_id, user_id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE ProjectMemberRequest (\n" +
//                "                                      project_id INT(11) NOT NULL,\n" +
//                "                                      user_id INT(11) NOT NULL,\n" +
//                "                                      status INT(11) NOT NULL,\n" +
//                "                                      request_time DATETIME,\n" +
//                "                                      PRIMARY KEY (project_id, user_id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE GroupMember (\n" +
//                "                             group_id INT(11) NOT NULL,\n" +
//                "                             user_id INT(11) NOT NULL,\n" +
//                "                             PRIMARY KEY (group_id, user_id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE Meeting_Participants (\n" +
//                "                                      id INT NOT NULL AUTO_INCREMENT,\n" +
//                "                                      meeting_id VARCHAR(256) NOT NULL,\n" +
//                "                                      participant_id INT NOT NULL,\n" +
//                "                                      role VARCHAR(256),\n" +
//                "                                      PRIMARY KEY (id)\n" +
//                ");");
//        jdbcTemplate.update("CREATE TABLE TaskMember (\n" +
//                "                            task_id INT(11) NOT NULL,\n" +
//                "                            user_id INT(11) NOT NULL,\n" +
//                "                            score INT(11),\n" +
//                "                            PRIMARY KEY (task_id, user_id)\n" +
//                ");");
    }

    @AfterAll
    public void tearDown() throws IOException {
//        // 清理数据库数据
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(mysqlContainer.getDriverClassName());
//        dataSource.setUrl(mysqlContainer.getJdbcUrl());
//        dataSource.setUsername(mysqlContainer.getUsername());
//        dataSource.setPassword(mysqlContainer.getPassword());
//
//        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
////        // 读取并执行清理脚本
////        Resource cleanupScript = new ClassPathResource("sqls/cleanup.sql");
////        String cleanupSql = new String(Files.readAllBytes(Paths.get(cleanupScript.getURI())));
////        jdbcTemplate.update(cleanupSql);
//        jdbcTemplate.update("DROP TABLE IF EXISTS Star;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS Meeting_Participants;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS TaskMember;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS GroupMember;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS ProjectMemberRequest;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS ProjectMember;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS Meeting;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS GroupChat;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS Event;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS File;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS Task;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS Project;");
//        jdbcTemplate.update("DROP TABLE IF EXISTS User;");
    }

    @Test
    @Transactional
    void testChatService() {
        APIResponse portResponse = chatService.getPort();
        assertThat(portResponse).isNotNull();

        APIResponse createGroupResponse = chatService.createGroup("Test Group" + randomString, "Group Info" + randomString, List.of("1", "2"));
        assertThat(createGroupResponse).isNotNull();

        GroupMemberResponse groupMemberResponse = chatService.getGroupMember(1);
        assertThat(groupMemberResponse).isNotNull();

        List<Integer> groups = chatService.getGroups(1);
        assertThat(groups).isNotEmpty();
    }

    @Test
    @Transactional
    void testFileService() {
        FileData fileData = new FileData();
        fileData.setName("testFile" + randomString);
        fileData.setSize(1024);
        // 使用 String.format() 来插入随机字符串到 URL 中
        String formattedUrl = String.format("http://example.com/testFile_%s.txt", randomString);
        fileData.setUrl(formattedUrl);
        fileData.setType("txt");
        fileData.setProjectId(1);
        fileData.setUserId(1);
        FileDataResponse uploadResponse = fileService.upload(fileData);
        assertThat(uploadResponse).isNotNull();

        FileResponse fileResponse = fileService.getFileByProject(1, 1);
        assertThat(fileResponse).isNotNull();

        APIResponse putStarResponse = fileService.putStar(1, 1);
        assertThat(putStarResponse).isNotNull();

        APIResponse deleteStarResponse = fileService.deleteStar(1, 1);
        assertThat(deleteStarResponse).isNotNull();

        FileResponse fileByStarResponse = fileService.getFileByStar(1);
        assertThat(fileByStarResponse).isNotNull();

//        FileData fileByURL = fileService.getFileByURL(formattedUrl);
//        assertThat(fileByURL).isNotNull();

        int isStarred = fileService.isStarred(1, 1);
        assertThat(isStarred).isGreaterThanOrEqualTo(0);

        APIResponse deleteResponse = fileService.delete(1, "testFile" + randomString);
        assertThat(deleteResponse).isNotNull();
    }

    @Test
    @Transactional
    void testMeetingService() {
        APIResponse cancelMeetingResponse = meetingService.cancelMeeting("1");
        assertThat(cancelMeetingResponse).isNotNull();

        MeetingListResponse meetingsForProject = meetingService.getMeetingsForProject(1);
        assertThat(meetingsForProject).isNotNull();

        MeetingListResponse meetingsForUser = meetingService.getMeetingsForUser(1);
        assertThat(meetingsForUser).isNotNull();

        MeetingResponse createMeetingResponse = meetingService.createMeeting(1, "Title" + randomString, "Description" + randomString, LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        assertThat(createMeetingResponse).isNotNull();

        APIResponse addParticipantResponse = meetingService.addParticipant("1", 1, "Role");
        assertThat(addParticipantResponse).isNotNull();

        APIResponse removeParticipantResponse = meetingService.removeParticipant("1", 1);
        assertThat(removeParticipantResponse).isNotNull();

        APIResponse setParticipantRoleResponse = meetingService.setParticipantRole("1", 1, "Role");
        assertThat(setParticipantRoleResponse).isNotNull();

        ParticipantListResponse participantsForMeeting = meetingService.getParticipantsForMeeting("1");
        assertThat(participantsForMeeting).isNotNull();
    }

    @Test
    @Transactional
    void testProjectService() {
        ProjectData projectData = new ProjectData(1, 10, "testProject" + randomString, "testDescription" + randomString, 1);
        ProjectIdResponse creatProjectResponse = projectService.creatProject(projectData);
        assertThat(creatProjectResponse).isNotNull();

        APIResponse addProjectMemberResponse = projectService.addProjectMember(1, 1);
        assertThat(addProjectMemberResponse).isNotNull();

        APIResponse updateProjectMemberPrivilegeResponse = projectService.updateProjectMemberPrivilege(1, 1, 1);
        assertThat(updateProjectMemberPrivilegeResponse).isNotNull();

        MembersResponse projectMembers = projectService.getProjectMembers(1);
        assertThat(projectMembers).isNotNull();

        ProjectInfoResponse projectInfo = projectService.getProjectInfo(1);
        assertThat(projectInfo).isNotNull();

        PrivilegeResponse projectMemberPrivilege = projectService.getProjectMemberPrivilege(1, 1);
        assertThat(projectMemberPrivilege).isNotNull();

        ProjectQueryResponse projectByUserId = projectService.getProjectByUserId(1);
        assertThat(projectByUserId).isNotNull();

        ProjectQueryResponse queryProjectResponse = projectService.queryProject();
        assertThat(queryProjectResponse).isNotNull();

        ProjectJoinRequestResponse projectJoinRequestResponse = projectService.getProjectJoinRequest(1);
        assertThat(projectJoinRequestResponse).isNotNull();

        APIResponse makeJoinRequestResponse = projectService.makeJoinRequest(1, 1);
        assertThat(makeJoinRequestResponse).isNotNull();

        APIResponse judgeJoinRequestResponse = projectService.judgeJoinRequest(1, 1, 1);
        assertThat(judgeJoinRequestResponse).isNotNull();

        APIResponse removeProjectMemberResponse = projectService.removeProjectMember(1, 1);
        assertThat(removeProjectMemberResponse).isNotNull();

        APIResponse deleteProjectResponse = projectService.deleteProject(1);
        assertThat(deleteProjectResponse).isNotNull();
    }

    @Test
    @Transactional
    void testScheduleService() {
        EventIdResponse createEventResponse = scheduleService.createEvent(1, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Description" + randomString, "Title" + randomString, 1);
        assertThat(createEventResponse).isNotNull();

        EventsResponse eventsResponse = scheduleService.getEvents(1);
        assertThat(eventsResponse).isNotNull();

        EventInfoResponse eventInfoResponse = scheduleService.getEventInfo(1);
        assertThat(eventInfoResponse).isNotNull();

        EventIdResponse eventIdResponse = scheduleService.getEventId(1, "Title");
        assertThat(eventIdResponse).isNotNull();

        APIResponse updateEventInfoResponse = scheduleService.updateEventInfo(1, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Updated Description" + randomString, "Updated Title" + randomString, 2);
        assertThat(updateEventInfoResponse).isNotNull();

        APIResponse removeEventResponse = scheduleService.removeEvent(1);
        assertThat(removeEventResponse).isNotNull();
    }

    @Test
    @Transactional
    void testTaskService() {
        TaskData taskData = new TaskData();
        CreateTaskResponse createTaskResponse = taskService.createTask("Task Name" + randomString, "Task Description" + randomString, 1, LocalDateTime.now().plusDays(1), 1, 1);
        assertThat(createTaskResponse).isNotNull();

        APIResponse addTaskMemberResponse = taskService.addTaskMember(1, 1);
        assertThat(addTaskMemberResponse).isNotNull();

        APIResponse scoreTaskMemberResponse = taskService.scoreTaskMember(1, 1, 10);
        assertThat(scoreTaskMemberResponse).isNotNull();

        APIResponse judgeTaskResponse = taskService.judgeTask(1, 1);
        assertThat(judgeTaskResponse).isNotNull();

        APIResponse uploadTaskFileResponse = taskService.uploadTaskFile(1, 1, "http://example.com/file" + randomString);
        assertThat(uploadTaskFileResponse).isNotNull();

        APIResponse updateTaskInfoResponse = taskService.updateTaskInfo(1, taskData);
        assertThat(updateTaskInfoResponse).isNotNull();

        TaskResponse taskInfoResponse = taskService.getTaskInfo(1);
        assertThat(taskInfoResponse).isNotNull();

        TaskMemberResponse taskMemberResponse = taskService.getTaskMember(1);
        assertThat(taskMemberResponse).isNotNull();

        ProjectTaskResponse tasksForProjectResponse = taskService.getTasksForProject(1);
        assertThat(tasksForProjectResponse).isNotNull();

        ProjectTaskResponse tasksForLeaderResponse = taskService.getTasksForLeader(1);
        assertThat(tasksForLeaderResponse).isNotNull();

        ProjectTaskResponse tasksForMemberResponse = taskService.getTasksForMember(1);
        assertThat(tasksForMemberResponse).isNotNull();

        int leader = taskService.getLeader(2);
        assertThat(leader).isGreaterThanOrEqualTo(0);

        APIResponse deleteTaskMemberResponse = taskService.deleteTaskMember(1, 1);
        assertThat(deleteTaskMemberResponse).isNotNull();

        APIResponse deleteTaskResponse = taskService.deleteTask(1);
        assertThat(deleteTaskResponse).isNotNull();
    }

    @Test
    @Transactional
    void testUserService() {
        RegisterRequest registerRequest = new RegisterRequest("testUser" + randomString, "password" + randomString, randomString + "test@example.com", "testAvatar" + randomString);
        RegisterResponse registerResponse = userService.register(registerRequest);
        assertThat(registerResponse).isNotNull();

        LoginResponse loginResponse = userService.checkID("testUser" + randomString, "password" + randomString);
        assertThat(loginResponse).isNotNull();

        AuthorizeResponse authorizeResponse = userService.authorize("token");
        assertThat(authorizeResponse).isNotNull();

//        APIResponse updateUserInfoResponse = userService.updateUserInfo(1, registerRequest);
//        assertThat(updateUserInfoResponse).isNotNull();

        UserResponse userInfoResponse = userService.getUserInfo(1);
        assertThat(userInfoResponse).isNotNull();

        UserResponse userInfoByEmailResponse = userService.getUserInfoByEmail(randomString + "test@example.com");
        assertThat(userInfoByEmailResponse).isNotNull();

        APIResponse deleteUserResponse = userService.deleteUser(1);
        assertThat(deleteUserResponse).isNotNull();
    }
}
