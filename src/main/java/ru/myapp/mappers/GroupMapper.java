package ru.myapp.mappers;

import org.mapstruct.Mapper;
import ru.myapp.dto.response.GroupResponseDto;
import ru.myapp.dto.response.GroupResponseDtoShort;
import ru.myapp.dto.response.PaidGroupResponseDto;
import ru.myapp.dto.response.PaidGroupResponseDtoShort;
import ru.myapp.persistence.model.Group;
import ru.myapp.persistence.model.PaidGroup;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    GroupResponseDto groupToGroupResponseDto(Group group);

    GroupResponseDtoShort groupToGroupResponseDtoShort(Group group);

    PaidGroupResponseDtoShort paidGroupToPaidGroupResponseDtoShort(PaidGroup paidGroup);

    PaidGroupResponseDto paidGroupToPaidGroupResponseDto(PaidGroup paidGroup);

    List<GroupResponseDtoShort> groupListToGroupResponseDtoShortList(List<Group> groups);

    List<PaidGroupResponseDtoShort> paidGroupsListToPaidGroupResponseDtoList(List<PaidGroup> allPaidGroups);
}
