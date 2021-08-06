package ru.granby.tabukan.model.business.dto.game;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.granby.tabukan.model.data.database.relations.game.Card;

@AllArgsConstructor
@Data
public class CardAndWordLettersAndSelectLetters {
    private Card card;
    private List<Character> wordLetters;
    private List<Character> selectLetters;
}
