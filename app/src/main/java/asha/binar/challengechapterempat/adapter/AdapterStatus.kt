package asha.binar.challengechapterempat.adapter

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import asha.binar.challengechapterempat.R
import asha.binar.challengechapterempat.databinding.ItemListBinding
import asha.binar.challengechapterempat.view.MainViewModel
import asha.binar.data.Status
import asha.binar.data.StatusDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AdapterStatus(var listStatus: List<Status>) :
    RecyclerView.Adapter<AdapterStatus.ViewHolder>() {
    private var mdb: StatusDatabase? = null

    class ViewHolder(var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Status) {
            binding.datanotes = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listStatus[position])
        //delete note
        holder.binding.btnDelete.setOnClickListener {
            mdb = StatusDatabase.getInstance(it.context)
            GlobalScope.async {
                MainViewModel(Application()).delete(listStatus[position])
                mdb?.statusDao()?.deleteNote(listStatus[position])
                kotlin.run {
                    Navigation.findNavController(it).navigate(R.id.mainFragment)
                }
            }
        }
        //update
        holder.binding.btnEdit.setOnClickListener {
            var update = Bundle()
            update.putSerializable("updaters", listStatus[position])
            Navigation.findNavController(it)
                .navigate(R.id.action_mainFragment_to_editFragment, update)
        }
        //detail
        holder.binding.btnDetail.setOnClickListener {
            var detail = Bundle()
            detail.putSerializable("datanotes", listStatus[position])
            Navigation.findNavController(it)
                .navigate(R.id.action_mainFragment_to_detailFragment, detail)
        }
    }

    override fun getItemCount(): Int {
        return listStatus.size
    }

    fun setNotes(itemNote: ArrayList<Status>) {
        this.listStatus = itemNote
    }

}




