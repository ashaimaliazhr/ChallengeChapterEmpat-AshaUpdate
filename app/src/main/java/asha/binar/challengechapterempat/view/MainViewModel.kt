package asha.binar.challengechapterempat.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import asha.binar.data.Status
import asha.binar.data.StatusDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel (app : Application) : AndroidViewModel(app) {
    // Implement the ViewModel
    var mNote : MutableLiveData<List<Status>>

    init {
        mNote = MutableLiveData()
        getNote()
    }

    //LiveData Observer
    fun getNoteObservers(): MutableLiveData<List<Status>> {
        return mNote
    }

    //mengambil data untuk di tampilkan
    fun getNote() {
        GlobalScope.launch {
            val dataDao = StatusDatabase.getInstance(getApplication())!!.statusDao()
            val listNote = dataDao.getDataNote()
            mNote.postValue(listNote)
        }
    }

    fun insert(entityNote: Status){
        val dataDao = StatusDatabase.getInstance(getApplication())?.statusDao()
        dataDao!!.insertNote(entityNote)
        getNote()
    }

    fun delete(entityNote: Status){
        val dataDao = StatusDatabase.getInstance(getApplication())!!.statusDao()
        dataDao?.deleteNote(entityNote)
        getNote()
    }

    fun update(entityNote: Status){
        val dataDao = StatusDatabase.getInstance(getApplication())!!.statusDao()
        dataDao?.updateNote(entityNote)
        getNote()
    }
}