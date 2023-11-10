package com.tzeentch.energy_saver.ui.compose.authorization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import com.tzeentch.energy_saver.helpers.NavigationItem
import com.tzeentch.energy_saver.ui.states.AuthStates
import com.tzeentch.energy_saver.viewModels.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Authorization(navController: NavController,viewModel: AuthViewModel = koinViewModel()){
    when(val authState = viewModel.authState.collectAsState().value){
        is AuthStates.Initial->{
           EnterIp()
        }

        is AuthStates.Form->{
            EnterAuthData(authState.error)
        }

        is AuthStates.GoToMainScreen->{
            navController.navigate(NavigationItem.MainScreen.route)
        }

        else -> {

        }
    }
}