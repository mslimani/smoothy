package smoothy.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import smoothy.BindExtra;
import smoothy.SmoothyBundle;
import smoothy.sample.models.Item;

public class DetailActivity extends AppCompatActivity {

    @BindExtra("ID")
    Integer mId;

    @BindExtra
    String mTitle;

    @BindExtra(optional = true)
    int mCount;

    @BindExtra(optional = true)
    int[] mInts;

    @BindExtra(optional = true)
    ArrayList<String> mTitles;

    @BindExtra(optional = true)
    Date mDate;

    @BindExtra(optional = true)
    List<Item> mItems;

    @BindExtra(optional = true)
    String[] mTitlesArray;

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

        ArrayAdapter<Item> adapter = new ArrayAdapter<>(this, android.R.layout
                .simple_list_item_1, mItems);
        mListView.setAdapter(adapter);
    }



}
