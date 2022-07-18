import android.R.attr
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.mr47.vazhehdictionary.R
import com.mr47.vazhehdictionary.ViewHolders.VocabViewHolder
import com.mr47.vazhehdictionary.Models.Vocab
import android.R.attr.label

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.core.content.ContextCompat.getSystemService
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import java.util.*
import kotlin.collections.ArrayList
import android.os.Bundle

internal class VocabsAdapter(listVocabs: ArrayList<Vocab>) :
    RecyclerView.Adapter<VocabViewHolder>(), Filterable {
    private val mArrayList: ArrayList<Vocab> = listVocabs
    var tts: TextToSpeech? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.vocab_list_layout, parent, false)
        mArrayList.sortedWith(
            compareBy(String.CASE_INSENSITIVE_ORDER) { v -> v.English.toString() }
        )
        tts = TextToSpeech(view.context) { status -> // TODO Auto-generated method stub
            // edit from original answer: I put double equal on this line
            if (status == TextToSpeech.SUCCESS) {
                tts?.setLanguage(Locale.UK)
            }
        }
        return VocabViewHolder(view)
    }

    override fun onBindViewHolder(holder: VocabViewHolder, position: Int) {
        val vocab = mArrayList[position]
        holder.tvPersian.text = vocab.Persian
        holder.tvEnglish.text = vocab.English

        holder.btmCopy.setOnClickListener {
            val clipboardManager =
                holder.itemView.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText(
                "Vocab",
                "English: " + vocab.English + " - Persian: " + vocab.Persian
            )
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(
                holder.itemView.context,
                "لغت مورد نظر شما کپی شد.",
                Toast.LENGTH_LONG
            )
                .show()
        }

        holder.btmSpell.setOnClickListener {
            val utteranceId = this.hashCode().toString() + ""
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "")
            tts!!.speak(vocab.English, TextToSpeech.QUEUE_FLUSH, params, utteranceId)

        }
    }

    override fun getItemCount(): Int {
        return mArrayList.size
    }

    override fun getFilter(): Filter {
        TODO("Not yet implemented")
    }
}
