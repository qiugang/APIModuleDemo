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

package io.letusplay.demo.plugin

import io.letusplay.demo.base.Bumblebee
import io.letusplay.demo.base.ModulePlugin
import io.letusplay.demo.plugin.account.AccountManager
import io.letusplay.demo.plugin.account.IAccount

class AccountPlugin : ModulePlugin() {

  override fun init() {
    Bumblebee.get()
        .register(
            IAccount::class.java, AccountManager.getInstance())
  }

  companion object {
    var instance: AccountPlugin = AccountPlugin()
  }
}
