# ComposeFest2021
Animation 작업물입니다.
본 폴더를 Android Studio를 이용해서 열어주세요.
작업을 완료하고, push 해주세요.

# Compose Animation Codelab

This folder contains the source code for the
[Compose Animation](https://developer.android.com/codelabs/android-compose-animation)
codelab.

In this codelab, you will learn how to use some Animation APIs in Jetpack Compose.

## Screenshot

![Screenshot](screenshots/app.png)

## License

```
Copyright 2021 The Android Open Source Project

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
## c
```kotlin
val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)
```
animateColorAsState로 색 변경시 애니메이션을 넣을 수 있다. <br>
