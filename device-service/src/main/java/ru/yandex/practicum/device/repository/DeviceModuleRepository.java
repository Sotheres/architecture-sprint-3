package ru.yandex.practicum.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.device.entity.DeviceModule;

@Repository
public interface DeviceModuleRepository extends JpaRepository<DeviceModule, Long> {

}
