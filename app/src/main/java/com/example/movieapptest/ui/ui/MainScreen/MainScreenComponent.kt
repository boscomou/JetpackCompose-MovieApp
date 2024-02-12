package com.example.movieapptest.ui.ui.MainScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.RegistrationAndLogin.HeadingTextComponent
import com.example.movieapptest.R

@Composable
fun NavigationDrawerHeader(emailId: String?) {
    Surface(color = Color(158, 200, 29)) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center


        ) {
            Column() {
                HeadingTextComponent(value = stringResource(R.string.navigation_header),30)
                if (emailId != null) {
                    HeadingTextComponent(value = emailId,15)
                }
            }
        }
    }
}

@Composable
fun NavigationDrawerBody(navigationItems: List<NavigationItem>,onNavigationItemClickedList: List<(NavigationItem) -> Unit>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        itemsIndexed(navigationItems) { index, item ->
            NavigationItemRow(item = item, onNavigationItemClicked = onNavigationItemClickedList[index])
        }
    }
}

@Composable
fun NavigationItemRow(item: NavigationItem,
                      onNavigationItemClicked:(NavigationItem)->Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)
            .clickable {
                onNavigationItemClicked.invoke(item)
            }
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.description
        )

        Spacer(modifier = Modifier.width(18.dp))

        Text(text = item.title)


    }
}
