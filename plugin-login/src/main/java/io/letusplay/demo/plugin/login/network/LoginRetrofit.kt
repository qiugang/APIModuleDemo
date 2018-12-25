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

package io.letusplay.demo.plugin.login.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class LoginRetrofit {

  companion object {
    @Volatile private var retrofit: Retrofit? = null

    private fun getOne(): Retrofit {
      return retrofit ?: synchronized(
          LoginRetrofit::class) {
        retrofit
            ?: buildRetrofit().also { retrofit = it }
      }
    }

    private fun buildRetrofit(): Retrofit {
      val client = OkHttpClient.Builder()
          .addInterceptor(HttpLoggingInterceptor().apply {
            level = BODY
          })
          .build()
      return Retrofit.Builder()
          .baseUrl("https://github.com")
          .client(client)
          .addConverterFactory(GsonConverterFactory.create())
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .build()
    }

    fun <T> service(clazz: Class<T>): T {
      return getOne()
          .create(clazz)
    }
  }
}