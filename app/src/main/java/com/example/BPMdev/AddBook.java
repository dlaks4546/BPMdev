package com.example.BPMdev;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBook extends AppCompatActivity implements View.OnClickListener {
    public static Retrofit retrofit;
    Button book_info_button;
    EditText book_info_title=null;
    EditText book_info_author=null;
    EditText book_info_publish=null;
    EditText book_info_firstprice=null;
    EditText book_info_secondprice=null;
    EditText book_info_status=null;
    EditText book_info_category=null;
    EditText book_info_some=null;
    EditText userid = null;

    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";
    public static final String URL = "http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080/img/"; //url 앞부분

    private ArrayList<String> _images;
    private GalleryPagerAdapter _adapter;

    @Optional
    @InjectView(R.id.pager_a)
    ViewPager _pager;
    @Optional
    @InjectView(R.id.thumbnails_a)
    LinearLayout _thumbnails;
    @Optional
    @InjectView(R.id.btn_close_a)
    ImageButton _closeButton;

    int i; //
    MaterialDialog mMaterialDialog;

    Intent intent; // 포스 받아올 시 사용
    String search;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_add);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        intent = getIntent();
        search = intent.getExtras().getString("search");


        findViewById(R.id.book_info_add_succ).setOnClickListener(this);

        book_info_button=(Button)findViewById(R.id.book_info_add_succ);
        book_info_title=(EditText)findViewById(R.id.booktitle_a);
        book_info_author=(EditText)findViewById(R.id.author_a);
        book_info_publish=(EditText)findViewById(R.id.publish_a);
        book_info_firstprice=(EditText)findViewById(R.id.firstprice_a);
        book_info_secondprice=(EditText)findViewById(R.id.secondprice_a);
        book_info_status=(EditText)findViewById(R.id.status_a);
        book_info_category=(EditText)findViewById(R.id.category_a);
        book_info_some=(EditText)findViewById(R.id.some_a);
        userid = (EditText)findViewById(R.id.userid_a);
        _closeButton=(ImageButton)findViewById(R.id.btn_close_a) ;

        _closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Close clicked");
                finish();
            }
        });

        mMaterialDialog = new MaterialDialog(this)
                .setTitle("추가하기")
                .setMessage("정말 추가하시겠습니까?")
/*                .setPositiveButton("네", new View.OnClickListener() {
                    @Override
                    *//*public void onClick(View v) {

                        BookService BookApi = retrofit.create(BookService.class);
                        final Call<BookData> nicknameChangeCall = BookApi.ADMIN_CALL("Add",
                                "0",
                                book_info_title.getText().toString(),
                                book_info_author.getText().toString(),
                                book_info_publish.getText().toString(),
                                book_info_firstprice.getText().toString(),
                                book_info_secondprice.getText().toString(),
                                book_info_status.getText().toString(),
                                book_info_category.getText().toString(),
                                book_info_some.getText().toString(),
                                "0",
                                "0",
                                userid.getText().toString());

                        nicknameChangeCall.enqueue(new Callback<BookData>() {
                            @Override
                            public void onResponse(Call<BookData> call, Response<BookData> response) {
                                BookData ldata = response.body();
                                if (response.isSuccessful()) {
                                    Log.i("....", "Take SUCCESS...........");
                                } else {
                                    Log.i("....", "FAILURE..........." + ldata);
                                }
                            }
                            @Override
                            public void onFailure(Call<BookData> call, Throwable t) {
                                Log.i("test....", "FAILURE...........");
                                Log.d("Error", t.getMessage());
                            }
                        });

                        mMaterialDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "추가 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),StaggeredGridEmptyViewActivity.class);
                        intent.putExtra("search",search);
                        startActivity(intent);
                        finish();
                    }*//*
                })*/
                .setNegativeButton("아니요", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "추가 취소", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onClick(View v) {//어떤 버튼을 눌렀냐에 따라 이벤트처리@@@@@@@@@@@@@
        //함수정의 하고 사용할 함수 정의

        if(v.getId() ==  R.id.book_info_add_succ){
            mMaterialDialog.show();

        }

    }

    class GalleryPagerAdapter extends PagerAdapter {

        Context _context;
        LayoutInflater _inflater;

        public GalleryPagerAdapter(Context context) {
            _context = context;
            _inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return _images.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = _inflater.inflate(R.layout.pager_gallery_item, container, false);
            container.addView(itemView);

            // Get the border size to show around each image
            int borderSize = _thumbnails.getPaddingTop();

            // Get the size of the actual thumbnail image
            int thumbnailSize = ((FrameLayout.LayoutParams)
                    _pager.getLayoutParams()).bottomMargin - (borderSize*2);

            // Set the thumbnail layout parameters. Adjust as required
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(thumbnailSize, thumbnailSize);
            params.setMargins(0, 0, borderSize, 0);

            // You could also set like so to remove borders
            //ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
            //        ViewGroup.LayoutParams.WRAP_CONTENT,
            //        ViewGroup.LayoutParams.WRAP_CONTENT);

            final ImageView thumbView = new ImageView(_context);
            thumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            thumbView.setLayoutParams(params);
            thumbView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Thumbnail clicked");

                    // Set the pager position when thumbnail clicked
                    _pager.setCurrentItem(position);
                }
            });
            _thumbnails.addView(thumbView);

            final SubsamplingScaleImageView imageView =
                    (SubsamplingScaleImageView) itemView.findViewById(R.id.image);

            // Asynchronously load the image and set the thumbnail and pager view
            Glide.with(_context)
                    .load(_images.get(position))    //images에 저장된 url로 서버에서 가져오기
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                            imageView.setImage(ImageSource.bitmap(bitmap));
                            thumbView.setImageBitmap(bitmap);
                        }
                    });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }
}
