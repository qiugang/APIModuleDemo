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

import io.letusplay.demo.plugin.AccountPlugin
import io.letusplay.demo.plugin.LoginPlugin
import io.letusplay.demo.plugin.TimelinePlugin

object ModuleInitializer {

  /**
   * 一般 router 框架会利用 apt 生成初始化代码，只需调用框架初始化函数即可
   * 不会因为初始化业务 module 导致主工程依赖具体的 module 代码
   *
   * demo 为了方便直接，这里就直接调用初始化代码了
   */
  fun init() {
    AccountPlugin.instance.init()
    LoginPlugin.instance.init()
    TimelinePlugin.instance.init()
  }
}
