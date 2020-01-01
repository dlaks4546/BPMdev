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

import java.util.List;

/**
 * Created by PC on 2016-05-03.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    Context context;
    List<Recycler_item> items;
    int item_layout;
    public RecyclerAdapter(Context context, List<Recycler_item> items, int item_layout) {
        this.context=context;
        this.items=items;
        this.item_layout=item_layout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview,null);
        return new ViewHolder(v);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Recycler_item item=items.get(position);
        holder.content.setText(item.getContent());
        holder.title.setText(item.getTitle());
        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                remove(Integer.toString(position),item.getTitle());
                Toast.makeText(context, item.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
//    private void remove(String position,String bookname)
//    {
//        CartService loginApi = MainActivity.retrofit.create(CartService.class);
//        final Call<CartData> nicknameChangeCall = loginApi.REMOVE_CART_DATA_CALL(bookname,LoginData.id);
//        nicknameChangeCall.enqueue(new Callback<CartData>() {
//            @Override
//            public void onResponse(Call<CartData> call, Response<CartData> response) {
//                CartData ldata = response.body();
//                if (response.isSuccessful()) {
//                    Log.i("....", "TRUE..........." + ldata);
//                } else {
//                    Log.i("....", "FAILURE..........." + ldata);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CartData> call, Throwable t) {
//                Log.i("test....", "FAILURE...........");
//                Log.d("Error", t.getMessage());
//            }
//        });
//    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView content;
        TextView title;
        CardView cardview;

        public ViewHolder(View itemView) {
            super(itemView);
            content=(TextView)itemView.findViewById(R.id.content);
            title=(TextView)itemView.findViewById(R.id.title);
            cardview=(CardView)itemView.findViewById(R.id.cardview);
        }
    }
}