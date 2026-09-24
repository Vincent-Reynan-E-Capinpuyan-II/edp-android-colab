package com.example.capinpuyan.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.capinpuyan.domain.model.User
import java.util.Calendar

@Composable
fun ProfileScreen(user: User, onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {
                Text(
                    "You successfully logged in!",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1B5E20)
                )
                Text("Welcome back, ${user.fullName}.")
            }
        }

        Text("My Profile", style = MaterialTheme.typography.headlineSmall)

        ProfileRow("Full name", user.fullName)
        ProfileRow("Email", user.email)
        ProfileRow("Birthdate", user.birthdate)
        ProfileRow("User ID", user.id)

        ageFrom(user.birthdate)?.let {
            ProfileRow("Age", "$it years old")
        }

        Button(onClick = onLogout, modifier = Modifier.fillMaxWidth()) {
            Text("Log out")
        }
    }
}

@Composable
fun ProfileRow(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
}

fun ageFrom(birthdate: String): Int? {
    val parts = birthdate.trim().split("-")
    if (parts.size != 3) return null
    if (parts[0].length != 4 || parts[1].length != 2 || parts[2].length != 2) return null

    val y = parts[0].toIntOrNull() ?: return null
    val m = parts[1].toIntOrNull() ?: return null
    val d = parts[2].toIntOrNull() ?: return null

    if (y < 1 || m !in 1..12 || d !in 1..31) return null

    val birthDateValid = try {
        val candidate = Calendar.getInstance().apply {
            clear()
            isLenient = false
            set(y, m - 1, d)
        }
        candidate.timeInMillis
        true
    } catch (_: IllegalArgumentException) {
        false
    }
    if (!birthDateValid) return null

    val now = Calendar.getInstance()
    val ty = now.get(Calendar.YEAR)
    val tm = now.get(Calendar.MONTH) + 1
    val td = now.get(Calendar.DAY_OF_MONTH)

    var age = ty - y
    if (tm < m || (tm == m && td < d)) age -= 1
    return age.takeIf { it >= 0 }
}
