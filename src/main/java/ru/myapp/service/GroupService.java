package ru.myapp.service;

import ru.myapp.dto.request.GroupRequestDto;
import ru.myapp.dto.request.PaidGroupRequestDto;
import ru.myapp.dto.response.GroupResponseDto;
import ru.myapp.dto.response.GroupResponseDtoShort;
import ru.myapp.dto.response.PaidGroupResponseDto;
import ru.myapp.dto.response.PaidGroupResponseDtoShort;

import java.util.List;

public interface GroupService extends CrudService<Integer, GroupRequestDto, GroupResponseDto, GroupResponseDtoShort> {

    List<GroupResponseDtoShort> getAllEntities();

    GroupResponseDto getEntityById(Integer groupId);

    GroupResponseDto createEntity(GroupRequestDto groupRequestDto);

    GroupResponseDto updateEntity(Integer groupId, GroupRequestDto groupRequestDto);

    void deleteEntityById(Integer groupId);

    List<PaidGroupResponseDtoShort> getPaidGroups();

    PaidGroupResponseDto createPaidGroup(PaidGroupRequestDto paidGroupRequestDto);

    PaidGroupResponseDto updatePaidGroup(Integer paidGroupId, PaidGroupRequestDto paidGroupRequestDto);

    PaidGroupResponseDto getPaidGroupById(Integer paidGroupId);
}
