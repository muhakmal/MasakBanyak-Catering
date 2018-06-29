package io.cauliframes.masakbanyak_catering.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import io.cauliframes.masakbanyak_catering.R;
import io.cauliframes.masakbanyak_catering.model.Order;
import io.cauliframes.masakbanyak_catering.ui.fragment.TransactionsFragment;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {
  private ArrayList<Order> orders = new ArrayList<>();
  
  private TransactionsFragment.OnFragmentInteractionListener listener;
  
  public OrdersAdapter(TransactionsFragment.OnFragmentInteractionListener listener) {
    this.listener = listener;
  }
  
  public void setOrders(ArrayList<Order> orders) {
    this.orders = orders;
    notifyDataSetChanged();
  }
  
  @NonNull
  @Override
  
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
    return new ViewHolder(view);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.cardView.setOnClickListener(v -> listener.onOrderClick(orders.get(position)));
    
    holder.orderTimeTextView.setText(orders.get(position).getOrder_time());
    holder.statusTextView.setText("Status: "+orders.get(position).getStatus().toUpperCase());
  }
  
  @Override
  public int getItemCount() {
    return orders.size();
  }
  
  @Override
  public int getItemViewType(int position) {
    return R.layout.itemview_order;
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder {
    private CardView cardView;
    private TextView orderTimeTextView;
    private TextView statusTextView;
    
    public ViewHolder(View itemView) {
      super(itemView);
      
      cardView = itemView.findViewById(R.id.cardView);
      orderTimeTextView = itemView.findViewById(R.id.orderTimeTextView);
      statusTextView = itemView.findViewById(R.id.statusTextView);
    }
  }
}
