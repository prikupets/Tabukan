package ru.granlovestea.forbiddenwords.ui.game;

import org.greenrobot.eventbus.EventBus;

import ru.granlovestea.forbiddenwords.model.DataManager;
import ru.granlovestea.forbiddenwords.model.dao.DictionaryDao;

public class GamePresenter implements GameContract.Presenter {
    private GameContract.View view;

    @Override
    public void attach(GameContract.View view) {
        this.view = view;
        //EventBus.getDefault().register(this);
    }

    @Override
    public void detach() {
        view = null;
        //EventBus.getDefault().unregister(this);
    }

    @Override
    public void onShowSampleTextClicked() {
        String text = new DictionaryDao().getAll().get(1).getName();
        view.setExampleText(text);
    }
}
