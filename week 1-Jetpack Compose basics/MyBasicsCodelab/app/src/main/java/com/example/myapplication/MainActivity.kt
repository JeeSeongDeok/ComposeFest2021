package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
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