package net.mizucoffee.canislupus.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PunishmentViewModel : ViewModel() {
    val transition = MutableLiveData<String>()

    fun next() {
        transition.postValue("s")
    }
}
