package ru.granby.tabukan.model.business.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardIndexAndCardsCountAfterInterstitialAd {
    private int cardIndex;
    private int cardsCountAfterInterstitialAd;
}
