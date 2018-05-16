package com.narancommunity.app.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.narancommunity.app.MeItemInterface;
import com.narancommunity.app.R;
import com.narancommunity.app.common.adapter.EasyAdapter;

import java.util.ArrayList;
import java.util.List;

public class PicUploadAdapter extends EasyAdapter<String> {
    MeItemInterface meItemInterface;

    public PicUploadAdapter(Activity context, List<String> list) {
        super(context, list);
    }

    public static final int DEFAULT_MAX_PIC_COUNT = 9;
    private int mMaxPicCount = DEFAULT_MAX_PIC_COUNT;

    public int getMaxPicCount() {
        synchronized (mLock) {
            return this.mMaxPicCount;
        }
    }

    public void setMaxPicCount(int maxPicCount) {
        synchronized (mLock) {
            if (maxPicCount < 0) {
                throw new IllegalArgumentException("the max pic count cannot be less than zero");
            }
            this.mMaxPicCount = maxPicCount;
        }
    }

    public void delItem(MeItemInterface meItemInterface) {
        this.meItemInterface = meItemInterface;
    }

    private boolean mShowDel = false;

    public boolean isShowDel() {
        synchronized (mLock) {
            return this.mShowDel;
        }
    }

    public void setShowDel(boolean showDel) {
        synchronized (mLock) {
            if (this.mShowDel != showDel) {
                this.mShowDel = showDel;
                notifyDataSetChanged();
            }
        }
    }

    @Override
    public int getCount() {
        if (super.getCount() < mMaxPicCount) {
            return super.getCount() + 1;
        } else if (super.getCount() == mMaxPicCount) {
            return super.getCount();
        } else if (super.getCount() > mMaxPicCount) {
            return mMaxPicCount;
        }
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        if (super.getCount() < mMaxPicCount && position == super.getCount()) {
            return null;
        } else {
            return super.getItem(position);
        }
    }

    @Override
    protected View getItemView(final int position, View convertView, ViewGroup parent) {
        parent.setClipChildren(false);
        parent.setClipToPadding(false);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = getInflater().inflate(R.layout.pic_upload_grid_item, parent, false);
            viewHolder = new ViewHolder(convertView);
        } else {
            viewHolder = ViewHolder.getFromView(convertView);
        }
        if (super.getCount() < mMaxPicCount && position == super.getCount()) {
            viewHolder.mPicIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setShowDel(false);
                    myOnClickListener.onLastClick(position);
//                    MultiImageSelector.create().showCamera(true).multi().count(mMaxPicCount - PicUploadAdapter.super.getCount())
//                            .start((Activity) getContext(), AlteringClothesMeasuredDataFragment.REQUEST_IMAGE);
                }
            });
        } else {
            viewHolder.mPicIv.setOnClickListener(null);
        }
        viewHolder.mPicIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                setShowDel(!isShowDel());
                return true;
            }
        });
        viewHolder.mDelIv.setVisibility(
                isShowDel() && !(super.getCount() < mMaxPicCount && position == super.getCount()) ?
                        View.VISIBLE :
                        View.INVISIBLE);
        viewHolder.mDelIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(position);
                meItemInterface.OnDelClick(position);
            }
        });
        viewHolder.render((String) getItem(position));
        return convertView;
    }

    @Override
    protected void onLastItemVisible() {
        //do nothing
    }

    public static class ViewHolder {
        View mItemView;
        ImageView mPicIv;
        ImageView mDelIv;

        public ViewHolder(View itemView) {
            mItemView = itemView;
            mPicIv = (ImageView) itemView.findViewById(R.id.pic_iv);
            mDelIv = (ImageView) itemView.findViewById(R.id.del_iv);
            itemView.setTag(this);
        }

        public static ViewHolder getFromView(View view) {
            Object tag = view.getTag();
            if (tag instanceof ViewHolder) {
                return (ViewHolder) tag;
            } else {
                return new ViewHolder(view);
            }
        }

        public void render(String path) {
            if (!TextUtils.isEmpty(path)) {
                if (path.startsWith("http")) {
                    Glide.with(mPicIv.getContext()).load(path).centerCrop().into(mPicIv);
                } else if (path.startsWith("/")) {
                    Glide.with(mPicIv.getContext()).load("file://" + path).centerCrop().into(mPicIv);
                }
            } else {
                Glide.with(mPicIv.getContext()).load(R.mipmap.fabu_icon_tianjiazhaopian).centerCrop().into(mPicIv);
            }
        }
    }

    public ArrayList<String> getUploadPic() {
        if (getList() == null || getList().size() == 0)
            return new ArrayList<String>();
        else {
            int count = super.getCount();
            List<String> list = getList();
            list = list.subList(0, count);
            ArrayList<String> arrlist = new ArrayList<String>();
            for (int i = 0; i < list.size(); i++) {
                arrlist.add(list.get(i));
            }
            return arrlist;
        }
    }

    public interface MyOnClickListener {
        void onLastClick(int position);

        void onItemClick(int position);
    }

    MyOnClickListener myOnClickListener;

    public void setMyOnClickListener(MyOnClickListener onClickListener) {
        this.myOnClickListener = onClickListener;
    }

}
