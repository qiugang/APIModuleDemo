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

package io.letusplay.demo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import io.letusplay.demo.base.Bumblebee
import io.letusplay.demo.plugin.account.IAccount
import io.letusplay.demo.plugin.timeline.ITimelineFragFactory

class MainActivity : AppCompatActivity() {

  private var accountManager: IAccount? = null

  init {
    accountManager = Bumblebee.get()
        .visit(IAccount::class.java)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initLogic()
  }

  private fun initLogic() {
    if (accountManager?.hasLogin() == true) {
      val timelineFragment = Bumblebee.get()
          .visit(ITimelineFragFactory::class.java)
          ?.newTimelineFrag()
      timelineFragment?.run {
        supportFragmentManager.beginTransaction()
            .add(R.id.contentHolder, this)
            .commitNow()
      }
    } else {
      val intent = Intent(Intent.ACTION_VIEW, Uri.parse("hubhub://auth"))
      startActivity(intent)
      finish()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    if (accountManager?.hasLogin() == true) {
      menuInflater.inflate(R.menu.menu_home, menu)
      menu.findItem(R.id.menu_home_user).run {
        setTitle(accountManager?.userName)
      }
      return true
    }
    return false
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_home_logout -> {
        accountManager?.logout()
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("hubhub://auth")))
        finish()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
