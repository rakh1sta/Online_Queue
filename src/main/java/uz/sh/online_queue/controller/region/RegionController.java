package uz.sh.online_queue.controller.region;

import uz.sh.online_queue.enums.Regions;
import uz.sh.online_queue.response.Data;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Region Controller", description = "the Region API")
public class RegionController {

    private final String PATH = "/api/v1";

    @Operation(summary = "list of region", description = "This method for get region list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get list of region", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Region not found", content = @Content)})
    @RequestMapping(value = PATH + "/region/list", method = RequestMethod.GET)
    public ResponseEntity<Data<List<String>>> getAll() {
        return new ResponseEntity<>(new Data<>(Regions.getList()), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful get region by name", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Region not found", content = @Content)})
    @Operation(summary = "get region ", description = "This method for get region by name")
    @RequestMapping(value = PATH + "/region/{regionName}", method = RequestMethod.GET)
    public ResponseEntity<Data<String>> get(
            @Parameter(description = "Name of the region for get. Cannot be empty.", required = true)
            @PathVariable String regionName) {
        return new ResponseEntity<>(new Data<>(Regions.getRegion(regionName)), HttpStatus.OK);
    }

}
