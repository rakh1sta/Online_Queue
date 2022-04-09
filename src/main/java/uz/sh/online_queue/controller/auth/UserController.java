package uz.sh.online_queue.controller.auth;

import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.dto.auth.user.UserCreateDto;
import uz.sh.online_queue.dto.auth.user.UserDto;
import uz.sh.online_queue.dto.auth.user.UserUpdateDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.auth.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Tag(name = "User Controller", description = "Available APIs for user controller")
public class UserController extends AbstractController<UserService> {

    public UserController(UserService service) {
        super(service);
    }

    @Operation(summary = "Creation of user ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered",content = @Content),
            @ApiResponse(responseCode = "406", description = "Conflict was occurred related to passport serial",content = @Content)})
    @RequestMapping(value = PATH + "/user", method = RequestMethod.POST)
    public ResponseEntity<Data<String>> create(@RequestBody UserCreateDto dto) {
        return service.create(dto);
    }

    @Operation(summary = "Updating a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated",content = @Content),
            @ApiResponse(responseCode = "404", description = "User by passport serial was not found",content = @Content)})
    @RequestMapping(value = PATH + "/user", method = RequestMethod.PUT)
    public ResponseEntity<Data<UserDto>> update(@RequestBody UserUpdateDto dto) {
        return service.update(dto);
    }

    @Operation(summary = "Getting user by passport serial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(responseCode = "404", description = "User by passport serial was not found",content = @Content)})
    @RequestMapping(value = PATH + "/user/{passportSerial}", method = RequestMethod.GET)
    public ResponseEntity<Data<UserDto>> get(@PathVariable String passportSerial) {
        return service.get(passportSerial);
    }

}
