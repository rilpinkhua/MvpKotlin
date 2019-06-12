package com.leifu.mvpkotlin.presenter

import android.util.Log
import com.leifu.mvpkotlin.base.BaseRxPresenter
import com.leifu.mvpkotlin.bean.ObjectBean
import com.leifu.mvpkotlin.net.response.BaseBean
import com.leifu.mvpkotlin.net.rx.RetrofitManager
import com.leifu.mvpkotlin.net.rx.RxManage
import com.leifu.mvpkotlin.net.except.ExceptionHandle
import com.leifu.mvpkotlin.presenter.contract.ObservableContract

/**
 *创建人:雷富
 *创建时间:2019/6/6 13:57
 *描述:
 */
class ObservablePresenter : BaseRxPresenter<ObservableContract.View>(), ObservableContract.Presenter {


    override fun getObjectData() {
        Log.e("okhttp", "getCategoryData")
        mView?.showLoading()
        addSubscription(
            RetrofitManager.apiService.getObservableObjectData()
                .compose(RxManage.rxSchedulerObservableHelper())
                .compose(RxManage.handleObservableResult<ObjectBean>())
                .subscribe({
                    run { ->
                        val b = it is ObjectBean
                        val c = it is BaseBean
                        Log.e("ok", it.data.appId + it.code + b + c)
                        mView?.showObjectData(it)
                        mView?.dismissLoading()
                    }
                },
                    { t ->
                        mView?.dismissLoading()
                        mView?.let { ExceptionHandle.handleException(t, it) }
                    })
        )
    }
}