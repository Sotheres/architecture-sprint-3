package ru.yandex.practicum.telemetry.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.telemetry.dto.TelemetryDto;
import ru.yandex.practicum.telemetry.service.TelemetryService;

@RestController
@RequestMapping("/api/telemetry")
@RequiredArgsConstructor
public class TelemetryController {

    private static final Logger logger = LoggerFactory.getLogger(TelemetryController.class);

    private final TelemetryService telemetryService;

    @PostMapping
    public void saveTelemetryData(@RequestBody List<TelemetryDto> telemetry) {
        logger.info("Saving device telemetry data");
        telemetryService.saveTelemetry(telemetry);
    }
}
