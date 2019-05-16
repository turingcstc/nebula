package io.github.turingcstc.nebula.application.service.mapper;

import io.github.turingcstc.nebula.application.domain.Group;
import io.github.turingcstc.nebula.application.service.dto.GroupDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface GroupMapper extends EntityMapper<GroupDTO, Group>{

    GroupDTO toDto(Group group);
    Group toEntity(GroupDTO groupDTO);

//    default Group fromId(Long id) {
//        if (id == null) {
//            return null;
//        }
//        Group group = new Group();
//        group.setId(id);
//        return group;
//    }
}
