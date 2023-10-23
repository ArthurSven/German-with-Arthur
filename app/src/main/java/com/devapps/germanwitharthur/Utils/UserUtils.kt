package com.devapps.germanwitharthur.Utils
import android.content.Context
object UserUtils {
    private const val PREFS_NAME = "UserPrefs"

    fun saveUserDetails(context: Context, displayName: String?, displayPicture: String?) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("displayName", displayName)
        editor.putString("displayPicture", displayPicture)
        // Add other user details to SharedPreferences as needed
        editor.apply()
    }

    fun getUserDetails(context: Context): Pair<String?, String?> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val displayName = sharedPreferences.getString("displayName", null)
        val displayPicture = sharedPreferences.getString("displayPicture", null)
        return Pair(displayName, displayPicture)
    }

    fun clearUserData(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // Clear all data in the SharedPreferences
        editor.apply()
        editor.commit()
    }
}