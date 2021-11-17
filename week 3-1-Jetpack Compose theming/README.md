# ComposeFest2021
2021 DevFest ComposeFest 코드랩 Repo 입니다
본 폴더를 Android Studio를 이용해서 열어주세요.
작업을 완료하고, push 해주세요.

# Jetpack Compose Theming Codelab

This folder contains the source code for the [Jetpack Compose Theming codelab](https://developer.android.com/codelabs/jetpack-compose-theming).

In this codelab you will learn how to use [Jetpack Compose](https://developer.android.com/jetpack/compose)’s theming APIs to style your application. We’ll see how to customize colors, shapes and typography so that they’re used consistently throughout your application, supporting multiple themes such as light & dark theme.

## Screenshots

![Start state](screenshots/start.png "Before: unstyled app")
![Finish state, light](screenshots/finish_light.png "After: styled app")
![Finish state, dark](screenshots/finish_dark.png "After: dark theme")

## License

```
Copyright 2020 The Android Open Source Project

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

## 4. Define your theme
### Color
색깔을 지정할 수 있는 theme<br>
<p align="center"><img src="https://developer.android.com/codelabs/jetpack-compose-theming/img/16a0a3d57f49b71d.png?authuser=4"></p>

### Typography
텍스트를 스케일링 할 수 있음 <br>
<p align="center"><img src = "https://developer.android.com/codelabs/jetpack-compose-theming/img/985064b5f0dbd8bd.png?authuser=4"></p>

### Shape
네모난 사각형에서 끝부분을 자르거나 둥글게 만드는 모양을 관리할 수 있음 <br>
<p align="center"><img src = "https://developer.android.com/codelabs/jetpack-compose-theming/img/ebcdf2fb3364f0d3.png?authuser=4"></p>

### Dark 모드
Theme에 darkColor를 지정 후 사용자가 다크모드를 설정한 것을 확인한 후 지정한 색을
```kotlin
@Composable
fun JetnewsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        typography = JetnewsTypography,
        shapes = JetnewsShapes,
        content = content
    )
}
```

## 5. Working with Color
하드 코딩된 Color는 적용되지 않음<br>
```kotlin
Surface(color = Color.LightGray) {
  Text(
    text = "Hard coded colors don't respond to theme changes :("
    textColor = Color(0xffff00ff)
  )
}
```

### Content Alpha
중요한 것을 강조하고 싶을 때 사용<br>
```kotlin
CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
  Text(
    text = text,
    modifier = modifier
  )
}
```

## 6. Working with Text
Text에 여러 스타일을 추가하고 싶은 경우
```kotlin
val text = buildAnnotatedString {
  append("This is some unstyled text\n")
  withStyle(SpanStyle(color = Color.Red)) {
    append("Red text\n")
  }
  withStyle(SpanStyle(fontSize = 24.sp)) {
    append("Large text")
  }
}
```

## 7. Working with Shapes
테두리를 둥글게 혹은 다양한 방식으로 수정할 수 있음.<br>
ex) Surface, Modifier.clip, Modifier.background, Modifier.border etc.
```kotlin
@Composable
fun PostItem(...) {
  ...
  Image(
    painter = painterResource(post.imageThumbId),
+   modifier = Modifier.clip(shape = MaterialTheme.shapes.small)
  )
```

## 8. Component "Styles"
전체적인 스타일을 지정 후 Composable함수 안에서 나만의 텍스트 스타일을 지정할 수 있다.
```kotlin
@Composable
fun LoginButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        colors = ButtonConstants.defaultButtonColors(
            backgroundColor = MaterialTheme.colors.secondary
        ),
        onClick = onClick,
        modifier = modifier
    ) {
        ProvideTextStyle(...) { // set our own text style
            content()
        }
    }
}
```