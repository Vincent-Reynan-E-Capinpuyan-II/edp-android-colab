package com.example.capinpuyan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.capinpuyan.ui.theme.CapinpuyanTheme

// Stretch Goal: Extract colors and sizes into named constants
object Brand {
    val PrimaryColor = Color(0xFF771C1B)
    val BackgroundColor = Color(0xFFF4F2F1)
    val AvatarSize = 120.dp
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CapinpuyanTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    BusinessCard(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


@Composable
fun BusinessCard(modifier: Modifier = Modifier) {
    // Step 2 — Add the outer layout
    // Use theme-aware background for true dark mode support
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Stretch Goal: Wrap the card content in a Material Card with elevation
        Card(
            modifier = Modifier.padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            // Use surface color for the card container
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // Step 3 — Build the circular avatar
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(Brand.AvatarSize)
                        .clip(CircleShape)
                        // Use primary from theme or brand color for border
                        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                )

                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Vincent Reynan E. Capinpuyan II",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    // Use onSurface for text to adapt to dark/light
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Software Engineer",
                    fontSize = 16.sp,
                    // Use onSurfaceVariant for secondary text
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                // Step 5 — Build reusable contact rows
                Spacer(Modifier.height(24.dp))
                ContactRow(
                    icon = Icons.Default.Phone,
                    label = "09532768123",
                    onClickLabel = "Call Vincent"
                )
                ContactRow(
                    icon = Icons.Default.Email,
                    label = "vcapinpuyan32170@liceo.edu.ph",
                    onClickLabel = "Email Vincent"
                )
            }
        }
    }
}

@Composable
fun ContactRow(
    icon: ImageVector,
    label: String,
    onClickLabel: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable(
                onClickLabel = onClickLabel,
                onClick = { /* TODO: real action later */ }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            // Use primary color from theme for icons
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(name = "Card - Light", showBackground = true, widthDp = 360)
@Composable
fun BusinessCardPreview() {
    CapinpuyanTheme(darkTheme = false) {
        BusinessCard()
    }
}

@Preview(
    name = "Card - Dark",
    showBackground = true,
    widthDp = 360,
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun BusinessCardDarkPreview() {
    // Force darkTheme = true for this preview
    CapinpuyanTheme(darkTheme = true) {
        BusinessCard()
    }
}

@Preview(name = "Card - Large Font", showBackground = true, widthDp = 360, fontScale = 1.5f)
@Composable
fun BusinessCardLargeFontPreview() {
    CapinpuyanTheme {
        BusinessCard()
    }
}
