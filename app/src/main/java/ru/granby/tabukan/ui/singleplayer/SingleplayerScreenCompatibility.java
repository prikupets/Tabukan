package ru.granby.tabukan.ui.singleplayer;

import ru.granby.tabukan.databinding.SingleplayerActivityBinding;
import ru.granby.tabukan.ui.base.screencompatibility.strategy.ScreenCompatibilityFixerStrategies;
import ru.granby.tabukan.ui.base.screencompatibility.strategy.ScreenCompatibilityFixer;

//TODO: remove?
public class SingleplayerScreenCompatibility extends ScreenCompatibilityFixer {
    public SingleplayerScreenCompatibility(String currentLayoutTag) {
        super(currentLayoutTag);
    }

    // when flow_maxElementsWrap increases, the size of elements decreases
    public void fixLettersMaxElementsWrap(SingleplayerActivityBinding binding, int defaultWordsLettersMaxElementsWrap, int visibleWordLettersCount) {
        fix(new ScreenCompatibilityFixerStrategies() {
            @Override
            public void fixOnDefaultLayout() {
                // nothing to fix
            }

            @Override
            public void fixOnSw360DpLayout() {
                int wordLettersMaxElementsWrap = defaultWordsLettersMaxElementsWrap - (visibleWordLettersCount % 2);
                int selectLettersMaxElementsWrap = wordLettersMaxElementsWrap;
                //int selectLettersMaxElementsWrap = wordLettersMaxElementsWrap + 1;

                binding.wordLettersFlow.setMaxElementsWrap(wordLettersMaxElementsWrap);
                binding.selectLettersFlow.setMaxElementsWrap(selectLettersMaxElementsWrap);
            }

            @Override
            public void fixOnSw600DpLayout() {

            }
        });
    }
}
