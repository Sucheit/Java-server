package ru.myapp.controllers;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.myapp.aop.Loggable;
import ru.myapp.dto.GroupRequestDto;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.dto.PaidGroupResponseDtoShort;
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
        return groupService.getGroups();
    }

    @Loggable
    @GetMapping("/paid")
    public List<PaidGroupResponseDtoShort> getPaidGroups() {
        return groupService.getPaidGroups();
    }

    @Loggable
    @GetMapping("/{groupId}")
    public GroupResponseDto getGroupById(@PathVariable Integer groupId) {
        return groupService.getGroupById(groupId);
    }

    @Loggable
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponseDto creatGroup(@RequestBody GroupRequestDto groupRequestDto) {
        return groupService.createGroup(groupRequestDto);
    }

    @Loggable
    @PutMapping("/{groupId}")
    public GroupResponseDto updateGroup(@PathVariable Integer groupId,
                                        @RequestBody GroupRequestDto groupRequestDto) {
        return groupService.updateGroup(groupId, groupRequestDto);
    }

    @Loggable
    @DeleteMapping("/{groupId}")
    public ResponseEntity<?> deleteGroupById(@PathVariable Integer groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.noContent().build();
    }
}
