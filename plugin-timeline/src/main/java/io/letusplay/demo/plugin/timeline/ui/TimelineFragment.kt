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

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import io.letusplay.demo.plugin.timeline.R
import io.letusplay.demo.plugin.timeline.ui.items.EventItemViewBinder
import io.letusplay.demo.plugin.timeline.ui.items.EventItemViewBinder.EventModelWrapper
import kotlinx.android.synthetic.main.fragment_timeline.recyclerView
import io.letusplay.demo.plugin.timeline.ui.delegate.LoadMoreDelegate
import me.drakeet.multitype.MultiTypeAdapter

class TimelineFragment : ITimeFragment() {

  private lateinit var eventsVM: TimelineVM
  private lateinit var adapter: MultiTypeAdapter
  private lateinit var loadMoreDelegate: LoadMoreDelegate

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    eventsVM = ViewModelProviders.of(this)
        .get(TimelineVM::class.java)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_timeline, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    initBasic()
    eventsVM.executeLoadData()
  }

  private fun initBasic() {
    adapter = MultiTypeAdapter().apply {
      register(
          EventModelWrapper::class.java,
          EventItemViewBinder()
      )
    }
    recyclerView.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = this@TimelineFragment.adapter
    }
    loadMoreDelegate = LoadMoreDelegate(recyclerView, eventsVM.loadMoreCallback).apply {
      isEnable = true
    }
    eventsVM.run {
      events.observe(this@TimelineFragment, Observer<MutableList<Any>> {
        adapter.items = it
        adapter.notifyDataSetChanged()
      })
      hasNextPageCallback = {
        loadMoreDelegate.isEnable = it
        if (!it) {
          Toast.makeText(context, "没有更多啦~", Toast.LENGTH_SHORT)
              .show()
        }
      }
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    eventsVM.hasNextPageCallback = null
  }

  companion object {
    fun newInstance(): ITimeFragment {
      return TimelineFragment()
    }
  }
}