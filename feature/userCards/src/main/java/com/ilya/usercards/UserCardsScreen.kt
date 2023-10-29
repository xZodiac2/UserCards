package com.ilya.usercards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun UserCardsScreen(
    onCardClick: (Int) -> Unit,
    viewModel: UserCardsViewModel = hiltViewModel(),
) {
    val state by viewModel.stateFlow.collectAsState()
    
    when (state) {
        is UserCardsState.Loading -> LoadingState()
        is UserCardsState.ErrorHaveNotInternet -> ErrorHaveNotInternetState(onTryAgainClick = { viewModel.getAllUsers() })
        is UserCardsState.ViewUserCards -> ViewUserCardsState((state as UserCardsState.ViewUserCards).users) { id ->
            onCardClick(id)
        }
    }
    
    LaunchedEffect(key1 = Unit, block = {
        if (state is UserCardsState.Loading) {
            viewModel.getAllUsers()
        }
    })
}

@Composable
private fun ErrorHaveNotInternetState(onTryAgainClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "No able to get user cards. Check your internet connection and try again")
        Button(onClick = onTryAgainClick) {
            Text(text = "Try again")
        }
    }
}

@Composable
private fun ViewUserCardsState(users: List<User>, onCardClick: (Int) -> Unit) {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(users) {
            Column(
                modifier = Modifier.clickable { onCardClick(it.id) },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(model = it.image, contentDescription = null)
                Text(text = "${it.firstName} ${it.lastName}")
            }
        }
    }
}


@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
