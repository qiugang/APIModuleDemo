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

package io.letusplay.demo.plugin.login

import android.content.Intent
import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import io.letusplay.demo.base.Bumblebee
import io.letusplay.demo.base.RetrofitCore
import io.letusplay.demo.plugin.account.IAccount
import io.letusplay.demo.plugin.login.network.ILoginService
import io.letusplay.demo.plugin.login.network.LoginRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginVM : ViewModel() {

  private var loginDispose: Disposable? = null
  var onLoginCallback: (() -> Unit)? = null

  @MainThread
  fun onNewIntent(intent: Intent?) {
    val data = intent?.data
    data?.run {
      if (Constants.CALLBACK_URI.host == host && Constants.CALLBACK_URI.path == path) {
        loginDispose = LoginRetrofit.service(
            ILoginService::class.java
        )
            .getAccessToken(
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET,
                getQueryParameter(Constants.PARAM_CODE)
            )
            .flatMap { githubToken ->
              Bumblebee.get()
                  .visit(IAccount::class.java)
                  ?.saveAccessToken(githubToken.accessToken)
              RetrofitCore.service(ILoginService::class.java)
                  .getUser()
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
              Bumblebee.get()
                  .visit(IAccount::class.java)
                  ?.saveUserName(it.login)
              onLoginCallback?.invoke()
            }, { it.printStackTrace() })
      }
    }
  }

  override fun onCleared() {
    loginDispose?.dispose()
  }
}