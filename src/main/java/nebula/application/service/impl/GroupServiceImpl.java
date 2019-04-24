package nebula.application.service.impl;

import nebula.application.domain.Group;
import nebula.application.repository.GroupRepository;
import nebula.application.service.GroupService;
import nebula.application.service.dto.GroupDTO;
import nebula.application.service.mapper.GroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final Logger log = LoggerFactory.getLogger(GroupServiceImpl.class);

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public GroupDTO save(GroupDTO groupDTO) {
        log.debug("Request to save Group : {}", groupDTO);
        Group group = groupMapper.toEntity(groupDTO);
        group = groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    @Override
    @Transactional(readOnly = true)
    public List<GroupDTO> findAll() {
        log.debug("Request to get all Departments");
        return groupRepository.findAll().stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GroupDTO> findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        return groupRepository.findById(id)
                .map(groupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);
        groupRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupDTO> findAll(Pageable pageable){
        return groupRepository.findAll(pageable).map(GroupDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupDTO> findByNameIgnoreCaseContaining(String name,Pageable pageable){
        return groupRepository.findByNameIgnoreCaseContaining(name,pageable).map(GroupDTO::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<GroupDTO> findIsSystem(Boolean isSystem,Pageable pageable) {
        return groupRepository.findByIsSystem(isSystem,pageable).map(GroupDTO::new);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<GroupDTO> findByNameIgnoreCaseContainingAndIsSystem(String name, boolean isSystem,Pageable pageable){
        return groupRepository.findByNameIgnoreCaseContainingAndIsSystem(name,isSystem,pageable).map(GroupDTO::new);
    }


}
