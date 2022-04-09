package uz.sh.online_queue.controller.auth;

import uz.sh.online_queue.controller.AbstractController;
import uz.sh.online_queue.dto.auth.AuthDto;
import uz.sh.online_queue.dto.auth.PasswordChangesDto;
import uz.sh.online_queue.dto.auth.SessionDto;
import uz.sh.online_queue.response.Data;
import uz.sh.online_queue.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@Tag(name = "Auth Controller", description = "Available APIs for auth controller")

public class AuthController extends AbstractController<AuthService> {

    public AuthController(AuthService service) {
        super(service);
    }

    @Operation(summary = "Getting token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully gotten",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content)})
    @RequestMapping(value = PATH + "/auth/token", method = RequestMethod.POST)
    public ResponseEntity<Data<SessionDto>> getToken(@RequestBody AuthDto dto) {
        return service.getToken(dto);
    }


    @Operation(summary = "Refreshing the token ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully refreshed",content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content)})
    @RequestMapping(value = PATH + "/auth/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<Data<SessionDto>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return service.refreshToken(request, response);
    }


    @Operation(summary = "Login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success in login",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "Not found",content = @Content)})
    @RequestMapping(value = PATH + "/auth/login", method = RequestMethod.POST)
    public ResponseEntity<Data<AuthDto>> loginByPassword(AuthDto dto) {
        return service.loginByPassword(dto.getPassword(), dto.getPassportSerial());
    }

    @Operation(summary = "Changing Password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully changed",content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission was denied due to security",content = @Content),
            @ApiResponse(responseCode = "404", description = "User was not found",content = @Content)})
    @RequestMapping(value = PATH + "/employee/change_password",method = RequestMethod.PATCH)
    public ResponseEntity<Data<Void>> changePassword(@RequestBody PasswordChangesDto dto) {
        return service.changePassword(dto);
    }

}