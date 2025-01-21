package com.msan.ysoftapp.domain.model


import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import dagger.assisted.Assisted
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Assignment(
    val id: Int,
    val teacherId: Int,
    val courseId: Int,
    val assistantId: Int,
    val name: String,
    val details: String,
    val marks: Int,
    val recurrence: String,
    val startDate: Date,
    val timeAllowed: Int,
    val difficulty: String,
) : Parcelable

class AssetParamType : NavType<Assignment>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Assignment? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Assignment {
        return Gson().fromJson(value, Assignment::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Assignment) {
        bundle.putParcelable(key, value)
    }
}
