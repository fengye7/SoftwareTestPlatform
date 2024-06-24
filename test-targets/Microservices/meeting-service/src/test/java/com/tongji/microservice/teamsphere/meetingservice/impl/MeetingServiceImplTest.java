package com.tongji.microservice.teamsphere.meetingservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.meetingservice.*;
import com.tongji.microservice.teamsphere.meetingservice.client.FeishuAPIClient;
import com.tongji.microservice.teamsphere.meetingservice.client.MeetingBackData;
import com.tongji.microservice.teamsphere.meetingservice.entities.Meeting;
import com.tongji.microservice.teamsphere.meetingservice.entities.MeetingParticipants;
import com.tongji.microservice.teamsphere.meetingservice.mapper.MeetingMapper;
import com.tongji.microservice.teamsphere.meetingservice.mapper.MeetingParticipantsMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MeetingServiceImplTest {

    @Mock
    private MeetingMapper meetingMapper;

    @Mock
    private MeetingParticipantsMapper participantsMapper;

    @Mock
    private FeishuAPIClient feishuAPIClient;

    @InjectMocks
    private MeetingServiceImpl meetingService;

    private Meeting meeting;

    @BeforeEach
    void setUp() {
        meeting = new Meeting();
        meeting.setId("1");
        meeting.setBookId("book1");
        meeting.setProjectId(1);
        meeting.setTitle("Test Meeting");
        meeting.setDescription("Test Description");
        meeting.setStartTime(LocalDateTime.now().plusDays(1));
        meeting.setDuration(60);
        meeting.setUrl("http://testurl.com");
    }

    @Test
    void cancelMeeting_meetingNotFound() {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        APIResponse response = meetingService.cancelMeeting("1");

        assertEquals(400, response.getCode());
        assertEquals("会议不存在", response.getMessage());
    }

    @Test
    void cancelMeeting_success() throws Exception {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(meetingMapper.deleteMeetingById(anyString())).thenReturn(1);
        when(participantsMapper.deleteMeetingById(anyString())).thenReturn(1);
        when(feishuAPIClient.CancelMeeting(anyString())).thenReturn(Boolean.TRUE);

        APIResponse response = meetingService.cancelMeeting("1");

        assertEquals(200, response.getCode());
        assertEquals("会议取消成功", response.getMessage());
    }

    @Test
    void cancelMeeting_databaseDeleteFailed() throws Exception {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(meetingMapper.deleteMeetingById(anyString())).thenReturn(-1); // 模拟数据库删除失败
        when(participantsMapper.deleteMeetingById(anyString())).thenReturn(1);
        when(feishuAPIClient.CancelMeeting(anyString())).thenReturn(Boolean.TRUE);

        APIResponse response = meetingService.cancelMeeting("1");

        assertEquals(401, response.getCode());
        assertEquals("会议未能从数据库中删除", response.getMessage());
    }

    @Test
    void cancelMeeting_exceptionThrown() throws Exception {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(meetingMapper.deleteMeetingById(anyString())).thenReturn(1);
        when(participantsMapper.deleteMeetingById(anyString())).thenReturn(1);
        doThrow(new RuntimeException("Network error")).when(feishuAPIClient).CancelMeeting(anyString()); // 模拟异常

        APIResponse response = meetingService.cancelMeeting("1");

        assertEquals(404, response.getCode());
        assertEquals("会议取消失败", response.getMessage());
    }

    @Test
    void createMeeting_invalidStartTime() {
        LocalDateTime pastTime = LocalDateTime.now().minusDays(1);
        MeetingResponse response = meetingService.createMeeting(1, "Title", "Description", pastTime, pastTime.plusHours(1));

        assertEquals(406, response.getCode());
        assertEquals("会议开始时间必须在未来时间", response.getMessage());
    }

    @Test
    void createMeeting_invalidDeadline() {
        LocalDateTime futureTime = LocalDateTime.now().plusDays(1);
        MeetingResponse response = meetingService.createMeeting(1, "Title", "Description", futureTime, futureTime.minusHours(1));

        assertEquals(407, response.getCode());
        assertEquals("会议结束时间必须在开始时间后", response.getMessage());
    }

    @Test
    void createMeeting_success() throws Exception {
        LocalDateTime futureTime = LocalDateTime.now().plusDays(1);
        MeetingBackData backData = new MeetingBackData();
        backData.status = true;
        backData.id = "id";
        backData.url = "url";
        backData.bookId = "bookId";
        when(feishuAPIClient.BookMeeting(any(LocalDateTime.class))).thenReturn(backData);
        when(meetingMapper.insert(any(Meeting.class))).thenReturn(1);

        MeetingResponse response = meetingService.createMeeting(1, "Title", "Description", futureTime, futureTime.plusHours(1));

        assertEquals(200, response.getCode());
        assertEquals("预约会议成功", response.getMessage());

        // 验证 meetingData 是否正确设置
        MeetingData meetingData = response.getMeetingData();
        assertNotNull(meetingData);
    }

    @Test
    void getMeetingsForProject_success() {
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting);
        when(meetingMapper.selectMeetingsByProjectId(anyInt())).thenReturn(meetings);

        MeetingListResponse response = meetingService.getMeetingsForProject(1);

        assertEquals(200, response.getCode());
        assertEquals("获取项目会议信息成功", response.getMessage());
        assertNotNull(response.getMeetings());
        assertEquals(1, response.getMeetings().size());
    }

    @Test
    void getMeetingsForUser_success() {
        List<String> meetingIds = new ArrayList<>();
        meetingIds.add("1");
        List<Meeting> meetings = new ArrayList<>();
        meetings.add(meeting);
        when(participantsMapper.selectMeetingIdsByParticipantId(anyInt())).thenReturn(meetingIds);
        when(meetingMapper.selectMeetingById(anyString())).thenReturn(meeting);

        MeetingListResponse response = meetingService.getMeetingsForUser(1);

        assertEquals(200, response.getCode());
        assertEquals("获取个人会议信息成功", response.getMessage());
        assertNotNull(response.getMeetings());
        assertEquals(1, response.getMeetings().size());
    }

    @Test
    void addParticipant_meetingNotFound() {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        APIResponse response = meetingService.addParticipant("1", 1, "role");

        assertEquals(401, response.getCode());
        assertEquals("会议不存在", response.getMessage());
    }

    @Test
    void addParticipant_participantExists() {
        // 模拟已存在的参会人
        MeetingParticipants participant = new MeetingParticipants("1", 1, "role");
        when(participantsMapper.selectOne(any(QueryWrapper.class))).thenReturn(participant);

        // 模拟已存在的会议
        Meeting meeting = new Meeting();
        meeting.setId("1");
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);

        APIResponse response = meetingService.addParticipant("1", 1, "role");

        assertEquals(400, response.getCode());
        assertEquals("参会人已存在", response.getMessage());
    }

    @Test
    void addParticipant_success() {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(participantsMapper.insert(any(MeetingParticipants.class))).thenReturn(1);

        APIResponse response = meetingService.addParticipant("1", 1, "role");

        assertEquals(200, response.getCode());
        assertEquals("添加参会人成功", response.getMessage());
    }

    @Test
    void removeParticipant_meetingNotFound() {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        APIResponse response = meetingService.removeParticipant("1", 1);

        assertEquals(401, response.getCode());
        assertEquals("会议不存在", response.getMessage());
    }

    @Test
    void removeParticipant_participantNotFound() {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(participantsMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        APIResponse response = meetingService.removeParticipant("1", 1);

        assertEquals(400, response.getCode());
        assertEquals("参会人不存在", response.getMessage());
    }

    @Test
    void removeParticipant_success() {
        MeetingParticipants participant = new MeetingParticipants("1", 1, "role");
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(participantsMapper.selectOne(any(QueryWrapper.class))).thenReturn(participant);
        when(participantsMapper.delete(any(QueryWrapper.class))).thenReturn(1);

        APIResponse response = meetingService.removeParticipant("1", 1);

        assertEquals(200, response.getCode());
        assertEquals("参会人移除成功", response.getMessage());
    }

    @Test
    void setParticipantRole_meetingNotFound() {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        APIResponse response = meetingService.setParticipantRole("1", 1, "role");

        assertEquals(401, response.getCode());
        assertEquals("会议不存在", response.getMessage());
    }

    @Test
    void setParticipantRole_participantNotFound() {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(participantsMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        APIResponse response = meetingService.setParticipantRole("1", 1, "role");

        assertEquals(400, response.getCode());
        assertEquals("参会人不存在", response.getMessage());
    }

    @Test
    void setParticipantRole_success() {
        MeetingParticipants participant = new MeetingParticipants("1", 1, "role");
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(participantsMapper.selectOne(any(QueryWrapper.class))).thenReturn(participant);
        when(participantsMapper.update(any(MeetingParticipants.class), any(QueryWrapper.class))).thenReturn(1);

        APIResponse response = meetingService.setParticipantRole("1", 1, "role");

        assertEquals(200, response.getCode());
        assertEquals("参会人角色修改成功", response.getMessage());
    }

    @Test
    void getParticipantsForMeeting_meetingNotExists() {
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        ParticipantListResponse response = meetingService.getParticipantsForMeeting("1");

        assertEquals(401, response.getCode());
        assertEquals("会议不存在", response.getMessage());
        assertNull(response.getParticipants());
    }

    @Test
    void getParticipantsForMeeting_meetingExistsNoParticipants() {
        Meeting meeting = new Meeting();
        meeting.setId("1");
        when(meetingMapper.selectOne(any(QueryWrapper.class))).thenReturn(meeting);
        when(participantsMapper.selectList(any(QueryWrapper.class))).thenReturn(new ArrayList<>());

        ParticipantListResponse response = meetingService.getParticipantsForMeeting("1");

        assertEquals(200, response.getCode());
        assertEquals("成功获取参会人列表", response.getMessage());
        assertTrue(response.getParticipants().isEmpty());
    }

}