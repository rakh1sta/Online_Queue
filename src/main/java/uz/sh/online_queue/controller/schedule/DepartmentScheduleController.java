package uz.sh.online_queue.controller.schedule;

import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.criteria.schedule.DepartmentScheduleCriteria;
import uz.sh.online_queue.dto.department.DepartmentDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepSchedule;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleCreateDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleDto;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepartmentScheduleUpdateDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.schedule.departmentSchedule.DepartmentScheduleServiceImpl;
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
@Tag(name = "Department Schedule Controller", description = "the Department Schedule API")
public class DepartmentScheduleController extends AbstractController<DepartmentScheduleServiceImpl> {

    @Autowired
    public DepartmentScheduleController(DepartmentScheduleServiceImpl service) {
        super(service);
    }

    @Operation(summary = "create department schedule ", description = "This method for create department schedule to department")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully create the Department schedule",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Department schedule is blocked", content = @Content),
            @ApiResponse(responseCode = "403", description = "Department blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found Department for create department schedule", content = @Content)})
    @RequestMapping(value = PATH + "/department-schedule/{departmentId}", method = RequestMethod.POST)
    public ResponseEntity<Data<String>> create(
            @Parameter(description = "dto for create department schedule. Cannot be empty.", required = true)
            @Valid @RequestBody DepartmentScheduleCreateDto dto,
            @Parameter(description = "id of the department for department schedule. Cannot be empty.", required = true)
            @PathVariable String departmentId) {return service.create(dto, departmentId);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get list of department schedule",
                    content = @Content(schema = @Schema(implementation = DepSchedule.class))),
            @ApiResponse(responseCode = "400", description = "Department not exist for organization yet ", content = @Content),
            @ApiResponse(responseCode = "403", description = "Organization blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)})
    @Operation(summary = "list of department", description = "This method for get department list of a organization by organization id")
    @RequestMapping(value = PATH + "/doctors-schedules/{orgId}", method = RequestMethod.GET)
    public ResponseEntity<Data<List<DepSchedule>>> getAll(
            @Parameter(description = "Id of the organization. Cannot be empty.", required = true)
            @PathVariable String orgId,
            @Parameter(description = "Criteria of the Organization for pagination.", required = true)
            @RequestBody DepartmentScheduleCriteria criteria) {return service.getAll(criteria,orgId);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful get department", content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Department schedule not found", content = @Content)})
    @Operation(summary = "get department schedule ", description = "This method for get department schedule by id")
    @RequestMapping(value = PATH + "/department-schedule/{departmentId}", method = RequestMethod.GET)
    public ResponseEntity<Data<DepartmentScheduleDto>> get(
            @Parameter(description = "id of the department schedule for get. Cannot be empty.", required = true)
            @PathVariable String departmentId) {return service.get(departmentId);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Department", content = @Content),
            @ApiResponse(responseCode = "404", description = "Department schedule not found", content = @Content)})
    @Operation(summary = "delete  department schedule", description = "This method for delete department schedule by id")
    @RequestMapping(value = PATH + "/department-schedule/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Data<Void>> delete(
            @Parameter(description = "id of the department schedule for delete. Cannot be empty.", required = true)
            @PathVariable String id) {return service.delete(id);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated Department",
                    content = @Content(schema = @Schema(implementation = DepartmentDto.class))),
            @ApiResponse(responseCode = "404", description = "Department schedule not found", content = @Content)})
    @Operation(summary = "update department schedule", description = "This method for update department schedule")
    @RequestMapping(value = PATH + "/department-schedule", method = RequestMethod.PATCH)
    public ResponseEntity<Data<DepartmentScheduleDto>> update(
            @Parameter(description = "dto of department for update. Cannot be empty.", required = true)
            @Valid @RequestBody DepartmentScheduleUpdateDto dto) {return service.update(dto);}
}
