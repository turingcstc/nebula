package nebula.application.repository;

import nebula.application.domain.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {

    Page<Group> findByNameIgnoreCaseContaining(String name, Pageable pageable);
    Page<Group> findByIsSystem(boolean isSystem, Pageable pageable);
    Page<Group> findByNameIgnoreCaseContainingAndIsSystem(String name, boolean isSystem, Pageable pageable);
}
