package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

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

@Composable
private fun Greeting(name: String) {
    // Surface 및 MaterialTheme는 사용자 인터페이스를 만드는데 도움이 되도록
    // 구글에서 만든 디자인 시스템 Material Desing과 관련된 개념
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
}
// xml처럼 코드를 구성하면 옆에서 Preview로 확인 가능
// withDp 320은 작은 휴대폰 기준
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        MyApp()
    }
}