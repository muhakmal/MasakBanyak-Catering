package io.cauliframes.masakbanyak_catering.ui.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.cauliframes.masakbanyak_catering.R;
import io.cauliframes.masakbanyak_catering.model.Packet;
import io.cauliframes.masakbanyak_catering.ui.fragment.PacketsFragment;

import static io.cauliframes.masakbanyak_catering.Constants.MASAKBANYAK_URL;

public class PacketsAdapter extends RecyclerView.Adapter<PacketsAdapter.ViewHolder> {
  
  private ArrayList<Packet> packets = new ArrayList<>();
  
  private PacketsFragment.OnFragmentInteractionListener listener;
  
  public PacketsAdapter(PacketsFragment.OnFragmentInteractionListener listener) {
    this.listener = listener;
  }
  
  public void setPackets(ArrayList<Packet> packets) {
    this.packets = packets;
    notifyDataSetChanged();
  }
  
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
    return new ViewHolder(view);
  }
  
  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
    holder.mTextView.setText(packets.get(position).getName());
    holder.mLayout.setOnClickListener(view -> {
      listener.onPacketClick(packets.get(position));
    });
    
    Picasso.get().load(MASAKBANYAK_URL + packets.get(position).getImages().get(0))
        .fit()
        .centerCrop()
        .into(holder.mImageView);
  }
  
  @Override
  public int getItemCount() {
    return packets.size();
  }
  
  @Override
  public int getItemViewType(int position) {
    return R.layout.itemview_packet;
  }
  
  public class ViewHolder extends RecyclerView.ViewHolder {
    private CardView mLayout;
    private ImageView mImageView;
    private TextView mTextView;
    
    public ViewHolder(View itemView) {
      super(itemView);
      
      mLayout = itemView.findViewById(R.id.packet_item_layout);
      mImageView = itemView.findViewById(R.id.packet_image);
      mTextView = itemView.findViewById(R.id.packet_name);
    }
  }
}
