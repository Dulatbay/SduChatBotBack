package kz.sdu.chat.mainservice.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UsageMetadataResponse {

    @JsonProperty("input_tokens")
    private int inputTokens;

    @JsonProperty("output_tokens")
    private int outputTokens;

    @JsonProperty("total_tokens")
    private int totalTokens;

    @JsonProperty("cacheReadInputTokens")
    private int cacheReadInputTokens;

    @JsonProperty("cacheWriteInputTokens")
    private int cacheWriteInputTokens;

    @JsonProperty("cacheHitCount")
    private int cacheHitCount;

    @JsonProperty("costUsd")
    private double costUsd;
}