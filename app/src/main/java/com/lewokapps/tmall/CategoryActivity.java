package com.lewokapps.tmall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lewokapps.tmall.R;

import java.util.ArrayList;
import java.util.List;

import static com.lewokapps.tmall.DBqueries.lists;
import static com.lewokapps.tmall.DBqueries.loadFragmentData;
import static com.lewokapps.tmall.DBqueries.loadedCategoriesNames;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView categoryRecyclerView;

    private List<com.lewokapps.tmall.HomePageModel> homePageModelFakeList = new ArrayList<>();

    private com.lewokapps.tmall.HomePageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String title = getIntent().getStringExtra("CategoryName");
        getSupportActionBar().setTitle(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ///// home page fake list

        List<com.lewokapps.tmall.SliderModel> sliderModelFakeList = new ArrayList<>();
        sliderModelFakeList.add(new com.lewokapps.tmall.SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new com.lewokapps.tmall.SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new com.lewokapps.tmall.SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new com.lewokapps.tmall.SliderModel("null", "#ffffff"));
        sliderModelFakeList.add(new com.lewokapps.tmall.SliderModel("null", "#ffffff"));

        List<com.lewokapps.tmall.HorizontalProductScrollModel> horizontalProductScrollModelFakeList = new ArrayList<>();

        horizontalProductScrollModelFakeList.add(new com.lewokapps.tmall.HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new com.lewokapps.tmall.HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new com.lewokapps.tmall.HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new com.lewokapps.tmall.HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new com.lewokapps.tmall.HorizontalProductScrollModel("", "", "", "", ""));
        horizontalProductScrollModelFakeList.add(new com.lewokapps.tmall.HorizontalProductScrollModel("", "", "", "", ""));

        homePageModelFakeList.add(new com.lewokapps.tmall.HomePageModel(0, sliderModelFakeList));
        homePageModelFakeList.add(new com.lewokapps.tmall.HomePageModel(1, "", "#ffffff"));
        homePageModelFakeList.add(new com.lewokapps.tmall.HomePageModel(2, "", "#ffffff", horizontalProductScrollModelFakeList, new ArrayList<com.lewokapps.tmall.WishlistModel>()));
        homePageModelFakeList.add(new com.lewokapps.tmall.HomePageModel(3, "", "#ffffff", horizontalProductScrollModelFakeList));

        ///// home page fake list

        categoryRecyclerView = findViewById(R.id.category_recyclerview);

        LinearLayoutManager testingLayoutManager = new LinearLayoutManager(this);
        testingLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecyclerView.setLayoutManager(testingLayoutManager);


        adapter = new com.lewokapps.tmall.HomePageAdapter(homePageModelFakeList);


        int listPosition = 0;

        for (int x = 0; x < loadedCategoriesNames.size(); x++) {

            if (loadedCategoriesNames.get(x).equals(title.toUpperCase())) {

                listPosition = x;
            }
        }

        if (listPosition == 0) {

            loadedCategoriesNames.add(title.toUpperCase());
            lists.add(new ArrayList<com.lewokapps.tmall.HomePageModel>());

            loadFragmentData(categoryRecyclerView, this, loadedCategoriesNames.size() - 1, title);
        } else {
            adapter = new com.lewokapps.tmall.HomePageAdapter(lists.get(listPosition));
        }
        categoryRecyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_search_icon) {
            return true;
        } else if (id == android.R.id.home) {

            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
