package com.mr47.vazhehdictionary.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mr47.vazhehdictionary.R

class VocabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tvEnglish: TextView = itemView.findViewById(R.id.tvenglish)
    var tvPersian: TextView = itemView.findViewById(R.id.tvpersian)

    var btmSpell:ImageView = itemView.findViewById(R.id.btn_spell)
    var btmCopy:ImageView = itemView.findViewById(R.id.btn_copy)

}