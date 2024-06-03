package com.example.databaseserver.controller;

import com.example.databaseserver.service.MicroservicesUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/microservices")
public class MicroservicesUnitController {

    @Autowired
    private MicroservicesUnitService microservicesUnitService;

    @GetMapping("/names")
    public List<String> getAllNames() {
        return microservicesUnitService.getAllNames();
    }

    @GetMapping("/description")
    public String getDescriptionByName(@RequestParam String name) {
        return microservicesUnitService.getDescriptionByName(name);
    }

    @PostMapping
    public void insertMicroserviceUnit(@RequestParam String name, @RequestParam String description) {
        microservicesUnitService.insertMicroserviceUnit(name, description);
    }

    @DeleteMapping("/{id}")
    public void deleteMicroserviceUnit(@PathVariable int id) {
        microservicesUnitService.deleteMicroserviceUnit(id);
    }
}