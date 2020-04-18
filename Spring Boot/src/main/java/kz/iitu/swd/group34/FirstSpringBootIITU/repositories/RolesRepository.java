package kz.iitu.swd.group34.FirstSpringBootIITU.repositories;

import kz.iitu.swd.group34.FirstSpringBootIITU.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, Long> {

    @Override
    Roles getOne(Long aLong);
}
