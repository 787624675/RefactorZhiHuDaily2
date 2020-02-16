package com.zhihu.refactorzhihudaily.network

import com.zhihu.refactorzhihudaily.adapters.MultiItemAdapter
import com.zhihu.refactorzhihudaily.model.ModMainDetail
import com.zhihu.refactorzhihudaily.model.ModMainDetail.screenHeight
import com.zhihu.refactorzhihudaily.model.RemixItem
import com.zhihu.refactorzhihudaily.network.RetrofitClient.reqApi
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Experimental {
    var mAdapter : MultiItemAdapter
    constructor(mAdapter:MultiItemAdapter){
        this.mAdapter = mAdapter
    }
    fun getFirstTwoDayNews(callback:suspend (RefreshState<Unit>) -> Unit = {}){
        GlobalScope.launch (Dispatchers.Main){
            reqApi.getTodayNews().awaitAndHandle {
                callback(RefreshState.Failure(it))
            }?.let {
                ModMainDetail.remixList.clear()
                ModMainDetail.idList.clear()

                ModMainDetail.topImages = it.getTopImages()
                ModMainDetail.topNewsList = it.getTopNews()
                ModMainDetail.todayNewsList = it.getNews()
                val dataBeanBefore = reqApi.getBeforeNews(
                    ModMainDetail.todayNewsList!!.get(0).date).await()
                ModMainDetail.beforeNewsList = dataBeanBefore.getNews()
                if (!ModMainDetail.isSampleList(ModMainDetail.topNewsList)){
                    ModMainDetail.remixList.add(0,
                        RemixItem(
                            list = ModMainDetail.topNewsList,
                            screenHeight = screenHeight,
                            type = 1
                        )
                    )

                }
                if (!ModMainDetail.isSampleList(ModMainDetail.todayNewsList)){
                    ModMainDetail.todayNewsList!!.forEach {
                        ModMainDetail.remixList.add(
                            RemixItem(
                                title = it.title,
                                hint = it.hint,
                                imageUrl = it.imageUrl,
                                id = it.id,
                                date = it.date,
                                type = 3
                            )
                        )
                        ModMainDetail.idList.add(it.id)
                    }

                }
                if (!ModMainDetail.isSampleList(ModMainDetail.beforeNewsList)){
                    ModMainDetail.remixList.add(
                        RemixItem(
                            date = RetrofitClient.convertDateToChinese(
                                ModMainDetail.beforeNewsList!!.get(
                                    0
                                ).date
                            ),
                            type = 2
                        )
                    )
                    ModMainDetail.beforeNewsList!!.forEach {
                        ModMainDetail.remixList.add(
                            RemixItem(
                                title = it.title,
                                hint = it.hint,
                                imageUrl = it.imageUrl,
                                id = it.id,
                                date = it.date,
                                type = 3
                            )
                        )
                        ModMainDetail.idList.add(it.id)
                    }
                }
                launch (Dispatchers.Main){
                    mAdapter.notifyDataSetChanged()
                }

            }
        }

    }
}
//挪用了微北洋里的RefreshState
sealed class RefreshState<M> {
    class Success<M>(val message: M) : RefreshState<M>()
    class Failure<M>(val throwable: Throwable) : RefreshState<M>()
    class Refreshing<M> : RefreshState<M>()
}
//挪用了微北洋里的awaitAndHandle
suspend fun <T> Deferred<T>.awaitAndHandle(handler: suspend (Throwable) -> Unit = {}): T? =
    try {
        await()
    } catch (t: Throwable) {
        handler(t)
        null
    }