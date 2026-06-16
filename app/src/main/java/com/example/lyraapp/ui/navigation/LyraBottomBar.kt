package com.example.lyraapp.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
<<<<<<< HEAD
=======
import androidx.compose.ui.graphics.Color
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LyraBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier.height(84.dp),
<<<<<<< HEAD
        // Sabit renk yerine M3 yüzey rengini kullanıyoruz (Tema geçişlerine tam uyum için)
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
=======
        containerColor = Color(0xFF141214),
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
        tonalElevation = 0.dp
    ) {
        BottomNavItem.items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavigate(item.route)
                    }
                },
                icon = {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
<<<<<<< HEAD
                            // Seçili arka plan rengini temanın sarmalayıcı tonlarından alıyoruz
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                                else androidx.compose.ui.graphics.Color.Transparent
                            )
=======
                            .background(if (isSelected) Color(0xFF423742) else Color.Transparent)
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
                            .padding(horizontal = 20.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
<<<<<<< HEAD
                            tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
=======
                            tint = if (isSelected) Color.White else Color.Gray,
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                label = {
                    Text(
                        text = item.title,
                        fontSize = 11.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
<<<<<<< HEAD
                        color = if (isSelected) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = androidx.compose.ui.graphics.Color.Transparent
=======
                        color = if (isSelected) Color.White else Color.Gray
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent
>>>>>>> fd59ceb7577583470f744260c5ed7ce5ccb4bede
                )
            )
        }
    }
}