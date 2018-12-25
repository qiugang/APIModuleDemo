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

package io.letusplay.demo.plugin.timeline.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class GithubEvent(
  var id: String = "",
  var type: String = "",
  var actor: Actor? = null,
  var repo: Repo? = null,
  var payload: Payload? = null,
  var public: Boolean = false,
  @SerializedName("created_at")
  var createdTime: Date? = null,
  var org: Org? = null
)

data class Repo(
  var id: Int = 0,
  var name: String = "",
  var url: String = ""
)

data class Payload(
  var action: String = ""
)

data class Actor(
  var id: Int = 0,
  var login: String = "",
  @SerializedName("display_login")
  var displayLogin: String = "",
  @SerializedName("gravatar_id")
  var gravatarId: String = "",
  var url: String = "",
  @SerializedName("avatar_url")
  var avatarUrl: String = ""
)

data class Org(
  var id: Int = 0,
  var login: String = "",
  @SerializedName("gravatar_id")
  var gravatarId: String = "",
  var url: String = "",
  @SerializedName("avatar_url")
  var avatarUrl: String = ""
)