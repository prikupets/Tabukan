package ru.granby.tabukan.ui.multiplayer.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FirstCardShownEvent {
    private int cardIndex;
}
