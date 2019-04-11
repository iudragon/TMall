package com.lewokapps.tmall;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyRewardsAdapter extends RecyclerView.Adapter<MyRewardsAdapter.ViewHolder> {

    private List<RewardModel> rewardModelList;

    private Boolean useMiniLayout = false;

    private RecyclerView couponsRecyclerView;

    private LinearLayout selectedCoupon;

    private String productOriginalPrice;
    private TextView selectedCouponTitle;
    private TextView selectedCouponExpiryDate;
    private TextView selectedCouponBody;
    private TextView discountedPrice;

    public MyRewardsAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
    }


    public MyRewardsAdapter(List<RewardModel> rewardModelList, Boolean useMiniLayout, RecyclerView couponsRecyclerView, LinearLayout selectedCoupon, String productOriginalPrice, TextView couponTitle, TextView couponExpiryDate, TextView couponBody, TextView discountedPrice) {
        this.rewardModelList = rewardModelList;
        this.useMiniLayout = useMiniLayout;
        this.couponsRecyclerView = couponsRecyclerView;
        this.selectedCoupon = selectedCoupon;
        this.productOriginalPrice = productOriginalPrice;
        this.selectedCouponTitle = couponTitle;
        this.selectedCouponExpiryDate = couponExpiryDate;
        this.selectedCouponBody = couponBody;
        this.discountedPrice = discountedPrice;
    }


    @NonNull
    @Override
    public MyRewardsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;

        if (useMiniLayout) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mini_rewards_item_layout, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rewards_item_layout, viewGroup, false);
        }

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyRewardsAdapter.ViewHolder viewHolder, int position) {

        String type = rewardModelList.get(position).getType();
        Date validity = rewardModelList.get(position).getTimestamp();
        String body = rewardModelList.get(position).getCouponBody();
        String lowerLimit = rewardModelList.get(position).getLowerLimit();
        String upperLimit = rewardModelList.get(position).getUpperLimit();
        String discORamt = rewardModelList.get(position).getDiscORamt();

        viewHolder.setData(type, validity, body, upperLimit, lowerLimit, discORamt);
    }

    @Override
    public int getItemCount() {
        return rewardModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView couponTitle;
        private TextView couponExpirydDate;
        private TextView couponBody;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            couponTitle = itemView.findViewById(R.id.coupon_title);
            couponExpirydDate = itemView.findViewById(R.id.coupon_validity);
            couponBody = itemView.findViewById(R.id.coupon_body);

        }

        private void setData(final String type, final Date validity, final String body, final String upperLimit, final String lowerLimit, final String discORamt) {

            if (type.equals("Discount")) {

                couponTitle.setText(type);
            } else {
                couponTitle.setText("FLAT Rs." + discORamt + " OFF");
            }

            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/YYYY");
            couponExpirydDate.setText("till " + simpleDateFormat.format(validity));
            couponBody.setText(body);

            if (useMiniLayout) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedCouponTitle.setText(type);
                        selectedCouponExpiryDate.setText(simpleDateFormat.format(validity));
                        selectedCouponBody.setText(body);

                        if (Long.valueOf(productOriginalPrice) > Long.valueOf(lowerLimit) && Long.valueOf(productOriginalPrice) < Long.valueOf(upperLimit)) {

                            if (type.equals("Discount")) {
                                Long discountAmount = Long.valueOf(productOriginalPrice) * Long.valueOf(discORamt) / 100;
                                discountedPrice.setText("Rs." + String.valueOf(Long.valueOf(productOriginalPrice) - discountAmount) + " /-");
                            } else {
                                discountedPrice.setText("Rs." + String.valueOf(Long.valueOf(productOriginalPrice) - Long.valueOf(discORamt)) + " /-");

                            }

                        } else {
                            discountedPrice.setText("Invalid");
                            Toast.makeText(itemView.getContext(), "Product does not matches the coupon terms", Toast.LENGTH_SHORT).show();
                        }

                        if (couponsRecyclerView.getVisibility() == View.GONE) {

                            couponsRecyclerView.setVisibility(View.VISIBLE);

                            selectedCoupon.setVisibility(View.GONE);
                        } else {
                            couponsRecyclerView.setVisibility(View.GONE);
                            selectedCoupon.setVisibility(View.VISIBLE);
                        }

                    }
                });
            }

        }
    }
}