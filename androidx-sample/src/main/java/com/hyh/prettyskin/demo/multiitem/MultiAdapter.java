package com.hyh.prettyskin.demo.multiitem;


import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

/**
 * @author eric
 * @description 一个用于处理多数据来源、多类型Item的Adapter
 * @data 2017/5/18
 */
public class MultiAdapter extends RecyclerView.Adapter<ItemHolder> {

    private static final String TAG = "MultiAdapter";

    private List<MultiItemFactory> mFactoryList = new ArrayList<>();

    private SparseArray<ItemTypeInfo> mTypeInfoArray = new SparseArray<>();

    private Map<MultiItemFactory, MultiItemTypeFactory> mItemTypeFactoryMap = new HashMap<>();

    private List<ItemHolderReference> mItemHolderReferences = new ArrayList<>();

    private ScrollListener mScrollListener = new ScrollListener();

    private Context mContext;

    public MultiAdapter(Context context) {
        this.mContext = context;
    }

    public <T> void addMultiModule(MultiItemFactory<T> multiItemFactory) {
        multiItemFactory.setup(mContext, mMultiAdapterClient);
        mFactoryList.add(multiItemFactory);
    }

    public <T> void addMultiModule(MultiItemFactory<T> multiItemFactory, int index) {
        int size = mFactoryList.size();
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
        multiItemFactory.setup(mContext, mMultiAdapterClient);
        mFactoryList.add(index, multiItemFactory);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTypeInfo itemTypeInfo = mTypeInfoArray.get(viewType);
        ItemHolder itemHolder;
        if (itemTypeInfo != null) {
            itemHolder = itemTypeInfo.multiItemFactory.onCreateViewHolder(parent, itemTypeInfo.itemType);
        } else {
            itemHolder = new EmptyHolder(new View(mContext));
        }
        ItemHolderReference itemHolderReference = new ItemHolderReference(itemHolder);
        if (!mItemHolderReferences.contains(itemHolderReference)) {
            mItemHolderReferences.add(itemHolderReference);
        }
        return itemHolder;
    }


    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if (position == RecyclerView.NO_POSITION) {
            return;
        }
        for (MultiItemFactory multiItemFactory : mFactoryList) {
            int itemCount = multiItemFactory.getItemCount();
            if (position <= itemCount - 1) {
                multiItemFactory.onBindViewHolder(holder, position);
                return;
            } else {
                position -= itemCount;
            }
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        for (MultiItemFactory multiItemFactory : mFactoryList) {
            itemCount += multiItemFactory.getItemCount();
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == RecyclerView.NO_POSITION) {
            return super.getItemViewType(position);
        }
        final int originalPosition = position;
        for (MultiItemFactory multiItemFactory : mFactoryList) {
            int itemCount = multiItemFactory.getItemCount();
            if (position <= itemCount - 1) {
                MultiItemTypeFactory multiItemTypeFactory = mItemTypeFactoryMap.get(multiItemFactory);
                if (multiItemTypeFactory == null) {
                    multiItemTypeFactory = new MultiItemTypeFactory(multiItemFactory);
                    mItemTypeFactoryMap.put(multiItemFactory, multiItemTypeFactory);
                }
                int itemViewType = multiItemFactory.getItemViewType(position);
                ItemTypeInfo itemTypeInfo = multiItemTypeFactory.obtain(itemViewType);
                int realItemViewType = System.identityHashCode(itemTypeInfo);
                if (mTypeInfoArray.get(realItemViewType) == null) {
                    mTypeInfoArray.put(realItemViewType, itemTypeInfo);
                }
                return realItemViewType;
            } else {
                position -= itemCount;
            }
        }
        return super.getItemViewType(originalPosition);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(mScrollListener);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position == RecyclerView.NO_POSITION) {
                        return 1;
                    }
                    for (MultiItemFactory multiItemFactory : mFactoryList) {
                        int itemCount = multiItemFactory.getItemCount();
                        if (position <= itemCount - 1) {
                            return multiItemFactory.getSpanSize(gridManager.getSpanCount(), position);
                        } else {
                            position -= itemCount;
                        }
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        recyclerView.removeOnScrollListener(mScrollListener);
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ItemHolder holder) {
        super.onViewAttachedToWindow(holder);
        if (isStaggeredGridLayout(holder)) {
            handleLayoutIsStaggeredGridLayout(holder, holder.getLayoutPosition());
        }
        holder.onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ItemHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.onViewDetachedFromWindow();
    }

    private boolean isStaggeredGridLayout(ItemHolder holder) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        return layoutParams instanceof StaggeredGridLayoutManager.LayoutParams;
    }

    private void handleLayoutIsStaggeredGridLayout(ItemHolder holder, int position) {
        for (MultiItemFactory multiItemFactory : mFactoryList) {
            int itemCount = multiItemFactory.getItemCount();
            if (position <= itemCount - 1) {
                break;
            } else {
                position -= itemCount;
            }
        }
        if (holder.isFullSpan(position)) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
            p.setFullSpan(true);
        }
    }

    @Override
    public void onViewRecycled(@NonNull ItemHolder holder) {
        super.onViewRecycled(holder);
        holder.onRecycled();
    }

    public void destroy() {
        mItemTypeFactoryMap.clear();
        mTypeInfoArray.clear();
        if (mFactoryList != null && !mFactoryList.isEmpty()) {
            for (MultiItemFactory multiItemFactory : mFactoryList) {
                multiItemFactory.onDestroy();
            }
            mFactoryList.clear();
        }
        if (mItemHolderReferences != null && !mItemHolderReferences.isEmpty()) {
            for (ItemHolderReference itemHolderReference : mItemHolderReferences) {
                ItemHolder itemHolder = itemHolderReference.get();
                if (itemHolder != null) {
                    itemHolder.onDestroy();
                }
            }
            mItemHolderReferences.clear();
        }
        mContext = null;
        mScrollListener = null;
    }

    private MultiItemFactory.MultiAdapterClient mMultiAdapterClient = new MultiItemFactory.MultiAdapterClient() {

        @Override
        public int getModulePosition(MultiItemFactory module) {
            return mFactoryList.indexOf(module);
        }

        @Override
        public void notifyDataSetChanged(int modulePosition, int oldSize, int currentSize) {
            int positionStart = getModuleFirstPosition(modulePosition);
            if (currentSize > oldSize) {
                if (oldSize == 0) {
                    MultiAdapter.this.notifyItemRangeChanged(positionStart, currentSize);
                } else {
                    MultiAdapter.this.notifyItemRangeChanged(positionStart, oldSize);
                    MultiAdapter.this.notifyItemRangeInserted(positionStart + oldSize, currentSize - oldSize);
                }
            } else {
                MultiAdapter.this.notifyItemRangeChanged(positionStart, currentSize);
                MultiAdapter.this.notifyItemRangeRemoved(positionStart + currentSize, oldSize - currentSize);
            }
        }

        @Override
        public void notifyItemRangeInserted(int modulePosition, int insertPosition, int size) {
            int positionStart = getModuleFirstPosition(modulePosition) + insertPosition;
            MultiAdapter.this.notifyItemRangeInserted(positionStart, size);
        }

        @Override
        public void notifyItemRemoved(int modulePosition, int index) {
            int removePosition = getModuleFirstPosition(modulePosition) + index;
            MultiAdapter.this.notifyItemRemoved(removePosition);
        }

        @Override
        public void notifyItemChanged(int modulePosition, int index) {
            int updatePosition = getModuleFirstPosition(modulePosition) + index;
            MultiAdapter.this.notifyItemChanged(updatePosition);
        }

        @Override
        public void notifyItemRangeChanged(int modulePosition, int startIndex, int itemCount) {
            int updatePosition = getModuleFirstPosition(modulePosition) + startIndex;
            MultiAdapter.this.notifyItemRangeChanged(updatePosition, itemCount);
        }
    };

    private int getModuleFirstPosition(int modulePosition) {
        int positionStart = 0;
        for (int i = 0; i < modulePosition; i++) {
            positionStart += mFactoryList.get(i).getItemCount();
        }
        return positionStart;
    }

    public int getModuleFirstPosition(MultiItemFactory multiItemFactory) {
        if (multiItemFactory == null || multiItemFactory.getItemCount() <= 0) {
            return -1;
        }
        int modulePosition = mFactoryList.indexOf(multiItemFactory);
        if (modulePosition < 0) {
            return -1;
        } else {
            return getModuleFirstPosition(modulePosition);
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + mFactoryList.size();
    }

    private class ScrollListener extends RecyclerView.OnScrollListener {

        private final static long POST_SCROLLED_TIME_INTERVAL = 150;

        private long mLastPostScrolledTimeMills;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager == null) {
                return;
            }
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
                int[] firstVisibleItemPositions = manager.findFirstVisibleItemPositions(null);
                int firstVisibleItemPosition = findMin(firstVisibleItemPositions);
                int[] lastVisibleItemPositions = manager.findLastVisibleItemPositions(null);
                int lastVisibleItemPosition = findMax(lastVisibleItemPositions);
                postScrollStateChanged(recyclerView, newState, firstVisibleItemPosition, lastVisibleItemPosition);
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                postScrollStateChanged(recyclerView, newState, firstVisibleItemPosition, lastVisibleItemPosition);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager == null) {
                return;
            }
            long currentTimeMillis = System.currentTimeMillis();
            if (Math.abs(currentTimeMillis - mLastPostScrolledTimeMills) < POST_SCROLLED_TIME_INTERVAL) {
                return;
            }
            mLastPostScrolledTimeMills = currentTimeMillis;
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
                int[] firstVisibleItemPositions = manager.findFirstVisibleItemPositions(null);
                int firstVisibleItemPosition = findMin(firstVisibleItemPositions);
                int[] lastVisibleItemPositions = manager.findLastVisibleItemPositions(null);
                int lastVisibleItemPosition = findMax(lastVisibleItemPositions);
                postScrolled(recyclerView, firstVisibleItemPosition, lastVisibleItemPosition);
            } else if (layoutManager instanceof LinearLayoutManager) {
                LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition();
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                postScrolled(recyclerView, firstVisibleItemPosition, lastVisibleItemPosition);
            }
        }

        private void postScrollStateChanged(RecyclerView recyclerView, int newState, int firstVisibleItemPosition, int lastVisibleItemPosition) {
            for (int index = firstVisibleItemPosition; index < lastVisibleItemPosition; index++) {
                ItemHolder itemHolder = (ItemHolder) recyclerView.findViewHolderForAdapterPosition(index);
                if (itemHolder == null) continue;
                itemHolder.onScrollStateChanged(newState);
            }
        }

        private void postScrolled(RecyclerView recyclerView, int firstVisibleItemPosition, int lastVisibleItemPosition) {
            for (int index = firstVisibleItemPosition; index < lastVisibleItemPosition; index++) {
                ItemHolder itemHolder = (ItemHolder) recyclerView.findViewHolderForAdapterPosition(index);
                if (itemHolder == null) continue;
                itemHolder.onScrolled(recyclerView.getScrollState());
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    private static class MultiItemTypeFactory {

        private final MultiItemFactory mMultiItemFactory;
        private final SparseArray<ItemTypeInfo> mTypeInfoArray;

        MultiItemTypeFactory(MultiItemFactory multiItemFactory) {
            this.mMultiItemFactory = multiItemFactory;
            this.mTypeInfoArray = new SparseArray<>();
        }

        @NonNull
        ItemTypeInfo obtain(int type) {
            ItemTypeInfo itemTypeInfo = mTypeInfoArray.get(type);
            if (itemTypeInfo == null) {
                itemTypeInfo = new ItemTypeInfo(mMultiItemFactory, type);
                mTypeInfoArray.put(type, itemTypeInfo);
            }
            return itemTypeInfo;
        }
    }

    private static class ItemHolderReference {

        private WeakReference<ItemHolder> mItemHolderRef;
        private int mItemHolderHashCode;

        public ItemHolderReference(@NonNull ItemHolder itemHolder) {
            this.mItemHolderRef = new WeakReference<>(itemHolder);
            this.mItemHolderHashCode = itemHolder.hashCode();
        }

        public ItemHolder get() {
            return mItemHolderRef.get();
        }

        @Override
        public int hashCode() {
            return mItemHolderHashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) return false;
            if (obj == this) return true;
            return obj.hashCode() == this.hashCode();
        }
    }
}