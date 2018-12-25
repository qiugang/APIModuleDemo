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

package io.letusplay.demo.plugin.login.auth

import android.text.TextUtils
import io.letusplay.demo.base.Bumblebee
import io.letusplay.demo.plugin.account.IAccount
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class AuthInterceptor : Interceptor {
  private var auth = ""

  override fun intercept(chain: Chain): Response {
    val request = chain.request()
        .newBuilder()
        .apply {
          addHeader("Accept", "application/json")
          auth = Bumblebee.get().visit(IAccount::class.java)?.accessToken ?: ""
          if (!TextUtils.isEmpty(auth)) {
            addHeader("Authorization", "Bearer $auth")
          }
        }
        .build()
    return chain.proceed(request)
  }
}