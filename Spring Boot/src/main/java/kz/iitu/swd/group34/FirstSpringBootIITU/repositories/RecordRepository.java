package kz.iitu.swd.group34.FirstSpringBootIITU.repositories;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Master;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Record;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Service;
import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecordRepository extends JpaRepository <Record, Long> {
        List<Record> findAll();
        Optional<Record> findById(Long id);
        List<Record> findAllByClient(Users client);
        List<Record> findAllByMaster(Master master);
        List<Record> findAllByService(Service service);
        List<Record> findAllByDate(java.util.Date date);
}
