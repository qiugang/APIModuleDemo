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
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import io.letusplay.demo.plugin.login.Constants.CALLBACK_URI
import io.letusplay.demo.plugin.login.Constants.OAUTH_URL
import io.letusplay.demo.plugin.login.Constants.PARAM_CALLBACK_URI
import io.letusplay.demo.plugin.login.Constants.PARAM_CLIENT_ID
import io.letusplay.demo.plugin.login.Constants.PARAM_SCOPE
import io.letusplay.demo.plugin.login.Constants.SCOPES
import kotlinx.android.synthetic.main.activity_login.authLogin

class LoginActivity : FragmentActivity(), View.OnClickListener {

  private lateinit var loginVM: LoginVM

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

    loginVM = ViewModelProviders.of(this)
        .get(LoginVM::class.java)
        .apply {
          onLoginCallback = {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("hubhub://timeline")))
            finish()
          }
        }
    authLogin.setOnClickListener(this)
  }

  override fun onClick(v: View?) {
    when (v?.id) {
      R.id.authLogin -> launchWebOauthLogin()
      else -> {
      }
    }
  }

  override fun onNewIntent(intent: Intent?) {
    loginVM.onNewIntent(intent)
  }

  private fun launchWebOauthLogin() {
    val uri = Uri.parse(OAUTH_URL)
        .buildUpon()
        .apply {
          appendQueryParameter(PARAM_CLIENT_ID, BuildConfig.CLIENT_ID)
          appendQueryParameter(PARAM_SCOPE, SCOPES)
          appendQueryParameter(PARAM_CALLBACK_URI, CALLBACK_URI.toString())
        }
        .build()
    startActivity(Intent().apply {
      data = uri
      action = Intent.ACTION_VIEW
    })
  }

  override fun onDestroy() {
    loginVM.onLoginCallback = null
    super.onDestroy()
  }
}