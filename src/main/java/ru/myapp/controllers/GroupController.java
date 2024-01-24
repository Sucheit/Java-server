package ru.myapp.controllers;

import jakarta.validation.Valid;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myapp.aop.Loggable;
import ru.myapp.dto.*;
import ru.myapp.service.GroupService;

import java.util.List;


@EnableAspectJAutoProxy(proxyTargetClass = true)
@RestController
@ResponseBody
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @Loggable
    @GetMapping
    public List<GroupResponseDtoShort> getGroups() {
        return groupService.getAllEntities();
    }

    @Loggable
    @GetMapping("/paid")
    public List<PaidGroupResponseDtoShort> getPaidGroups() {
        return groupService.getPaidGroups();
    }

    @Loggable
    @GetMapping("/{groupId}")
    public GroupResponseDto getGroupById(@PathVariable Integer groupId) {
        return groupService.getEntityById(groupId);
    }

    @Loggable
    @GetMapping("/paid/{paidGroupId}")
    public PaidGroupResponseDto getPaidGroupById(@PathVariable Integer paidGroupId) {
        return groupService.getPaidGroupById(paidGroupId);
    }

    @Loggable
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponseDto createGroup(@RequestBody @Valid GroupRequestDto groupRequestDto) {
        return groupService.createEntity(groupRequestDto);
    }

    @Loggable
    @PostMapping("/paid")
    @ResponseStatus(HttpStatus.CREATED)
    public PaidGroupResponseDto createPaidGroup(@RequestBody @Valid PaidGroupRequestDto paidGroupRequestDto) {
        return groupService.createPaidGroup(paidGroupRequestDto);
    }

    @Loggable
    @PutMapping("/{groupId}")
    public GroupResponseDto updateGroup(@PathVariable Integer groupId,
                                        @RequestBody @Valid GroupRequestDto groupRequestDto) {
        return groupService.updateEntity(groupId, groupRequestDto);
    }

    @Loggable
    @PutMapping("/paid/{paidGroupId}")
    public PaidGroupResponseDto updatePaidGroup(@PathVariable Integer paidGroupId,
                                                @RequestBody @Valid PaidGroupRequestDto paidGroupRequestDto) {
        return groupService.updatePaidGroup(paidGroupId, paidGroupRequestDto);
    }

    @Loggable
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroupById(@PathVariable Integer groupId) {
        groupService.deleteEntityById(groupId);
        return ResponseEntity.noContent().build();
    }
}
