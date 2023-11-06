package com.ilya.usercards.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ilya.data.retrofit.models.User
import com.ilya.usercards.R
import com.ilya.usercards.UserCardsError
import com.ilya.usercards.UserCardsState
import com.ilya.usercards.UserCardsViewModel

@Composable
fun UserCardsScreen(
    onCardClick: (Int) -> Unit,
    viewModel: UserCardsViewModel = hiltViewModel(),
) {
    val state by viewModel.stateFlow.collectAsState()
    
    when (state) {
        is UserCardsState.Loading -> LoadingState()
        is UserCardsState.Error -> ErrorState(
            error = (state as UserCardsState.Error).error,
            onTryAgainClick = { viewModel.handleEvent(UserCardsScreenEvent.Retry) }
        )
        
        is UserCardsState.ViewUserCards -> ViewUserCardsState(
            users = (state as UserCardsState.ViewUserCards).users,
            onCardClick = { onCardClick(it) }
        )
    }
    
    LaunchedEffect(key1 = Unit, block = {
        viewModel.handleEvent(UserCardsScreenEvent.Start)
    })
}

@Composable
private fun ErrorState(error: UserCardsError, onTryAgainClick: () -> Unit) {
    when (error) {
        is UserCardsError.NoInternet -> ErrorNoInternet(onTryAgainClick)
    }
}

@Composable
private fun ErrorNoInternet(onTryAgainClick: () -> Unit) {
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
                Text(text = stringResource(id = R.string.user_name, it.firstName, it.lastName))
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
