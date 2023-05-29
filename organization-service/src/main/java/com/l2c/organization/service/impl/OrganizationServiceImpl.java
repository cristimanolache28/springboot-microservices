package com.l2c.organization.service.impl;

import com.l2c.organization.dto.OrganizationDto;
import com.l2c.organization.entity.Organization;
import com.l2c.organization.mapper.OrganizationMapper;
import com.l2c.organization.repository.OrganizationRepository;
import com.l2c.organization.service.OrganizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepository organizationRepository;

    @Override
    public OrganizationDto saveOrganization(OrganizationDto organizationDto) {
       Organization organization = OrganizationMapper.mapToOrganization(organizationDto);
       Organization savedOrganization = organizationRepository.save(organization);
       return OrganizationMapper.mapToOrganizationDto(savedOrganization);
    }

    @Override
    public OrganizationDto getOrganizationByCode(String organizationCode) {
        Organization organization = organizationRepository.findByOrganizationCode(organizationCode);
        return OrganizationMapper.mapToOrganizationDto(organization);
    }
}
