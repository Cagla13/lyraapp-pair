package com.example.lyraapp.ui.navigation

<<<<<<< HEAD
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
=======
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.lyraapp.ui.auth.login.LoginRoute
import com.example.lyraapp.ui.auth.register.RegisterRoute
import com.example.lyraapp.ui.home.HomeRoute
import com.example.lyraapp.ui.search.SearchScreen
import com.example.lyraapp.ui.search.SearchViewModel
import com.example.lyraapp.ui.favorites.FavoritesScreen
import com.example.lyraapp.ui.favorites.FavoritesViewModel
<<<<<<< HEAD
import com.example.lyraapp.ui.profile.ProfileScreen
import com.example.lyraapp.ui.profile.ProfileViewModel
import com.example.lyraapp.ui.library.LibraryScreen
import com.example.lyraapp.ui.library.LibraryViewModel
=======
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede

@Composable
fun LyraNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBarScreens = listOf(
        LyraDestination.Home.route,
        LyraDestination.Search.route,
        LyraDestination.Library.route,
        LyraDestination.Favorites.route,
        LyraDestination.Profile.route
    )

    Scaffold(
        bottomBar = {
            if (currentRoute in showBottomBarScreens) {
                LyraBottomBar(
                    currentRoute = currentRoute,
                    onNavigate = { targetRoute ->
                        navController.navigate(targetRoute) {
                            // Ana sayfaya veya diğer sekmelere basıldığında eski ekranların birikmesini önler
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LyraDestination.Login.route,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(LyraDestination.Login.route) {
                LoginRoute(
                    onNavigateToHome = {
                        navController.navigate(LyraDestination.Home.route) {
                            popUpTo(LyraDestination.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(LyraDestination.Register.route) {
                            launchSingleTop = true
                        }
                    },
                )
            }

            composable(LyraDestination.Register.route) {
                RegisterRoute(
                    onNavigateToHome = {
                        navController.navigate(LyraDestination.Home.route) {
                            popUpTo(LyraDestination.Login.route) { inclusive = true }
                            launchSingleTop = true
                        }
                    },
                    onNavigateToLogin = {
                        navController.navigate(LyraDestination.Login.route) {
                            popUpTo(LyraDestination.Login.route) { inclusive = false }
                            launchSingleTop = true
                        }
                    },
                    onNavigateBack = { navController.popBackStack() },
                )
            }

            composable(LyraDestination.Home.route) {
                HomeRoute(
                    onNavigateToDetails = { itemId -> },
                    onNavigateToProfile = {
                        navController.navigate(LyraDestination.Profile.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(LyraDestination.Search.route) {
                val searchViewModel: SearchViewModel = viewModel()
                SearchScreen(viewModel = searchViewModel)
            }

<<<<<<< HEAD
            // GÜNCELLENEN VE GERÇEK KÜTÜPHANE TASARIMINA BAĞLANAN KÜTÜPHANE ROTASI
            composable(LyraDestination.Library.route) {
                val libraryViewModel: LibraryViewModel = hiltViewModel()
                LibraryScreen(
                    viewModel = libraryViewModel,
                    modifier = Modifier.fillMaxSize()
                )
=======
            composable(LyraDestination.Library.route) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Kütüphane Ekranı İçeriği")
                }
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
            }

            // GÜNCELLENEN VE YENİ EKRANA BAĞLANAN FAVORİLER ROTASI
            composable(LyraDestination.Favorites.route) {
                val favoritesViewModel: FavoritesViewModel = viewModel()
                FavoritesScreen(
                    viewModel = favoritesViewModel,
                    onNavigateBack = {
                        // Üst bar geri butonuna tıklandığında güvenli bir şekilde Ana Sayfaya döner
                        navController.navigate(LyraDestination.Home.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

<<<<<<< HEAD

            composable(LyraDestination.Profile.route) {
                val profileViewModel: ProfileViewModel = hiltViewModel()
                ProfileScreen(
                    viewModel = profileViewModel,
                    modifier = Modifier.fillMaxSize()
                )
=======
            composable(LyraDestination.Profile.route) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Profil Ekranı İçeriği")
                }
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
            }
        }
    }
}