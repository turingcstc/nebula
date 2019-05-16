package io.github.turingcstc.nebula.application.service;

import io.github.turingcstc.nebula.application.service.dto.GroupDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GroupService {

    GroupDTO save(GroupDTO groupDTO);

    List<GroupDTO> findAll();

    Optional<GroupDTO> findOne(Long id);

    void delete(Long id);

    Page<GroupDTO> findAll(Pageable pageable);
    Page<GroupDTO> findByNameIgnoreCaseContaining(String name, Pageable pageable);
    Page<GroupDTO> findIsSystem(Boolean isSystem, Pageable pageable);
    Page<GroupDTO> findByNameIgnoreCaseContainingAndIsSystem(String name, boolean isSystem, Pageable pageable);

}
