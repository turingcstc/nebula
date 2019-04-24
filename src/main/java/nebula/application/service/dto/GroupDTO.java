package nebula.application.service.dto;

import lombok.Data;
import nebula.application.domain.Group;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class GroupDTO implements Serializable {
    private Long id;

    @NotNull
    private String name;

    private String description;

    private  Boolean isSystem;

    public GroupDTO() {
        // Empty constructor needed for Jackson.
    }

    public GroupDTO(Group group) {
        this.id = group.getId();
        this.name = group.getName();
        this.description = group.getDescription();
        this.isSystem = group.getIsSystem();
    }

}
