# 1주차 Codelab
## 4. Tweaking the UI

### Surface와 Material
```kotlin
@Composable
fun Greeting(name: String) {
    // Surface 및 MaterialTheme는 사용자 인터페이스를 만드는데 도움이 되도록
    // 구글에서 만든 디자인 시스템 Material Desing과 관련된 개념
    Surface(color = MaterialTheme.colors.primary){
        // TextView 대신 Composable func을 넣어 UI를 구성함
        // 대부분의 Compose UI(Text 및 Surface)들은 modifier 파라미터를 허용한다.
        // modifier는 부모 레이아웃 내에서 레이아웃, 동작, 표시하는 방법을 알려줌
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
}
```
<p>Surface 및 MaterialTheme는 사용자 인터페이스를 만드는데 도움이 되도록 구글에서 만든 디자인 시스템 Material Desing과 관련된 개념</p>

<p>Surface와 같은 material 구성요소들은 기본값과 패턴을 제공하기 때문에 독단적이다. 위 코드에서는 배경색만 정했지만, 글자색도 자동으로 정해줬다. </p>

### Modifiers

<p>대부분의 Compose UI(Text 및 Surface)들은 modifier 파라미터를 허용한다.</p>
<p>modifier는 부모 레이아웃 내에서 레이아웃, 동작, 표시하는 방법을 알려줌. </p>

[modifier](https://developer.android.com/jetpack/compose/modifiers-list?authuser=4)의 자세한 기능 주소


## 5. Reusing composables
```kotlin
@Composable
private fun MyApp() {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Greeting("Android")
    }
}

@Composable
private fun Greeting(name: String) {
    // Surface 및 MaterialTheme는 사용자 인터페이스를 만드는데 도움이 되도록
    // 구글에서 만든 디자인 시스템 Material Desing과 관련된 개념
    Surface(color = MaterialTheme.colors.primary){
        // TextView 대신 Composable func을 넣어 UI를 구성함
        // 대부분의 Compose UI(Text 및 Surface)들은 modifier 파라미터를 허용한다.
        // modifier는 부모 레이아웃 내에서 레이아웃, 동작, 표시하는 방법을 알려줌
        Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
    }
}
// xml처럼 코드를 구성하면 옆에서 Preview로 확인 가능
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MyApp()
    }
}
```
<p>UI에 구성요소를 추가할수록 더 많은 요소들이 생긴다. </p>
<p>함수안에 코드가 많아지면 가독성에 영향을 준다. </p> 
<p>재사용 가능한 작은 구성 요소를 만들어 앱에서 사용되는 UI 라이브러리를 쉽게 구축이 가능하다. </p>
<p>각각은 화면의 작은 부분을 담당하며 독립적으로 편집할 수 있다.</p>


## 6. Creating columns and rows
```kotlin
 Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)){
            // 대부분의 Compose UI(Text 및 Surface)들은 modifier 파라미터를 허용한다.
            // modifier는 부모 레이아웃 내에서 레이아웃, 동작, 표시하는 방법을 알려줌
            // Compose에는 alignEnd가 없으므로, weight로 가중치를 통해서 배치
            Column(modifier = Modifier.weight(1f)) {
                // TextView 대신 Composable func을 넣어 UI를 구성함
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { /*TODO*/ }
            ) {
                Text("Show more")
            }
        }
    }
```
<p>Compose에서 layout의 기본적인 종류는 Column, Row, Box가 있다.</p>
<p>Column은 Vertical, Row는 Horizontal, Box는 Frame이다.</p>

### Compose and kotlin
```kotlin
@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {
    // A surface container using the 'background' color from the theme
    Surface(color = MaterialTheme.colors.background) {
        Column {
            // Kotlin의 For문을 이용해서 UI요소를 추가할 수 있다.
            for (name in names){
                Greeting(name = name)
            }
        }
    }
}
```

<p> 코틀린 문법으로 Compose 함수를 불러 UI를 구성할 수 있다.</p>
<p> for문으로 Greeting 함수를 불러 UI를 구성할 수 있고, if문으로 원하는 UI를 구성할 수 있다. </p>
<p> -> 기본 언어의 유연성을 Jetpack Compose의 주요 장점 중 하나 </p>

## 7. State in Compose

<p>Greeting 함수안에 Boolean을 넣고 클릭시 true, false로 변경하여 버튼의 이벤트를 관리할려고 했지만, 이 코드는 실행이 안됨 </p>
<p>Compose의 재구성이 트리거되지 않는 이유는 Compose에서 트래킹하지 않고, Greeting이 호출될 때 변수가 false로 선언되기 때문이다 </p>

### Compose의 재구성
참고 주소 - [Compose 이해](https://developer.android.com/jetpack/compose/mental-model?authuser=4#recomposition)<br>

<p> 명령형 UI에서 위젯을 변경하려면 위젯에서 setter를 호출하여 내부 상태를 변경함 </p>
<p> Compose에서 새 데이터를 사용해 구성 가능한 함수를 다시 호출. </p>
<p> 이런 방식으로 하면 함수가 재구성되며, 위젯이 새 데이터로 다시 그려짐 </p>

### Remember
```kotlin
 Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        // Remeber는 재구성을 방지함
        val expended = remember { mutableStateOf(false) }
        Row(modifier = Modifier.padding(24.dp)){
            OutlinedButton(
                onClick = { expended.value = !expended.value }
            ) {
                Text(if (expended.value) "Show less" else "Show more")
            }
        }
    }
```
<p> Remeber는 재구성을 방지하는데 사용됨. 상태가 재 설정되지 않음 </p>
<p> 화면에서 다른 부분에서 동일한 컴포저블을 호출하면 고유한 버전의 상태가 있는 다른 UI를 생성함 </p>

## 8. State Hoisiting
```kotlin
@Composable
private fun MyApp() {
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}
```
<p> 구성가능한 함수에서 여러함수를 읽거나, 수정된 상태는 공통 조상이 있어야한다. 이 것을 호이스팅이라고 한다. </p>
<p> 상태를 호이스팅하게 만들면 상태를 상태 복제 및 버그 도입을 방지하고 컴포저블을 재사용하는 데 도움이 되며 컴포저블을 훨씬 더 쉽게 테스트할 수 있다. </p>
<p> 반대로 컴포저블의 부모에 의해 제어될 필요가 없는 상태는 호이스팅되어서는 안됨.</p>

## 9. Creating a performant lazy list
```kotlin
@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}
```
<p> View를 모두 갱신하는것이 아닌, 재사용하는것 처럼 보이는데, 실제론 뒷단에서 옵저빙을 걸어두고, 모든 View의 데이터 변경사항을 체크합니다. </p> 
<p> 그리고 그중에서 변경되어야 할 점만 변경하는게 Compose 입니다. </p>
<p> LazyColumn 역시 이를 활용하니 RecycleView 보다는 좋은 성능을 가질 수 있습니다.(굳이 재활용하지 않더라두요) </p>

## 10. Persisting state
```kotlin
    // Remeber는 컴포저블이 컴포지션이 유지될 동안에만 실행
    // rememberSaveable는 구성 변경(기기 화면 돌리기) 및 프로세스 종료에서 살아남은 각 상태가 저장됨
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }
```
<p> Remeber는 컴포저블이 컴포지션이 유지될 동안에만 실행 </p>
<p> rememberSaveable는 구성 변경(기기 화면 돌리기) 및 프로세스 종료에서 살아남은 각 상태가 저장됨 </p>

## 11. Animating your list
```kotlin
// animateDpAsState는 Compose에서 제공하는 애니메이션 효과
    val extraPadding by animateDpAsState(
        // 클릭 시 공간 확보를 위해서
        if (expended.value) 48.dp else 0.dp,
        // animation을 커스터마이징 가능
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
```
## 12. Styling and theming your app
```kotlin
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
```

## 13. Finishing touches!
코드 마무리
