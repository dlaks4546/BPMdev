package com.example.BPMdev;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by PC on 2016-05-30.
 */
public class StoreListAdapter extends RecyclerView.Adapter<StoreListAdapter.Holder> {
    Context context;
    List<Store_Item> items;
    int item_layout;

    public StoreListAdapter(Context context, List<Store_Item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
     }

    public void set_data(List<Store_Item> items){
        this.items = items;
    }
    @Override

    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cartlist, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final Holder holder,final int position) {
        final Store_Item item = items.get(position);
//        Drawable drawable=context.getResources().getDrawable(item.getImage());
//        holder.image.setBackground(drawable);

        holder.title.setText(item.getTitle());
        holder.price.setText(item.getPrice());
        holder.itemno.setText(item.getItemNo());
        Glide
                .with(context)
                .load(item.getImage())

                .crossFade()
                .into(holder.image);
        /*holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(item.getTitle());
                Toast.makeText(context,"삭제 완료 했습니다.",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

 /*   private void remove(String bookname)
    {
        CartService loginApi = MainActivity.retrofit.create(CartService.class);
        final Call<CartData> nicknameChangeCall = loginApi.REMOVE_CART_DATA_CALL(bookname, LoginData.id);
        nicknameChangeCall.enqueue(new Callback<CartData>() {
            @Override
            public void onResponse(Call<CartData> call, Response<CartData> response) {
                CartData ldata = response.body();
                if (response.isSuccessful()) {
                    Log.i("....", "TRUE..........." + ldata.message);
                }
                else {
                    Log.i("....", "FAILURE...........h" + ldata);
                }
            }

            @Override
            public void onFailure(Call<CartData> call, Throwable t) {
                Log.i("test....", "FAILURE...........");
                Log.d("Error", t.getMessage());
            }
        });

    }*/


    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView price;
        TextView itemno;
        CardView cardview;

        public Holder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.cartimage);
            title=(TextView)itemView.findViewById(R.id.title);
            price=(TextView)itemView.findViewById(R.id.price);
            itemno=(TextView)itemView.findViewById(R.id.itemno);

            cardview=(CardView)itemView.findViewById(R.id.cartcardview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO : process click event.
                    Toast.makeText(context, "아이템 번호 전달"+itemno.getText(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, BPMitemtinfoActivity.class);
                    intent.putExtra("itemno", itemno.getText());
                    context.startActivity(intent);
                    //finish();
                }
            });
        }
    }
}