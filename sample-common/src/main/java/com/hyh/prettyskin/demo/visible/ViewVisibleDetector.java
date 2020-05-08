package com.hyh.prettyskin.demo.visible;

import android.os.SystemClock;
import android.view.View;

import com.hyh.prettyskin.common.R;
import com.hyh.prettyskin.demo.utils.ThreadUtil;
import com.hyh.prettyskin.demo.utils.ViewUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @description 用于监听View的可见与不可见状态
 * @data 2019/6/3
 */
public class ViewVisibleDetector {

    public static void setVisibleHandler(View view, IVisibleHandler visibleHandler) {
        if (view == null) return;
        view.setTag(R.id.view_visible_handler_id, visibleHandler);
        register(view);
    }

    public static void addVisibleHandler(View view, IVisibleHandler visibleHandler) {
        if (view == null) return;
        List<IVisibleHandler> visibleHandlers = getVisibleHandlers(view);
        if (visibleHandlers == null) {
            visibleHandlers = new ArrayList<>();
            view.setTag(R.id.view_visible_handlers_id, visibleHandlers);
            visibleHandlers.add(visibleHandler);
        } else {
            if (!visibleHandlers.contains(visibleHandler)) {
                visibleHandlers.add(visibleHandler);
            }
        }
        register(view);
    }

    private static void register(View view) {
        Object tag1 = view.getTag(R.id.view_visible_view_tree_listener_id);
        if (!(tag1 instanceof ViewTreeListener)) {
            ViewTreeListener viewTreeListener = new ViewTreeListener(view);
            view.setTag(R.id.view_visible_view_tree_listener_id, viewTreeListener);
        }
        Object tag2 = view.getTag(R.id.view_visible_detector_id);
        if (!(tag2 instanceof ViewVisibleDetector)) {
            ViewVisibleDetector viewVisibleDetector = new ViewVisibleDetector(view);
            view.setTag(R.id.view_visible_detector_id, viewVisibleDetector);
            viewVisibleDetector.onDetect();
        }
    }

    private final Map<ItemVisibleStrategy, ItemVisibleInfo> mItemVisibleInfoMap = new HashMap<>();

    private final WeakReference<View> mViewRef;

    private IVisibleHandler mVisibleHandler;

    private ViewVisibleDetector(View view) {
        this.mViewRef = new WeakReference<>(view);
    }

    void onDetect() {
        View view = mViewRef.get();
        if (view == null) return;
        {
            IVisibleHandler visibleHandler = getVisibleHandler(view);
            if (visibleHandler != null) {
                if (mVisibleHandler != visibleHandler) {
                    reset();
                    mVisibleHandler = visibleHandler;
                }
                handleDetect(view, visibleHandler);
            }
        }
        {
            List<IVisibleHandler> visibleHandlers = getVisibleHandlers(view);
            if (visibleHandlers != null && !visibleHandlers.isEmpty()) {
                for (IVisibleHandler visibleHandler : visibleHandlers) {
                    handleDetect(view, visibleHandler);
                }
            }
        }
    }

    private void handleDetect(View view, IVisibleHandler exposureHandler) {
        List<ItemVisibleStrategy> itemVisibleStrategies = exposureHandler.getItemVisibleStrategies();
        if (itemVisibleStrategies == null || itemVisibleStrategies.isEmpty()) {
            return;
        }
        for (ItemVisibleStrategy itemVisibleStrategy : itemVisibleStrategies) {
            if (itemVisibleStrategy == null) continue;
            ItemVisibleInfo itemVisibleInfo = mItemVisibleInfoMap.get(itemVisibleStrategy);
            if (itemVisibleInfo == null) {
                itemVisibleInfo = new ItemVisibleInfo();
                mItemVisibleInfoMap.put(itemVisibleStrategy, itemVisibleInfo);
            }
            if (itemVisibleStrategy.requiredVisibleTimeMillis <= 0) {
                detectWithoutVisibleTimeMillis(view, exposureHandler, itemVisibleStrategy, itemVisibleInfo);
            } else {
                detectWithVisibleTimeMillis(view, exposureHandler, itemVisibleStrategy, itemVisibleInfo);
            }
        }
    }

    /**
     * called by {@linkplain ViewTreeListener#onViewDetachedFromWindow(android.view.View)}
     * 1. View#onViewDetachedFromWindow 后，不会走到 {@linkplain #onDetect} 这里，所以也不会重置 View 的可见状态
     * 2. View#onViewDetachedFromWindow 主动重置View的可见状态，并回调曝光工具 onViewInvisible
     */
    void onViewDetached() {
        View view = mViewRef.get();
        if (view == null) return;
        {
            IVisibleHandler visibleHandler = getVisibleHandler(view);
            if (visibleHandler != null) {
                handleDetached(view, visibleHandler);
            }
        }

        List<IVisibleHandler> visibleHandlers = getVisibleHandlers(view);
        if (visibleHandlers != null && !visibleHandlers.isEmpty()) {
            for (IVisibleHandler visibleHandler : visibleHandlers) {
                handleDetached(view, visibleHandler);
            }
        }
    }


    public void onScreenOff() {
        View view = mViewRef.get();
        if (view == null) return;
        {
            IVisibleHandler visibleHandler = getVisibleHandler(view);
            if (visibleHandler != null) {
                handleDetached(view, visibleHandler);
            }
        }

        List<IVisibleHandler> visibleHandlers = getVisibleHandlers(view);
        if (visibleHandlers != null && !visibleHandlers.isEmpty()) {
            for (IVisibleHandler visibleHandler : visibleHandlers) {
                handleDetached(view, visibleHandler);
            }
        }
    }


    private void handleDetached(View view, IVisibleHandler visibleHandler) {
        List<ItemVisibleStrategy> itemVisibleStrategies = visibleHandler.getItemVisibleStrategies();
        if (itemVisibleStrategies != null) {
            for (ItemVisibleStrategy itemVisibleStrategy : itemVisibleStrategies) {
                ItemVisibleInfo itemVisibleInfo = mItemVisibleInfoMap.get(itemVisibleStrategy);
                if (itemVisibleInfo == null) {
                    continue;
                }
                if (itemVisibleInfo.isVisible) {
                    itemVisibleInfo.isVisible = false;
                    visibleHandler.onViewInvisible(view, itemVisibleStrategy);
                }
                itemVisibleInfo.reset();
            }
        }
    }

    private void detectWithoutVisibleTimeMillis(View view,
                                                IVisibleHandler exposureHandler,
                                                ItemVisibleStrategy itemVisibleStrategy,
                                                ItemVisibleInfo itemVisibleInfo) {
        int requiredVisibleType = itemVisibleStrategy.requiredVisibleType;
        float visibleScale = getVisibleScale(view, requiredVisibleType);
        if (visibleScale <= 0.0f) {
            if (itemVisibleInfo.isVisible) {
                itemVisibleInfo.isVisible = false;
                exposureHandler.onViewInvisible(view, itemVisibleStrategy);
            }
        } else {
            if (visibleScale >= itemVisibleStrategy.requiredVisibleScale) {
                if (!itemVisibleInfo.isVisible) {
                    itemVisibleInfo.isVisible = true;
                    exposureHandler.onViewVisible(view, itemVisibleStrategy);
                }
            }
        }
    }

    private float getVisibleScale(View view, int requiredVisibleType) {
        float visibleScale;
        switch (requiredVisibleType) {
            case ItemVisibleStrategy.VISIBLE_TYPE_VERTICAL: {
                visibleScale = ViewUtil.getVerticalVisibleScale(view);
                break;
            }
            case ItemVisibleStrategy.VISIBLE_TYPE_HORIZONTAL: {
                visibleScale = ViewUtil.getHorizontalVisibleScale(view);
                break;
            }
            case ItemVisibleStrategy.VISIBLE_TYPE_ALL:
            default: {
                visibleScale = ViewUtil.getVisibleScale(view);
                break;
            }
        }
        return visibleScale;
    }

    private void detectWithVisibleTimeMillis(View view,
                                             IVisibleHandler exposureHandler,
                                             ItemVisibleStrategy itemVisibleStrategy,
                                             ItemVisibleInfo itemVisibleInfo) {
        int requiredVisibleType = itemVisibleStrategy.requiredVisibleType;
        float visibleScale = getVisibleScale(view, requiredVisibleType);

        if (visibleScale <= 0.0f) {
            // 理论上，这里只有 View 滑出屏幕后 且定时任务执行的时候才会走这里；
            // 实际在 View#onViewDetachedFromWindow 后就要重置状态
            if (itemVisibleInfo.isVisible) {
                itemVisibleInfo.isVisible = false;
                exposureHandler.onViewInvisible(view, itemVisibleStrategy);
            }
            itemVisibleInfo.reset();
        } else {
            if (visibleScale >= itemVisibleStrategy.requiredVisibleScale) {
                if (!itemVisibleInfo.isVisible) {
                    long currentTimeMillis = SystemClock.elapsedRealtime();
                    if (itemVisibleInfo.lastVisibleTimeMillis != 0) {
                        itemVisibleInfo.totalVisibleTimeMillis += (currentTimeMillis - itemVisibleInfo.lastVisibleTimeMillis);
                    }
                    itemVisibleInfo.lastVisibleTimeMillis = currentTimeMillis;
                    if (itemVisibleInfo.totalVisibleTimeMillis >= itemVisibleStrategy.requiredVisibleTimeMillis) {
                        itemVisibleInfo.isVisible = true;
                        exposureHandler.onViewVisible(view, itemVisibleStrategy);
                    } else {
                        if (!itemVisibleInfo.isPostDetectDelayed) {
                            itemVisibleInfo.isPostDetectDelayed = true;
                            ThreadUtil.postUiThreadDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    onDetect();
                                }
                            }, itemVisibleStrategy.requiredVisibleTimeMillis);
                        }
                    }
                }
            }
        }
    }

    private void reset() {
        mItemVisibleInfoMap.clear();
    }

    private static IVisibleHandler getVisibleHandler(View view) {
        Object tag = view.getTag(R.id.view_visible_handler_id);
        if (tag instanceof IVisibleHandler) {
            return (IVisibleHandler) tag;
        }
        return null;
    }


    @SuppressWarnings("all")
    private static List<IVisibleHandler> getVisibleHandlers(View view) {
        Object tag = view.getTag(R.id.view_visible_handlers_id);
        if (tag instanceof List) {
            return (List<IVisibleHandler>) tag;
        }
        return null;
    }

    private static class ItemVisibleInfo {

        boolean isVisible;

        long lastVisibleTimeMillis;

        int totalVisibleTimeMillis;

        boolean isPostDetectDelayed;

        void reset() {
            isVisible = false;
            lastVisibleTimeMillis = 0L;
            totalVisibleTimeMillis = 0;
            isPostDetectDelayed = false;
        }
    }
}