package com.example.BPMdev;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by PC on 2016-06-10.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    Context context;
    List<Order_Item> items;
    int item_layout;
    public OrderAdapter(Context context, List<Order_Item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }
    public void set_data(List<Order_Item> items){
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orderlist, null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final Order_Item item = items.get(position);
        holder.name.setText(item.getProductName());
        holder.price.setText(item.getPrice());
        holder.date.setText(item.getDate());
        holder.deliveryno.setText(item.getDeliveryno());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "삭제 완료 했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView name;
        TextView price;
        TextView deliveryno;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.order_name);
            price=(TextView)itemView.findViewById(R.id.order_price);
            date=(TextView)itemView.findViewById(R.id.order_date);
            deliveryno=(TextView)itemView.findViewById(R.id.order_deliveryno);

            cardview=(CardView)itemView.findViewById(R.id.ordercardview);
        }
    }
}
