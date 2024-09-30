package ru.yandex.practicum.device.service;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.stereotype.Service;

@Service
public class TemperatureSensorSystemStub implements TemperatureSensorSystem {

    @Override
    public double getCurrentTemperature(long ignored) {
        return ThreadLocalRandom.current().nextInt(11_0,33_0) / 10.0;
    }
}
