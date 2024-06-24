package com.tongji.microservice.teamsphere.scheduleservice.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.sceduleservice.*;
import com.tongji.microservice.teamsphere.scheduleservice.entities.Event;
import com.tongji.microservice.teamsphere.scheduleservice.mapper.EventMapper;
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

class ScheduleServiceImplTest {

    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEvent_success() {
        when(eventMapper.insert(any(Event.class))).thenReturn(1);
        when(eventMapper.getMaxId()).thenReturn(1);

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        EventIdResponse response = scheduleService.createEvent(1, startTime, deadline, "Description", "Title", 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(1, response.getScheduleId());
        verify(eventMapper, times(1)).insert(any(Event.class));
    }

    @Test
    void createEvent_fail_unexpected_priority() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        EventIdResponse response = scheduleService.createEvent(1, startTime, deadline, "Description", "Title", 3);

        assertEquals(400, response.getCode());
        assertEquals("Unexpected Priority", response.getMessage());
        assertEquals(-1, response.getScheduleId());
    }

    @Test
    void createEvent_fail_insert_error() {
        when(eventMapper.insert(any(Event.class))).thenReturn(0);

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        EventIdResponse response = scheduleService.createEvent(1, startTime, deadline, "Description", "Title", 1);

        assertEquals(400, response.getCode());
        assertEquals("Unable to insert into database", response.getMessage());
        assertEquals(-1, response.getScheduleId());
    }

    @Test
    void getEventId_success() {
        Event event = new Event();
        event.id = 1;
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(event);

        EventIdResponse response = scheduleService.getEventId(1, "Title");

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(1, response.getScheduleId());
    }

    @Test
    void getEventId_fail_no_event() {
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        EventIdResponse response = scheduleService.getEventId(1, "Title");

        assertEquals(400, response.getCode());
        assertEquals("没有符合要求的事件", response.getMessage());
        assertEquals(-1, response.getScheduleId());
    }

    @Test
    void removeEvent_success() {
        Event event = new Event();
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(event);
        when(eventMapper.delete(any(QueryWrapper.class))).thenReturn(1);

        APIResponse response = scheduleService.removeEvent(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void removeEvent_fail_no_event() {
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        APIResponse response = scheduleService.removeEvent(1);

        assertEquals(400, response.getCode());
        assertEquals("No such event", response.getMessage());
    }

    @Test
    void updateEventInfo_success() {
        Event event = new Event();
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(event);
        when(eventMapper.update(any(UpdateWrapper.class))).thenReturn(1);

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        APIResponse response = scheduleService.updateEventInfo(1, startTime, deadline, "Description", "Title", 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
    }

    @Test
    void updateEventInfo_fail_no_event() {
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        APIResponse response = scheduleService.updateEventInfo(1, startTime, deadline, "Description", "Title", 1);

        assertEquals(400, response.getCode());
        assertEquals("No such event", response.getMessage());
    }

    @Test
    void updateEventInfo_fail_unexpected_priority() {
        Event event = new Event();
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(event);

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime deadline = LocalDateTime.now().plusDays(1);
        APIResponse response = scheduleService.updateEventInfo(1, startTime, deadline, "Description", "Title", 3);

        assertEquals(400, response.getCode());
        assertEquals("Unexpected priority", response.getMessage());
    }

    @Test
    void getEventInfo_success() {
        Event event = new Event();
        event.userId = 1;
        event.startTime = LocalDateTime.now();
        event.deadline = LocalDateTime.now().plusDays(1);
        event.description = "Description";
        event.title = "Title";
        event.priority = 1;
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(event);

        EventInfoResponse response = scheduleService.getEventInfo(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(1, response.getUserId());
        assertEquals("Description", response.getDescription());
        assertEquals("Title", response.getTitle());
        assertEquals(1, response.getPriority());
    }

    @Test
    void getEventInfo_fail_no_event() {
        when(eventMapper.selectOne(any(QueryWrapper.class))).thenReturn(null);

        EventInfoResponse response = scheduleService.getEventInfo(1);

        assertEquals(400, response.getCode());
        assertEquals("No such event", response.getMessage());
    }

    @Test
    void getEvents_success() {
        List<Event> events = new ArrayList<>();
        Event event = new Event();
        event.title = "Title";
        event.description = "Description";
        event.startTime = LocalDateTime.now();
        event.deadline = LocalDateTime.now().plusDays(1);
        event.priority = 1;
        events.add(event);
        when(eventMapper.selectList(any(QueryWrapper.class))).thenReturn(events);

        EventsResponse response = scheduleService.getEvents(1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getEvents());
        assertEquals(1, response.getEvents().size());
        assertEquals("Title", response.getEvents().get(0).getTitle());
    }
}