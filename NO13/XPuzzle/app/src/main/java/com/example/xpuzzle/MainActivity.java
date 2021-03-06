package com.example.xpuzzle;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import adapter.GridPicListAdapter;
import util.ScreenUtil;

public class MainActivity extends Activity implements View.OnClickListener {

    // 返回码：系统图库
    private static final int RESULT_IMAGE = 100;
    // 返回码：相机
    private static final int RESULT_CAMERA = 200;
    // IMAGE TYPE
    private static final String IMAGE_TYPE = "image/*";
    // Temp照片路径
    public static String TEMP_IMAGE_PATH;
//    File outputImage = new File(getExternalCacheDir(), "output_image.jpg");

    private Uri imageUir;

    // GridView 显示图片
    private GridView mGvPicList;
    private List<Bitmap> mPicList;
    // 主页图片资源ID
    private int[] mResPicId;
    // 显示Type
    private TextView mTvPuzzleMainTypeSelected;//难度选择
    private LayoutInflater mLayoutInflater;
    private PopupWindow mPopupWindow;
    private View mPopupView;
    private TextView mTvType2;
    private TextView mTvType3;
    private TextView mTvType4;
    // 游戏类型N*N
    private int mType = 2;
    // 本地图册、相机选择
    private String[] mCustomItems = new String[]{"本地图册", "相机拍照"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        TEMP_IMAGE_PATH = Environment.getExternalStorageDirectory().getPath() + "/temp.png";
//        TEMP_IMAGE_PATH = outputImage.getPath();
        mPicList = new ArrayList<Bitmap>();
        // 初始化Views
        initViews();
        // 数据适配器
        mGvPicList.setAdapter(new GridPicListAdapter(mPicList, MainActivity.this));
        // Item点击监听
        mGvPicList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mResPicId.length - 1){
                    // 选择本地图库 相机
                    showDialogCustom();
                }else {
                    // 选择默认图片
                    Intent intent = new Intent(MainActivity.this, PuzzleMain.class);
                    intent.putExtra("picSelectedID", mResPicId[position]);
                    intent.putExtra("mType", mType);
                    startActivity(intent);
                }
            }
        });

        /**
         * 显示难度Type
         */
        mTvPuzzleMainTypeSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 弹出popup window
                popupShow(v);
            }
        });
    }

    /**
     * 显示选择系统图库 相机对话框
     */
    private void showDialogCustom(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("选择：");
        builder.setItems(mCustomItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (0 == which){
                    // 本地图册
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }else {
                        openAlbum();
                    }
//                    Intent intent = new Intent(Intent.ACTION_PICK, null);//选择数据
//                    intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_TYPE);
//                    startActivityForResult(intent, RESULT_IMAGE);
                }else if (1 == which){
                    // 系统相机
                    File outputImage = new File(getExternalCacheDir(), "output_image.jpg");
                    TEMP_IMAGE_PATH = outputImage.getPath();
                    try {
                        if (outputImage.exists()){
                            outputImage.delete();
                        }
                        outputImage.createNewFile();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    if (Build.VERSION.SDK_INT >= 24){
                        imageUir = FileProvider.getUriForFile(MainActivity.this, "com.example.xpuzzle.fileprovider", outputImage);
                    }else {
                        imageUir = Uri.fromFile(outputImage);
                    }
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    Uri photoUri = Uri.fromFile(new File(TEMP_IMAGE_PATH));
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUir);
                    startActivityForResult(intent, RESULT_CAMERA);
                }
            }
        });
        builder.create().show();
    }

    private void openAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK, null);//选择数据
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_TYPE);
        startActivityForResult(intent, RESULT_IMAGE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    /**
     * 调用图库相机回调方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == RESULT_IMAGE && data != null){
                // 相册
                if (Build.VERSION.SDK_INT >= 19){
                    //4.4及以上系统使用此方法处理图片
                    handleImageOnKitKat(data);
                }else {
                    //4.4以下系统使用此方法处理图片
                    handleImageBeforeKitKat(data);
                }
//                cursor.moveToFirst();//指向查询结果的第一个位置
//                String imagePath = cursor.getString(cursor.getColumnIndex("_data"));
//                Intent intent = new Intent(MainActivity.this, PuzzleMain.class);
//                intent.putExtra("mPicPath", imagePath);
//                intent.putExtra("mType", mType);
//                cursor.close();
//                startActivity(intent);
            }else if (requestCode == RESULT_CAMERA){
                // 相机
//                try {
//                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUir));
//                }catch (FileNotFoundException e){
//                    e.printStackTrace();
//                }
                Intent intent = new Intent(MainActivity.this, PuzzleMain.class);
                intent.putExtra("mPicPath", TEMP_IMAGE_PATH);
                intent.putExtra("mType", mType);
                startActivity(intent);
            }
        }
    }

    private void handleImageOnKitKat(Intent data){
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];//解析数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri, null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection){
        String path = null;
        Cursor cursor = this.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath){
        if (imagePath != null){
            Intent intent = new Intent(MainActivity.this, PuzzleMain.class);
            intent.putExtra("mPicPath", imagePath);
            intent.putExtra("mType", mType);
            startActivity(intent);
        }else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT);
        }
    }

    /**
     *显示点击选择难度的popup window
     *
     * @param view popup window
     */
    private void popupShow(View view){
        int density = (int) ScreenUtil.getDeviceDensity(this);
        // 显示popup window
        mPopupWindow = new PopupWindow(mPopupView, 200 * density, 50 * density);
        mPopupWindow.setFocusable(true);//设置此参数获得焦点,否则无法点击,
        mPopupWindow.setOutsideTouchable(true);//是用来判断点击当前的popupWindow区域外的touch事件是否有效
        // 透明背景
        Drawable transpent = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(transpent);
        // 获取位置
        int[] location = new int[2];
        view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        mPopupWindow.showAtLocation(//弹出的Window坐标
                view,
                Gravity.NO_GRAVITY,//在屏幕左上角
                location[0] - 40 * density,//x的偏移量
                location[1] + 30 * density);//y的偏移量
    }

    /**
     * 初始化initViews()
     */
    private void initViews(){
        mGvPicList = (GridView) findViewById(R.id.gv_xpuzzle_main_pic_list);
        // 初始化Bitmap数据
        mResPicId = new int[]{
                R.drawable.pic1,R.drawable.pic2,R.drawable.pic3,
                R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,
                R.drawable.pic7,R.drawable.pic8,R.drawable.pic9,
                R.drawable.pic10,R.drawable.pic11,R.drawable.pic12,
                R.drawable.pic13,R.drawable.pic14,R.drawable.pic15,
                R.mipmap.ic_launcher};
        Bitmap[] bitmaps = new Bitmap[mResPicId.length];
        for (int i = 0;i < bitmaps.length; i++){
            bitmaps[i] = BitmapFactory.decodeResource(getResources(), mResPicId[i]);
            mPicList.add(bitmaps[i]);
        }
        // 显示type
        mTvPuzzleMainTypeSelected = (TextView) findViewById(R.id.tv_puzzle_main_type_selected);
        mLayoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        // mType view
        mPopupView = mLayoutInflater.inflate(R.layout.xpuzzle_main_type_selected, null);
        mTvType2 = (TextView) mPopupView.findViewById(R.id.tv_main_type_2);
        mTvType3 = (TextView) mPopupView.findViewById(R.id.tv_main_type_3);
        mTvType4 = (TextView) mPopupView.findViewById(R.id.tv_main_type_4);
        // 监听事件
        mTvType2.setOnClickListener(this);
        mTvType3.setOnClickListener(this);
        mTvType4.setOnClickListener(this);
    }

    /**
     * popup window item点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            // Type
            case R.id.tv_main_type_2:
                mType = 2;
                mTvPuzzleMainTypeSelected.setText("2 X 2");
                break;
            case R.id.tv_main_type_3:
                mType = 3;
                mTvPuzzleMainTypeSelected.setText("3 X 3");
                break;
            case R.id.tv_main_type_4:
                mType = 4;
                mTvPuzzleMainTypeSelected.setText("4 X 4");
                break;
            default:
                break;
        }
        mPopupWindow.dismiss();
    }
}
