package com.ilya.userinfo.screen

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ilya.data.retrofit.models.User
import com.ilya.userinfo.R
import com.ilya.userinfo.UserInfoError
import com.ilya.userinfo.UserInfoState
import com.ilya.userinfo.UserInfoViewModel


@Composable
fun UserInfoScreen(
    userId: Int,
    onBackClick: () -> Unit,
    viewModel: UserInfoViewModel = hiltViewModel(),
) {
    val state = viewModel.stateFlow.collectAsState()
    
    when (val stateValue = state.value) {
        is UserInfoState.Error -> ErrorState(
            error = stateValue.error,
            onBackClick = onBackClick,
            onTryAgainClick = { viewModel.handleEvent(UserInfoScreenEvent.Retry(userId)) }
        )
        
        is UserInfoState.Loading -> LoadingState()
        is UserInfoState.ViewUserInfo -> ViewUserInfoState(
            user = stateValue.user,
            onBackClick = onBackClick
        )
    }
    
    BackHandler(onBack = onBackClick)
    
    LaunchedEffect(key1 = Unit, block = {
        viewModel.handleEvent(UserInfoScreenEvent.Start(userId))
    })
}

@Composable
private fun ErrorState(error: UserInfoError, onBackClick: () -> Unit, onTryAgainClick: () -> Unit) {
    when (error) {
        is UserInfoError.NoId -> ErrorNoId(onBackClick)
        is UserInfoError.NoInternet -> ErrorNoInternet(onBackClick, onTryAgainClick)
    }
}

@Composable
private fun ErrorNoInternet(onBackClick: () -> Unit, onTryAgainClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.error_have_not_internet),
            textAlign = TextAlign.Center
        )
        Button(onClick = onTryAgainClick) {
            Text(text = stringResource(id = R.string.try_again))
        }
        Button(onClick = onBackClick, modifier = Modifier.padding(top = 32.dp)) {
            Text(text = stringResource(id = R.string.back))
        }
    }
}

@Composable
private fun ErrorNoId(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.error_have_not_id),
            textAlign = TextAlign.Center
        )
        Button(onClick = onBackClick) {
            Text(text = stringResource(id = R.string.back))
        }
    }
}

@Composable
private fun ViewUserInfoState(user: User, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = user.image, contentDescription = null)
        Text(text = stringResource(id = R.string.user_name, user.firstName, user.lastName))
        Text(text = stringResource(id = R.string.hair_color, user.hair.color))
        Button(onClick = onBackClick) {
            Text(text = stringResource(id = R.string.back))
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}