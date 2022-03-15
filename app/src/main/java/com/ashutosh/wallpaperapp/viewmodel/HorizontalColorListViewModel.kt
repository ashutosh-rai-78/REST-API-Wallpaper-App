package com.ashutosh.wallpaperapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashutosh.wallpaperapp.models.CategoryModel
import com.ashutosh.wallpaperapp.models.ColorModel
import com.ashutosh.wallpaperapp.network.ListStatus
import com.ashutosh.wallpaperapp.network.ListStatusObserver
import com.ashutosh.wallpaperapp.repository.WallpapersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HorizontalColorListViewModel : ViewModel() {

    private val _list: ArrayList<ColorModel> = ArrayList()
    val list: List<ColorModel> = _list
    private val _listObserver: MutableLiveData<ListStatusObserver> =
        MutableLiveData(ListStatusObserver(ListStatus.INITIAL_LOADING))

    val listObserver: LiveData<ListStatusObserver> = _listObserver

    private val wallpapersRepository = WallpapersRepository()

    fun getColor() {
        if (_list.isNotEmpty()) return

        viewModelScope.launch(Dispatchers.IO) {
            val response = wallpapersRepository.getColor()
            Log.d("TAG", "onCreate: $response")
            if (response.isSuccessful) {
                val wallpaperPageModel = response.body()
                Log.d("TAG", "onCreate: $wallpaperPageModel")
                withContext(Dispatchers.Main) {
                    wallpaperPageModel?.let {
                        _list.addAll(it)
                        _listObserver.value = ListStatusObserver(ListStatus.INSERTED, 0, _list.size)
                    }
                }
            }
        }
    }


}