package com.tongji.microservice.teamsphere.chatservice.impl;

import com.tongji.microservice.teamsphere.chatservice.entities.GroupChat;
import com.tongji.microservice.teamsphere.chatservice.entities.GroupMember;
import com.tongji.microservice.teamsphere.chatservice.mapper.GroupMapper;
import com.tongji.microservice.teamsphere.chatservice.mapper.GroupMemberMapper;
import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.chatservice.GroupMemberResponse;
import com.tongji.microservice.teamsphere.dubbo.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceImplTest {

    @Mock
    private GroupMapper groupMapper;

    @Mock
    private GroupMemberMapper memberMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private ChatServiceImpl chatService;

    @BeforeEach
    void setUp() {
        // 在此设置必要的模拟行为
    }

    @Test
    void getPort() {
        APIResponse response = chatService.getPort();
        assertEquals(APIResponse.success().getCode(), response.getCode());
    }

    @Test
    void createGroup() {
        List<String> memberIds = Arrays.asList("1", "2", "3");

        // 使用正确的Mockito语法来模拟返回值
        when(groupMapper.insert(any(GroupChat.class))).thenReturn(1);
        when(groupMapper.getMaxId()).thenReturn(1);
        when(memberMapper.insert(any(GroupMember.class))).thenReturn(1);

        APIResponse response = chatService.createGroup("Test Group", "avatar.png", memberIds);

        assertEquals(APIResponse.success().getCode(), response.getCode());
        verify(groupMapper).insert(any(GroupChat.class));
        verify(groupMapper).getMaxId();
        verify(memberMapper, times(3)).insert(any(GroupMember.class));
    }

    @Test
    void createGroup_Exception() {
        List<String> memberIds = Arrays.asList("1", "2", "3");

        // 模拟抛出异常
        when(groupMapper.insert(any(GroupChat.class))).thenThrow(new RuntimeException("Database error"));

        APIResponse response = chatService.createGroup("Test Group", "avatar.png", memberIds);

        assertEquals(APIResponse.fail("Database error").getCode(), response.getCode());
        verify(groupMapper).insert(any(GroupChat.class));
    }

    @Test
    void getGroupMember() {
        when(memberMapper.getMemberByGroupId(1)).thenReturn(Arrays.asList(1, 2, 3));

        GroupMemberResponse response = chatService.getGroupMember(1);

        assertEquals(Arrays.asList(1, 2, 3), response.getList());
    }

    @Test
    void getGroupMember_Exception() {
        when(memberMapper.getMemberByGroupId(1)).thenThrow(new RuntimeException("Database error"));

        GroupMemberResponse response = chatService.getGroupMember(1);

        assertEquals(APIResponse.fail("数据库访问失败Database error").getCode(), response.getCode());
        assertEquals(APIResponse.fail("数据库访问失败Database error").getMessage(), response.getMessage());
    }

    @Test
    void getGroups() {
        when(memberMapper.getGroupByMember(1)).thenReturn(Arrays.asList(1, 2, 3));

        List<Integer> groups = chatService.getGroups(1);

        assertEquals(Arrays.asList(1, 2, 3), groups);
    }
}
