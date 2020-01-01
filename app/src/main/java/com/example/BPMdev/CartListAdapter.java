package com.example.BPMdev;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by PC on 2016-05-30.
 */
public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    Context context;
    List<Cart_Item> items;
    int item_layout;
    public CartListAdapter(Context context, List<Cart_Item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
            }
    public void set_data(List<Cart_Item> items){
        this.items = items;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cartlist,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder,final int position) {
        final Cart_Item item = items.get(position);
//        Drawable drawable=context.getResources().getDrawable(item.getImage());
//        holder.image.setBackground(drawable);

        holder.title.setText(item.getTitle());
        holder.price.setText(item.getPrice());
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView price;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.cartimage);
            title=(TextView)itemView.findViewById(R.id.title);
            price=(TextView)itemView.findViewById(R.id.price);

            cardview=(CardView)itemView.findViewById(R.id.cartcardview);
        }
    }
}