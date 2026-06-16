package com.example.lyraapp.ui.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        // Ekran her açıldığında bilgilerin güncelliğinden emin olmak için intent tetikliyoruz
        viewModel.onIntent(ProfileContract.Intent.LoadUserProfile)

        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is ProfileContract.SideEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
                ProfileContract.SideEffect.NavigateToSettings -> {
                    // Navigasyon lojiği
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Profil", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                actions = {
                    IconButton(onClick = { viewModel.onIntent(ProfileContract.Intent.OnSettingsClick) }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Ayarlar",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Profil Resmi Avatarı (Gelen dinamik ismin baş harflerine göre otomatik hesaplanır)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.secondary
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                val initials = state.userName.split(" ")
                    .filter { it.isNotBlank() }
                    .mapNotNull { it.firstOrNull()?.uppercase() }
                    .joinToString("")

                Text(
                    text = if (initials.isNotEmpty()) initials else "MK",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dinamik Kullanıcı Adı Soyadı
            Text(
                text = state.userName.ifBlank { "Yükleniyor..." },
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            // Dinamik E-posta / Kullanıcı Etiketi
            Text(
                text = state.userHandle,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // İstatistik Alanı (Çalma Listesi, Takipçi, Takip)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileStatItem(count = state.playlistCount.toString(), label = "Çalma listesi")
                ProfileStatItem(count = state.followersCount.toString(), label = "Takipçi")
                ProfileStatItem(count = state.followingCount.toString(), label = "Takip")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Görünüm (Tema Seçimi) Bölümü
            Text(
                text = "Görünüm",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Açık / Koyu Tema Seçim Çubuğu
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(4.dp)
            ) {
                val activeModifier = Modifier
                    .weight(1.0f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primary)

                val inactiveModifier = Modifier
                    .weight(1.0f)
                    .fillMaxHeight()
                    .clickable { viewModel.onIntent(ProfileContract.Intent.ToggleTheme) }

                Box(
                    modifier = if (!state.isDarkTheme) activeModifier else inactiveModifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "☀️ Açık",
                        color = if (!state.isDarkTheme) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
                Box(
                    modifier = if (state.isDarkTheme) activeModifier else inactiveModifier,
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🌙 Koyu",
                        color = if (state.isDarkTheme) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Ayarlar Listesi Satırları
            ProfileSettingRow(
                title = "Ses kalitesi",
                value = state.audioQuality,
                onClick = { viewModel.onIntent(ProfileContract.Intent.OnAudioQualityClick(state.audioQuality)) }
            )

            ProfileSettingRowWithSwitch(
                title = "Çevrimdışı indirme",
                checked = state.isOfflineDownloadEnabled,
                onCheckedChange = { viewModel.onIntent(ProfileContract.Intent.OnOfflineDownloadToggle) }
            )

            ProfileSettingRow(
                title = "Bildirimler",
                onClick = { viewModel.onIntent(ProfileContract.Intent.OnNotificationsClick) }
            )

            ProfileSettingRow(
                title = "Gizlilik",
                onClick = { viewModel.onIntent(ProfileContract.Intent.OnPrivacyClick) }
            )

            ProfileSettingRow(
                title = "Yardım ve destek",
                onClick = { viewModel.onIntent(ProfileContract.Intent.OnHelpSupportClick) }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileStatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = count, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
fun ProfileSettingRow(
    title: String,
    value: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (value != null) {
                Text(text = value, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(end = 8.dp))
            }
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun ProfileSettingRowWithSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onBackground)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}