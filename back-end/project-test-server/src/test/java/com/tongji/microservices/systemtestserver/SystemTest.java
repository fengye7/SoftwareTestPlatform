package com.tongji.microservices.systemtestserver;

import com.example.projecttestserver.ProjectTestServerApplication;
import com.tongji.microservice.teamsphere.dubbo.api.*;
import com.tongji.microservice.teamsphere.dto.*;
import com.tongji.microservice.teamsphere.dto.chatservice.*;
import com.tongji.microservice.teamsphere.dto.fileservice.*;
import com.tongji.microservice.teamsphere.dto.meetingservice.*;
import com.tongji.microservice.teamsphere.dto.projectservice.*;
import com.tongji.microservice.teamsphere.dto.sceduleservice.*;
import com.tongji.microservice.teamsphere.dto.taskservice.*;
import com.tongji.microservice.teamsphere.dto.userservice.*;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ProjectTestServerApplication.class)
@ActiveProfiles("test")
public class SystemTest {

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

    @Test
    void testEndToEndScenario() {
        // 生成一个随机字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS"); // 年月日时分秒毫秒
        // 获取当前时间的字符串表示-作为唯一标识
        String randomString = sdf.format(new Date());
        // Step 1: User Registration
        RegisterRequest registerRequest = new RegisterRequest("testuser", "password", "testemail", "testavatar");
        RegisterResponse registerResponse = userService.register(registerRequest);
        assertThat(registerResponse).isNotNull();

        // Step 2: User Login
        LoginResponse loginResponse = userService.checkID("testuser", "password");
        assertThat(loginResponse).isNotNull();
        int userId = loginResponse.getUserid();

        // Step 3: Create Project
        ProjectData projectData = new ProjectData(1, 10, "testproject" + randomString, "testdescription", userId);
        ProjectIdResponse createProjectResponse = projectService.creatProject(projectData);
        assertThat(createProjectResponse).isNotNull();
        int projectId = createProjectResponse.getProjectId();

        // Step 4: Create Task
        CreateTaskResponse createTaskResponse = taskService.createTask("Task Name" + randomString, "Task Description", projectId, LocalDateTime.now().plusDays(1), userId, userId);
        assertThat(createTaskResponse).isNotNull();
        int taskId = createTaskResponse.getTaskId();

        // Step 5: Schedule Event
        EventIdResponse createEventResponse = scheduleService.createEvent(projectId, LocalDateTime.now(), LocalDateTime.now().plusHours(1), "Description", "Title", userId);
        assertThat(createEventResponse).isNotNull();
        int eventId = createEventResponse.getScheduleId();

        // Step 6: Create Meeting
        MeetingResponse createMeetingResponse = meetingService.createMeeting(projectId, "Title" + randomString, "Description", LocalDateTime.now(), LocalDateTime.now().plusHours(1));
        assertThat(createMeetingResponse).isNotNull();

        // Step 7: Upload File
        FileData fileData = new FileData();
        FileDataResponse uploadResponse = fileService.upload(fileData);
        assertThat(uploadResponse).isNotNull();

        // Step 8: Chat Operation
        APIResponse createGroupResponse = chatService.createGroup("Test Group" + randomString, "Group Info", List.of(String.valueOf(userId)));
        assertThat(createGroupResponse).isNotNull();

        // Step 9: Verify Project Info
        ProjectInfoResponse projectInfo = projectService.getProjectInfo(projectId);
        assertThat(projectInfo).isNotNull();
        assertThat(projectInfo.getData().getName()).isEqualTo("testproject" + randomString);
    }
}