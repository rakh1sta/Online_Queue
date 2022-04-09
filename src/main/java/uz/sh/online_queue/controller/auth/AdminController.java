package uz.sh.online_queue.controller.auth;

import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.criteria.user.UserCriteria;
import uz.sh.online_queue.dto.auth.employee.*;
import uz.sh.online_queue.dto.auth.role.AdminRoleDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.auth.employee.AdminCrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Tag(name = "Admin Controller", description = "Available APIs for admin controller")

@PreAuthorize("hasAuthority('super_admin')")
public class AdminController extends AbstractController<AdminCrudService> {

    public AdminController(AdminCrudService service) {
        super(service);
    }

    @Operation(summary = "Creating an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully saved",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Conflict was occurred related to passport serial",content = @Content)})
    @RequestMapping(value = PATH + "/admin", method = RequestMethod.POST)
    public ResponseEntity<Data<String>> create(@RequestBody AdminCreateDto dto) {
        return service.create(dto);
    }

    @Operation(summary = "Updating an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated",content = @Content(schema = @Schema(implementation = EmployeeUpdateDto.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Admin was not found",content = @Content)})
    @RequestMapping(value = PATH + "/admin", method = RequestMethod.PUT)
    public ResponseEntity<Data<AdminDto>> update(@RequestBody AdminUpdateDto dto) {
        return service.update(dto);
    }

    @Operation(summary = "Deleting an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Admin was not found",content = @Content)})
    @RequestMapping(value = PATH + "/admin/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Data<Void>> delete(@Parameter(description = "id of admin to be deleted",required = true) @PathVariable String id) {
        return service.delete(id);
    }

    @Operation(summary = "Getting an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Admin was not found",content = @Content)})
    @RequestMapping(value = PATH + "/admin/{id}", method = RequestMethod.GET)
    public ResponseEntity<Data<AdminDto>> get(@Parameter(description = "id of admin to be gotten",required = true)@PathVariable String id) {
        return service.get(id);
    }

    @Operation(summary = "Getting all admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Admin was not found",content = @Content)})
    @RequestMapping(value = PATH + "/admin", method = RequestMethod.GET)
    public ResponseEntity<Data<List<AdminDto>>> getAll(@RequestBody UserCriteria criteria) {
        return service.getAll(criteria);
    }

    @Operation(summary = "Getting all blocked admins")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = EmployeeDto.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Admins were not found",content = @Content)})
    @RequestMapping(value = PATH + "/admin/blocked", method = RequestMethod.GET)
    public ResponseEntity<Data<List<AdminDto>>> getAllBlocked(@RequestBody UserCriteria criteria) {
        return service.getAllBlocked(criteria);
    }

    @Operation(summary = "Blocking an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully blocked",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Admin was not found",content = @Content)})
    @RequestMapping(value = PATH + "/admin/block/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> block(@Parameter(description = "id of admin to be blocked",required = true)@PathVariable String id) {
        return service.block(id);
    }

    @Operation(summary = "Unblocking an admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully unblocked",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Admin was not found",content = @Content)})
    @RequestMapping(value = PATH + "/admin/unblock/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> unblock(@Parameter(description = "id of admin to be unblocked",required = true)@PathVariable String id) {
        return service.unblock(id);
    }

    @Operation(summary = "Getting roles")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = AdminRoleDto.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content)})
    @RequestMapping(value = PATH + "/admin/roles", method = RequestMethod.GET)
    public ResponseEntity<Data<List<String>>> getRoles() {
        AdminRoleDto adminRoleDto = new AdminRoleDto();
        return new ResponseEntity<>(new Data<>(adminRoleDto.getRoles()), HttpStatus.OK);
    }

    @Operation(summary = "Attaching role ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully attached",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Admin was not found",content = @Content)})
    @RequestMapping(value = PATH + "/admin/attach_role/{id}", method = RequestMethod.POST)
    public ResponseEntity<Data<Void>> addRole(@Parameter(description = "id of admin to be promoted",required = true)@PathVariable String id, String role) {
        return service.addRole(id, role);
    }

}
