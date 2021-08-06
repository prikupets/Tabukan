package ru.granby.tabukan.model.business.dto.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.granby.tabukan.model.data.database.relations.game.Card;


@AllArgsConstructor
@Getter
public class CoinBalanceAndCard {
    private int coinBalance;
    private Card card;
}

