package com.tzeentch.energy_saver.ui.compose.authorization

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tzeentch.energy_saver.ui.compose.components.CustomOutlinedTextField

@Composable
fun EnterAuthData(resError:String, onRegClick:(name:String, pass:String)->Unit, onEnterClick:(name:String, pass:String)->Unit){
    var name by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var error by remember(resError) {
        mutableStateOf(resError)
    }

    Column(
        modifier = Modifier.padding(top = 45.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        Text(text = "Регистрация", fontSize = 23.sp, color = Color(0xFF284779))

        CustomOutlinedTextField(defText = name,
            defTitle = "Имя",
            hint = "сберегатель",
            isError = error.isNotEmpty(),
            onValueChange = {
                error = ""
                name = it
            })

        CustomOutlinedTextField(defText = password,
            defTitle = "Пароль",
            isError = error.isNotEmpty(),
            hint = "*******",
            onValueChange = {
                error = ""
                password = it
            })

        AnimatedVisibility(error.isNotEmpty()) {
            Text(text = error, textAlign = TextAlign.Center, color = Color.Red)
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = {
                   onEnterClick(name,password)
                },
                enabled = name.isNotEmpty() && password.isNotEmpty() && error.isEmpty(),
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
            ) {
                Text(text = "Войти")
            }

            Button(
                onClick = {
                    onRegClick(name,password)
                }, enabled = name.isNotEmpty() && password.isNotEmpty() && error.isEmpty(),
                modifier = Modifier
                    .weight(1f)
                    .height(55.dp)
            ) {
                Text(text = "Создать")
            }
        }
    }
}