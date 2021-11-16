# ComposeFest2021
Using state in Jetpack Compose 본 폴더를 Android Studio를 이용해서 열어주세요.
작업을 완료하고, push 해주세요.

# Using State in Jetpack Compose Codelab

This folder contains the source code for the [Using State in Jetpack Compose codelab](https://developer.android.com/codelabs/jetpack-compose-state).


In this codelab, you will explore patterns for working with state in a declarative world by building a Todo application. We'll see what unidirectional
data flow is, and how to apply it in a Jetpack Compose application to build stateless and stateful composables.

## Screenshots

![Finished code](screenshots/state_movie.gif "After: Animation of fully completed project")

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


## 4. Compose and ViewModels

TodoScreen.kt는 Stateless이여서, TodoScreen 자체로는 디자인을 수정할 수 없다. <br>

Stateless Composable을 수정하기 위해서는 Hoisting을 이용해 수정할 수 있다. <br>

```kotlin
class TodoViewModel : ViewModel() {
    // state: TodoItem
    private var _todoItems = MutableLiveData(listOf<TodoItem>())
    val todoItems: LiveData<List<TodoItem>> = _todoItems
    // event: 아이템 추가
    fun addItem(item: TodoItem) {
        _todoItems.value = _todoItems.value!! + listOf(item)
    }
    // event: 아이템 제거
    fun removeItem(item: TodoItem) {
        _todoItems.value = _todoItems.value!!.toMutableList().also {
            it.remove(item)
        }
    }
}
``` 
뷰모델을 이용해 TodoScreen에서 상태를 hositing을 할려고 한다.<br>
data flow은 다음 사진과 같이 이루어진다.<br>
<img src ="https://developer.android.com/codelabs/jetpack-compose-state/img/58baca1f648c1a64.png?authuser=4">

TodoActivity
```kotlin
@Composable
private fun TodoActivityScreen(todoViewModel: TodoViewModel) {
   val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())
   TodoScreen(
       items = items,
       onAddItem = { todoViewModel.addItem(it) },
       onRemoveItem = { todoViewModel.removeItem(it) }
   )
}
```
val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())에서 viewmodel의 Livedata를 관찰함<br>
todoViewModel.todoItems는 뷰 모델의 todoItems를 지정하고 있으며, observeAsState는 LiveData를 관찰하고 State<T>로 반환해서 Compose가 값을 변경할 수 있도록 한다.<br>
listOf()는 null값을 피하기 위해서 초기값을 지정한 것이다. <br>

## 5. Memory in Compose
아이콘에 색깔을 추가하기 위해서 randomTint()를 씀<br>
해당 아이콘을 색깔을 추가하는 코드를 추가했는데 목록이 변경될 때 마다 randomTint()가 다시 호출됨<br>
<p align="center"><img src = "https://developer.android.com/codelabs/jetpack-compose-state/img/86dbbb4eefbc61c.gif?authuser=4"></p>
Recomposition(재구성)은 작성 트리를 업데이트하기 위해 새 입력으로 다시 구성을 호출하는 프로세스<br>
이 경우 새 목록으로 TodoScreen을 다시 호출하면 LazyColumn이 화면의 모든 자식을 재구성<br>
그러면 TodoRow가 다시 호출되어서 randomTint가 발생<br>
<p align="center"><img src = "https://developer.android.com/codelabs/jetpack-compose-state/img/6f5faa4342c63d88.png?authuser=4"></p>

### memory to composable function
Compose를 사용하면 컴포지션 트리에 값을 저장할 수 있다.<br>
remember는 Composable function memory를 제공함
```kotlin
val iconAlpha: Float = remember(todo.id) { randomTint() }
Icon(
   imageVector = todo.icon.imageVector,
   tint = LocalContentColor.current.copy(alpha = iconAlpha),
   contentDescription = stringResource(id = todo.icon.contentDescription)
)
```

## 6. State in Compose
Stateful composable은 변경가능한 상태를 소유하는 컴포저블<br>

## 7. Dynamic UI based on state
```kotlin
@Composable
fun TodoItemInput(onItemComplete: (TodoItem) -> Unit) {
   val (text, setText) = remember { mutableStateOf("") }
   val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default)}
   val iconsVisible = text.isNotBlank()
   val submit = {
       onItemComplete(TodoItem(text, icon))
       setIcon(TodoIcon.Default)
       setText("")
   }
   Column {
       Row(Modifier
           .padding(horizontal = 16.dp)
           .padding(top = 16.dp)
       ) {
           TodoInputText(
               text = text,
               onTextChange = setText,
               modifier = Modifier
                   .weight(1f)
                   .padding(end = 8.dp),
               onImeAction = submit // pass the submit callback to TodoInputText
           )
           TodoEditButton(
               onClick = submit, // pass the submit callback to TodoEditButton
               text = "Add",
               modifier = Modifier.align(Alignment.CenterVertically),
               enabled = text.isNotBlank()
           )
       }
       if (iconsVisible) {
           AnimatedIconRow(icon, setIcon, Modifier.padding(top = 8.dp))
       } else {
           Spacer(modifier = Modifier.height(16.dp))
       }
   }
}
```
InputText에서 onImeAction을 통해 안드로이드 자판에서 엔터를 누르면 작동할 수 있도록 만듬.<br>

## 8. Extracting stateless composables

Stateful compose를 stateless와 stateful 상태를 나누는 작업<br>
```kotlin
@Composable
fun TodoItemEntryInput(onItemComplete: (TodoItem) -> Unit) {
   val (text, setText) = remember { mutableStateOf("") }
   val (icon, setIcon) = remember { mutableStateOf(TodoIcon.Default)}
   val iconsVisible = text.isNotBlank()
   val submit = {
       onItemComplete(TodoItem(text, icon))
       setIcon(TodoIcon.Default)
       setText("")
   }
   TodoItemInput(
       text = text,
       onTextChange = setText,
       icon = icon,
       onIconChange = setIcon,
       submit = submit,
       iconsVisible = iconsVisible
   )
}

@Composable
fun TodoItemInput(
   text: String,
   onTextChange: (String) -> Unit,
   icon: TodoIcon,
   onIconChange: (TodoIcon) -> Unit,
   submit: () -> Unit,
   iconsVisible: Boolean
) {
   Column {
       Row(
           Modifier
               .padding(horizontal = 16.dp)
               .padding(top = 16.dp)
       ) {
           TodoInputText(
               text,
               onTextChange,
               Modifier
                   .weight(1f)
                   .padding(end = 8.dp),
               submit
           )
           TodoEditButton(
               onClick = submit,
               text = "Add",
               modifier = Modifier.align(Alignment.CenterVertically),
               enabled = text.isNotBlank()
           )
       }
       if (iconsVisible) {
           AnimatedIconRow(icon, onIconChange, Modifier.padding(top = 8.dp))
       } else {
           Spacer(modifier = Modifier.height(16.dp))
       }
   }
}
```
상태를 다르게 지원하려는 상황에서 코드를 재사용할 수 있다는 장점이 있음.<br>

## 9. Use State in ViewModel
mutableState를 사용하면 관찰이 가능한 mutableList인스턴스가 만들어짐.<br>
그래서 해당 예제에서는 viewmodel에 있는 LiveData를 지우고 대신에 mutableStateOf사용함<br>
단, View를 사용하는 경우가 있으면 viewmodel에서 LiveData를 사용하는 것이 더 좋다<br>

