package common.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record CampaignDto(UserDto startedBy, LocalDateTime startedOn, Set<CampaignItemDto> items) { }