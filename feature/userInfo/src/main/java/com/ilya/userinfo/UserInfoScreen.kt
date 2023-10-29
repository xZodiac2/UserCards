package com.ilya.userinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ilya.data.retrofit.models.User


@Composable
fun UserInfoScreen(
    userId: Int?,
    onGoBackClick: () -> Unit,
    viewModel: UserInfoViewModel = hiltViewModel(),
) {
    if (userId == null || userId == UserInfoViewModel.DEFAULT_USER_ID) {
        viewModel.onHaveNotId()
    }
    
    val state by viewModel.stateFlow.collectAsState()
    
    when (state) {
        is UserInfoState.ErrorHaveNotId -> ErrorHaveNotIdState(onGoBackClick)
        is UserInfoState.ErrorHaveNotInternet -> ErrorHaveNotInternetState(onGoBackClick, onTryAgainClick = {
            userId?.let {
                viewModel.getUserById(it)
            } ?: viewModel.onHaveNotId()
        })
        
        is UserInfoState.Loading -> LoadingState()
        is UserInfoState.ViewUserInfo -> ViewUserInfoState((state as UserInfoState.ViewUserInfo).user, onGoBackClick)
    }
    
    LaunchedEffect(key1 = Unit, block = {
        if (state is UserInfoState.Loading && userId != null && userId != UserInfoViewModel.DEFAULT_USER_ID) {
            viewModel.getUserById(userId)
        }
    })
}

@Composable
private fun ErrorHaveNotInternetState(onGoBackClick: () -> Unit, onTryAgainClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No able to get user info. Check your internet connection and try again")
        Button(onClick = onTryAgainClick) {
            Text(text = "Try again")
        }
        Button(onClick = onGoBackClick, modifier = Modifier.padding(top = 32.dp)) {
            Text(text = "Back")
        }
    }
}

@Composable
private fun ErrorHaveNotIdState(onGoBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "User is not found")
        Button(onClick = onGoBackClick) {
            Text(text = "Back")
        }
    }
}

@Composable
private fun ViewUserInfoState(user: User, onGoBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = user.image, contentDescription = null)
        Text(text = "${user.firstName}, ${user.lastName}")
        Text(text = "Hair color: ${user.hair.color}")
        Button(onClick = onGoBackClick) {
            Text(text = "Back")
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}