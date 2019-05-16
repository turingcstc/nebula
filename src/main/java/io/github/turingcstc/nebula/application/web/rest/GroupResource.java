package io.github.turingcstc.nebula.application.web.rest;

//import com.codahale.metrics.annotation.Timed;
import io.github.turingcstc.nebula.application.service.GroupService;
import io.github.turingcstc.nebula.application.service.dto.GroupDTO;
import io.github.turingcstc.nebula.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

//import nebula.application.web.rest.errors.BadRequestAlertException;
//import nebula.application.web.rest.util.PaginationUtil;

//import nebula.application.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
//@CrossOrigin(origins = "http://localhost:4200")
public class GroupResource {
    private final Logger log = LoggerFactory.getLogger(GroupResource.class);

    private static final String ENTITY_NAME = "group";

    private final GroupService groupService;

    public GroupResource(GroupService groupService) {
        this.groupService = groupService;
    }

//    @GetMapping("/groups")
//    //@Timed
//    public ResponseEntity<List<GroupDTO>> findAllGroups(String name,Boolean isSystem, Pageable pageable) {
//
//        Page<GroupDTO> page;
//
//        if (name == null || name.isEmpty() ){
//            if(isSystem == null){
//                page = groupService.findAll(pageable);
//            } else {
//                page = groupService.findIsSystem(isSystem,pageable);
//            }
//        } else {
//            if(isSystem == null){
//                page = groupService.findByNameIgnoreCaseContaining(name,pageable);
//            } else {
//                page = groupService.findByNameIgnoreCaseContainingAndIsSystem(name,isSystem,pageable);
//            }
//        }
//
//        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/groups");
//        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
//    }

    @GetMapping("/groups/{id}")
    //@Timed
    public ResponseEntity<GroupDTO> getGroup(@PathVariable Long id) {
        log.debug("REST request to get Group : {}", id);
        Optional<GroupDTO> groupDTO = groupService.findOne(id);
        log.error(groupDTO.toString());
        return ResponseUtil.wrapOrNotFound(groupDTO);
    }

//    /**
//     * PUT  /departments : Updates an existing department.
//     *
//     * @param groupDTO the departmentDTO to update
//     * @return the ResponseEntity with status 200 (OK) and with body the updated departmentDTO,
//     * or with status 400 (Bad Request) if the departmentDTO is not valid,
//     * or with status 500 (Internal Server Error) if the departmentDTO couldn't be updated
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PutMapping("/groups")
//    //@Timed
//    public ResponseEntity<GroupDTO> updateGroup(@Valid @RequestBody GroupDTO groupDTO) throws URISyntaxException {
//        log.debug("REST request to update Gruop : {}", groupDTO);
//        if (groupDTO.getId() == null) {
//            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
//        }
//        GroupDTO result = groupService.save(groupDTO);
//        return ResponseEntity.ok()
//                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, groupDTO.getId().toString()))
//                .body(result);
//    }


//    /**
//     * POST  /departments : Create a new department.
//     *
//     * @param departmentDTO the departmentDTO to create
//     * @return the ResponseEntity with status 201 (Created) and with body the new departmentDTO, or with status 400 (Bad Request) if the department has already an ID
//     * @throws URISyntaxException if the Location URI syntax is incorrect
//     */
//    @PostMapping("/groups")
//    //@Timed
//    public ResponseEntity<GroupDTO> createGroup(@Valid @RequestBody GroupDTO groupDTO) throws URISyntaxException {
//        log.debug("REST request to save Department : {}", groupDTO);
//        if (groupDTO.getId() != null) {
//            throw new BadRequestAlertException("A new department cannot already have an ID", ENTITY_NAME, "idexists");
//        }
//        GroupDTO result = groupService.save(groupDTO);
//        return ResponseEntity.created(new URI("/api/departments/" + result.getId()))
//                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
//                .body(result);
//    }

}
