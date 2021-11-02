package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment


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
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if (shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
    } else {
        Greetings()
    }
}

@Composable
private fun Greetings(names: List<String> = listOf("World", "Compose")) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        for (name in names) {
            Greeting(name = name)
        }
    }
}
@Composable
private fun Greeting(name: String) {
    // Remeber는 재구성을 방지함
    val expended = remember { mutableStateOf(false) }
    // 클릭 시 공간 확보를 위해서
    val extraPadding = if (expended.value) 48.dp else 0.dp

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
                .padding(bottom = extraPadding)
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