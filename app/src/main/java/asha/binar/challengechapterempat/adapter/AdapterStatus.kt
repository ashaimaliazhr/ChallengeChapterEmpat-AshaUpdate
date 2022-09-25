package asha.binar.challengechapterempat.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import asha.binar.challengechapterempat.MainActivity
import asha.binar.challengechapterempat.R
import asha.binar.challengechapterempat.view.HomeScreenFragment
import asha.binar.data.Status
import asha.binar.data.StatusDatabase
import kotlinx.android.synthetic.main.edit_data.view.*
import kotlinx.android.synthetic.main.item_list.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AdapterStatus(val listStatus: List<Status>) :
    RecyclerView.Adapter<AdapterStatus.ViewHolder>() {
    private var mdb: StatusDatabase? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: AdapterStatus.ViewHolder, position: Int) {
        holder.itemView.tv_id.text = listStatus[position].id.toString()
        holder.itemView.tv_name.text = listStatus[position].nama
        holder.itemView.tv_note.text = listStatus[position].status

        //        ===================================== Codingan Bagian Hapus Data ============================
        holder.itemView.btn_delete.setOnClickListener {
            mdb = StatusDatabase.getInstance(it.context)

            AlertDialog.Builder(it.context)
                .setTitle("Hapus Data")
                .setMessage("Yakin Hapus Data ?")
                .setPositiveButton("Ya") { dialogInterface: DialogInterface, i: Int ->
                    GlobalScope.async {
                        val result = mdb?.statusDao()?.deleteStatus(listStatus[position])

                        (holder.itemView.context as HomeScreenFragment).activity?.runOnUiThread {
                            if (result != 0) {
                                Toast.makeText(
                                    it.context,
                                    "Data ${listStatus[position].nama} Berhasil di Hapus",
                                    Toast.LENGTH_LONG
                                ).show()
                                (holder.itemView.context as HomeScreenFragment).getDataStatus()
                            } else {
                                Toast.makeText(
                                    it.context,
                                    "Data ${listStatus[position].nama} Berhasil di Hapus",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
                .setNegativeButton("Tidak") { dialogInterface: DialogInterface, i: Int ->
                    dialogInterface.dismiss()
                }
                .show()
        }
// ============================== Codingan Bagian Edit Data  ==========================================
        holder.itemView.btn_edit.setOnClickListener {
            mdb = StatusDatabase.getInstance(it.context)

            val pindah = LayoutInflater.from(it.context).inflate(R.layout.edit_data, null, false)
            val dialogBuilder = AlertDialog.Builder(it.context)
                .setView(pindah)
                .create()

            pindah.input_judul.setText(listStatus[position].nama)
            pindah.input_note.setText(listStatus[position].status)


// ============================== Codingan bagian custom dialog untuk merubah data  ================
            pindah.btn_update.setOnClickListener {
                val nama = pindah.input_judul.text.toString()
                val status = pindah.input_note.text.toString()

                listStatus[position].nama = nama
                listStatus[position].status = status

                GlobalScope.async {
                    val ubah = mdb?.statusDao()?.updateStatus(listStatus[position])

                    (pindah.context as MainActivity).runOnUiThread{
                        if (ubah != 0){
                            Toast.makeText(
                                it.context,
                                "Data ${listStatus[position].nama} Berhasil di Ubah",
                                Toast.LENGTH_LONG
                            ).show()
                            (pindah.context as MainActivity).recreate()
                        }else{
                            Toast.makeText(
                                it.context,
                                "Data ${listStatus[position].nama} Berhasil di Ubah",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
            dialogBuilder.show()
        }

    }

    override fun getItemCount(): Int {
        return listStatus.size
    }
}



