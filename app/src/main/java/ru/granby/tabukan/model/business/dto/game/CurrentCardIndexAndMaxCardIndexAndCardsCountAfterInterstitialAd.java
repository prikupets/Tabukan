package ru.granby.tabukan.model.business.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CurrentCardIndexAndMaxCardIndexAndCardsCountAfterInterstitialAd {
    private int currentCardIndex;
    private int maxCardIndex;
    private int cardsCountAfterInterstitialAd;
}
