package common.mapper;

import common.dto.CampaignItemDto;
import data.domain.CampaignItem;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ICampaignItemMapper {
    ICampaignItemMapper INSTANCE = Mappers.getMapper(ICampaignItemMapper.class);

    CampaignItemDto toRecord(CampaignItem campaignItem);
}