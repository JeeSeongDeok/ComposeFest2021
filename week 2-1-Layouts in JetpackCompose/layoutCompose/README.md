## 3. Modifiers
Modifiers를 이용하면 컴포저블을 꾸밀 수 있다. 동작, 모양을 변경하고, 접근성 레입르과 같은 정보를 추가, 사용자 입력을 처리하거나 클릭 가능, 스크롤 가능, 드래그 가능 또는 확대/축소 가능과 같은 고급 상호 작용도 추가가 가능하다.<br>

수정자는 일반 코틀린 객체. 변수에 할당하고 재사용할 수 있다. <br>
대부분의 컴포저블은 선택적 modifier 매개변수를 받아 유연하게 만들어 호출자가 수정할 수 있다.<br>
자신만의 컴포저블을 생성하는 경우 수정자를 매개변수로 사용하는 것을 고려하고 기본적으로 숮어자로 설정하고 이를 함수의 루트 컴포저블에 적용한다.<br>

### Modifier의 순서 중요성
``` kotlin
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(modifier
        .padding(16.dp)
        .clickable(onClick = { /* Ignoring onClick */ })
    ) {
        ...
    }
}
```
위에 코드처럼 .padding 후 .clickable을 한 경우 클릭 가능한 수정자 전에 패딩이 적용이 되었기 때문에 아래의 사진처럼 작동함.<br>
<img src = "https://developer.android.com/codelabs/jetpack-compose-layouts/img/c15a1050b051617f.gif?authuser=4"><br>
따라서, 패딩과 clickable의 위치를 변경 시 아래처럼 됨<br>
<img src = "https://developer.android.com/codelabs/jetpack-compose-layouts/img/a1ea4c8e16d61ffa.gif?authuser=4"><br>

이런 방식을 이용하면 외부 간격을 추가한 뒤 클릭 시 둥근 모양을 보여주고 싶다면 아래와 같은 코드를 사용하면 된다.
``` kotlin
@Composable
fun PhotographerCard(modifier: Modifier = Modifier) {
    Row(modifier
        .padding(8.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colors.surface)
        .clickable(onClick = { /* Ignoring onClick */ })
        .padding(16.dp)
    ) {
        ...
    }
}
```

## 4.Slot API
사용자 정의 레이어를 가져오기 위해 Compose가 도입한 패턴이다.<br>

## 5. Material Components
### Scaffold
Material Design 레이아웃 구조로 UI를 구현할 수 있다. TopAppBar, BottomAppBar, FloatingActionBar 및 Drawer와 같은 것을 구성할 수 있다.

### TopAppBar
슬롯 API 패턴에 따라 TopAppBar의 제목 슬롯에 화면 제목이 있는 텍스트가 포함되어야함 <br>
TopAppBar는 미리 구성된 아이콘을 쓸 수 있다.<br>

### LazyList
<img src = "https://developer.android.com/codelabs/jetpack-compose-layouts/img/9c6a666c57a84211.gif?authuser=4"><br>
위 이미지와 같이 스크롤을 하는 동안 목록을 렌더링하기 때문에 이미지가 늦게 뜨거나 일정하지 않게 뜨는 경우가 있다. <br>
이 경우를 방지하기 위해 RememberCoroutineScope 함수를 사용해서 CoroutineScope를 생성해서 제어한다. <br>

```kotlin
val listSize = 100
// 스크롤 위치를 저장하는 상태객체
val scrollState = rememberLazyListState()
// 애니메이션 스크롤이 실행될 코루틴 범위를 저장
val coroutineScope = rememberCoroutineScope()
```
<img src = "https://developer.android.com/codelabs/jetpack-compose-layouts/img/9bc52801a90401f3.gif?authuser=4"><br>

### LazyList
<img src = "https://developer.android.com/codelabs/jetpack-compose-layouts/img/9c6a666c57a84211.gif?authuser=4"><br>
위 이미지와 같이 스크롤을 하는 동안 목록을 렌더링하기 때문에 이미지가 늦게 뜨거나 일정하지 않게 뜨는 경우가 있다. <br>
이 경우를 방지하기 위해 RememberCoroutineScope 함수를 사용해서 CoroutineScope를 생성해서 제어한다. <br>

```kotlin
val listSize = 100
// 스크롤 위치를 저장하는 상태객체
val scrollState = rememberLazyListState()
// 애니메이션 스크롤이 실행될 코루틴 범위를 저장
val coroutineScope = rememberCoroutineScope()
```

## 7. Create your custom layout
Column, Row, Box를 사용하지 않고 Custom으로 레이아웃을 만들 수 있다.<br>
Custom layout을 만들기 위해서는 Compose의 레이아웃 원칙을 아는 것이 중요하다. <br>

### Compose의 레이아웃 원칙
일부 구성 가능한 함수는 호출될 때 화면에 렌더링될 UI 트리에 추가되는 UI Element를 보낸다. 각 요소에는 하나의 부모와 잠재적으로 많은 자식이 있다. 그리고 부모 내 위치 및 크기가 있다.<br>
element는 충족되어야 하는 Constraints로 스스로 측정해야함. <br>
제약 조건은 요소의 최소 및 최대 너비와 높이를 제한해야한다. <br>
ComposeUI는 다중 패스 측정을 허용하지 않는다. <br>
레이아웃 요소가 다른 측정 구성을 시도하기 위해 자식을 두번 이상 측정하지 않는다. <br>

레이아웃 수정자를 사용할 때 두개의 람다 매개변수를 얻는다.
```kotlin
fun Modifier.customLayoutModifier(...) = Modifier.layout { measurable, constraints ->
  ...
})
```
measurable: child의 측정 및 배치<br>
constraints:  child의 너비와 높이에 대한 최소 및 최대<br>

화면에 텍스트를 제어하기위해 BaseLine의 거리를 제어할려고 한다.

```kotlin
fun Modifier.firstBaselineToTop(
  firstBaselineToTop: Dp
) = this.then(
    layout { measurable, constraints ->
        ...
    }
)
```
가장 먼저 할 일은 컴포저블을 측정한다. <br>
Compose의 레이아웃은 앞의 원칙에서 언급했듯이 한 번만 측정할 수 있다.<br>
measurable.measure(constraints)를 호출하여 컴포저블을 측정합니다.<br>
measure(constraints)를 호출할 때 제약 조건 람다 매개변수에서 사용 가능한 컴포저블의 주어진 제약 조건을 전달하거나 직접 생성할 수 있습니다.<br>
Measurable에 대한 measure() 호출의 결과는 나중에 할 것처럼 placeRelative(x, y)를 호출하여 위치를 지정할 수 있는 Placeable입니다.<br>

### Custom Layout

```kotlin
@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->

        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }
    }
}
```

레이아웃 modifier가 작동하는 방식과 유사하게 measurables 람다 매개변수에서 measurable.measure(constraints)를 호출하여 측정할 수 있는 모든 콘텐츠를 얻습니다.<br>


```kotlin
@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Measure children - code in the previous code snippet
        ...

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children
        }
    }
}
```
이제 로직에 측정된 자식 목록이 있으므로 화면에 배치하기 전에 Column의 크기를 계산해야 합니다. <br>
부모만큼 크게 만들 때 크기는 부모가 전달한 제약 조건입니다.<br>
layout(width, height) 메서드를 호출하여 자체 열의 크기를 지정합니다.<br>
이 메서드는 자식 배치에 사용되는 람다도 제공합니다.<br>

```kotlin
@Composable
fun MyOwnColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Don't constrain child views further, measure them with given constraints
        // List of measured children
        val placeables = measurables.map { measurable ->
            // Measure each child
            measurable.measure(constraints)
        }

        // Track the y co-ord we have placed children up to
        var yPosition = 0

        // Set the size of the layout as big as it can
        layout(constraints.maxWidth, constraints.maxHeight) {
            // Place children in the parent layout
            placeables.forEach { placeable ->
                // Position item on the screen
                placeable.placeRelative(x = 0, y = yPosition)

                // Record the y co-ord placed up to
                yPosition += placeable.height
            }
        }
    }
}
```
마지막으로 placeable.placeRelative(x, y)를 호출하여 화면에 childeren을 배치합니다.<br>
children들을 수직으로 배치하기 위해 우리는 아이들을 배치한 y 좌표를 추적합니다.<br>

## 8. Complex custom layout
지그재그 레이아웃 만들기 <br>


```kotlin
Layout(
    modifier = modifier,
    content = content
) { measurables, constraints ->

    // Keep track of the width of each row
    val rowWidths = IntArray(rows) { 0 }

    // Keep track of the max height of each row
    val rowHeights = IntArray(rows) { 0 }

    // Don't constrain child views further, measure them with given constraints
    // List of measured children
    val placeables = measurables.mapIndexed { index, measurable ->

        // Measure each child
        val placeable = measurable.measure(constraints)

        // Track the width and max height of each row
        val row = index % rows
        rowWidths[row] += placeable.width
        rowHeights[row] = Math.max(rowHeights[row], placeable.height)

        placeable
    }
    ...
}
```
이전과 똑같이 children들을 측정<br>

```kotlin
Layout(
    modifier = modifier,
    content = content
) { measurables, constraints ->

    // Keep track of the width of each row
    val rowWidths = IntArray(rows) { 0 }

    // Keep track of the max height of each row
    val rowHeights = IntArray(rows) { 0 }

    // Don't constrain child views further, measure them with given constraints
    // List of measured children
    val placeables = measurables.mapIndexed { index, measurable ->

        // Measure each child
        val placeable = measurable.measure(constraints)

        // Track the width and max height of each row
        val row = index % rows
        rowWidths[row] += placeable.width
        rowHeights[row] = Math.max(rowHeights[row], placeable.height)

        placeable
    }
    ...
}
```
자식을 배치하기 전 크기를 계산하는 과정<br>
자식들의 최대 높이는 이전에 구했기 때문에(측정을 통해서) 각 행이 배치할 위치를 계산할 수 있다.<br>

```kotlin
Layout(
    content = content,
    modifier = modifier
) { measurables, constraints ->
    ... 

    // Grid's width is the widest row
    val width = rowWidths.maxOrNull()
        ?.coerceIn(constraints.minWidth.rangeTo(constraints.maxWidth)) ?: constraints.minWidth

    // Grid's height is the sum of the tallest element of each row
    // coerced to the height constraints 
    val height = rowHeights.sumOf { it }
        .coerceIn(constraints.minHeight.rangeTo(constraints.maxHeight))

    // Y of each row, based on the height accumulation of previous rows
    val rowY = IntArray(rows) { 0 }
    for (i in 1 until rows) {
        rowY[i] = rowY[i-1] + rowHeights[i-1]
    }

    ...
}
```
마지막으로 placeable.placeRelative(x, y)를 호출하여 화면에 children들을 배치함.<br>
우리의 사용 사례에서는 rowX 변수의 각 행에 대한 X 좌표도 추적<br>

## StaggeredGrid Custom Layout

### Chip
``` kotlin
@Composable
fun Chip(modifier: Modifier = Modifier, text: String) {
    Card(
        modifier = modifier,
        border = BorderStroke(color = Color.Black, width = Dp.Hairline),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(start = 8.dp, top = 4.dp, end = 8.dp, bottom = 4.dp), 
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(16.dp, 16.dp)
                    .background(color = MaterialTheme.colors.secondary)
            )
            Spacer(Modifier.width(4.dp))
            Text(text = text)
        }
    }
}

@Preview
@Composable
fun ChipPreview() {
    LayoutsCodelabTheme {
        Chip(text = "Hi there")
    }
}
```
이 칩을 위에 만들었던 StruggeredGrid Layout에 배치하기<br>
```kotlin
@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        StaggeredGrid {
            for (topic in topics) {
                Chip(modifier = Modifier.padding(8.dp), text = topic)
            }
        }
    }
}
```
Row는 화면 밖으로 튀어나오는 칩들을 정렬하기 위해서 사용<br>

## Layout modifiers under the hood

```kotlin
@Stable
fun Modifier.padding(all: Dp) =
    this.then(
        PaddingModifier(start = all, top = all, end = all, bottom = all, rtlAware = true)
    )

// Implementation detail
private class PaddingModifier(
    val start: Dp = 0.dp,
    val top: Dp = 0.dp,
    val end: Dp = 0.dp,
    val bottom: Dp = 0.dp,
    val rtlAware: Boolean,
) : LayoutModifier {

    override fun MeasureScope.measure(
        measurable: Measurable,
        constraints: Constraints
    ): MeasureResult {

        val horizontal = start.roundToPx() + end.roundToPx()
        val vertical = top.roundToPx() + bottom.roundToPx()

        val placeable = measurable.measure(constraints.offset(-horizontal, -vertical))

        val width = constraints.constrainWidth(placeable.width + horizontal)
        val height = constraints.constrainHeight(placeable.height + vertical)
        return layout(width, height) {
            if (rtlAware) {
                placeable.placeRelative(start.roundToPx(), top.roundToPx())
            } else {
                placeable.place(start.roundToPx(), top.roundToPx())
            }
        }
    }
}
```
요소의 새 너비는 자식의 너비 + 요소의 너비 제약 조건으로 강제 변환된 시작 및 끝 패딩 값이 됩니다.<br>
높이는 자식의 높이 + 요소의 높이 제약 조건으로 강제된 위쪽 및 아래쪽 패딩 값이 됩니다.<br>

## 10. Constraint Layout
```kotlin
@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {
        // Creates references for the three composables
        // in the ConstraintLayout's body
        val (button1, button2, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button1) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) { 
            Text("Button 1") 
        }

        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button1.bottom, margin = 16.dp)
            centerAround(button1.end)
        })

        val barrier = createEndBarrier(button1, text)
        Button(
            onClick = { /* Do something */ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) { 
            Text("Button 2") 
        }
    }
}
```
top.linkto로 연결<br>
참조는 createRefs()(또는 createRef())를 사용하여 생성해야함<br>
barrier는 이전의 체인같은 역활임 <br>

### 자동 줄바꿈 사용자 정의

```kotlin
width = Dimension.preferredWrapContent
```
이 명령어를 이용하면 width가 오버되면 자동으로 줄 바꿔준다.<br>

### 인라인에서 분리하는 사용자 정의
```kotlin
@Composable
fun DecoupledConstraintLayout() {
    BoxWithConstraints {
        val constraints = if (maxWidth < maxHeight) {
            decoupledConstraints(margin = 16.dp) // Portrait constraints
        } else {
            decoupledConstraints(margin = 32.dp) // Landscape constraints
        }

        ConstraintLayout(constraints) {
            Button(
                onClick = { /* Do something */ },
                modifier = Modifier.layoutId("button")
            ) {
                Text("Button")
            }

            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button) {
            top.linkTo(parent.top, margin= margin)
        }
        constrain(text) {
            top.linkTo(button.bottom, margin)
        }
    }
}
```

