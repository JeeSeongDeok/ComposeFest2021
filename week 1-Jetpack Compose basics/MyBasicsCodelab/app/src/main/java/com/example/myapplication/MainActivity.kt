package com.example.myapplication

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.saveable.rememberSaveable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*
         * XML 대신 setContent 람다 함수 안쪽
         * Composable func을 넣어 구성 가능함
         */
        setContent {
            MyApplicationTheme {
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp() {
    // Remeber는 컴포저블이 컴포지션이 유지될 동안에만 실행
    // rememberSaveable는 구성 변경(기기 화면 돌리기) 및 프로세스 종료에서 살아남은 각 상태가 저장됨
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = List(1000) { "$it" } ) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}
@Composable
private fun Greeting(name: String) {
    // Remeber는 재구성을 방지함
    val expended = remember { mutableStateOf(false) }
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

    // Surface 및 MaterialTheme는 사용자 인터페이스를 만드는데 도움이 되도록
    // 구글에서 만든 디자인 시스템 Material Desing과 관련된 개념
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)){
            // 대부분의 Compose UI(Text 및 Surface)들은 modifier 파라미터를 허용한다.
            // modifier는 부모 레이아웃 내에서 레이아웃, 동작, 표시하는 방법을 알려줌
            // Compose에는 alignEnd가 없으므로, weight로 가중치를 통해서 배치
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                // TextView 대신 Composable func을 넣어 UI를 구성함
                Text(text = "Hello,")
                Text(text = name)
            }
            OutlinedButton(
                onClick = { expended.value = !expended.value }
            ) {
                Text(if (expended.value) "Show less" else "Show more")
            }
        }
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier
                    .padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}
// 다크모드 프리뷰
@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
// xml처럼 코드를 구성하면 옆에서 Preview로 확인 가능
// withDp 320은 작은 휴대폰 기준
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    MyApplicationTheme() {
        OnboardingScreen(onContinueClicked = {})
    }
}