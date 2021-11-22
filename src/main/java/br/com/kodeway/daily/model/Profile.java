package br.com.kodeway.daily.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class Profile {

    private final UUID uniqueId;

    private Map<String, Long> redeemedRewards;
    private int totalRedeemedRewards;

    public void incrementTotalRedeemed() {
        this.totalRedeemedRewards++;
    }

}
