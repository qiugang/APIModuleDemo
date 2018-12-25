# API Module Demo

[![Apache 2.0 License](https://img.shields.io/badge/license-Apache%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0.html) 

模块 API 化 Demo

### 背景

模块间拆分有个避免不了的痛点是: **模块公共代码的外露**。纯手工的维护模块暴露的接口比较繁琐且不太直观，下沉解决会造成 common lib 的冗杂和打破代码边界。最终 common lib 很有可能成为下一个大胖子。

微信在[微信Android模块化架构重构实践](https://mp.weixin.qq.com/s/6Q818XA5FaHd7jJMFBG60w)分享中提及的模块 api 化是非常棒的思路，模块公共代码的维护还是在所属模块内，大大提升开发体验。这个 demo 由此而来。

### demo

* ```plugin-xxx``` 的 module 均为业务 module，
* ```plugin-xxx-api``` 均为对应业务自动生成的 api module

module 中有需要向外提供的接口与数据结构，可将 java 、 kotlin 源代码文件 修改后缀为 ```.japi```、```kapi```（在 Android Studio/Editor File Type 中添加 ```.japi```、```kapi``` 作为 java 与 kotlin 文件类型即可高亮）, 在 project 同步时，会自动生成 module 的 api 子工程(```module-api```)供其他模块使用。同时 module 下的 ```sdk.gradle``` 将会作为 moudle-api 的 ```build.gradle```(目的在于保证最小化的依赖项被暴露出，这是需要手动维护的一个配置项)


需要使用该 module-api 的模块，添加如下配置即可:

```
apply plugin: 'com.android.library'
...
apply plugin: 'include-api'

dependencies {
  ...
  // 等同于 api project(':${module-name-api}')
  includeApi(':${module-name}')
}

```

ps: demo 以 Github V3 API 为例，module 划分仅为演示。另外 build demo 之前请前往 [Github Developers](https://github.com/settings/developers) 新建应用获取 ```Client ID``` 与 ``` Client Secret ```，之后配置到 ```plugin-login``` 的 ```build.gradle``` 中

详细可以查看 example~

### License

    Copyright 2018 qiugang(thisisqg@gmail.com)

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


