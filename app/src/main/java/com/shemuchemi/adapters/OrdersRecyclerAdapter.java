package com.shemuchemi.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.shemuchemi.R;
import com.shemuchemi.model.Order;

import java.util.List;


public class OrdersRecyclerAdapter extends RecyclerView.Adapter<OrdersRecyclerAdapter.OrderViewHolder> {

    private List<Order> listOrders;

    public OrdersRecyclerAdapter(List<Order> listOrders) {
        this.listOrders = listOrders;
    }
    @Override
    public OrderViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        //inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext()).inflate( R.layout.item_order_recycler, parent,false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {

        holder.textViewFoodType.setText(listOrders.get(position).getFoodType());
        holder.textViewLocation.setText(listOrders.get(position).getLocation());
        holder.textViewPrice.setText( (int) listOrders.get(position).getPrice() );
    }

    @Override
    public int getItemCount() {
        Log.v(OrdersRecyclerAdapter.class.getSimpleName(),"" + listOrders.size());
        return listOrders.size();
    }
    public class OrderViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewFoodType;
        public AppCompatTextView textViewLocation;
        public AppCompatTextView textViewPrice;

        public OrderViewHolder(View View) {
            super( View );
            textViewFoodType = View.findViewById(R.id.textViewFoodType);
            textViewLocation = View.findViewById(R.id.textViewLocation);
            textViewPrice = View.findViewById(R.id.textViewPrice);

        }
    }
}
