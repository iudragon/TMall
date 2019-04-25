package com.lewokapps.tmall;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.HashMap;
import java.util.Map;

public class OrderDetailsActivity extends AppCompatActivity {

    private int position;

    private TextView title, price, quantity;
    private ImageView productImagew, orderedIndicator, packedIndicator, shippedIndicator, deliveredIndicator;
    private ProgressBar O_P_progress, P_S_progress, S_D_progress;
    private TextView orderedTitle, packedTitle, shippedTitle, deliveredTitle;
    private TextView orderedDate, packedDate, shippedDate, deliveredDate;
    private TextView orderedBody, packedBody, shippedBody, deliveredBody;


    private LinearLayout rateNowContainer;

    private int rating;

    private TextView fullName, address, pincode;

    private TextView totalItemsPrice, deliveryPrice, totalAmount, totalItems, savedAmount;

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

        final MyOrderItemModel model = DBqueries.myOrderItemModelList.get(position);

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

        rateNowContainer = findViewById(R.id.rate_now_container);

        fullName = findViewById(R.id.fullname);
        address = findViewById(R.id.address);
        pincode = findViewById(R.id.pincode);

        totalItems = findViewById(R.id.total_items);

        totalItemsPrice = findViewById(R.id.total_items_price);
        deliveryPrice = findViewById(R.id.delivery_price);
        totalAmount = findViewById(R.id.total_price);

        savedAmount = findViewById(R.id.saved_amount);


        title.setText(model.getProductTitle());
        if (!model.getDiscountedPrice().equals("")) {
            price.setText("Rs." + model.getDiscountedPrice() + "/-");
        } else {
            price.setText("Rs." + model.getProductPrice() + "/-");
        }

        quantity.setText("Qty " + String.valueOf(model.getProductQuantity()));
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

                if (model.getPackedDate().after(model.getOrderedDate())) {
                    if (model.getShippedDate().after(model.getPackedDate())) {


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

        ///// ratings layout

        rating = model.getRating();

        setRating(rating);

        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {

            final int starPosition = x;
            rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setRating(starPosition);
                    final DocumentReference documentReference = FirebaseFirestore.getInstance().collection("PRODUCTS").document(model.getProductId());

                    FirebaseFirestore.getInstance().runTransaction(new Transaction.Function<Object>() {
                        @Nullable
                        @Override
                        public Object apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                            DocumentSnapshot documentSnapshot = transaction.get(documentReference);

                            if (rating != 0) {

                                Long increase = documentSnapshot.getLong(starPosition + 1 + "_star") + 1;
                                Long decrease = documentSnapshot.getLong(rating + 1 + "_star") - 1;
                                transaction.update(documentReference, starPosition + 1 + "_star", increase);
                                transaction.update(documentReference, rating + 1 + "_star", decrease);
                            } else {

                                Long increase = documentSnapshot.getLong(starPosition + 1 + "_star") + 1;
                                transaction.update(documentReference, starPosition + 1 + "_star", increase);


                            }

                            return null;
                        }
                    }).addOnSuccessListener(new OnSuccessListener<Object>() {
                        @Override
                        public void onSuccess(Object o) {


                            Map<String, Object> myRating = new HashMap<>();

                            if (DBqueries.myRatedIds.contains(model.getProductId())) {

                                myRating.put("rating_" + DBqueries.myRatedIds.indexOf(model.getProductId()), (long) starPosition + 1);
                            } else {
                                myRating.put("list_size", (long) DBqueries.myRatedIds.size() + 1);
                                myRating.put("product_ID_" + DBqueries.myRatedIds.size(), model.getProductId());
                                myRating.put("rating_" + DBqueries.myRatedIds.size(), (long) starPosition + 1);
                            }
                            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid()).collection("USER_DATA").document("MY_RATINGS").update(myRating).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        DBqueries.myOrderItemModelList.get(position).setRating(starPosition);
                                        if (DBqueries.myRatedIds.contains(model.getProductId())) {

                                            DBqueries.myRating.set(DBqueries.myRatedIds.indexOf(model.getProductId()), Long.parseLong(String.valueOf(starPosition + 1)));
                                        } else {

                                            DBqueries.myRatedIds.add(model.getProductId());
                                            DBqueries.myRating.add(Long.parseLong(String.valueOf(starPosition + 1)));

                                        }
                                    } else {
                                        String error = task.getException().getMessage();
                                        Toast.makeText(OrderDetailsActivity.this, error, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }
                    });
                }
            });
        }

        ///// ratings layout


        fullName.setText(model.getFullname());
        address.setText(model.getAddress());
        pincode.setText(model.getPincode());

        totalItems.setText("Price(" + model.getProductQuantity() + " items)");

        Long totalItemsPriceValue;

        if (model.getDiscountedPrice().equals("")) {
            totalItemsPriceValue = model.getProductQuantity() * Long.valueOf(model.getProductPrice());
            totalItemsPrice.setText("Rs." + totalItemsPriceValue + "/-");

        } else {

            totalItemsPriceValue = model.getProductQuantity() * Long.valueOf(model.getDiscountedPrice());


            totalItemsPrice.setText("Rs." + totalItemsPriceValue + "/-");
        }

        if (model.getDeliveryPrice().equals("FREE")) {

            deliveryPrice.setText(model.getDeliveryPrice());

            totalAmount.setText(totalItemsPrice.getText());
        } else {

            deliveryPrice.setText("Rs." + model.getDeliveryPrice() + "/-");
            totalAmount.setText("Rs." + (totalItemsPriceValue + Long.valueOf(model.getDeliveryPrice())) + "/-");
        }

        if (!model.getCuttedPrice().equals("")){

            if (!model.getDiscountedPrice().equals("")){

                savedAmount.setText("You saved Rs." + model.getProductQuantity() * (Long.valueOf(model.getCuttedPrice()) - Long.valueOf(model.getDiscountedPrice())) + "on this order");

            } else{
                savedAmount.setText("You saved Rs." + model.getProductQuantity() * (Long.valueOf(model.getCuttedPrice()) - Long.valueOf(model.getProductPrice())) + "on this order");

            }
        }else {
            if (!model.getDiscountedPrice().equals("")){

                savedAmount.setText("You saved Rs." + model.getProductQuantity() * (Long.valueOf(model.getProductPrice()) - Long.valueOf(model.getDiscountedPrice())) + "on this order");

            } else{
                savedAmount.setText("You saved Rs. 0 /- on this order");

            }
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

    private void setRating(int startPosition) {

        for (int x = 0; x < rateNowContainer.getChildCount(); x++) {

            ImageView starButton = (ImageView) rateNowContainer.getChildAt(x);
            starButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#8F8989")));
            if (x <= startPosition) {

                starButton.setImageTintList(ColorStateList.valueOf(Color.parseColor("#D6D60B")));

            }
        }
    }
}
