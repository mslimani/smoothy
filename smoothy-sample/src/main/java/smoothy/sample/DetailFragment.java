package smoothy.sample;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import smoothy.BindExtra;
import smoothy.SmoothyBundle;

/**
 * Created by mehdi on 21/10/2015.
 */
public class DetailFragment extends Fragment {

    @BindExtra(optional = true)
    String mName;
    @BindExtra(optional = true)
    String mSurname;

    @Bind(R.id.detail_title)
    TextView mTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SmoothyBundle.bind(this);
        mTitle.setText(mName + " - " + mSurname);
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }
}
