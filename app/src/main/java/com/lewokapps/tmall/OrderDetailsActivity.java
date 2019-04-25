package com.lewokapps.tmall;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class OrderDetailsActivity extends AppCompatActivity {

    private int position;

    private TextView title, price, quantity;
    private ImageView productImagew, orderedIndicator, packedIndicator, shippedIndicator, deliveredIndicator;
    private ProgressBar O_P_progress, P_S_progress, S_D_progress;
    private TextView orderedTitle, packedTitle, shippedTitle, deliveredTitle;
    private TextView orderedDate, packedDate, shippedDate, deliveredDate;
    private TextView orderedBody, packedBody, shippedBody, deliveredBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(true);

        getSupportActionBar().setTitle("Order Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        position = getIntent().getIntExtra("Position", -1);

        MyOrderItemModel model = DBqueries.myOrderItemModelList.get(position);

        title = findViewById(R.id.product_title);
        price = findViewById(R.id.product_price);
        quantity = findViewById(R.id.product_quantity);

        orderedIndicator = findViewById(R.id.ordered_indicator);
        packedIndicator = findViewById(R.id.packed_indicator);
        shippedIndicator = findViewById(R.id.shipping_indicator);
        deliveredIndicator = findViewById(R.id.delivered_indicator);

        O_P_progress = findViewById(R.id.ordered_packed_progress_bar);
        P_S_progress = findViewById(R.id.packed_shipping_progress);
        S_D_progress = findViewById(R.id.shipping_delivered_progress);

        orderedTitle = findViewById(R.id.ordered_title);
        packedTitle = findViewById(R.id.packed_title);
        shippedTitle = findViewById(R.id.shipping_title);
        deliveredTitle = findViewById(R.id.delivered_title);

        orderedDate = findViewById(R.id.ordered_date);
        packedDate = findViewById(R.id.packed_date);
        shippedDate = findViewById(R.id.shipping_date);
        deliveredDate = findViewById(R.id.delivered_date);

        orderedBody = findViewById(R.id.ordered_body);
        packedBody = findViewById(R.id.packed_body);
        shippedBody = findViewById(R.id.shipping_body);
        deliveredBody = findViewById(R.id.delivered_body);
        productImagew = findViewById(R.id.product_image);

        title.setText(model.getProductTitle());
        if (model.getDiscountedPrice() != null) {
            price.setText(model.getDiscountedPrice());
        } else {
            price.setText(model.getProductPrice());
        }

        quantity.setText(String.valueOf(model.getProductQuantity()));
        Glide.with(this).load(model.getProductImage()).into(productImagew);

        switch (model.getOrderStatus()) {
            case "Ordered":

                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                orderedDate.setText(String.valueOf(model.getOrderedDate()));

                P_S_progress.setVisibility(View.GONE);
                S_D_progress.setVisibility(View.GONE);
                O_P_progress.setVisibility(View.GONE);

                packedIndicator.setVisibility(View.GONE);
                packedBody.setVisibility(View.GONE);
                packedDate.setVisibility(View.GONE);
                packedTitle.setVisibility(View.GONE);

                shippedBody.setVisibility(View.GONE);
                shippedDate.setVisibility(View.GONE);
                shippedIndicator.setVisibility(View.GONE);
                shippedTitle.setVisibility(View.GONE);


                deliveredBody.setVisibility(View.GONE);
                deliveredTitle.setVisibility(View.GONE);
                deliveredDate.setVisibility(View.GONE);
                deliveredIndicator.setVisibility(View.GONE);

                break;

            case "Packed":

                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                orderedDate.setText(String.valueOf(model.getOrderedDate()));

                packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                packedDate.setText(String.valueOf(model.getPackedDate()));

                O_P_progress.setProgress(100);

                P_S_progress.setVisibility(View.GONE);
                S_D_progress.setVisibility(View.GONE);

                shippedBody.setVisibility(View.GONE);
                shippedDate.setVisibility(View.GONE);
                shippedIndicator.setVisibility(View.GONE);
                shippedTitle.setVisibility(View.GONE);

                deliveredBody.setVisibility(View.GONE);
                deliveredTitle.setVisibility(View.GONE);
                deliveredDate.setVisibility(View.GONE);
                deliveredIndicator.setVisibility(View.GONE);

                break;

            case "Shipped":

                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                orderedDate.setText(String.valueOf(model.getOrderedDate()));

                packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                packedDate.setText(String.valueOf(model.getPackedDate()));

                shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                shippedDate.setText(String.valueOf(model.getShippedDate()));

                O_P_progress.setProgress(100);

                P_S_progress.setProgress(100);
                S_D_progress.setVisibility(View.GONE);



                deliveredBody.setVisibility(View.GONE);
                deliveredTitle.setVisibility(View.GONE);
                deliveredDate.setVisibility(View.GONE);
                deliveredIndicator.setVisibility(View.GONE);

                break;
            case "Delivered":


                orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                orderedDate.setText(String.valueOf(model.getOrderedDate()));

                packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                packedDate.setText(String.valueOf(model.getPackedDate()));

                shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                shippedDate.setText(String.valueOf(model.getShippedDate()));

                deliveredIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                deliveredDate.setText(String.valueOf(model.getDeliveredDate()));


                O_P_progress.setProgress(100);

                P_S_progress.setProgress(100);
                S_D_progress.setProgress(100);


                break;

            case "Cancelled":

                if (model.getPackedDate().after(model.getOrderedDate())){
                    if (model.getShippedDate().after(model.getPackedDate())){


                        orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        orderedDate.setText(String.valueOf(model.getOrderedDate()));

                        packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        packedDate.setText(String.valueOf(model.getPackedDate()));

                        shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        shippedDate.setText(String.valueOf(model.getShippedDate()));

                        deliveredIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.btnRed)));
                        deliveredDate.setText(String.valueOf(model.getCancelledDate()));
                        deliveredTitle.setText("Cancelled");
                        deliveredBody.setText("Your order has been cancelled");


                        O_P_progress.setProgress(100);

                        P_S_progress.setProgress(100);
                        S_D_progress.setProgress(100);
                    } else {

                        orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        orderedDate.setText(String.valueOf(model.getOrderedDate()));

                        packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                        packedDate.setText(String.valueOf(model.getPackedDate()));

                        shippedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.btnRed)));
                        shippedDate.setText(String.valueOf(model.getCancelledDate()));

                        shippedTitle.setText("Cancelled");
                        shippedBody.setText("Your order has been cancelled");

                        O_P_progress.setProgress(100);

                        P_S_progress.setProgress(100);
                        S_D_progress.setVisibility(View.GONE);



                        deliveredBody.setVisibility(View.GONE);
                        deliveredTitle.setVisibility(View.GONE);
                        deliveredDate.setVisibility(View.GONE);
                        deliveredIndicator.setVisibility(View.GONE);
                    }
                } else {
                    orderedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
                    orderedDate.setText(String.valueOf(model.getOrderedDate()));

                    packedIndicator.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.btnRed)));
                    packedDate.setText(String.valueOf(model.getCancelledDate()));
                    packedTitle.setText("Cancelled");
                    packedBody.setText("Your order has been cancelled");

                    O_P_progress.setProgress(100);

                    P_S_progress.setVisibility(View.GONE);
                    S_D_progress.setVisibility(View.GONE);

                    shippedBody.setVisibility(View.GONE);
                    shippedDate.setVisibility(View.GONE);
                    shippedIndicator.setVisibility(View.GONE);
                    shippedTitle.setVisibility(View.GONE);

                    deliveredBody.setVisibility(View.GONE);
                    deliveredTitle.setVisibility(View.GONE);
                    deliveredDate.setVisibility(View.GONE);
                    deliveredIndicator.setVisibility(View.GONE);
                }

                break;





        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            finish();
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
