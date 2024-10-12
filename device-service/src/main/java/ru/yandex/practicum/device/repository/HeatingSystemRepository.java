package ru.yandex.practicum.device.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.device.entity.HeatingSystem;

@Repository
public interface HeatingSystemRepository extends JpaRepository<HeatingSystem, Long> {
}
