package com.tongji.microservice.teamsphere.taskservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileData;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dto.taskservice.*;
import com.tongji.microservice.teamsphere.dto.userservice.UserResponse;
import com.tongji.microservice.teamsphere.dubbo.api.FileService;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.taskservice.entities.Task;
import com.tongji.microservice.teamsphere.taskservice.entities.TaskMember;
import com.tongji.microservice.teamsphere.taskservice.mapper.TaskMapper;
import com.tongji.microservice.teamsphere.taskservice.mapper.TaskMemberMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskMemberMapper memberMapper;

    @Mock
    private UserService userService;

    @Mock
    private FileService fileService;

    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_success() {
        when(taskMapper.insert(any(Task.class))).thenReturn(1);
        when(taskMapper.getTaskId(anyInt(), anyString())).thenReturn(1);

        CreateTaskResponse response = taskService.createTask("TaskName", "Description", 1, LocalDateTime.now(), 1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(1, response.getTaskId());
    }

    @Test
    void createTask_fail() {
        when(taskMapper.insert(any(Task.class))).thenReturn(0);

        CreateTaskResponse response = taskService.createTask("TaskName", "Description", 1, LocalDateTime.now(), 1, 1);

        assertEquals(400, response.getCode());
        assertEquals("创建失败", response.getMessage());
        assertEquals(0, response.getTaskId());
    }

    @Test
    void deleteTask_success() {
        when(taskMapper.getProjectId(anyInt())).thenReturn(1);
        when(taskMapper.deleteTaskById(anyInt())).thenReturn(1);

        APIResponse response = taskService.deleteTask(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void deleteTask_fail() {
        when(taskMapper.getProjectId(anyInt())).thenReturn(1);
        when(taskMapper.deleteTaskById(anyInt())).thenReturn(0);

        APIResponse response = taskService.deleteTask(1);

        assertEquals(400, response.getCode());
        assertEquals("删除失败", response.getMessage());
    }

    @Test
    void addTaskMember_success() {
        when(memberMapper.addMember(anyInt(), anyInt())).thenReturn(1);

        APIResponse response = taskService.addTaskMember(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void addTaskMember_fail() {
        when(memberMapper.addMember(anyInt(), anyInt())).thenReturn(0);

        APIResponse response = taskService.addTaskMember(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("添加失败", response.getMessage());
    }

    @Test
    void deleteTaskMember_success() {
        when(taskMapper.getProjectId(anyInt())).thenReturn(1);
        when(memberMapper.deleteMember(anyInt(), anyInt())).thenReturn(1);

        APIResponse response = taskService.deleteTaskMember(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void deleteTaskMember_fail() {
        when(taskMapper.getProjectId(anyInt())).thenReturn(1);
        when(memberMapper.deleteMember(anyInt(), anyInt())).thenReturn(0);

        APIResponse response = taskService.deleteTaskMember(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("删除失败", response.getMessage());
    }

    @Test
    void scoreTaskMember_success() {
        when(taskMapper.getProjectId(anyInt())).thenReturn(1);
        when(memberMapper.setScore(anyInt(), anyInt(), anyInt())).thenReturn(1);

        APIResponse response = taskService.scoreTaskMember(1, 1, 90);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void scoreTaskMember_fail() {
        when(taskMapper.getProjectId(anyInt())).thenReturn(1);
        when(memberMapper.setScore(anyInt(), anyInt(), anyInt())).thenReturn(0);

        APIResponse response = taskService.scoreTaskMember(1, 1, 90);

        assertEquals(400, response.getCode());
        assertEquals("评分失败", response.getMessage());
    }

    @Test
    void judgeTask_success() {
        when(taskMapper.setStatus(anyInt(), anyInt())).thenReturn(1);

        APIResponse response = taskService.judgeTask(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void judgeTask_fail() {
        when(taskMapper.setStatus(anyInt(), anyInt())).thenReturn(0);

        APIResponse response = taskService.judgeTask(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("审批失败", response.getMessage());
    }

    @Test
    void uploadTaskFile_success() {
        when(memberMapper.exists(any(QueryWrapper.class))).thenReturn(true);
        when(taskMapper.setFileURL(anyInt(), anyString(), any(LocalDateTime.class))).thenReturn(1);

        APIResponse response = taskService.uploadTaskFile(1, 1, "fileURL");

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void uploadTaskFile_fail_not_member() {
        when(memberMapper.exists(any(QueryWrapper.class))).thenReturn(false);

        APIResponse response = taskService.uploadTaskFile(1, 1, "fileURL");

        assertEquals(400, response.getCode());
        assertEquals("你不是该任务成员", response.getMessage());
    }

    @Test
    void uploadTaskFile_fail_upload_error() {
        when(memberMapper.exists(any(QueryWrapper.class))).thenReturn(true);
        when(taskMapper.setFileURL(anyInt(), anyString(), any(LocalDateTime.class))).thenReturn(0);

        APIResponse response = taskService.uploadTaskFile(1, 1, "fileURL");

        assertEquals(400, response.getCode());
        assertEquals("上传失败", response.getMessage());
    }

    @Test
    void updateTaskInfo_success() {
        when(taskMapper.update(any(Task.class), any(UpdateWrapper.class))).thenReturn(1);

        TaskData taskData = new TaskData();
        APIResponse response = taskService.updateTaskInfo(1, taskData);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void updateTaskInfo_fail() {
        when(taskMapper.update(any(Task.class), any(UpdateWrapper.class))).thenReturn(0);

        TaskData taskData = new TaskData();
        APIResponse response = taskService.updateTaskInfo(1, taskData);

        assertEquals(400, response.getCode());
        assertEquals("更新失败", response.getMessage());
    }

    @Test
    void getTaskInfo_success() {
        Task task = new Task();
        task.setId(1);
        when(taskMapper.selectById(anyInt())).thenReturn(task);
        when(userService.getUserInfo(anyInt())).thenReturn(new UserResponse(1,"username","email","avatar"));
        when(fileService.getFileByURL(anyString())).thenReturn(new FileData(1,"file-url","file-type","file-name",LocalDateTime.now(),1,1,100,50));

        TaskResponse response = taskService.getTaskInfo(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getTaskData());
        assertEquals(1, response.getTaskData().getId());
    }

    @Test
    void getTaskInfo_fail() {
        when(taskMapper.selectById(anyInt())).thenReturn(null);

        TaskResponse response = taskService.getTaskInfo(1);

        assertEquals(400, response.getCode());
        assertEquals("任务不存在", response.getMessage());
    }

    @Test
    void getTaskMember_success() {
        Task task = new Task();
        task.setId(1);
        when(taskMapper.selectById(anyInt())).thenReturn(task);
        List<TaskMember> members = new ArrayList<>();
        TaskMember member = new TaskMember(1,1,90);
        members.add(member);
        when(memberMapper.getMembersByTaskId(anyInt())).thenReturn(members);

        TaskMemberResponse response = taskService.getTaskMember(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void getTaskMember_fail() {
        when(taskMapper.selectById(anyInt())).thenReturn(null);

        TaskMemberResponse response = taskService.getTaskMember(1);

        assertEquals(400, response.getCode());
        assertEquals("任务不存在", response.getMessage());
    }

    @Test
    void getTasksForProject_success() {
        List<Task> tasks = new ArrayList<>();
        Task task = new Task();
        task.setId(1);
        task.setName("TaskName");
        task.setDescription("Description");
        task.setProjectId(1);
        task.setDeadline(LocalDateTime.now());
        task.setLeader(1);
        task.setPriority(1);
        task.setFile("fileURL");
        task.setStatus(1);
        tasks.add(task);
        when(userService.getUserInfo(anyInt())).thenReturn(new UserResponse(1,"username","email","avatar"));
        when(taskMapper.selectTaskByProjectId(anyInt())).thenReturn(tasks);

        ProjectTaskResponse response = taskService.getTasksForProject(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void getTasksForProject_fail() {
        when(taskMapper.selectTaskByProjectId(anyInt())).thenThrow(new RuntimeException("Some error"));

        ProjectTaskResponse response = taskService.getTasksForProject(1);

        assertEquals(400, response.getCode());
        assertEquals("Some error", response.getMessage());
    }

    @Test
    void getTasksForLeader_success() {
        Task task = new Task();
        task.setId(1);
        task.setName("TaskName");
        task.setDescription("Description");
        task.setProjectId(1);
        task.setDeadline(LocalDateTime.now());
        task.setLeader(1);
        task.setPriority(1);
        task.setFile("fileURL");
        task.setStatus(1);
        when(userService.getUserInfo(anyInt())).thenReturn(new UserResponse(1,"username","email","avatar"));
        when(taskMapper.getTaskByLeader(anyInt())).thenReturn(new Task[]{task});

        ProjectTaskResponse response = taskService.getTasksForLeader(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void getTasksForLeader_fail() {
        when(taskMapper.getTaskByLeader(anyInt())).thenThrow(new RuntimeException("Some error"));

        ProjectTaskResponse response = taskService.getTasksForLeader(1);

        assertEquals(400, response.getCode());
        assertEquals("Some error", response.getMessage());
    }


    @Test
    void getTasksForMember_success() {
        List<Integer> taskIds = new ArrayList<>();
        taskIds.add(1);
        when(userService.getUserInfo(anyInt())).thenReturn(new UserResponse(1,"username","email","avatar"));
        when(memberMapper.getTaskByUserId(anyInt())).thenReturn(taskIds);

        Task task = new Task();
        task.setId(1);
        task.setName("TaskName");
        task.setDescription("Description");
        task.setProjectId(1);
        task.setDeadline(LocalDateTime.now());
        task.setLeader(1);
        task.setPriority(1);
        task.setFile("fileURL");
        task.setStatus(1);
        when(taskMapper.getTaskById(anyInt())).thenReturn(task);

        ProjectTaskResponse response = taskService.getTasksForMember(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void getTasksForMember_fail() {
        when(memberMapper.getTaskByUserId(anyInt())).thenThrow(new RuntimeException("Some error"));

        ProjectTaskResponse response = taskService.getTasksForMember(1);

        assertEquals(400, response.getCode());
        assertEquals("Some error", response.getMessage());
    }

    @Test
    void getLeader_success() {
        when(taskMapper.getLeader(anyInt())).thenReturn(1);

        int leader = taskService.getLeader(1);

        assertEquals(1, leader);
    }

    @Test
    void getLeader_fail() {
        when(taskMapper.getLeader(anyInt())).thenThrow(new RuntimeException("Some error"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.getLeader(1);
        });

        assertEquals("Some error", exception.getMessage());
    }
}