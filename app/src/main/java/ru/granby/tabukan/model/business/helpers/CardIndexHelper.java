package ru.granby.tabukan.model.business.helpers;

import ru.granby.tabukan.exception.IncorrectCardIndexException;

public class CardIndexHelper {
    public static int changeIndexTo(int newCardIndex, int cardsCount) {
        if (newCardIndex > cardsCount) {
            throw new IncorrectCardIndexException("newCardIndex can't be greater than cardsCount");
        }
        return newCardIndex;
    }
}
