package uz.sh.online_queue.controller.queue;

import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.criteria.queue.QueueCriteria;
import uz.sh.online_queue.dto.queue.QueueCreateDto;
import uz.sh.online_queue.dto.queue.QueueDto;
import uz.sh.online_queue.dto.queue.QueueUpdateDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.queue.QueueServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Queue Controller", description = "the Queue API")
public class QueueController extends AbstractController<QueueServiceImpl> {
    public QueueController(QueueServiceImpl service) {
        super(service);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created the Queue",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Parameters is null", content = @Content),
    })
    @Operation(summary = "create queue", description = "This method for create queue to organization by organization id")
    @RequestMapping(value = PATH + "/queue/create", method = RequestMethod.POST)
    public ResponseEntity<Data<String>> create(@RequestBody QueueCreateDto dto) {
        return service.create(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the Queue",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Queue not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Parameters is null", content = @Content),
    })
    @Operation(summary = "update queue", description = "This method for update queue to organization by organization id")
    @RequestMapping(value = PATH + "/queue/update", method = RequestMethod.PATCH)
    public ResponseEntity<Data<QueueDto>> update(@RequestBody QueueUpdateDto dto) {
        return service.update(dto);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted the Queue",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Queue not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Parameters is null", content = @Content),
    })
    @Operation(summary = "delete queue", description = "This method for delete queue to organization by organization id")
    @RequestMapping(value = PATH + "/queue/delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Data<Void>> delete(@PathVariable(name = "id") String id) {
        return service.delete(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get the Queue",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Queue not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Parameters is null", content = @Content),
    })
    @Operation(summary = "get queue", description = "This method for get queue to organization by organization id")
    @RequestMapping(value = PATH + "/queue/get/{id}", method = RequestMethod.GET)
    public ResponseEntity<Data<QueueDto>> get(@PathVariable(name = "id") String id) {
        return service.get(id);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get all the Queue",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Parameters is null", content = @Content),
    })
    @Operation(summary = "get all queue", description = "This method for get all queue to organization by organization id")
    @RequestMapping(value = PATH + "/queue/getAll", method = RequestMethod.GET)
    public ResponseEntity<Data<List<QueueDto>>> getAll() {
        return service.getAll(new QueueCriteria());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the Queue",
                    content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "500", description = "Parameters is null", content = @Content),
    })
    @Operation(summary = "get all by department ID queue", description = "This method for get all by department ID queue to organization by organization id")
    @RequestMapping(value = PATH + "/queue/getAll-by-department/{departmentId}", method = RequestMethod.GET)
    public ResponseEntity<Data<List<QueueDto>>> getAllByDepartmentId(@PathVariable(name = "departmentId") String departmentId) {
        return service.getAllByDepartmentId(departmentId, new QueueCriteria());
    }
}
