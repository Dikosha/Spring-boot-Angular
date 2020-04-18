package kz.iitu.swd.group34.FirstSpringBootIITU.repositories;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    @Override
    Service getOne(Long aLong);
    List<Service> findAll();
    Optional<Service> findById(Long id);
    Optional<Service> findByNameContaining(String name);
}
