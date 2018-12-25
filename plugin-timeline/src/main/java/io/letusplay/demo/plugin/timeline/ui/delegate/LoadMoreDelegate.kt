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

package io.letusplay.demo.plugin.timeline.ui.delegate

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager

class LoadMoreDelegate(
  recyclerView: RecyclerView,
  loadMoreCallback: OnLoadMoreCallback?
) {
  private var loadMoreScrollListener: LoadMoreScrollListener

  var isEnable: Boolean
    get() = loadMoreScrollListener.isEnable
    set(enable) {
      loadMoreScrollListener.isEnable = enable
    }

  init {
    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    loadMoreScrollListener = LoadMoreScrollListener(layoutManager, loadMoreCallback)
    recyclerView.addOnScrollListener(loadMoreScrollListener)
  }

  class LoadMoreScrollListener(
    private val layoutManager: LinearLayoutManager,
    private val loadMoreCallBack: OnLoadMoreCallback?
  ) : RecyclerView.OnScrollListener() {

    var isEnable = true

    private var lastDy: Int = 0

    override fun onScrolled(
      recyclerView: RecyclerView,
      dx: Int,
      dy: Int
    ) {
      lastDy = dy
      if (isEnable) {
        if (dy <= 0 || loadMoreCallBack?.onLoading() == true)
          return
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        if (totalItemCount <= lastVisibleItemPosition + VISIBLE_THRESHOLD) {
          loadMoreCallBack?.onLoadMore()
        }
      }
    }

    companion object {
      const val VISIBLE_THRESHOLD = 5
    }
  }

  interface OnLoadMoreCallback {
    fun onLoading(): Boolean

    fun onLoadMore()
  }
}