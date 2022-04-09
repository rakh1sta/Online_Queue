package uz.sh.online_queue.controller.department;

import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.criteria.department.DepartmentCriteria;
import uz.sh.online_queue.dto.department.DepartmentCreateDto;
import uz.sh.online_queue.dto.department.DepartmentDto;
import uz.sh.online_queue.dto.department.DepartmentUpdateDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.department.DepartmentServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Tag(name = "Department Controller", description = "the Department API")
public class DepartmentController extends AbstractController<DepartmentServiceImpl> {
    @Autowired
    public DepartmentController(DepartmentServiceImpl service) {
        super(service);
    }

    @Operation(summary = "list of department", description = "This method for get department list of a organization by organization id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get list of department", content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Department not exist for organization yet ", content = @Content),
            @ApiResponse(responseCode = "403", description = "Organization blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)})
    @RequestMapping(value = PATH + "/departments/{orgId}", method = RequestMethod.GET)
    public ResponseEntity<Data<List<DepartmentDto>>> getAll(
            @Parameter(description = "Id of the organization. Cannot be empty.", required = true)
            @PathVariable String orgId,
            @Parameter(description = "Criteria of the Department for pagination.", required = true)
            @RequestBody DepartmentCriteria criteria) {return service.blockList(criteria, orgId);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful get department", content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
            @ApiResponse(responseCode = "403", description = "Department blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found", content = @Content)})
    @Operation(summary = "get department ", description = "This method for get department by id")
    @RequestMapping(value = PATH + "/department/{departmentId}", method = RequestMethod.GET)
    public ResponseEntity<Data<DepartmentDto>> get(
            @Parameter(description = "id of the department for get. Cannot be empty.", required = true)
            @PathVariable String departmentId) {return service.get(departmentId);}


    @Operation(summary = "delete  organization", description = "This method for delete department by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Department", content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found", content = @Content)})
    @RequestMapping(value = PATH + "/department/{departmentId}", method = RequestMethod.DELETE)
    public ResponseEntity<Data<Void>> delete(
            @Parameter(description = "id of the department for delete. Cannot be empty.", required = true)
            @PathVariable String departmentId) {return service.delete(departmentId);}


    @Operation(summary = "update department ", description = "This method for update department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated Department",
                    content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Department not found", content = @Content)})
    @RequestMapping(value = PATH + "/department", method = RequestMethod.POST)
    public ResponseEntity<Data<DepartmentDto>> update(
            @Parameter(description = "dto of department for update. Cannot be empty.", required = true)
            @Valid @RequestBody DepartmentUpdateDto dto) {return service.update(dto);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully create the Department",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Already exist department", content = @Content),
            @ApiResponse(responseCode = "403", description = "Organization blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found Organization for create department", content = @Content)})
    @Operation(summary = "create department ", description = "This method for create department to organization by organization id")
    @RequestMapping(value = PATH + "/department/{orgId}", method = RequestMethod.POST)
    public ResponseEntity<Data<String>> create(
            @Parameter(description = "dto for create department. Cannot be empty.", required = true)
            @Valid @RequestBody DepartmentCreateDto dto,
            @Parameter(description = "id of the organization for department. Cannot be empty.", required = true)
            @PathVariable String orgId) {return service.create(dto, orgId);}


    @Operation(summary = "block department", description = "This method for block department by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully blocked Department", content = @Content),
            @ApiResponse(responseCode = "400", description = "Department already blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found by id", content = @Content)})
    @RequestMapping(value = PATH + "/department/block/{departmentId}", method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> block(
            @Parameter(description = "id of department for block. Cannot be empty.", required = true)
            @PathVariable String departmentId) {return service.block(departmentId);}


    @Operation(summary = "unblock department", description = "This method for unblock department by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully unblocked Department", content = @Content),
            @ApiResponse(responseCode = "400", description = "Department was unblock", content = @Content),
            @ApiResponse(responseCode = "404", description = "Department not found", content = @Content)})
    @RequestMapping(value = PATH + "/department/unblock/{departmentId}", method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> unblock(
            @Parameter(description = "id of department for unblock. Cannot be empty.", required = true)
            @PathVariable String departmentId) {return service.unblock(departmentId);}


    @Operation(summary = "block list of department of a region", description = "This method for unblock department by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get blocked list of Department",
                    content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Blocked Department not have for this organization yet ", content = @Content),
            @ApiResponse(responseCode = "403", description = "Organization blocked ", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)})
    @RequestMapping(value = PATH + "/departments/blocked/{organizationId}", method = RequestMethod.GET)
    public ResponseEntity<Data<List<DepartmentDto>>> blockList(
            @Parameter(description = "Id of the organization. Cannot be empty.", required = true)
            @PathVariable String organizationId,
            @Parameter(description = "Criteria of the Organization for pagination.", required = true)
            @RequestBody DepartmentCriteria criteria) {return service.blockList(criteria, organizationId);}
}
