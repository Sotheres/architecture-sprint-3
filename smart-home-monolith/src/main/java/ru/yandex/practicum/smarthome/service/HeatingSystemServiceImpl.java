package ru.yandex.practicum.smarthome.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.smarthome.dto.DeviceStatusDto;

@Service
public class HeatingSystemServiceImpl implements HeatingSystemService {

    private final RestClient httpClient = RestClient.create();

    @Value("${device_service_url}")
    private String deviceServiceUrl;

    @Override
    public void changeStatus(long id, DeviceStatusDto status) {
        httpClient.put()
                .uri(deviceServiceUrl + "/api/devices/{id}/change-status", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(status)
                .retrieve()
                .toBodilessEntity();
    }
}