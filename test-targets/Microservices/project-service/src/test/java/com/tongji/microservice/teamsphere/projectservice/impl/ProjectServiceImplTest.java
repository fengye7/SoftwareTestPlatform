package com.tongji.microservice.teamsphere.projectservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.*;
import com.tongji.microservice.teamsphere.dto.userservice.UserResponse;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import com.tongji.microservice.teamsphere.projectservice.entities.Project;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMember;
import com.tongji.microservice.teamsphere.projectservice.entities.ProjectMemberRequest;
import com.tongji.microservice.teamsphere.projectservice.mapper.ProjectMapper;
import com.tongji.microservice.teamsphere.projectservice.mapper.ProjectMemberMapper;
import com.tongji.microservice.teamsphere.projectservice.mapper.ProjectMemberRequestMapper;
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

class ProjectServiceImplTest {

    @Mock
    private ProjectMapper projectMapper;

    @Mock
    private ProjectMemberMapper memberMapper;

    @Mock
    private ProjectMemberRequestMapper requestMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectServiceImpl projectService;

    private Project project;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        project = new Project();
        project.setId(1);
        project.setName( "Test Project");
        project.setDescription("Test Description");
        project.setScale(1);
        project.setLeader(1);
    }

    @Test
    void creatProject_success() {
        ProjectData projectData = new ProjectData();
        projectData.setName("Test Project");

        when(projectMapper.insert(any(Project.class))).thenReturn(1);
        when(projectMapper.selectOne(any())).thenReturn(project);

        ProjectIdResponse response = projectService.creatProject(projectData);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(1, response.getProjectId());
        verify(projectMapper, times(1)).insert(any(Project.class));
        verify(memberMapper, times(1)).insert(any(ProjectMember.class));
    }

    @Test
    void creatProject_fail() {
        ProjectData projectData = new ProjectData();
        projectData.setName("Test Project");

        when(projectMapper.insert(any(Project.class))).thenReturn(0);

        ProjectIdResponse response = projectService.creatProject(projectData);

        assertEquals(400, response.getCode());
        assertEquals("创建项目失败", response.getMessage());
        assertEquals(-1, response.getProjectId());
    }

    @Test
    void addProjectMember_success() {
        when(memberMapper.selectOne(any())).thenReturn(null);
        when(memberMapper.insert(any(ProjectMember.class))).thenReturn(1);

        APIResponse response = projectService.addProjectMember(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        verify(memberMapper, times(1)).insert(any(ProjectMember.class));
    }

    @Test
    void addProjectMember_fail_member_exists() {
        when(memberMapper.selectOne(any())).thenReturn(new ProjectMember());

        APIResponse response = projectService.addProjectMember(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("成员已存在", response.getMessage());
    }

    @Test
    void updateProjectInfo_success() {
        ProjectData projectData = new ProjectData();
        projectData.setName("Updated Project");

        when(projectMapper.update(any())).thenReturn(1);

        APIResponse response = projectService.updateProjectInfo(1, projectData);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        verify(projectMapper, times(1)).update(any());
    }

    @Test
    void updateProjectInfo_fail() {
        ProjectData projectData = new ProjectData();
        projectData.setName("Updated Project");

        when(projectMapper.update(any())).thenReturn(0);

        APIResponse response = projectService.updateProjectInfo(1, projectData);

        assertEquals(400, response.getCode());
        assertEquals("编辑项目失败", response.getMessage());
    }

    @Test
    void removeProjectMember_success() {
        when(memberMapper.delete(any())).thenReturn(1);

        APIResponse response = projectService.removeProjectMember(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        verify(memberMapper, times(1)).delete(any());
    }

    @Test
    void removeProjectMember_fail() {
        when(memberMapper.delete(any())).thenReturn(0);

        APIResponse response = projectService.removeProjectMember(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("移除成员失败", response.getMessage());
    }

    @Test
    void updateProjectMemberPrivilege_success() {
        when(memberMapper.update(any())).thenReturn(1);

        APIResponse response = projectService.updateProjectMemberPrivilege(1, 1, 2);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        verify(memberMapper, times(1)).update(any());
    }

    @Test
    void updateProjectMemberPrivilege_fail() {
        when(memberMapper.update(any())).thenReturn(0);

        APIResponse response = projectService.updateProjectMemberPrivilege(1, 1, 2);

        assertEquals(400, response.getCode());
        assertEquals("变更权限失败", response.getMessage());
    }

    @Test
    void getProjectMembers_success() {
        List<ProjectMember> members = new ArrayList<>();
        ProjectMember member = new ProjectMember();
        member.setUserId(1);
        members.add(member);
        when(memberMapper.getMembers(anyInt())).thenReturn(members);
        when(userService.getUserInfo(anyInt())).thenReturn(new UserResponse(1, "username","email","avatar"));

        MembersResponse response = projectService.getProjectMembers(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getMembers());
        assertEquals(1, response.getMembers().size());
    }

    @Test
    void getProjectInfo_success() {
        when(projectMapper.selectOne(any())).thenReturn(project);

        ProjectInfoResponse response = projectService.getProjectInfo(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void getProjectInfo_fail() {
        when(projectMapper.selectOne(any())).thenReturn(null);

        ProjectInfoResponse response = projectService.getProjectInfo(1);

        assertEquals(400, response.getCode());
        assertEquals("项目不存在", response.getMessage());
    }

    @Test
    void deleteProject_success() {
        when(projectMapper.selectOne(any())).thenReturn(new Project());
        when(projectMapper.deleteById(anyInt())).thenReturn(1);

        APIResponse response = projectService.deleteProject(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void deleteProject_fail() {
        when(projectMapper.selectOne(any())).thenReturn(null);

        APIResponse response = projectService.deleteProject(1);

        assertEquals(400, response.getCode());
        assertEquals("项目不存在", response.getMessage());
    }

    @Test
    void getProjectMemberPrivilege_success() {
        when(memberMapper.getPrivilege(anyInt(), anyInt())).thenReturn(2);

        PrivilegeResponse response = projectService.getProjectMemberPrivilege(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(2, response.getPrivilege());
    }

    @Test
    void getProjectMemberPrivilege_fail() {
        when(memberMapper.getPrivilege(anyInt(), anyInt())).thenThrow(new RuntimeException("获取失败"));

        PrivilegeResponse response = projectService.getProjectMemberPrivilege(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("获取失败", response.getMessage());
    }

    @Test
    void getProjectByUserId_success() {
        List<Integer> projectIds = new ArrayList<>();
        projectIds.add(1);
        when(memberMapper.getProjectByUserId(anyInt())).thenReturn(projectIds);
        when(projectMapper.selectProjectById(anyInt())).thenReturn(new ProjectData());

        ProjectQueryResponse response = projectService.getProjectByUserId(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void queryProject_success() {
        List<Project> projects = new ArrayList<>();
        projects.add(new Project());
        when(projectMapper.selectAll()).thenReturn(projects);

        ProjectQueryResponse response = projectService.queryProject();

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void getProjectJoinRequest_success() {
        List<ProjectMemberRequest> requests = new ArrayList<>();
        ProjectMemberRequest request = new ProjectMemberRequest();
        request.setUserId(1);
        request.setRequestTime(LocalDateTime.now());
        requests.add(request);
        when(requestMapper.getRequestByProjectId(anyInt())).thenReturn(requests);
        when(userService.getUserInfo(anyInt())).thenReturn(new UserResponse(1, "username","email","avatar"));

        ProjectJoinRequestResponse response = projectService.getProjectJoinRequest(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void makeJoinRequest_success() {
        when(memberMapper.exists(any())).thenReturn(false);
        when(requestMapper.exists(any())).thenReturn(false);
        when(requestMapper.insert(any(ProjectMemberRequest.class))).thenReturn(1);

        APIResponse response = projectService.makeJoinRequest(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void makeJoinRequest_fail_already_member() {
        when(memberMapper.exists(any())).thenReturn(true);

        APIResponse response = projectService.makeJoinRequest(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("您已加入组织", response.getMessage());
    }

    @Test
    void makeJoinRequest_fail_already_requested() {
        when(memberMapper.exists(any())).thenReturn(false);
        when(requestMapper.exists(any())).thenReturn(true);

        APIResponse response = projectService.makeJoinRequest(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("您已发出加入申请", response.getMessage());
    }

    @Test
    void makeJoinRequest_fail_insert_error() {
        when(memberMapper.exists(any())).thenReturn(false);
        when(requestMapper.exists(any())).thenReturn(false);
        when(requestMapper.insert(any(ProjectMemberRequest.class))).thenReturn(0);

        APIResponse response = projectService.makeJoinRequest(1, 1);

        assertEquals(400, response.getCode());
        assertEquals("操作失败", response.getMessage());
    }

    @Test
    void judgeJoinRequest_success_refuse() {
        when(requestMapper.refuse(anyInt(), anyInt())).thenReturn(1);

        APIResponse response = projectService.judgeJoinRequest(1, 1, 0);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void judgeJoinRequest_fail() {
        when(requestMapper.agree(anyInt(), anyInt())).thenReturn(0);
        when(requestMapper.refuse(anyInt(), anyInt())).thenReturn(0);

        APIResponse response = projectService.judgeJoinRequest(1, 1, 1);

        assertEquals(400, response.getCode());
        assertEquals("操作失败", response.getMessage());
    }

    @Test
    void judgeJoinRequest_exception() {
        when(requestMapper.agree(anyInt(), anyInt())).thenThrow(new RuntimeException("发生错误"));

        APIResponse response = projectService.judgeJoinRequest(1, 1, 1);

        assertEquals(400, response.getCode());
        assertEquals("发生错误:发生错误", response.getMessage());
    }
}