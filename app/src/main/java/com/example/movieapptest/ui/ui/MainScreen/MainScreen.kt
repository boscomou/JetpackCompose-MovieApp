package com.example.movieapptest.ui.ui.MainScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieapptest.R
import com.example.movieapptest.ui.ui.genres.ActionMovies
import com.example.movieapptest.ui.ui.genres.AnimationMovie
import com.example.movieapptest.ui.ui.popular.PopularScreen
import com.example.movieapptest.ui.ui.search.Search
import com.example.movieapptest.ui.ui.topRate.TopRateScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class)
@Composable

fun MainScreen(navController: NavController,mainScreenViewModel:MainScreenViewModel= hiltViewModel()) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()


    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ){
                NavigationDrawerHeader(mainScreenViewModel.state.value.firstName)
                Column(modifier = Modifier.padding(20.dp)) {
                    NavigationDrawerBody(mainScreenViewModel.navigationItemsList,
                        onNavigationItemClickedList = listOf(
                            { /* Behavior for item 0 */
                                navController.navigate("ProfileScreen")
                            },
                            {
                                navController.navigate("BookmarkScreen")},
                            {
                            /* Behavior for item 1 */
                            },
                            { /* Behavior for item 2 */
                                mainScreenViewModel.logout()
                                navController.navigate("LoginScreen")
                            },
                            {
                                navController.navigate("SentimentAnalysisScreen")
                            }
                            // Add more lambdas for each item in navigationItemsList
                        ))

                }
            }

        }

    ){
        Column(modifier = Modifier.fillMaxSize()) {
            var globalQuery by remember { mutableStateOf("") }
            var isSearchBarButtonExpended by remember { mutableStateOf(false) }
            val historySearch = remember { mutableStateListOf("a", "b") }
            var popUp by remember { mutableStateOf(false) }

            val state = rememberCollapsingToolbarScaffoldState()
            CollapsingToolbarScaffold(
                modifier = Modifier,
                state = state,
                scrollStrategy = ScrollStrategy.EnterAlways,
                toolbar = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        Box(

                            modifier = Modifier

                                .background(Color(255, 255, 255))
                                .sizeIn(maxHeight = 800.dp)
                                .width(
                                    if (isSearchBarButtonExpended ) {
                                        (500 + 2 * state.offsetY).dp
                                        // 500.dp

                                    } else {
                                        350.dp
                                    }
                                ),
                            contentAlignment = Alignment.Center

                        ) {
                            state.toolbarState.progress
                            val offsetY = state.offsetY
                            SearchBar(
                                modifier = Modifier
                                    .width((500 + 2 * offsetY).dp)
                                    .padding(8.dp),

                                query = globalQuery,
                                onQueryChange = { globalQuery = it },
                                onSearch = {
                                    historySearch.add(globalQuery)
                                    popUp = true

                                },
                                active = isSearchBarButtonExpended,
                                onActiveChange = {
                                    isSearchBarButtonExpended = it
                                },
                                placeholder = { Text(text = "Search") },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Search,
                                        contentDescription = "Search Icon"
                                    )
                                },
                                trailingIcon = {
                                    if (isSearchBarButtonExpended) {
                                        Icon(
                                            modifier = Modifier.clickable {
                                                if (globalQuery.isNotEmpty()) {
                                                    globalQuery = ""
                                                } else {
                                                    isSearchBarButtonExpended = false
                                                }
                                            },
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close Icon"
                                        )
                                    }
                                })

                            {
                                if (popUp) {
                                    DialogBox({
                                        popUp = false
                                        isSearchBarButtonExpended = false
                                        globalQuery = ""


                                    }, globalQuery)

                                }

                                historySearch.forEach {
                                    Row(modifier = Modifier
                                        .padding(all = 14.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            globalQuery = it
                                            popUp = true
                                        }) {
                                        Icon(
                                            modifier = Modifier.padding(end = 10.dp),
                                            imageVector = Icons.Default.History,
                                            contentDescription = "History Icon"
                                        )
                                        Text(text = it)

                                    }

                                }


                            }
                        }
                        Box(contentAlignment = Alignment.Center,
                            modifier= Modifier
                                .fillMaxSize()
                                .height(80.dp)
                                .background(Color(255, 255, 255))
                        ){
                            Image(
                                painter = painterResource(id = R.drawable.more),
                                contentDescription = "more",
                                Modifier.clickable {
                                    coroutineScope.launch{
                                        drawerState.open()
                                    }
                                }

                            )
                        }
                    }

                }
            ) {

                val pagerState = rememberPagerState()
                val coroutineScope = rememberCoroutineScope()
                val scrollState = rememberScrollState()
                val tabRowItems = listOf(
                    TabRowItem(
                        title = "Popular",
                        screen = {
                            PopularScreen()
                        }
                    ),
                    TabRowItem(
                        title = "TopRated",
                        screen = {
                            TopRateScreen()
                        }
                    ),
                    TabRowItem(
                        title = "Genres",
                        screen = {
                            Column(modifier = Modifier.verticalScroll(scrollState)) {
                                ActionMovies(navController)
                                AnimationMovie(navController)
                            }

                        }
                    )
                )
                Column {
                    Box {
                        TabRow(
                            selectedTabIndex = pagerState.currentPage,
                            backgroundColor = Color(158, 200, 29),
                            indicator = { tabPosition ->
                                TabRowDefaults.Indicator(
                                    height = 5.dp,
                                    modifier = Modifier.pagerTabIndicatorOffset(
                                        pagerState,
                                        tabPosition
                                    ),
                                    color = Color.White
                                )
                            }
                        ) {
                            tabRowItems.forEachIndexed { index, item ->
                                Tab(
                                    selected = pagerState.currentPage == index,
                                    onClick = {
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(index)
                                        }
                                    },
                                    text = {
                                        Text(
                                            text = item.title,
                                            color = Color.White
                                        )
                                    }
                                )
                            }
                        }
                    }

                    HorizontalPager(
                        count = tabRowItems.size,
                        state = pagerState,
                    ) {
                        tabRowItems[pagerState.currentPage].screen()
                    }

                }

            }


        }
    }




}

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit
)


@Composable
fun DialogBox(onDismiss: () -> Unit, globalQuery: String) {

    Dialog(

        onDismissRequest = {
            onDismiss()
        },
        content = {
            Box(
                modifier = Modifier
                    .width(500.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
            ) {
                Search(globalQuery)
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(3.dp)
                ) {
                    Button(
                        onClick = {
                            onDismiss()
                        }
                    ) {
                        Text(text = "X")
                    }
                }

            }

        }
    )
}
