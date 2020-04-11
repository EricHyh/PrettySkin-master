package com.hyh.prettyskin.demo.multiitem;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @description
 * @data 2017/5/18
 */

public abstract class MultiItemFactory<T> {

    private Context mContext;

    private List<T> mList;

    public List<T> getList() {
        return mList;
    }

    private MultiAdapterClient mAdapterClient;

    protected abstract ItemHolder<T> onCreateViewHolder(ViewGroup parent, int viewType);

    protected Context getContext() {
        return mContext;
    }

    protected void setup(Context context, MultiAdapterClient multiAdapterClient) {
        this.mContext = context;
        this.mAdapterClient = multiAdapterClient;
    }

    protected void onBindViewHolder(ItemHolder<T> holder, int position) {
        holder.onBindViewHolder(mList, position);
    }

    protected int getSpanSize(int spanCount, int position) {
        return 1;
    }

    protected abstract int getItemViewType(int position);

    int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void setDataList(List<T> list) {
        int oldSize = this.mList == null ? 0 : this.mList.size();
        this.mList = list;
        int currentSize = list == null ? 0 : list.size();
        if (mAdapterClient != null) {
            mAdapterClient.notifyDataSetChanged(mAdapterClient.getModulePosition(this), oldSize, currentSize);
        }
    }

    public void addDataList(List<T> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (this.mList == null) {
            setDataList(list);
        } else {
            int oldSize = mList.size();
            mList.addAll(list);
            if (mAdapterClient != null) {
                mAdapterClient.notifyItemRangeInserted(mAdapterClient.getModulePosition(this), oldSize, list.size());
            }
        }
    }

    public void addData(T t) {
        if (t == null) {
            return;
        }
        if (this.mList == null) {
            ArrayList<T> list = new ArrayList<>();
            list.add(t);
            setDataList(list);
        } else {
            int oldSize = mList.size();
            mList.add(t);
            if (mAdapterClient != null) {
                mAdapterClient.notifyItemRangeInserted(mAdapterClient.getModulePosition(this), oldSize, 1);
            }
        }
    }

    public void insertDataList(List<T> list, int position) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (this.mList == null) {
            setDataList(list);
        } else {
            int oldSize = mList.size();
            int insertPosition = position >= oldSize ? oldSize : position;
            mList.addAll(insertPosition, list);
            if (mAdapterClient != null) {
                mAdapterClient.notifyItemRangeInserted(mAdapterClient.getModulePosition(this), insertPosition, list.size());
            }
        }
    }

    public boolean insertData(T t, int position) {
        if (t == null) {
            return false;
        }
        if (this.mList == null) {
            ArrayList<T> list = new ArrayList<>();
            list.add(t);
            setDataList(list);
            return true;
        } else {
            int oldSize = mList.size();
            if (position <= oldSize) {
                mList.add(position, t);
                if (mAdapterClient != null) {
                    mAdapterClient.notifyItemRangeInserted(mAdapterClient.getModulePosition(this), position, 1);
                }
                return true;
            }
        }
        return false;
    }

    public T removeDataByPosition(int position) {
        if (this.mList == null || position >= this.mList.size()) {
            return null;
        }
        T remove = this.mList.remove(position);
        if (mAdapterClient != null) {
            mAdapterClient.notifyItemRemoved(mAdapterClient.getModulePosition(this), position);
        }
        return remove;
    }

    public int removeData(T t) {
        int index = -1;
        if (this.mList == null || (index = this.mList.indexOf(t)) < 0) {
            return index;
        }
        mList.remove(t);
        if (mAdapterClient != null) {
            mAdapterClient.notifyItemRemoved(mAdapterClient.getModulePosition(this), index);
        }
        return index;
    }

    public void updateItem(int index) {
        if (this.mList == null || index >= this.mList.size()) {
            return;
        }
        if (mAdapterClient != null) {
            mAdapterClient.notifyItemChanged(mAdapterClient.getModulePosition(this), index);
        }
    }

    public void updateItem(T t, int index) {
        if (this.mList == null || index >= this.mList.size()) {
            return;
        }
        this.mList.remove(index);
        this.mList.add(index, t);
        if (mAdapterClient != null) {
            mAdapterClient.notifyItemChanged(mAdapterClient.getModulePosition(this), index);
        }
    }

    public void updateRangeItem(int startIndex, int itemCount) {
        if (this.mList == null || startIndex >= this.mList.size()) {
            return;
        }
        itemCount = (this.mList.size() - startIndex < itemCount) ? this.mList.size() - startIndex : itemCount;
        if (mAdapterClient != null) {
            mAdapterClient.notifyItemRangeChanged(mAdapterClient.getModulePosition(this), startIndex, itemCount);
        }
    }

    protected void onDestroy() {
        final List<T> list = mList;
        mList = null;
        if (list != null) {
            list.clear();
        }
        mContext = null;
        mAdapterClient = null;
    }

    interface MultiAdapterClient {

        int getModulePosition(MultiItemFactory module);

        void notifyDataSetChanged(int modulePosition, int oldSize, int currentSize);

        void notifyItemRangeInserted(int modulePosition, int insertPosition, int size);

        void notifyItemRemoved(int modulePosition, int index);

        void notifyItemChanged(int modulePosition, int index);

        void notifyItemRangeChanged(int modulePosition, int startIndex, int itemCount);
    }
}
