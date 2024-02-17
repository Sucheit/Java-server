package ru.myapp.service;

import ru.myapp.dto.GroupRequestDto;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.dto.PaidGroupRequestDto;
import ru.myapp.dto.PaidGroupResponseDto;
import ru.myapp.dto.PaidGroupResponseDtoShort;
import ru.myapp.dto.Response;

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

    Response getResponse();
}
