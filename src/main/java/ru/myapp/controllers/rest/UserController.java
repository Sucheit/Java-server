package ru.myapp.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.request.UserRequestDto;
import ru.myapp.dto.response.UserResponseDto;
import ru.myapp.dto.response.UserResponseDtoShort;
import ru.myapp.error.ApiError;
import ru.myapp.service.UserService;
import ru.myapp.utils.Utils;

@RestController
@ResponseBody
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @Operation(summary = "Get users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found users",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = UserResponseDtoShort.class))})})
  @GetMapping
  public List<UserResponseDtoShort> getUsers(
      @RequestParam(defaultValue = "0") @PositiveOrZero int from,
      @RequestParam(defaultValue = "10") @Positive int size,
      @RequestParam(defaultValue = "id") String sortBy,
      @RequestParam(defaultValue = "true") boolean asc
  ) {
    return userService.getAllEntities(Utils.getPageRequest(from, size, sortBy, asc));
  }

  @Operation(summary = "Get user by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found user by Id",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserResponseDto.class))}),
      @ApiResponse(responseCode = "404", description = "User not request",
          content = @Content(schema = @Schema(hidden = true)))})
  @GetMapping("/{userId}")
  public UserResponseDto getUserById(@PathVariable Integer userId) {
    return userService.getEntityById(userId);
  }

  @Operation(summary = "User creation")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "User created",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "UserRequestDto not valid",
          content = @Content(schema = @Schema(hidden = true)))})
  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public String createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
    userService.sendToKafka(userRequestDto);
    return "UserRequestDto sent to kafka!";
  }

  @Operation(summary = "User update")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User updated",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "UserRequestDto not valid",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(schema = @Schema(hidden = true)))})
  @PutMapping("/{userId}")
  public UserResponseDto updateUser(@RequestBody @Valid UserRequestDto userRequestDto,
      @PathVariable Integer userId) {
    return userService.updateEntity(userId, userRequestDto);
  }

  @Operation(summary = "User deleting")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "User deleted"),
      @ApiResponse(responseCode = "404", description = "User not found",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))})
  @DeleteMapping("/{userId}")
  public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
    userService.deleteEntityById(userId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Adding user to group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User added to group",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "User or group not found",
          content = @Content(schema = @Schema(hidden = true)))})
  @PostMapping("/{userId}/groups/{groupId}")
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponseDto addUserToGroup(@PathVariable Integer userId,
      @PathVariable Integer groupId) {
    return userService.addUserToGroup(userId, groupId);
  }

  @Operation(summary = "Deleting user from group")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "User deleted from group",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = UserResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "User or group not found",
          content = @Content(schema = @Schema(hidden = true)))})
  @DeleteMapping("/{userId}/groups/{groupId}")
  public UserResponseDto deleteUserToGroup(@PathVariable Integer userId,
      @PathVariable Integer groupId) {
    return userService.deleteUserFromGroup(userId, groupId);
  }

  @GetMapping("/example")
  public List<UserResponseDtoShort> getAllUsers(
      @RequestBody UserRequestDto userRequestDto,
      @RequestParam(defaultValue = "0") @PositiveOrZero int from,
      @RequestParam(defaultValue = "10") @Positive int size
  ) {
    return userService.getAllUsersByExample(userRequestDto, Utils.getPageRequest(from, size));
  }

  @GetMapping("/qdsl")
  public List<UserResponseDtoShort> getAllUsers(
      @RequestBody UserRequestDto userRequestDto
  ) {
    return userService.getAllUsersByQueryDSL(userRequestDto);
  }

  @GetMapping("/criteria-api")
  public List<UserResponseDtoShort> getAllUsersByCriteriaAPI(
      @RequestBody UserRequestDto userRequestDto
  ) {
    return userService.getAllUsersByCriteriaAPI(userRequestDto);
  }
}
