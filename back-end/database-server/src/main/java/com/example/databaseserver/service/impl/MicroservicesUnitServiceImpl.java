package com.example.databaseserver.service.impl;

import com.example.databaseserver.entity.MicroservicesUnit;
import com.example.databaseserver.mapper.MicroservicesUnitMapper;
import com.example.databaseserver.service.MicroservicesUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MicroservicesUnitServiceImpl implements MicroservicesUnitService {

    @Autowired
    private MicroservicesUnitMapper microservicesUnitMapper;

    @Override
    public List<String> getAllNames() {
        return microservicesUnitMapper.getAllNames();
    }

    @Override
    public String getDescriptionByName(String name) {
        return microservicesUnitMapper.getDescriptionByName(name);
    }

    @Override
    public void insertMicroserviceUnit(String name, String description) {
        MicroservicesUnit unit = new MicroservicesUnit();
        unit.setName(name);
        unit.setDescription(description);
        microservicesUnitMapper.insert(unit);
    }

    @Override
    public void deleteMicroserviceUnit(int unitId) {
        microservicesUnitMapper.deleteById(unitId);
    }
}