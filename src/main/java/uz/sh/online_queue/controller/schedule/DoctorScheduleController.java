package uz.sh.online_queue.controller.schedule;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.criteria.schedule.DoctorScheduleCriteria;
import uz.sh.online_queue.dto.schedule.departmentSchedule.DepSchedule;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DocSchedule;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleCreateDto;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleDto;
import uz.sh.online_queue.dto.schedule.doctorSchedule.DoctorScheduleUpdateDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.schedule.doctorSchedule.DoctorScheduleServiceImpl;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Doctor Schedule Controller", description = "the Doctor Schedule API")
public class DoctorScheduleController extends AbstractController<DoctorScheduleServiceImpl> {
    public DoctorScheduleController(DoctorScheduleServiceImpl service) {
        super(service);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully create the Doctor schedule",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "list of DoctorScheduleCreateDto is empty", content = @Content),
            @ApiResponse(responseCode = "403", description = "Doctor blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found Doctor for create department schedule", content = @Content)})
    @Operation(summary = "create doctor schedule ", description = "This method for create doctor schedule to department")
    @RequestMapping(value = PATH + "/doctor-schedule/{doctorId}", method = RequestMethod.POST)
    public ResponseEntity<Data<Map<LocalDate,String>>> create(
            @Parameter(description = "dto for create doctor schedule. Cannot be empty.", required = true)
            @Valid @RequestBody List<DoctorScheduleCreateDto> dto,
            @Parameter(description = "id of the Doctor for doctor schedule. Cannot be empty.", required = true)
            @PathVariable String doctorId) {return service.create(dto, doctorId);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get list of doctor schedule",
                    content = @Content(schema = @Schema(implementation = DepSchedule.class))),
            @ApiResponse(responseCode = "400", description = "Doctor not exist for organization yet ", content = @Content),
            @ApiResponse(responseCode = "400", description = "Organization blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)})
    @Operation(summary = "list of doctor schedule", description = "This method for get doctor schedule list of a organization by organization id")
    @RequestMapping(value = PATH + "/doctor-schedules/{organizationId}", method = RequestMethod.GET)
    public ResponseEntity<Data<List<DocSchedule>>> getAll(
            @Parameter(description = "Id of the organization. Cannot be empty.", required = true)
            @PathVariable String organizationId,
            @Parameter(description = "Criteria of the Organization for pagination.", required = true)
            @RequestBody DoctorScheduleCriteria criteria) {return service.getAll(criteria,organizationId);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful get doctor schedules",
                    content = @Content(schema = @Schema(implementation = DoctorScheduleDto.class))),
            @ApiResponse(responseCode = "404", description = "Doctor schedule not found", content = @Content)})
    @Operation(summary = "get doctor schedule ", description = "This method for get doctor schedule by id")
    @RequestMapping(value = PATH + "/doctor-schedule/{doctorId}", method = RequestMethod.GET)
    public ResponseEntity<Data<DoctorScheduleDto>> get(
            @Parameter(description = "id of the doctor schedule for get. Cannot be empty.", required = true)
            @PathVariable String doctorId) {return service.get(doctorId);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Doctor schedule", content = @Content),
            @ApiResponse(responseCode = "404", description = "Doctor schedule not found", content = @Content)})
    @Operation(summary = "delete  doctor schedule", description = "This method for delete doctor schedule by id")
    @RequestMapping(value = PATH + "/doctor-schedule/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Data<Void>> delete(
            @Parameter(description = "id of the doctor schedule for delete. Cannot be empty.", required = true)
            @PathVariable String id) {return service.delete(id);}


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated Doctor schedule",
                    content = @Content(schema = @Schema(implementation = DoctorScheduleDto.class))),
            @ApiResponse(responseCode = "404", description = "Doctor schedule not found", content = @Content)})
    @Operation(summary = "update doctor schedule", description = "This method for update doctor schedule")
    @RequestMapping(value = PATH + "/doctor-schedule", method = RequestMethod.PATCH)
    public ResponseEntity<Data<DoctorScheduleDto>> update(
            @Parameter(description = "dto of doctor schedule for update. Cannot be empty.", required = true)
            @Valid @RequestBody DoctorScheduleUpdateDto dto) {return service.update(dto);}

}
