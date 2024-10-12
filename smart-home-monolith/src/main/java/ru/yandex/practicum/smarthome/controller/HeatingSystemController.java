package ru.yandex.practicum.smarthome.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.smarthome.dto.DeviceStatusDto;
import ru.yandex.practicum.smarthome.service.HeatingSystemService;

@RestController
@RequestMapping("/api/heating")
@RequiredArgsConstructor
public class HeatingSystemController {

    private final HeatingSystemService heatingSystemService;

    private static final Logger logger = LoggerFactory.getLogger(HeatingSystemController.class);

    @PutMapping("/{id}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable("id") long id, @RequestBody DeviceStatusDto status) {
        logger.info("Changing status of system with id {}", id);
        heatingSystemService.changeStatus(id, status);
        return ResponseEntity.noContent().build();
    }
}
