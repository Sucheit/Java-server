package ru.myapp.service;

import ru.myapp.dto.GroupRequestDto;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.dto.PaidGroupResponseDtoShort;

import java.util.List;

public interface GroupService {

    List<GroupResponseDtoShort> getGroups();

    GroupResponseDto getGroupById(Integer groupId);

    GroupResponseDto createGroup(GroupRequestDto groupRequestDto);

    GroupResponseDto updateGroup(Integer groupId, GroupRequestDto groupRequestDto);

    void deleteGroup(Integer groupId);

    List<PaidGroupResponseDtoShort> getPaidGroups();
}
