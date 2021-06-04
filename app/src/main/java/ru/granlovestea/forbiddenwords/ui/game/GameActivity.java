package ru.granlovestea.forbiddenwords.ui.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import ru.granlovestea.forbiddenwords.R;
import ru.granlovestea.forbiddenwords.databinding.ActivityGameBinding;

public class GameActivity extends AppCompatActivity implements GameContract.View {
    private GameContract.Presenter presenter;
    private ActivityGameBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGameBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        if(presenter == null) {
            presenter = new GamePresenter();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detach();
    }

    @Override
    public void onShowSampleTextClicked(View view) {
        presenter.onShowSampleTextClicked();
    }

    @Override
    public void setExampleText(String text) {
        binding.exampleText.setText(text);
    }
}