package com.hyh.prettyskin.demo.multiitem.load;

/**
 * @author Administrator
 * @description
 * @data 2017/11/25
 */

public interface LoadState {

    int LOAD_STATE_IDLE = 1;            //初始状态

    int LOAD_STATE_REFRESHING = 2;      //正在刷新数据

    int LOAD_STATE_LOADING = 3;         //正在加载更多

    int LOAD_STATE_LOAD_SUCCESS = 4;    //加载更多成功

    int LOAD_STATE_LOAD_FAILURE = 5;    //加载更多失败

    int LOAD_STATE_NO_MORE = 6;         //没有更多数据了

}