package common.mapper;

import common.dto.CampaignStopDto;
import data.domain.CampaignStop;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ICampaignStopMapper {
    ICampaignStopMapper INSTANCE = Mappers.getMapper(ICampaignStopMapper.class);

    CampaignStopDto toRecord(CampaignStop campaignStop);
}