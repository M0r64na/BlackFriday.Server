package common.mapper;

import common.dto.CampaignDto;
import data.domain.Campaign;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ICampaignMapper {
    ICampaignMapper INSTANCE = Mappers.getMapper(ICampaignMapper.class);

    CampaignDto toRecord(Campaign campaign);
}