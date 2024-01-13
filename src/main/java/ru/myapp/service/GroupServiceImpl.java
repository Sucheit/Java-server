package ru.myapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.dto.GroupRequestDto;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.dto.PaidGroupResponseDtoShort;
import ru.myapp.error.NotFoundException;
import ru.myapp.mappers.GroupMapper;
import ru.myapp.model.Group;
import ru.myapp.repository.GroupRepository;

import java.util.List;

@Service
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    private final GroupMapper groupMapper;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<GroupResponseDtoShort> getGroups() {
        return groupMapper.groupListToGroupResponseDtoShortList(groupRepository.findAll());
    }

    @Override
    public GroupResponseDto getGroupById(Integer groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        return groupMapper.groupToGroupResponseDto(group);
    }


    @Override
    public GroupResponseDto createGroup(GroupRequestDto groupRequestDto) {
        Group group = new Group();
        group.setName(groupRequestDto.name());
        group.setDescription(groupRequestDto.description());
        group = groupRepository.save(group);
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Transactional
    @Override
    public GroupResponseDto updateGroup(Integer groupId, GroupRequestDto groupRequestDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        group.setName(groupRequestDto.name());
        group.setDescription(groupRequestDto.description());
        groupRepository.save(group);
        return groupMapper.groupToGroupResponseDto(group);
    }


    @Override
    public void deleteGroup(Integer groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(String.format("Group id=%s not found", groupId)));
        groupRepository.deleteById(groupId);
    }

    @Override
    public List<PaidGroupResponseDtoShort> getPaidGroups() {
        return groupMapper.paidGroupsListToPaidGroupResponseDtoList(groupRepository.findAllPaidGroups());
    }
}
