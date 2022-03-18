package com.example.trip.screen

import android.app.Application
import android.util.Log
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.trip.R
import com.example.trip.TripConst
import com.example.trip.viewModel.CategoryViewModel
import com.example.trip.viewModel.CategoryViewModelFactory
import com.example.trip.viewModel.TripViewModel
import com.example.trip.viewModel.TripViewModelFactory
import kotlinx.coroutines.CoroutineScope

@Composable
fun MenuScreen(user: String) {
    val tripAppState = rememberTripAppState(user = user)
    Scaffold(
        scaffoldState = tripAppState.scaffoldState,
        topBar =  { MenuScreenTopBar() },
        bottomBar = { BottomMenuBar( tripAppState.navController ) },
        floatingActionButton = { ActionNew(tripAppState = tripAppState) }
    ) {
        NavigationGraph(tripAppState)
    }
}


@Composable
fun ActionNew(tripAppState: TripAppState) {
    val currentRoute = tripAppState.navController
        .currentBackStackEntryFlow
        .collectAsState(initial = tripAppState.navController.currentBackStackEntry)
        .value?.destination?.route

    if (tripAppState.isShowNewButton(currentRoute)) {
        FloatingActionButton(
            onClick = {
                when (currentRoute) {
                    "category" -> {
                        tripAppState.navigate("categoryForm")
                    }
                    "trip" -> {
                        tripAppState.navigate("tripForm")
                    }
                }
            }
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
        }
    }
}

@Composable
fun rememberTripAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    user: String
) = remember(scaffoldState, navController) {
    TripAppState(scaffoldState, navController, coroutineScope, user)
}


@Composable
fun NavigationGraph(state: TripAppState) {
    val context = LocalContext.current
    val application = context.applicationContext as Application

    NavHost(state.navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("tripForm") {
            var model: TripViewModel = viewModel(factory = TripViewModelFactory(application))
            model.user = state.user
            TripFormScreen(nav = state.navController, scaffoldState = state.scaffoldState, model = model)
        }
        composable("tripForm/{tripId}", arguments = listOf(navArgument("tripId") { type = NavType.IntType} )) {
            val id = it.arguments?.getInt("tripId")
            Log.i(TripConst.TAG, "Navigation trip ${id}")
            var model: TripViewModel = viewModel(factory = TripViewModelFactory(application))
            model.user = state.user
            if (id != null) {
                model.findById(id)
            }
            TripFormScreen(nav = state.navController, scaffoldState = state.scaffoldState, model = model)
        }

        composable("trip") {
            var model: TripViewModel = viewModel(factory = TripViewModelFactory(application))
            model.user = state.user
            TripScreen(
                model = model,
                onAddTrip = { state.navController.navigate("tripForm")},
                onEditTrip = {
                    state.navController.navigate("tripForm/${it.id}")
                },
                onAddSpent = {},
                onDelete = {}
            )
        }
        composable("category") {
            val model : CategoryViewModel = viewModel(factory = CategoryViewModelFactory(app = application))

            CategoryScreen(model = model, state)
        }
        composable("categoryForm") {
            val model : CategoryViewModel = viewModel(factory = CategoryViewModelFactory(app = application))
            CategoryFormScreen(model = model, app = state)
        }
        composable("categoryForm/{categoryId}", arguments = listOf(navArgument("categoryId") { type = NavType.IntType} )) {
            val id = it.arguments?.getInt("categoryId")
            Log.i(TripConst.TAG, "Navigation ${id}")
            val model : CategoryViewModel = viewModel(factory = CategoryViewModelFactory(app = application))
            if (id != null) {
                model.findById(id)
            }
            CategoryFormScreen(model = model, app = state)
        }
    }
}

@Composable
fun BottomMenuBar(nav: NavController) {
    var selectedIndex by remember { mutableStateOf(0) }
    BottomNavigation( elevation = 16.dp) {
        BottomNavigationItem(
            selected = selectedIndex == 0,
            onClick = {
                            selectedIndex = 0
                            nav.navigate("home")
                      },
            icon = {
                Icon(imageVector = Icons.Default.Home, "")
            },
            label = {
                Text(text = "Home")
            }
        )
        BottomNavigationItem(
            selected = selectedIndex == 1,
            onClick = {
                        selectedIndex = 1
                        nav.navigate("trip")
                      },
            icon = {
                Icon(imageVector = Icons.Default.AirplaneTicket, "")
            },
            label = {
                Text(text = "Trip")
            }
        )
        BottomNavigationItem(
            selected = selectedIndex == 2,
            onClick = {
                        selectedIndex = 2
                        nav.navigate("tripForm")
                      },
            icon = {
                Icon(imageVector = Icons.Default.AirplanemodeActive, "")
            },
            label = {
                Text(text = "New trip")
            }
        )
        BottomNavigationItem(
            selected = selectedIndex == 2,
            onClick = {
                selectedIndex = 2
                nav.navigate("category")
            },
            icon = {
                Icon(imageVector = Icons.Default.Category, "")
            },
            label = {
                Text(text = "Category")
            }
        )

    }
}

@Composable
fun MenuScreenTopBar() {
    TopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },

    )
}
