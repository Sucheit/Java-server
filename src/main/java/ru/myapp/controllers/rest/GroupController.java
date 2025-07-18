package ru.myapp.controllers.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.dto.request.GroupRequestDto;
import ru.myapp.dto.request.PaidGroupRequestDto;
import ru.myapp.dto.response.GroupResponseDto;
import ru.myapp.dto.response.GroupResponseDtoShort;
import ru.myapp.dto.response.PaidGroupResponseDto;
import ru.myapp.dto.response.PaidGroupResponseDtoShort;
import ru.myapp.error.ApiError;
import ru.myapp.service.GroupService;


@RestController
@ResponseBody
@RequestMapping("/groups")
public class GroupController {

  private final GroupService groupService;

  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }


  @Operation(summary = "Get all group(paid and not)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found groups",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = GroupResponseDtoShort.class))})})
  @GetMapping
  public List<GroupResponseDtoShort> getGroups() {
    return groupService.getAllEntities();
  }

  @Operation(summary = "Get paid groups)")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found paid groups",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PaidGroupResponseDtoShort.class))})})
  @GetMapping("/paid")
  public List<PaidGroupResponseDtoShort> getPaidGroups() {
    return groupService.getPaidGroups();
  }

  @Operation(summary = "Get group by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found group by Id",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = GroupResponseDto.class))}),
      @ApiResponse(responseCode = "404", description = "User not request",
          content = @Content(schema = @Schema(hidden = true)))})
  @GetMapping("/{groupId}")
  public GroupResponseDto getGroupById(@PathVariable Integer groupId) {
    return groupService.getEntityById(groupId);
  }

  @Operation(summary = "Get paid group by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Found group by Id",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PaidGroupResponseDto.class))}),
      @ApiResponse(responseCode = "404", description = "Paid group not founrd",
          content = @Content(schema = @Schema(hidden = true)))})
  @GetMapping("/paid/{paidGroupId}")
  public PaidGroupResponseDto getPaidGroupById(@PathVariable Integer paidGroupId) {
    return groupService.getPaidGroupById(paidGroupId);
  }

  @Operation(summary = "Group creation")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Group created",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = GroupResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "GroupRequestDto not valid",
          content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = ApiError.class)))})
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public GroupResponseDto createGroup(@RequestBody @Valid GroupRequestDto groupRequestDto) {
    return groupService.createEntity(groupRequestDto);
  }

  @Operation(summary = "Paid group creation")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Paid group created",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PaidGroupResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "PaidGroupRequestDto not valid",
          content = @Content(schema = @Schema(hidden = true)))})
  @PostMapping("/paid")
  @ResponseStatus(HttpStatus.CREATED)
  public PaidGroupResponseDto createPaidGroup(
      @RequestBody @Valid PaidGroupRequestDto paidGroupRequestDto) {
    return groupService.createPaidGroup(paidGroupRequestDto);
  }


  @Operation(summary = "Group update")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Group updated",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = GroupResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "GroupRequestDto not valid",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "404", description = "Group not found",
          content = @Content(schema = @Schema(hidden = true)))})
  @PutMapping("/{groupId}")
  public GroupResponseDto updateGroup(@PathVariable Integer groupId,
      @RequestBody @Valid GroupRequestDto groupRequestDto) {
    return groupService.updateEntity(groupId, groupRequestDto);
  }

  @Operation(summary = "Paid group update")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Paid group updated",
          content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
              schema = @Schema(implementation = PaidGroupResponseDto.class))}),
      @ApiResponse(responseCode = "400", description = "PaidGroupRequestDto not valid",
          content = @Content(schema = @Schema(hidden = true))),
      @ApiResponse(responseCode = "404", description = "Group not found",
          content = @Content(schema = @Schema(hidden = true)))})
  @PutMapping("/paid/{paidGroupId}")
  public PaidGroupResponseDto updatePaidGroup(@PathVariable Integer paidGroupId,
      @RequestBody @Valid PaidGroupRequestDto paidGroupRequestDto) {
    return groupService.updatePaidGroup(paidGroupId, paidGroupRequestDto);
  }

  @Operation(summary = "Delete group by Id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Delete group by Id"),
      @ApiResponse(responseCode = "404", description = "Group not request",
          content = @Content(schema = @Schema(hidden = true)))})
  @DeleteMapping("/{groupId}")
  public ResponseEntity<?> deleteGroupById(@PathVariable Integer groupId) {
    groupService.deleteEntityById(groupId);
    return ResponseEntity.noContent().build();
  }
}
