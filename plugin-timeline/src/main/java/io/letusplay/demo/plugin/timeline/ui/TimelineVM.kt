/*
 * Copyright 2018 qiugang(thisisqg@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.letusplay.demo.plugin.timeline.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.letusplay.demo.base.Bumblebee
import io.letusplay.demo.plugin.account.IAccount
import io.letusplay.demo.plugin.timeline.network.EventService
import io.letusplay.demo.plugin.timeline.ui.items.EventItemViewBinder.EventModelWrapper
import io.letusplay.demo.plugin.timeline.ui.delegate.LoadMoreDelegate
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.drakeet.multitype.Items

@Suppress("PrivatePropertyName")
class TimelineVM : ViewModel() {
  // Github v3 events api only can fetch 300 events
  private var PAGE_SIZE_LIMIT = 30
  private var PAGE_LIMIT = 10

  private var loadDisposable: Disposable? = null
  private var data = Items()
  private var page = 1
  private var loading = false

  var hasNextPageCallback: ((Boolean) -> Unit)? = null
  var events = MutableLiveData<MutableList<Any>>()

  var loadMoreCallback = object : LoadMoreDelegate.OnLoadMoreCallback {
    override fun onLoading(): Boolean {
      return loading
    }

    override fun onLoadMore() {
      loadNextPage()
    }
  }

  fun executeLoadData() {
    loadData(page)
  }

  fun loadNextPage() {
    loadData(page + 1)
  }

  private fun loadData(pageValue: Int) {
    loading = true
    loadDisposable = EventService.PROVIDER
        .getPublicEvents(Bumblebee.get().visit(IAccount::class.java)?.userName, pageValue)
        .subscribeOn(Schedulers.io())
        .filter { mutableList ->
          if (pageValue == 0) {
            data.clear()
          }
          mutableList.run {
            forEach {
              data.add(EventModelWrapper(it))
            }
          }
          true
        }
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({ mutableList ->
          mutableList?.run {
            page = pageValue
            events.value = data
          }
          hasNextPageCallback?.invoke(pageValue < PAGE_LIMIT)
          loading = false
        }, {
          loading = false
        })
  }

  override fun onCleared() {
    loadDisposable?.dispose()
  }
}