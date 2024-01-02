package com.barisu.healthactivityapp

import androidx.lifecycle.ViewModel

class ChangePasswordViewModel : ViewModel() {
    private var currentPassword: String = "" //TODO to be retrieved from server.

    fun changePassword(
        newPassword: String,
        confirmPassword: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        // Check if passwords match.
        if (newPassword == confirmPassword) {
            updatePassword(newPassword)
            onSuccess.invoke()
        } else {
            onError.invoke()
        }
    }

    private fun updatePassword(newPassword: String) {
        // TODO new password will be sent to the server.
        currentPassword = newPassword
    }
}


