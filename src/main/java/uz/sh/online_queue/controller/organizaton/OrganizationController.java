package uz.sh.online_queue.controller.organizaton;

import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.criteria.organization.OrganizationCriteria;
import uz.sh.online_queue.dto.organization.OrganizationCreateDto;
import uz.sh.online_queue.dto.organization.OrganizationDto;
import uz.sh.online_queue.dto.organization.OrganizationUpdateDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.organization.OrganizationServiceImpl;
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
@Tag(name = "Organization Controller", description = "the Organization API")
public class OrganizationController extends AbstractController<OrganizationServiceImpl> {

    @Autowired
    public OrganizationController(OrganizationServiceImpl service) {
        super(service);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully create the Organization",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Already exist organization", content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found Region for create organization", content = @Content)})
    @Operation(summary = "create organization ", description = "This method for create organization to region by region name")
    @RequestMapping(value = PATH + "/organization/{regionName}", method = RequestMethod.POST)
    public ResponseEntity<Data<String>> create(
            @Parameter(description = "dto for create organization. Cannot be empty.", required = true)
            @Valid @RequestBody OrganizationCreateDto organizationCreateDto,
            @Parameter(description = "name of the region for organization. Cannot be empty.", required = true)
            @PathVariable String regionName) {
        return service.create(organizationCreateDto, regionName);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get list of Organization",
                    content = @Content(schema = @Schema(implementation = OrganizationDto.class))),
            @ApiResponse(responseCode = "400", description = "Organization not exist for this region yet ", content = @Content),
            @ApiResponse(responseCode = "404", description = "Region not found", content = @Content)})
    @Operation(summary = "list of organization", description = "This method for get organization  block list of a region by region name")
    @RequestMapping(value = PATH + "/organization/list/{regionName}", method = RequestMethod.GET)
    public ResponseEntity<Data<List<OrganizationDto>>> getAll(OrganizationCriteria criteria) {
        return service.getAll(criteria);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get blocked list of Organization",
                    content = @Content(schema = @Schema(implementation = OrganizationDto.class))),
            @ApiResponse(responseCode = "400", description = "Blocked Organization not have for this region yet ", content = @Content),
            @ApiResponse(responseCode = "404", description = "Region not found", content = @Content)})
    @Operation(summary = "block list of organization of a region", description = "This method for unblock organization by id")
    @RequestMapping(value = PATH + "/organizations/blocked/{regionName}", method = RequestMethod.GET)
    public ResponseEntity<Data<List<OrganizationDto>>> blockList(OrganizationCriteria criteria) {
        return service.blockList(criteria);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful get organization",
                    content = @Content(schema = @Schema(implementation = OrganizationDto.class))),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)})
    @Operation(summary = "get organization ", description = "This method for get organization by id")
    @RequestMapping(value = PATH + "/organization/{id}", method = RequestMethod.GET)
    public ResponseEntity<Data<OrganizationDto>> get(
            @Parameter(description = "id of the organization for get. Cannot be empty.", required = true)
            @PathVariable String id) {
        return service.get(id);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated Organization",
                    content = @Content(schema = @Schema(implementation = OrganizationDto.class))),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)})
    @Operation(summary = "update organization ", description = "This method for update organization")
    @RequestMapping(value = PATH + "/organization", method = RequestMethod.PATCH)
    public ResponseEntity<Data<OrganizationDto>> update(
            @Parameter(description = "dto of organization for update. Cannot be empty.", required = true)
            @Valid @RequestBody OrganizationUpdateDto updateDto) {
        return service.update(updateDto);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully blocked Organization", content = @Content),
            @ApiResponse(responseCode = "400", description = "Organization already blocked", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found by id", content = @Content)})
    @Operation(summary = "block organization", description = "This method for block organization by id")
    @RequestMapping(value = PATH + "/organization/block/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> block(
            @Parameter(description = "id of organization for block. Cannot be empty.", required = true)
            @PathVariable String id) {
        return service.block(id);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully unblocked Organization", content = @Content),
            @ApiResponse(responseCode = "400", description = "Organization was unblock", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)})
    @Operation(summary = "unblock organization", description = "This method for unblock organization by id")
    @RequestMapping(value = PATH + "/organization/unblock/{id}", method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> unblock(
            @Parameter(description = "id of organization for unblock. Cannot be empty.", required = true)
            @PathVariable String id) {
        return service.unblock(id);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted Organization", content = @Content),
            @ApiResponse(responseCode = "404", description = "Organization not found", content = @Content)})
    @Operation(summary = "delete  organization", description = "This method for delete organization by id")
    @RequestMapping(value = PATH + "/organization/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Data<Void>> delete(
            @Parameter(description = "id of organization for delete. Cannot be empty.", required = true)
            @PathVariable String id) {
        return service.delete(id);
    }

}