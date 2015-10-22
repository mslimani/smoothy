package smoothy.sample;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by mehdi on 22/10/2015.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
