package kz.iitu.swd.group34.FirstSpringBootIITU.repositories;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Master;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Record;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Service;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MasterRepository extends JpaRepository<Master, Long> {
    List<Master> findAll();
    Optional<Master> findById(Long id);
    List<Master> findAllByServicesContaining(Service service);
}
