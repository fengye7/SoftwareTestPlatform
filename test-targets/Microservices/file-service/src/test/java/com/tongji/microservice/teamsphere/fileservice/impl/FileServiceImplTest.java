package com.tongji.microservice.teamsphere.fileservice.impl;

import com.tongji.microservice.teamsphere.dto.APIResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileData;
import com.tongji.microservice.teamsphere.dto.fileservice.FileDataResponse;
import com.tongji.microservice.teamsphere.dto.fileservice.FileResponse;
import com.tongji.microservice.teamsphere.dto.projectservice.PrivilegeResponse;
import com.tongji.microservice.teamsphere.dubbo.api.ProjectService;
import com.tongji.microservice.teamsphere.fileservice.entities.FileInfo;
import com.tongji.microservice.teamsphere.fileservice.entities.Star;
import com.tongji.microservice.teamsphere.fileservice.mapper.FileMapper;
import com.tongji.microservice.teamsphere.fileservice.mapper.StarMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileServiceImplTest {

    @Mock
    private FileMapper fileMapper;

    @Mock
    private StarMapper starMapper;

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void upload_success() {
        FileData fileData = new FileData(0, "url", "type", "name", null, 1, 1, 100, 0);
        when(fileMapper.getFileByName(anyString())).thenReturn(new FileInfo());

        FileDataResponse response = fileService.upload(fileData);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertEquals(fileData, response.getFileData());
        verify(fileMapper, times(1)).insert(any(FileInfo.class));
        verify(starMapper, times(1)).insert(any(Star.class));
    }

    @Test
    void delete_success() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(1);
        fileInfo.setUserId(1);
        when(fileMapper.getFileByName(anyString())).thenReturn(fileInfo);
        when(projectService.getProjectMemberPrivilege(anyInt(), anyInt())).thenReturn(new PrivilegeResponse(2));

        APIResponse response = fileService.delete(1, "fileName");

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        verify(starMapper, times(1)).deleteByFileId(anyInt());
        verify(fileMapper, times(1)).deleteById(anyInt());
    }

    @Test
    void delete_fail_no_privilege() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(1);
        fileInfo.setUserId(2);
        when(fileMapper.getFileByName(anyString())).thenReturn(fileInfo);
        when(projectService.getProjectMemberPrivilege(anyInt(), anyInt())).thenReturn(new PrivilegeResponse(1));

        APIResponse response = fileService.delete(1, "fileName");

        assertEquals(400, response.getCode());
        assertEquals("文件不属于你，且你不是管理员，无权删除", response.getMessage());
        verify(starMapper, times(0)).deleteByFileId(anyInt());
        verify(fileMapper, times(0)).deleteById(anyInt());
    }

    @Test
    void getFileByProject_success() {
        List<FileInfo> fileInfos = new ArrayList<>();
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(1);
        fileInfo.setName("fileName");
        fileInfos.add(fileInfo);
        when(fileMapper.getFileByProject(anyInt())).thenReturn(fileInfos);
        when(starMapper.isStarred(anyInt(), anyInt())).thenReturn(1);

        FileResponse response = fileService.getFileByProject(1, 1);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getList());
        assertEquals(1, response.getList().size());
        assertEquals("fileName", response.getList().get(0).getName());
    }

    @Test
    void putStar_success() {
        APIResponse response = fileService.putStar(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        verify(starMapper, times(1)).insert(any(Star.class));
    }

    @Test
    void deleteStar_success() {
        APIResponse response = fileService.deleteStar(1, 1);

        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        verify(starMapper, times(1)).deleteStar(anyInt(), anyInt());
    }

    @Test
    void getFileByStar_success() {
        List<Integer> starIds = new ArrayList<>();
        starIds.add(1);
        when(starMapper.getStarByUserId(anyInt())).thenReturn(starIds);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(1);
        fileInfo.setName("fileName");
        when(fileMapper.getFileById(anyInt())).thenReturn(fileInfo);

        FileResponse response = fileService.getFileByStar(1);

        assertNotNull(response);
        assertEquals(200, response.getCode());
        assertEquals("success", response.getMessage());
        assertNotNull(response.getList());
        assertEquals(1, response.getList().size());
        assertEquals("fileName", response.getList().get(0).getName());
    }

    @Test
    void getFileByURL_success() {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(1);
        fileInfo.setUrl("url");
        fileInfo.setName("fileName");
        when(fileMapper.getFileByURL(anyString())).thenReturn(fileInfo);

        FileData fileData = fileService.getFileByURL("url");

        assertNotNull(fileData);
        assertEquals("fileName", fileData.getName());
    }

    @Test
    void isStarred_success() {
        when(starMapper.isStarred(anyInt(), anyInt())).thenReturn(1);

        int result = fileService.isStarred(1, 1);

        assertEquals(1, result);
    }
}