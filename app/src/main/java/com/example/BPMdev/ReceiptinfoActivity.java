package com.example.BPMdev;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

//import junit.framework.Assert;

import java.util.ArrayList;

import butterknife.InjectView;
import retrofit2.Retrofit;

public class ReceiptinfoActivity extends AppCompatActivity implements View.OnClickListener {
    public static Retrofit retrofit;
    Button receipt_info_button=null;
    TextView receipt_info_title=null;
    TextView receipt_info_buydate=null;
    TextView receipt_info_buyprice=null;
    TextView receipt_info_reqprice=null;
    TextView receipt_info_status=null;
    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";
    public static final String URL = "http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080/img/"; //url 앞부분

    private ArrayList<String> _images;
    private GalleryPagerAdapter _adapter;
    private String search;
    String itemno;

    @InjectView(R.id.pager)
    ViewPager _pager;
    @InjectView(R.id.thumbnails)
    LinearLayout _thumbnails;
    @InjectView(R.id.btn_close)


    ImageButton _closeButton;
    int i; //

    Intent intent; // 포스 받아올 시 사용
    String pos; // 카트 추가 시 사용

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt_info);
        findViewById(R.id.receipt_info_back).setOnClickListener(this);
        Intent intent = getIntent();
        if(intent!=null){
            itemno = intent.getStringExtra("itemno");
            Toast.makeText(getApplicationContext(), itemno, Toast.LENGTH_SHORT).show();
        }
        //pos=String.valueOf(intent.getExtras().getInt("pos"));

/*
        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
*/


        reset();
        SetInfoData();


        //book_info_image.setBackground(getDrawable(R.drawable.b)); 배경변화 시키는 코드
        //book_info_image.setBackground(getDrawable(R.drawable.b)); 배경변화 시키는 코드
    }

    private void reset()
    {
        receipt_info_button=(Button)findViewById(R.id.receipt_info_back);
        receipt_info_title=(TextView)findViewById(R.id.receipt_info_name);
        receipt_info_buydate=(TextView)findViewById(R.id.receipt_info_buydate);
        receipt_info_buyprice=(TextView)findViewById(R.id.receipt_info_buyprice);
        receipt_info_reqprice=(TextView)findViewById(R.id.receipt_info_reqprice);
        receipt_info_status=(TextView)findViewById(R.id.receipt_info_state);
    }

    private void SetInfoData()
    {
        //TODO : 이전 페이지에 받아온 값으로 디비 조회에서 데이터 셋팅.

 /*       String url[] = intent.getExtras().getString("BookUrl").split(":");   //jsp에서 받은 url 구분자로 나누기

        _images = new ArrayList<String>();

        for(int j=0 ;j<url.length; j++){
            _images.add(URL+url[j]);    // images Arraylist에 값 추가
        }

        //Assert.assertNotNull(_images);

        _adapter = new GalleryPagerAdapter(getApplicationContext());
        _pager.setAdapter(_adapter);    //adapter로 올리기
        _pager.setOffscreenPageLimit(6); // how many images to load into memory

        */
        receipt_info_button.setText("뒤로가기");
        receipt_info_title.setText("gg1");
        receipt_info_buydate.setText("gg2");
        receipt_info_buyprice.setText("gg3");
        receipt_info_reqprice.setText("gg4");
        receipt_info_status.setText("gg5");
    }


    @Override
    public void onClick(View v) {
        //함수정의 하고 사용할 함수 정의

            Intent intent = new Intent(getApplicationContext(),ReceiptList.class);
            intent.putExtra("search",search);
            startActivity(intent);
            finish();

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

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Back button pressed.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}
