package ru.granby.tabukan.localization;

import android.content.Context;
import android.util.Log;

import lombok.Getter;
import ru.granby.tabukan.App;
import ru.granby.tabukan.model.data.database.entity.localization.Localized;

public class Localizer {
    private static final String TAG = "~Localizer";
    private static Localizer instance;

    //TODO[priority=low]: check if the new lower case alphabet is correct
    public static final char[] RU_ALPHABET = {'а','б','в','г','д','е','ё','ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х','ц','ч','ш','щ','ъ','ы','ь','э','ю','я'};
    public static final char[] EN_ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static final char[] ES_ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ň','o','p','q','r','s','t','u','v','w','x','y','z'};
    public static final char[] DE_ALPHABET = {'a','ä','b','c','d','e','f','g','h','i','j','k','l','m','n','o','ö','p','q','r','s','ß','t','u','ü','v','w','x','y','z'};
    public static final char[] FR_ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','р','q','r','s','t','u','v','w','x','y','z'};
    public static final char[] TR_ALPHABET = {'a','b','c','ç','d','e','f','g','ğ','h','i','i','j','k','l','m','n','o','ö','p','r','s','ş','t','u','ü','v','y','z'};
    public static final char[] IT_ALPHABET = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

    @Getter
    private String language;
    @Getter
    private char[] alphabet;

    public Localizer(String currentLanguage) {
        switch(currentLanguage) {
            case "ru":
                alphabet = RU_ALPHABET;
                break;
            case "en":
                alphabet = EN_ALPHABET;
                break;
            case "es":
                alphabet = ES_ALPHABET;
                break;
            case "de":
                alphabet = DE_ALPHABET;
                break;
            case "fr":
                alphabet = FR_ALPHABET;
                break;
            case "tr":
                alphabet = TR_ALPHABET;
                break;
            case "it":
                alphabet = IT_ALPHABET;
                break;
            default:
                Log.w(TAG, "setup: can't determine language, setting to en");
                language = "en";
                alphabet = EN_ALPHABET;
                return;
        }

        language = currentLanguage;
    }

    public static Localizer getInstance() {
        if(instance == null) {
            Context context = App.getInstance();

            String currentLanguage;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                currentLanguage = context.getResources().getConfiguration().getLocales().get(0).getLanguage();
            } else {
                currentLanguage = context.getResources().getConfiguration().locale.getLanguage();
            }
            instance = new Localizer(currentLanguage);
            Log.i(TAG, "Set up Localization with language: " + currentLanguage);
        }

        return instance;
    }

    public String localize(Localized localized) {
        switch(language) {
            case "ru":
                return localized.getRu();
            case "en":
                return localized.getEn();
            case "es":
                return localized.getEs();
            case "de":
                return localized.getDe();
            case "fr":
                return localized.getFr();
            case "tr":
                return localized.getTr();
            case "it":
                return localized.getIt();
            default:
                String errorMsg = "getText: can't determine language, using en";
                Log.e(TAG, errorMsg);
                return localized.getEn();
        }
    }
}
