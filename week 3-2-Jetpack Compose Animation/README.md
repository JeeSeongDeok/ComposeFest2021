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

## 5. Animating content size change
Column에서 아이템 클릭 시 padding 적용
```kotlin
Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .animateContentSize()
) {
    // ... the title and the body
}
```
<p align="center"><img src="https://developer.android.com/codelabs/jetpack-compose-animation/img/c0ad7381779fcb09.gif?authuser=4"></p>

## 6. Multiple value animation
```kotlin
val transition = updateTransition(
    tabPage,
    label = "Tab indicator"
)
val indicatorLeft by transition.animateDp(
    transitionSpec = {
        if (TabPage.Home isTransitioningTo TabPage.Work) {
            // Indicator moves to the right.
            // The left edge moves slower than the right edge.
            spring(stiffness = Spring.StiffnessVeryLow)
        } else {
            // Indicator moves to the left.
            // The left edge moves faster than the right edge.
            spring(stiffness = Spring.StiffnessMedium)
        }
    },
    label = "Indicator left"
) { page ->
    tabPositions[page.ordinal].left
}
val indicatorRight by transition.animateDp(
    transitionSpec = {
        if (TabPage.Home isTransitioningTo TabPage.Work) {
            // Indicator moves to the right
            // The right edge moves faster than the left edge.
            spring(stiffness = Spring.StiffnessMedium)
        } else {
            // Indicator moves to the left.
            // The right edge moves slower than the left edge.
            spring(stiffness = Spring.StiffnessVeryLow)
        }
    },
    label = "Indicator right"
) { page ->
    tabPositions[page.ordinal].right
}
val color by transition.animateColor(
    label = "Border color"
) { page ->
    if (page == TabPage.Home) Purple700 else Green800
}
```
<p align="center"><img src="https://developer.android.com/codelabs/jetpack-compose-animation/img/2ad4adbefce04ae2.gif?authuser=4"></p>

## 7. Repeated animation
유튜브 로딩때 화면 깜빡거리는 애니메이션 모션
```kotlin
val infiniteTransition = rememberInfiniteTransition()
val alpha by infiniteTransition.animateFloat(
    initialValue = 0f,
    targetValue = 1f,
    animationSpec = infiniteRepeatable(
        animation = keyframes {
            durationMillis = 1000
            0.7f at 500
        },
        repeatMode = RepeatMode.Reverse
    )
)
```
<p align="center"><img src="https://developer.android.com/codelabs/jetpack-compose-animation/img/ca4d1d5bfe87b2a9.gif?authuser=4"></p>

## 8. Gesture animation
```kotlin
private fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    // This `Animatable` stores the horizontal offset for the element.
    val offsetX = remember { Animatable(0f) }
    pointerInput(Unit) {
        // Used to calculate a settling position of a fling animation.
        val decay = splineBasedDecay<Float>(this)
        // Wrap in a coroutine scope to use suspend functions for touch events and animation.
        coroutineScope {
            while (true) {
                // Wait for a touch down event.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                // Interrupt any ongoing animation.
                offsetX.stop()
                // Prepare for drag events and record velocity of a fling.
                val velocityTracker = VelocityTracker()
                // Wait for drag events.
                awaitPointerEventScope {
                    horizontalDrag(pointerId) { change ->
                        // Record the position after offset
                        val horizontalDragOffset = offsetX.value + change.positionChange().x
                        launch {
                            // Overwrite the `Animatable` value while the element is dragged.
                            offsetX.snapTo(horizontalDragOffset)
                        }
                        // Record the velocity of the drag.
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                        // Consume the gesture event, not passed to external
                        change.consumePositionChange()
                    }
                }
                // Dragging finished. Calculate the velocity of the fling.
                val velocity = velocityTracker.calculateVelocity().x
                // Calculate where the element eventually settles after the fling animation.
                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
                // The animation should end as soon as it reaches these bounds.
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )
                launch {
                    if (targetOffsetX.absoluteValue <= size.width) {
                        // Not enough velocity; Slide back to the default position.
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    } else {
                        // Enough velocity to slide away the element to the edge.
                        offsetX.animateDecay(velocity, decay)
                        // The element was swiped away.
                        onDismissed()
                    }
                }
            }
        }
    }
        // Apply the horizontal offset to the element.
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
}
```
Custom으로 슬라이딩시 어디까지 옮길 수 있고 제한할 수 있는지 할 수 있는 애니메이션
<p align="center"><img src="https://developer.android.com/codelabs/jetpack-compose-animation/img/7cdefce823f6b9bd.png?authuser=4"></p>