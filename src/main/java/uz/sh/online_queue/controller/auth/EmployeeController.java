package uz.sh.online_queue.controller.auth;

import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.criteria.user.UserCriteria;
import uz.sh.online_queue.dto.auth.employee.EmployeeCreateDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeDto;
import uz.sh.online_queue.dto.auth.employee.EmployeeUpdateDto;
import uz.sh.online_queue.dto.auth.role.EmployeeRoleDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.auth.employee.EmployeeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Employee Controller", description = "Available APIs for employee controller")
//@PreAuthorize("hasRole('admin')")
public class EmployeeController extends AbstractController<EmployeeServiceImpl> {

    public EmployeeController(EmployeeServiceImpl service) {
        super(service);
    }

    @Operation(summary = "Creating an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully saved",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Conflict was occurred related to passport serial",content = @Content)})
    @RequestMapping(value = PATH + "/employee", method = RequestMethod.POST)
    public ResponseEntity<Data<String>> create(@RequestBody EmployeeCreateDto dto) {
        return service.create(dto);
    }

    @Operation(summary = "Updating an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee was not found",content = @Content)})
    @RequestMapping(value = PATH + "/employee", method = RequestMethod.PUT)
    public ResponseEntity<Data<EmployeeDto>> update(@RequestBody EmployeeUpdateDto dto) {
        return service.update(dto);
    }

    @Operation(summary = "Deleting an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee was not found",content = @Content)})
    @RequestMapping(value = PATH + "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Data<Void>> delete(@PathVariable String id) {
        return service.delete(id);
    }

    @Operation(summary = "Getting an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee was not found",content = @Content)})
    @RequestMapping(value = PATH + "/employee/{id}", method = RequestMethod.GET)
    public ResponseEntity<Data<EmployeeDto>> get(@PathVariable String id) {
        return service.get(id);
    }


    @Operation(summary = "Blocking an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully blocked",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee was not found",content = @Content)})
    @RequestMapping(value = PATH + "/employee/block/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> block(@PathVariable String id) {
        return service.block(id);
    }

    @Operation(summary = "Unblocking an employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully unblocked",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee was not found",content = @Content)})
    @RequestMapping(value = PATH + "/employee/unblock/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> unblock(@PathVariable String id) {
        return service.unblock(id);
    }


    @RequestMapping(value = PATH + "/employee/org/{orgId}",method = RequestMethod.GET)
    public ResponseEntity<Data<List<EmployeeDto>>> getAllByOrgId(@PathVariable String orgId,@RequestBody UserCriteria criteria) {
        return service.getAllByOrgId(criteria,orgId);
    }

    @Operation(summary = "Getting all blocked employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee was not found",content = @Content)})
    @RequestMapping(value = PATH + "/employee/blocked/{orgId}",method = RequestMethod.GET)
    public ResponseEntity<Data<List<EmployeeDto>>> getAllBlockedEmployees(@PathVariable String orgId,@RequestBody UserCriteria criteria) {
        return service.getAllBlockedEmployees(criteria,orgId);
    }


    @Operation(summary = "Getting roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = EmployeeRoleDto.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content)})
    @RequestMapping(value = PATH + "/employee/roles",method = RequestMethod.GET)
    public ResponseEntity<Data<List<String>>> getRoles() {
        EmployeeRoleDto employeeRoleDto = new EmployeeRoleDto();
        return new ResponseEntity<>(new Data<>(employeeRoleDto.getRoles()), HttpStatus.OK);
    }

    @Operation(summary = "Attaching role ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully attached",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee was not found",content = @Content)})
    @RequestMapping(value = PATH + "/employee/attach_role/{id}",method = RequestMethod.POST)
    public ResponseEntity<Data<Void>> addRole (@PathVariable String id, String role) {
        return service.addRole(id,role);
    }
}

