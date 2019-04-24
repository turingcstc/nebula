package nebula.application.service.mapper;

import nebula.application.domain.Group;
import nebula.application.service.dto.GroupDTO;
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
