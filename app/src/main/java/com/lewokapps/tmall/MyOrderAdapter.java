package com.lewokapps.tmall;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.ViewHolder> {

    private List<MyOrderItemModel> myOrderItemModelList;

    public MyOrderAdapter(List<MyOrderItemModel> myOrderItemModelList) {
        this.myOrderItemModelList = myOrderItemModelList;
    }

    @NonNull
    @Override
    public MyOrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_order_item_layout, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.ViewHolder viewHolder, int position) {

        String resource = myOrderItemModelList.get(position).getProductImage();
//        int rating = myOrderItemModelList.get(position).getRating();
        String title = myOrderItemModelList.get(position).getProductTitle();

        String orderStatus = myOrderItemModelList.get(position).getOrderStatus();
        Date date;
        switch (orderStatus) {
            case "Ordered":

                date = myOrderItemModelList.get(position).getOrderedDate();

                break;

            case "Packed":

                date = myOrderItemModelList.get(position).getPackedDate();

                break;
            case "Shipped":

                date = myOrderItemModelList.get(position).getShippedDate();

                break;
            case "Delivered":

                date = myOrderItemModelList.get(position).getDeliveredDate();

                break;
            case "Cancelled":

                date = myOrderItemModelList.get(position).getCancelledDate();

                break;

            default:

                date = myOrderItemModelList.get(position).getCancelledDate();

        }

        viewHolder.setData(resource, title, orderStatus, date);

    }

    @Override
    public int getItemCount() {
        return myOrderItemModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView productImage;
        private ImageView orderIndicator;
        private TextView productTitle;
        private TextView deliveryStatus;
        private LinearLayout rateNowContainer;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.product_image);
            productTitle = itemView.findViewById(R.id.product_title);
            orderIndicator = itemView.findViewById(R.id.order_indicator);
            deliveryStatus = itemView.findViewById(R.id.ordered_delivered_date);

            rateNowContainer = itemView.findViewById(R.id.rate_now_container);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent orderDetailsIntent = new Intent(itemView.getContext(), OrderDetailsActivity.class);
                    itemView.getContext().startActivity(orderDetailsIntent);
                }
            });

        }

        private void setData(String resource, String title, String orderStatus, Date date) {

            Glide.with(itemView.getContext()).load(resource).into(productImage);


            productTitle.setText(title);
            if (orderStatus.equals("Cancelled")) {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorAccent)));
            } else {
                orderIndicator.setImageTintList(ColorStateList.valueOf(itemView.getContext().getResources().getColor(R.color.colorPrimary)));
            }
            deliveryStatus.setText(orderStatus + String.valueOf(date));

            ///// ratings layout

//            setRating(rating);

            for (int x = 0; x < rateNowContainer.getChildCount(); x++) {

                final int startPosition = x;
                rateNowContainer.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setRating(startPosition);
                    }
                });
            }

            ///// ratings layout
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


}






