package com.example.databaseserver.service;

import com.example.databaseserver.entity.MicroservicesUnit;

import java.util.List;

public interface MicroservicesUnitService {
    List<String> getAllNames();
    String getDescriptionByName(String name);
    void insertMicroserviceUnit(String name, String description);
    void deleteMicroserviceUnit(int unitId);
}