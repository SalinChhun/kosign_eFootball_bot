package com.salin.kosign_eFootball_bot.payload.gpt.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String type;
    private String text;

    @JsonProperty("image_url")
    private ImageUrl imageUrl;
}

