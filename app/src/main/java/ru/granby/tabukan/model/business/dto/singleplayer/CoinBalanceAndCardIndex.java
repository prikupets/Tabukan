package ru.granby.tabukan.model.business.dto.singleplayer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.granby.tabukan.exception.IncorrectCardIndexException;
import ru.granby.tabukan.exception.NotEnoughBalanceException;

@AllArgsConstructor
@Getter
public class CoinBalanceAndCardIndex {
    private int coinBalance;
    private int cardIndex;
}
