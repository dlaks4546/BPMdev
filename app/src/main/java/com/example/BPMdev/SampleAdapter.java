package com.example.BPMdev;


import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.etsy.android.grid.util.DynamicHeightImageView;
import com.etsy.android.grid.util.DynamicHeightTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/***
 * ADAPTER
 */

public class SampleAdapter extends ArrayAdapter<String> {
    public static Retrofit retrofit;
    private static final String TAG = "SampleAdapter";
    MaterialDialog mMaterialDialog;
    public int pos;
    JSONArray accountInfoArray;
    String buffer[];
    static class ViewHolder {
        DynamicHeightTextView txtLineOne;
        DynamicHeightImageView imgViewOne;
        Button btnGo;
    }
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private final ArrayList<Integer> mBackgroundColors;
    private final ArrayList<Integer> color=new ArrayList<Integer>();

    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();

    public SampleAdapter(final Context context, final int textViewResourceId) {
        super(context, textViewResourceId);
        final StaggeredGridEmptyViewActivity takepos = new StaggeredGridEmptyViewActivity();
        mLayoutInflater = LayoutInflater.from(context);
        mRandom = new Random();
        mContext = context;
        mBackgroundColors = new ArrayList<Integer>();
        mBackgroundColors.add(R.color.orange);
        mBackgroundColors.add(R.color.green);
        mBackgroundColors.add(R.color.blue);
        mBackgroundColors.add(R.color.yellow);
        mBackgroundColors.add(R.color.grey);
        final ReceiptinfoActivity add = new ReceiptinfoActivity();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMaterialDialog = new MaterialDialog(context)
                .setTitle("장바구니")
                .setMessage("장바구니에 담으시겠습니까?")
                .setPositiveButton("네", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        AddCartData(Integer.toString(pos), LoginData.user_id);
                        Toast.makeText(getContext(), "장바구니 입력 성공", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("아니요", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Toast.makeText(getContext(), "다시", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // 장바구니에 입력하려고 자바쪽으로 포지션 넘겨준 후 그에 해당하는 정보 찾아 장바구니로 넣음

    private void AddCartData(String pos, String id)
    {
        AddCartService CartApi = retrofit.create(AddCartService.class);
        final Call<AddCartData> nicknameChangeCall = CartApi.ADD_CART_DATA_CALL(pos, id);
        nicknameChangeCall.enqueue(new Callback<AddCartData>() {
            @Override
            public void onResponse(Call<AddCartData> call, Response<AddCartData> response) {
                AddCartData ldata = response.body();
                if (response.isSuccessful()) {
                    Log.i("....", "Take SUCCESS...........");

                } else {
                    Log.i("....", "FAILURE...........g" + ldata);
                }
            }

            @Override
            public void onFailure(Call<AddCartData> call, Throwable t) {
                Log.i("test....", "FAILURE...........");
            }
        });
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        final ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_sample, parent, false);
            vh = new ViewHolder();
            vh.txtLineOne = (DynamicHeightTextView) convertView.findViewById(R.id.txt_line1);
            vh.btnGo = (Button) convertView.findViewById(R.id.btn_go);
            vh.imgViewOne = (DynamicHeightImageView) convertView.findViewById(R.id.img_view1);

            convertView.setTag(vh);
        }
        else {
            vh = (ViewHolder) convertView.getTag();
        }

        double positionHeight = getPositionRatio(position);
        int backgroundIndex = position >= mBackgroundColors.size() ?
                position % mBackgroundColors.size() : position;

        convertView.setBackgroundResource(mBackgroundColors.get(backgroundIndex));
        /////////////////////////////////포지션값으로 디비를 접근 하는가 ?
        Log.d(TAG, "getView position:" + position + " h:" + positionHeight);
        vh.imgViewOne.setHeightRatio(positionHeight);
        final String s=getItem(position);   // 서버 주소

        buffer = s.split(",");
        int num = Integer.parseInt(buffer[1]);
        pos = Integer.parseInt(buffer[2]);
        if (num == 0) {
            vh.btnGo.setBackgroundResource(R.color.a);  // 대기중이면 흰색
        } else if (num == 1) {
            vh.btnGo.setBackgroundResource(R.color.b);  // 예약중이면 블루
        } else if (num == 2) {
            vh.btnGo.setBackgroundResource(R.color.c);  // 판매완료면 블랙
        }

        Glide
                .with(mContext)
                .load(buffer[0])
                .centerCrop()
                .crossFade()
                .into(vh.imgViewOne);

//        vh.txtLineOne.setHeightRatio(positionHeight);
//        vh.txtLineOne.setText(getItem(position) + position);


        vh.btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    JSONObject object =  (JSONObject) accountInfoArray.get(position);
                    pos=Integer.parseInt(object.getString("BookNumber"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mMaterialDialog.show();
            }
        });

        return convertView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        return (mRandom.nextDouble() / 2.0) + 1.0; // height will be 1.0 - 1.5 the width
    }
}