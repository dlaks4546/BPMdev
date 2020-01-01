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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.drakeet.materialdialog.MaterialDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivityAdmin extends AppCompatActivity implements View.OnClickListener {
    public static Retrofit retrofit;
    TextView book_info_title=null;
    TextView book_info_author=null;
    TextView book_info_publish=null;
    TextView book_info_firstprice=null;
    TextView book_info_secondprice=null;
    TextView book_info_status=null;
    TextView book_info_category=null;
    TextView book_info_some=null;

    String booknumber;

    public static final String TAG = "GalleryActivity";
    public static final String EXTRA_NAME = "images";
    public static final String URL = "http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080/img/"; //url 앞부분

    private ArrayList<String> _images;
    private GalleryPagerAdapter _adapter;
    private String search;

    @InjectView(R.id.pager_admin)
    ViewPager _pager;
    @InjectView(R.id.thumbnails_admin)
    LinearLayout _thumbnails;
    @InjectView(R.id.btn_close_admin)

    ImageButton _closeButton;
    Button book_info_delete = null; //관리자 삭제 버튼 @@@@@@@@@@@@@
    Button book_info_revise = null; // 관리자 수정 버튼 @@@@@@@@@@@@@@@@@@
    int i; //
    MaterialDialog mMaterialDialog;
    Intent intent; // 포스 받아올 시 사용
    String pos; // 카트 추가 시 사용

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_admin);

        findViewById(R.id.book_info_delete).setOnClickListener(this);
        findViewById(R.id.book_info_revise).setOnClickListener(this);

        intent = getIntent();
        pos=String.valueOf(intent.getExtras().getInt("pos"));

        retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-52-11-216-22.us-west-2.compute.amazonaws.com:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ButterKnife.inject(this);
        _closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Close clicked");
                finish();
            }
        });

        reset();
        SetBookData();


        //book_info_image.setBackground(getDrawable(R.drawable.b)); 배경변화 시키는 코드
        //book_info_image.setBackground(getDrawable(R.drawable.b)); 배경변화 시키는 코드
    }

    private void reset()
    {
        book_info_title=(TextView)findViewById(R.id.booktitle_admin);
        book_info_author=(TextView)findViewById(R.id.author_admin);
        book_info_publish=(TextView)findViewById(R.id.publish_admin);
        book_info_firstprice=(TextView)findViewById(R.id.firstprice_admin);
        book_info_secondprice=(TextView)findViewById(R.id.secondprice_admin);
        book_info_status=(TextView)findViewById(R.id.status_admin);
        book_info_category=(TextView)findViewById(R.id.category_admin);
        book_info_some=(TextView)findViewById(R.id.some_admin);
        book_info_delete=(Button)findViewById(R.id.book_info_delete); //삭제버튼 설정@@@@@@@@@
        book_info_revise=(Button)findViewById(R.id.book_info_revise); //수정버튼 설정@@@@@@@@@@@
    }

    private void SetBookData()
    {
        String url[] = intent.getExtras().getString("BookUrl").split(":");   //jsp에서 받은 url 구분자로 나누기

        _images = new ArrayList<String>();

        for(int j=0 ;j<url.length; j++){
            _images.add(URL+url[j]);    // images Arraylist에 값 추가
        }

        //Assert.assertNotNull(_images);

        _adapter = new GalleryPagerAdapter(getApplicationContext());
        _pager.setAdapter(_adapter);    //adapter로 올리기
        _pager.setOffscreenPageLimit(6); // how many images to load into memory

        booknumber = intent.getExtras().getString("BookNumber");
        book_info_title.setText(intent.getExtras().getString("BookName"));
        book_info_author.setText(intent.getExtras().getString("BookAuthor"));
        book_info_publish.setText(intent.getExtras().getString("BookPublisher"));
        book_info_firstprice.setText(intent.getExtras().getString("BookCost"));
        book_info_secondprice.setText(intent.getExtras().getString("BookPrice"));
        book_info_status.setText(intent.getExtras().getString("BookState"));
        book_info_category.setText(intent.getExtras().getString("BookCategory"));
        book_info_some.setText(intent.getExtras().getString("BookComment"));
        search = intent.getExtras().getString("search");


        mMaterialDialog = new MaterialDialog(this)
                .setTitle("삭제하기")
                .setMessage("정말 삭제하시겠습니까?")
/*                .setPositiveButton("네", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        BookService BookApi = retrofit.create(BookService.class);
                        final Call<BookData> nicknameChangeCall = BookApi.DELETE_CALL("Delete",booknumber);
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
                        Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),StaggeredGridEmptyViewActivity.class);
                        intent.putExtra("search",search);
                        startActivity(intent);
                        finish();
                    }
                })*/
                .setNegativeButton("아니요", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mMaterialDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "삭제 취소", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {//어떤 버튼을 눌렀냐에 따라 이벤트처리@@@@@@@@@@@@@
        //함수정의 하고 사용할 함수 정의

        if(v.getId() ==  R.id.book_info_delete){
            mMaterialDialog.show();

        }
        else if(v.getId() ==  R.id.book_info_revise){

            Intent intent1 = new Intent(getApplicationContext(),ReviseBook.class);
            intent1.putExtra("BookNumber",intent.getExtras().getString("BookNumber"));
            intent1.putExtra("BookName",intent.getExtras().getString("BookName"));
            intent1.putExtra("BookAuthor",intent.getExtras().getString("BookAuthor"));
            intent1.putExtra("BookPublisher",intent.getExtras().getString("BookPublisher"));
            intent1.putExtra("BookCost",intent.getExtras().getString("BookCost"));
            intent1.putExtra("BookPrice",intent.getExtras().getString("BookPrice"));
            intent1.putExtra("BookState",intent.getExtras().getString("BookState"));
            intent1.putExtra("BookCategory",intent.getExtras().getString("BookCategory"));
            intent1.putExtra("BookComment",intent.getExtras().getString("BookComment"));
            intent1.putExtra("BookUrl",intent.getExtras().getString("BookUrl"));
            intent1.putExtra("search",search);
            startActivity(intent1);
            finish();
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
