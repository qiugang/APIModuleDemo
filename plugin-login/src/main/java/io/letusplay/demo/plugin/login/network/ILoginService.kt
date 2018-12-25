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

import io.letusplay.demo.plugin.account.UserProfile
import io.letusplay.demo.plugin.login.model.GithubToken
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ILoginService {

  @GET("/user")
  fun getUser(): Single<UserProfile>

  @POST("/login/oauth/access_token")
  @Headers("Accept: application/json")
  @FormUrlEncoded
  fun getAccessToken(
    @Field("client_id")
    clientId: String,
    @Field("client_secret")
    clientSecret: String,
    @Field("code")
    paramCode: String?
  ): Single<GithubToken>
}