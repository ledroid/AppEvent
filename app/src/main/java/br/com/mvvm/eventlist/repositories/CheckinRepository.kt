package br.com.mvvm.eventlist.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import br.com.mvvm.eventlist.models.EventPeople
import br.com.mvvm.eventlist.network.ApiClient
import br.com.mvvm.eventlist.utilities.Utility
import br.com.mvvm.eventlist.utilities.Utility.showProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object CheckinRepository {

    fun getMutableLiveData(
        context: Context,
        idEvent: String,
        name: String,
        email: String
    ): MutableLiveData<ArrayList<EventPeople>> {

        val mutableLiveData = MutableLiveData<ArrayList<EventPeople>>()

        context.showProgressBar()

        ApiClient.apiService.postCheckin(idEvent, name, email).enqueue(object :
            Callback<MutableList<EventPeople>> {
            override fun onFailure(call: Call<MutableList<EventPeople>>, t: Throwable) {
                Utility.hideProgressBar()
                Log.e("error", t.localizedMessage)
            }

            override fun onResponse(
                call: Call<MutableList<EventPeople>>,
                response: Response<MutableList<EventPeople>>
            ) {
                Utility.hideProgressBar()
                val usersResponse = response.body()
                usersResponse?.let { mutableLiveData.value = it as ArrayList<EventPeople> }
            }

        })

        return mutableLiveData
    }
}