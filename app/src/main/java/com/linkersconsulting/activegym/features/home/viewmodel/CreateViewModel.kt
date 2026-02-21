package com.linkersconsulting.activegym.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.linkersconsulting.activegym.features.init.data.model.GymMembersDataClass
import com.linkersconsulting.activegym.features.init.data.repository.UserRepository
import java.util.Calendar

class UserViewModel : ViewModel() {

    private val repository = UserRepository()


    private val _saveResult = MutableLiveData<Boolean>()
    val saveResult: LiveData<Boolean> = _saveResult

    fun createUser(name: String, email: String, membership: String) {

        val calendar = Calendar.getInstance()

        when (membership) {
            "Diaria" -> calendar.add(Calendar.DAY_OF_YEAR, 1)
            "Semanal" -> calendar.add(Calendar.DAY_OF_YEAR, 7)
            "Mensual" -> calendar.add(Calendar.MONTH, 1)
            "Anual" -> calendar.add(Calendar.YEAR, 1)
        }

        val user = GymMembersDataClass(
            name = name,
            email = email,
            membershipType = membership,
            membershipEndDate = calendar.timeInMillis
        )

        repository.saveUser(user) { success ->
            _saveResult.postValue(success)
        }
    }
}
