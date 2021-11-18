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
## 3. Animating a simple value change
```kotlin
val backgroundColor by animateColorAsState(if (tabPage == TabPage.Home) Purple100 else Green300)
```
<p align="center"><img src="https://developer.android.com/codelabs/jetpack-compose-animation/img/6946feb47acc2cc6.gif?authuser=4"></p>
animateColorAsState로 색 변경시 애니메이션을 넣을 수 있다. <br>

## 4. Animating visibility
Floating Button이 화면의 위치에 따라 텍스트 보여주는 여부를 결정함
```kotlin
AnimatedVisibility(extended) {
    Text(
        text = stringResource(R.string.edit),
        modifier = Modifier
            .padding(start = 8.dp, top = 3.dp)
    )
}
```
결과
<p align="center"><img src="https://developer.android.com/codelabs/jetpack-compose-animation/img/37a613b87156bfbe.gif?authuser=4"></p>

알림문 위에서 아래로 시간이 지난 후 아래에서 위로 설정하는 법
```kotlin
    AnimatedVisibility(
        visible = shown,
        enter = slideInVertically(
            // Enters by sliding down from offset -fullHeight to 0.
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ),
        exit = slideOutVertically(
            // Exits by sliding up from offset 0 to -fullHeight.
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing)
        )
    )
```
<p align="center"><img src="https://developer.android.com/codelabs/jetpack-compose-animation/img/76895615b43b9263.gif?authuser=4"></p>