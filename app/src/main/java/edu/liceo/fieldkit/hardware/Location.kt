package edu.liceo.fieldkit.hardware

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource

@SuppressLint("MissingPermission") // GIVEN: LocationCard checks the permission first
fun Context.currentLocation(onResult: (Location?) -> Unit) {
    val client = LocationServices.getFusedLocationProviderClient(this)

    // TODO 10a: client.getCurrentLocation(HIGH_ACCURACY priority, a new token)
    // A field report wants the most exact position, so we pay the battery
    // cost once rather than streaming updates.
    // TODO 10b: .addOnSuccessListener -> give the location (may be null) to onResult
    // loc is null when Location is switched off or there is no fix yet.
    // TODO 10c: .addOnFailureListener -> onResult(null)
    client.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    )
        .addOnSuccessListener { loc -> onResult(loc) }
        .addOnFailureListener { onResult(null) }
}