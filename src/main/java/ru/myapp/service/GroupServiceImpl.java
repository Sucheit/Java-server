package ru.myapp.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myapp.dto.GroupRequestDto;
import ru.myapp.dto.GroupResponseDto;
import ru.myapp.dto.GroupResponseDtoShort;
import ru.myapp.dto.PaidGroupRequestDto;
import ru.myapp.dto.PaidGroupResponseDto;
import ru.myapp.dto.PaidGroupResponseDtoShort;
import ru.myapp.dto.Response;
import ru.myapp.error.NotFoundException;
import ru.myapp.mappers.GroupMapper;
import ru.myapp.model.Group;
import ru.myapp.model.PaidGroup;
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

    @Transactional(readOnly = true)
    @Override
    public List<GroupResponseDtoShort> getAllEntities() {
        return groupMapper.groupListToGroupResponseDtoShortList(groupRepository.findAll());
    }

    @Override
    public GroupResponseDto getEntityById(Integer groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group id=%s not found".formatted(groupId)));
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Override
    public GroupResponseDto createEntity(GroupRequestDto groupRequestDto) {
        Group group = new Group();
        group.setName(groupRequestDto.name());
        group.setDescription(groupRequestDto.description());
        group = groupRepository.save(group);
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Override
    public GroupResponseDto updateEntity(Integer groupId, GroupRequestDto groupRequestDto) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group id=%s not found".formatted(groupId)));
        group.setName(groupRequestDto.name());
        group.setDescription(groupRequestDto.description());
        groupRepository.save(group);
        return groupMapper.groupToGroupResponseDto(group);
    }

    @Override
    public void deleteEntityById(Integer groupId) {
        groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException("Group id=%s not found".formatted(groupId)));
        groupRepository.deleteById(groupId);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaidGroupResponseDtoShort> getPaidGroups() {
        return groupMapper.paidGroupsListToPaidGroupResponseDtoList(groupRepository.findAllPaidGroups());
    }

    @Override
    public PaidGroupResponseDto createPaidGroup(PaidGroupRequestDto paidGroupRequestDto) {
        PaidGroup paidGroup = new PaidGroup();
        paidGroup.setName(paidGroupRequestDto.name());
        paidGroup.setDescription(paidGroupRequestDto.description());
        paidGroup.setCost(paidGroupRequestDto.cost());
        paidGroup = groupRepository.save(paidGroup);
        return groupMapper.paidGroupToPaidGroupResponseDto(paidGroup);
    }

    @Override
    public PaidGroupResponseDto updatePaidGroup(Integer paidGroupId, PaidGroupRequestDto paidGroupRequestDto) {
        PaidGroup paidGroup = groupRepository.findPaidGroupById(paidGroupId)
                .orElseThrow(() -> new NotFoundException("PaidGroup id=%s not found".formatted(paidGroupId)));
        paidGroup.setName(paidGroupRequestDto.name());
        paidGroup.setDescription(paidGroupRequestDto.description());
        paidGroup.setCost(paidGroupRequestDto.cost());
        paidGroup = groupRepository.save(paidGroup);
        return groupMapper.paidGroupToPaidGroupResponseDto(paidGroup);
    }

    @Transactional(readOnly = true)
    @Override
    public PaidGroupResponseDto getPaidGroupById(Integer paidGroupId) {
        PaidGroup paidGroup = groupRepository.findPaidGroupById(paidGroupId)
                .orElseThrow(() -> new NotFoundException("PaidGroup id=%s not found".formatted(paidGroupId)));
        return groupMapper.paidGroupToPaidGroupResponseDto(paidGroup);
    }

    @Override
    public Response getResponse() {
        return groupRepository.getResponse(2, 1)
                .orElseThrow(() -> new NotFoundException("RESPONSE NOT FOUND"));
    }
}
