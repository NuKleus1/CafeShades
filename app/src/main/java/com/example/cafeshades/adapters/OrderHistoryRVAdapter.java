package com.example.cafeshades.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cafeshades.R;
import com.example.cafeshades.models.OrderModelClass;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryRVAdapter extends RecyclerView.Adapter<OrderHistoryRVAdapter.ViewHolder> {

    static List<OrderModelClass> orderList;
    Context context;

    public OrderHistoryRVAdapter(Context context, List<OrderModelClass> orderList) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_order_item, parent, false);
        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvOrderNumber.setText(orderList.get(position).getOrderId());
        holder.tvDate.setText(orderList.get(position).getOrderDate());
        holder.tvTotalAmount.setText(orderList.get(position).getTotalAmount());
        holder.tvOrderStatus.setText(orderList.get(position).getOrderStatus());

        OrderHistoryItemRVAdapter adapter = new OrderHistoryItemRVAdapter(orderList.get(position).getProductList());

        holder.rvOrderHistoryItemList.setLayoutManager(new LinearLayoutManager(context));
        holder.rvOrderHistoryItemList.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderNumber, tvDate, tvTotalAmount, tvOrderStatus;
        RecyclerView rvOrderHistoryItemList;
        Button btnViewOrder;
        ExpandableLayout expandableLayout;
        View v;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.v = itemView;
            init();
        }

        private void init() {
            tvOrderNumber = v.findViewById(R.id.tv_order_id);
            tvDate = v.findViewById(R.id.tv_date);
            tvTotalAmount = v.findViewById(R.id.tv_total_amount);
            tvOrderStatus = v.findViewById(R.id.tv_order_status);
            rvOrderHistoryItemList = v.findViewById(R.id.rv_items);
            btnViewOrder = v.findViewById(R.id.btn_view_order);
            expandableLayout = v.findViewById(R.id.expandable_layout);

            btnViewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ExpandableLayout.State.COLLAPSED == expandableLayout.getState()) {
                        expandableLayout.expand();
                        btnViewOrder.setText(R.string.btn_collapse);
                    } else {
                        expandableLayout.collapse();
                        btnViewOrder.setText(R.string.btn_view_order);
                    }
                }
            });
        }
    }
}
