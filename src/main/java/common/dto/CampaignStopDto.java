package common.dto;

import java.time.LocalDateTime;

public record CampaignStopDto(CampaignDto campaign, UserDto stoppedBy, LocalDateTime stoppedOn) { }