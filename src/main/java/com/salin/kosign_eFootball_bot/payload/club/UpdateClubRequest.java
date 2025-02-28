package com.salin.kosign_eFootball_bot.payload.club;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpdateClubRequest {

    @NotNull
    private Long clubId;

    @NotBlank
    private String clubName;

    private String clubLogo;

    @NotNull
    private List<Long> seasonIds;

}
