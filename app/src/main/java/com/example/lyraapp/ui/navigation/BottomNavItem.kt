package com.example.lyraapp.ui.navigation

<<<<<<< HEAD
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
=======
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.List
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.lyraapp.ui.icons.LyraIcons

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomNavItem(LyraDestination.Home.route, "Ana sayfa", LyraIcons.Home)
    data object Search : BottomNavItem(LyraDestination.Search.route, "Ara", LyraIcons.Search)
    data object Library : BottomNavItem(LyraDestination.Library.route, "Kütüphane", LyraIcons.LibraryMusic)
<<<<<<< HEAD


    data object Favorites : BottomNavItem(LyraDestination.Favorites.route, "Favoriler", Icons.Default.Favorite)


    data object Profile : BottomNavItem(LyraDestination.Profile.route, "Profil", Icons.Default.Person)
=======
    data object Favorites : BottomNavItem(LyraDestination.Favorites.route, "Favoriler", androidx.compose.material.icons.Icons.Default.List)
    data object Profile : BottomNavItem(LyraDestination.Profile.route, "Profil", androidx.compose.material.icons.Icons.Default.List)
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede

    companion object {
        val items = listOf(Home, Search, Library, Favorites, Profile)
    }
}