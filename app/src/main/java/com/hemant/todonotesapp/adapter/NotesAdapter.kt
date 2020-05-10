//package com.hemant.todonotesapp.adapter
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.CheckBox
//import android.widget.CompoundButton
//import android.widget.TextView
package com.hemant.todonotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hemant.todonotesapp.R
import com.hemant.todonotesapp.clicklisteners.ItemClickListener
import com.hemant.todonotesapp.db.Notes

class NotesAdapter(val listNotes: List<Notes>,val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notes = listNotes[position]
        val title = notes.title
        val description = notes.description
        holder.textViewTitle.text = title
        holder.checkboxMarkStatus.isChecked=notes.isTaskCompleted
        holder.textViewDescription.text = description
        Glide.with(holder.itemView).load(notes.imagePath).into(holder.imageView)
        holder.itemView.setOnClickListener { itemClickListener.onClick(notes) }
        //for checkbox
        holder.checkboxMarkStatus.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                notes.isTaskCompleted=isChecked
                itemClickListener.onUpdate(notes)

            }

        })
    }

    override fun getItemCount(): Int {
        return listNotes.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewTitle: TextView= itemView.findViewById(R.id.textViewTitle)
        var textViewDescription: TextView= itemView.findViewById(R.id.textViewDescription)
        var checkboxMarkStatus:CheckBox=itemView.findViewById(R.id.checkboxMarkStatus)
        var imageView:ImageView=itemView.findViewById(R.id.imageView)
    }
}
//import androidx.recyclerview.widget.RecyclerView

//import com.hemant.todonotesapp.R
//import com.hemant.todonotesapp.clicklisteners.ItemClickListener
//import com.hemant.todonotesapp.db.Notes
//
//class NotesAdapter (val list:List<Notes>, val itemClickListener: ItemClickListener):RecyclerView.Adapter<NotesAdapter.ViewHolder>(){
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesAdapter.ViewHolder {
//    val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//
//    }
//
//    override fun onBindViewHolder(holder: NotesAdapter.ViewHolder, position: Int) {
//        val notes = list[position]
//        val title = notes.title
//        val description = notes.description
//        holder.textViewTitle.text=title
//        holder.textViewDescription.text=description
//        holder.checkboxMarkStatus.isChecked=notes.isTaskCompleted
//
//        holder.itemView.setOnClickListener(object: View.OnClickListener {
//            override fun onClick(p0: View?) {
//                itemClickListener.onClick(notes)
//            }
//
//        })
//                holder.checkboxMarkStatus.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener{
//            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
//              //  notes.isTaskCompleted=isChecked
//                notes.isTaskCompleted=isChecked
//                itemClickListener.onUpdate(notes)
//
//            }
//
//        })
//
//    }
//    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
//        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
//        val checkboxMarkStatus:CheckBox=itemView.findViewById(R.id.checkboxMarkStatus)
//
//    }
//
//}
////class NotesAdapter(val listNotes: List<Notes>,val itemClickListener: ItemClickListener) : RecyclerView.Adapter<NotesAdapter.ViewHolder>()
////{
////    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
////        val view = LayoutInflater.from(parent.context).inflate(R.layout.notes_adapter_layout, parent, false)
////        return ViewHolder(view)
////    }
////
////    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
////        val notes = listNotes[position]
////        val title = notes.title
////        val description = notes.description
////        holder.textViewTitle.text = title
////        holder.checkboxMarkStatus.isChecked=notes.isTaskCompleted
////        holder.textViewDescription.text = description
////        Glide.with(holder.itemView).load(notes.imagePath).into(holder.imageView)
////        holder.itemView.setOnClickListener { itemClickListener.onClick(notes) }
////        //for checkbox
////        holder.checkboxMarkStatus.setOnCheckedChangeListener(object:CompoundButton.OnCheckedChangeListener{
////            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
////                notes.isTaskCompleted=isChecked
////                itemClickListener.onUpdate(notes)
////
////            }
////
////        })
////    }
////
////    override fun getItemCount(): Int {
////        return listNotes.size
////    }
////    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
////        var textViewTitle: TextView= itemView.findViewById(R.id.textViewTitle)
////        var textViewDescription: TextView= itemView.findViewById(R.id.textViewDescription)
////        var checkboxMarkStatus:CheckBox=itemView.findViewById(R.id.checkboxMarkStatus)
////        var imageView:ImageView=itemView.findViewById(R.id.imageView)
////    }
////}