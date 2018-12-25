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

package io.letusplay.demo.plugin.timeline.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.letusplay.demo.plugin.timeline.R
import io.letusplay.demo.plugin.timeline.model.EventType
import io.letusplay.demo.plugin.timeline.model.GithubEvent
import io.letusplay.demo.plugin.timeline.model.transEvent
import io.letusplay.demo.plugin.timeline.ui.items.EventItemViewBinder.EventModelWrapper
import io.letusplay.demo.plugin.timeline.ui.items.EventItemViewBinder.ViewHolder
import io.letusplay.demo.plugin.timeline.util.DateUtil
import me.drakeet.multitype.ItemViewBinder

class EventItemViewBinder : ItemViewBinder<EventModelWrapper, ViewHolder>() {

  override fun onCreateViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup
  ): ViewHolder {
    return ViewHolder(
        inflater.inflate(R.layout.recycler_item_event, parent, false)
    )
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    item: EventModelWrapper
  ) {
    holder.run {
      nickname.text = item.data.actor?.displayLogin
      actionDesc.text = item.actionText
      eventTime.text = item.showTime
      Picasso.get()
          .load(item.data.actor?.avatarUrl)
          .into(avatar)
    }
  }

  @Suppress("HasPlatformType")
  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val avatar = itemView.findViewById<ImageView>(R.id.avatar)
    val nickname = itemView.findViewById<TextView>(R.id.nickname)
    val actionDesc = itemView.findViewById<TextView>(R.id.actionDesc)
    val eventTime = itemView.findViewById<TextView>(R.id.eventTime)
  }

  class EventModelWrapper(var data: GithubEvent) {
    var showTime: String? = ""
    var actionText: String? = ""

    init {
      actionText = data.run {
        if (type == EventType.FollowEvent.name) {
          "${transEvent(type)} ${actor?.displayLogin}"
        } else {
          "${transEvent(type)} ${repo?.name}"
        }
      }
      showTime = data.createdTime?.run {
        DateUtil.formatEventTime(this)
      }
    }
  }
}