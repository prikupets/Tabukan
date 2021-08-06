package ru.granby.tabukan.model.business.dto.game;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class CardsCountAndCardIndex {
    private int cardsCount;
    private int cardIndex;
}
