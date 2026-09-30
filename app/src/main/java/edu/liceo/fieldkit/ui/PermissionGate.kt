package edu.liceo.fieldkit.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import edu.liceo.fieldkit.permissions.*

@Composable
fun PermissionGate(
    state: PermissionState,
    feature: String,   // for example "Camera"
    reason: String,    // why the app needs it
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        when (state.status) {
            // 4a: granted -> show the real feature
            PermStatus.Granted -> content()

            // 4b: never asked -> offer to ask
            PermStatus.NotAsked -> Button(onClick = state.request) {
                Text("Allow $feature")
            }

            // 4c: asked once and turned down -> explain why, then ask again
            PermStatus.NeedsRationale -> {
                Text(reason)
                Button(onClick = state.request) { Text("Try again") }
            }

            // 4d: blocked for good -> the dialog will not show again, so the
            // only way out is the app's Settings page
            PermStatus.Denied -> {
                Text("$feature is blocked. Turn it on in Settings.")
                Button(onClick = { context.openAppSettings() }) { Text("Open Settings") }
            }
        }
    }
}