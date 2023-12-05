package com.example.attendancemanager_multi_facedetectioninrealtime.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.LinearLayout
import android.widget.TextView
import com.example.attendancemanager_multi_facedetectioninrealtime.R

class CheckableSpinnerAdapter<T>(
    private val context: Context,
    private val headerText: String,
    private val allItems: List<SpinnerItem<T>>,
    private val courseCode: List<SpinnerCode<T>>,
    private val selectedItems: MutableSet<T>,
    private val selectedCourse: MutableSet<T>
) : BaseAdapter() {

    override fun getCount(): Int = allItems.size + 1

    override fun getItem(position: Int): Any? =
        if (position < 1) null else allItems[position - 1]

    override fun getItemId(position: Int): Long = 0

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView: View

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            itemView = layoutInflater.inflate(R.layout.spinner_item, parent, false)

            holder = ViewHolder()
            holder.linearLayout = itemView.findViewById(R.id.llId)
            holder.textView = itemView.findViewById(R.id.text)
            holder.checkBox = itemView.findViewById(R.id.checkbox)
            holder.codeView = itemView.findViewById(R.id.code)
            itemView.tag = holder
        } else {
            itemView = convertView
            holder = itemView.tag as ViewHolder
        }

        if (position < 1) {
            holder.checkBox.visibility = View.INVISIBLE
            holder.codeView.visibility = View.INVISIBLE
            holder.textView.text = headerText
        } else {
            val listPos = position - 1
            holder.checkBox.visibility = View.VISIBLE
            holder.codeView.visibility = View.VISIBLE
            holder.textView.text = allItems[listPos].txt
            holder.codeView.text = courseCode[listPos].code

            val item = allItems[listPos].item
            val codeT = courseCode[listPos].codeT
            val isSel = selectedItems.contains(item)

            holder.checkBox.setOnCheckedChangeListener(null)
            holder.checkBox.isChecked = isSel

            holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    selectedItems.add(item)
                    selectedCourse.add(codeT)
                } else {
                    selectedItems.remove(item)
                    selectedCourse.remove(codeT)
                }
            }

            holder.linearLayout.setOnClickListener {
                holder.checkBox.toggle()
            }
        }

        return itemView
    }

    private class ViewHolder {
        lateinit var textView: TextView
        lateinit var checkBox: CheckBox
        lateinit var codeView: TextView
        lateinit var linearLayout: LinearLayout
    }
}

class SpinnerItem<T>(val item: T, val txt: String)

class SpinnerCode<T>(val codeT: T, val code: String)
