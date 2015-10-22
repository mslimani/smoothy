package smoothy.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import smoothy.BindExtra;
import smoothy.SmoothyBundle;
import smoothy.sample.models.Item;

public class DetailActivity extends AppCompatActivity {

    @BindExtra
    String mTitle;
    @BindExtra
    int mCount;
    @BindExtra
    Date mDate;
    @BindExtra
    List<Item> mItems;

    @Bind(android.R.id.text1)
    TextView mTextView;

    @Bind(android.R.id.list)
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SmoothyBundle.bind(this);
        mTextView.setText(mTitle + " - " + mCount);

        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this, android.R.layout
                .simple_list_item_1, mItems);
        mListView.setAdapter(adapter);
    }



}
