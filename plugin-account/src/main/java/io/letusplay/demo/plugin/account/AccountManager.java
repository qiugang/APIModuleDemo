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

package io.letusplay.demo.plugin.account;

import android.text.TextUtils;
import com.tencent.mmkv.MMKV;

public class AccountManager implements IAccount {
  private static final String KEY_AUTH = "app_access_token";
  private static final String KEY_USER = "app_username";

  public static IAccount getInstance() {
    return HOLDER.instance;
  }

  private static class HOLDER {
    private static final AccountManager instance = new AccountManager();
  }

  private String accessToken;
  private String username;

  private AccountManager() {
    accessToken = MMKV.defaultMMKV().decodeString(KEY_AUTH, "");
    username = MMKV.defaultMMKV().decodeString(KEY_USER, "");
  }

  @Override public void saveAccessToken(String data) {
    this.accessToken = data;
    MMKV.defaultMMKV().encode(KEY_AUTH, data);
  }

  @Override public String getAccessToken() {
    return accessToken;
  }

  @Override public boolean hasLogin() {
    return !TextUtils.isEmpty(accessToken);
  }

  @Override public String getUserName() {
    return username;
  }

  @Override public void saveUserName(String data) {
    this.username = data;
    MMKV.defaultMMKV().encode(KEY_USER, data);
  }

  @Override public void logout() {
    this.accessToken = "";
    this.username = "";
    MMKV.defaultMMKV()
        .remove(KEY_AUTH)
        .remove(KEY_USER);
  }
}
