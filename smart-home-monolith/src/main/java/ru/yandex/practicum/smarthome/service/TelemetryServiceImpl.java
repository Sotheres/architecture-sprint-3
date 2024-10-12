package ru.yandex.practicum.smarthome.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.smarthome.dto.TelemetryDto;

@Service
public class TelemetryServiceImpl implements TelemetryService {

    private static final Logger logger = LoggerFactory.getLogger(TelemetryServiceImpl.class);

    private final RestClient httpClient = RestClient.create();

    @Value("${device_service_url}")
    private String deviceServiceUrl;

    @Value("${telemetry_service_url}")
    private String telemetryServiceUrl;

    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.SECONDS)
    public void updateTelemetry() {
        List<TelemetryDto> telemetry = httpClient.get()
                .uri(deviceServiceUrl + "/api/devices/telemetry")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
        int dataCount = Optional.ofNullable(telemetry).map(List::size).orElse(0);

        logger.info("Received telemetry data; count = {}", dataCount);

        httpClient.post()
                .uri(telemetryServiceUrl + "/api/telemetry")
                .contentType(MediaType.APPLICATION_JSON)
                .body(telemetry)
                .retrieve()
                .toBodilessEntity();
        logger.info("Send telemetry data");
    }
}
