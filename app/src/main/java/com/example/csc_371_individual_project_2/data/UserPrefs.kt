package com.example.csc_371_individual_project_2.data

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

object UserPrefs {
    private const val PREFS = "codeiq_prefs"
    private const val K_EMAIL = "user_email"
    private const val K_PASSWORD = "user_password"
    private const val K_NAME = "user_name"
    private const val K_HIGHSCORE = "user_highscore"
    private const val K_HISTORY = "history_json"

    /**
     * UserPrefs
     *  - Saves User Preferences, Reg-info, and HS
     *  - Context =  State
     */
    fun saveUser(context: Context, firstName: String, email: String, password: String) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit()
            .putString(K_NAME, firstName)
            .putString(K_EMAIL, email)
            .putString(K_PASSWORD, password)
            .apply()
    }

    fun getEmail(context: Context) = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(K_EMAIL, null)
    fun getPassword(context: Context) = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(K_PASSWORD, null)
    fun getName(context: Context) = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(K_NAME, null)

    fun getHighScore(context: Context) = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getInt(K_HIGHSCORE, 0)
    fun updateHighScoreIfBetter(context: Context, score: Int) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val current = prefs.getInt(K_HIGHSCORE, 0)
        if (score > current) prefs.edit().putInt(K_HIGHSCORE, score).apply()
    }

    fun appendAttempt(context: Context, score: Int, total: Int, timeMillis: Long = System.currentTimeMillis()) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = prefs.getString(K_HISTORY, "[]") ?: "[]"
        val arr = JSONArray(json)
        val obj = JSONObject().apply {
            put("t", timeMillis)
            put("s", score)
            put("tot", total)
        }
        arr.put(obj)
        prefs.edit().putString(K_HISTORY, arr.toString()).apply()
    }

    fun getHistory(context: Context): List<Attempt> {
        val json = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(K_HISTORY, "[]") ?: "[]"
        val arr = JSONArray(json)
        val out = mutableListOf<Attempt>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            out += Attempt(
                timeMillis = o.optLong("t", 0L),
                score = o.optInt("s", 0),
                total = o.optInt("tot", 0)
            )
        }
        return out
    }

    data class Attempt(val timeMillis: Long, val score: Int, val total: Int)
}
