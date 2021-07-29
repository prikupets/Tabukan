package ru.granby.tabukan.ui.base.screencompatibility.strategy;

public class ScreenCompatibilityFixer {
    private final String currentLayoutTag;

    public ScreenCompatibilityFixer(String currentLayoutTag) {
        this.currentLayoutTag = currentLayoutTag;
    }

    protected void fix(ScreenCompatibilityFixerStrategies screenCompatibilityFixStrategy) {
        switch (currentLayoutTag) {
            case "sw360dp": {
                screenCompatibilityFixStrategy.fixOnSw360DpLayout();
                break;
            }

            case "sw600dp": {
                screenCompatibilityFixStrategy.fixOnSw600DpLayout();
                break;
            }

            default:
                screenCompatibilityFixStrategy.fixOnDefaultLayout();
                break;
        }
    }
}