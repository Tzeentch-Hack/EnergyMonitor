package com.tzeentch.energy_saver.ui.compose.authorization

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tzeentch.energy_saver.ui.compose.components.CustomOutlinedTextField

@Composable
fun EnterIp(onIpEnter:(ip:String)->Unit){
    var ip by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(top = 45.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {

        Text(text = "Ввод серверного адреса", fontSize = 23.sp, color = Color(0xFF284779))

        CustomOutlinedTextField(defText = ip,
            defTitle = "IP",
            hint = "0.0.0.0",
            onValueChange = {
                ip = it
            })

        Button(
            onClick = {
                onIpEnter(ip)
            },
            enabled = ip.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text(text = "Далее")
        }
    }
}