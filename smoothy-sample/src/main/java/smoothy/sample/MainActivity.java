package smoothy.sample;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import smoothy.SmoothyBundle;
import smoothy.sample.models.Item;
import smoothy.sample.services.HomeServiceBuilder;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SmoothyBundle.bind(this);

        DetailFragment detailFragment = new DetailFragmentBuilder()
                .name("Mehdi")
                .surname("Slimani")
                .build(this);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, detailFragment)
                .commit();
    }

    //region onClick

    @OnClick(R.id.detail)
    void onDetailClick() {
        List<Item> items = getItems();
        Intent detailIntent = new DetailActivityBuilder()
                .title("Smoothy Title")
                .count(34)
                .items(items)
                .build(this);
        startActivity(detailIntent);
    }

    @OnClick(R.id.service)
    void onServiceClick() {
        Intent homeService = new HomeServiceBuilder()
                .name("One title")
                .build(this);
        startService(homeService);
    }

    //endregion onClick

    private List<Item> getItems() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Item item = new Item();
            item.a = "i " + i;
            item.b = "j " + (i + 10);
            items.add(item);
        }
        return items;
    }
}
