package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.R.color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

import util.ScreenUtil;

/**
 * Created by 25400 on 2019/12/18.
 * 程序主界面数据适配器
 */

public class GridPicListAdapter extends BaseAdapter {

    // 映射List
    private List<Bitmap> picList;
    private Context context;

    public GridPicListAdapter(List<Bitmap> picList, Context context) {
        this.picList = picList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return picList.size();
    }

    @Override
    public Object getItem(int position) {
        return picList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv_pic_item = null;
        int density = (int) ScreenUtil.getDeviceDensity(context);
        if (convertView == null){
            iv_pic_item = new ImageView(context);
            // 设置布局 图片
            iv_pic_item.setLayoutParams(new GridView.LayoutParams(80 * density, 100 * density)); // 设置GirdView布局参数,横向布局的关键
            // 设置显示比例类型
            iv_pic_item.setScaleType(ImageView.ScaleType.FIT_XY);//不按比例缩放图片，目标是把图片塞满整个View。
        }else {
            iv_pic_item = (ImageView) convertView;
        }
        iv_pic_item.setBackgroundColor(color.black);
        iv_pic_item.setImageBitmap(picList.get(position));
        return iv_pic_item;
    }
}
