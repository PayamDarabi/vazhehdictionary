package com.mr47.vazhehdictionary.ui.vocab

import VocabsAdapter
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatEditText
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mr47.vazhehdictionary.Helpers.DBHelper
import com.mr47.vazhehdictionary.Models.Major
import com.mr47.vazhehdictionary.Models.Vocab
import com.mr47.vazhehdictionary.R
import com.mr47.vazhehdictionary.databinding.FragmentVocabBinding

class VocabsFragment : Fragment() {
    var majors : MutableList<Major> = ArrayList()
    lateinit var db: DBHelper
    var selectedMajorId = 0;
    private var _binding: FragmentVocabBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = this?.let { context?.let { it1 -> DBHelper(it1) } }!!
        majors = this?.let { context?.let { it1 -> getMajors() } }!!

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         _binding = FragmentVocabBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val rootView = root.findViewById<ConstraintLayout>(R.id.vocabsArea)
        val edtSearch: AppCompatEditText = rootView!!.findViewById(R.id.edtSearchBox)
        edtSearch.hint = resources.getString(R.string.strSearchText)
        val type = this?.let { context?.let { it1 -> ResourcesCompat.getFont(it1, R.font.iroodak) } }
        edtSearch.typeface=type
        val vocabs = this?.let { context?.let { _ -> getVocabs(majors[0].Id) } }

        val vocabsView: RecyclerView = rootView.findViewById(R.id.vocabsList)
        val linearLayoutManager = LinearLayoutManager(context)
        val spinner = rootView.findViewById<Spinner>(R.id.spinner)
        if (spinner != null) {
            spinner.adapter = context?.let {
                ArrayAdapter(
                    it,
                    R.layout.custom_spinner_item, majors
                )
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    val major: Major = parent.selectedItem as Major
                    val vocabs = context?.let { getVocabs(major.Id) }
                    selectedMajorId = major.Id
                    if (vocabs?.size !!> 0) {
                        vocabsView.visibility = View.VISIBLE
                        val mAdapter = this?.let { VocabsAdapter(vocabs) }
                        vocabsView.adapter = mAdapter
                    } else {
                        vocabsView.visibility = View.GONE

                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }

        vocabsView.layoutManager = linearLayoutManager
        vocabsView.setHasFixedSize(true)
        if (vocabs?.size !!> 0) {
            vocabsView.visibility = View.VISIBLE
            val mAdapter = this?.let { VocabsAdapter(vocabs) }
            vocabsView.adapter = mAdapter
        } else {
            vocabsView.visibility = View.GONE

        }
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if(s.length>3){
                    val vocabs= doSearch(s, vocabs as ArrayList<Vocab>?)

                    vocabsView.layoutManager = linearLayoutManager
                    vocabsView.setHasFixedSize(true)
                    if (vocabs?.size !!> 0) {
                        vocabsView.visibility = View.VISIBLE
                        val mAdapter = this?.let { VocabsAdapter(vocabs as ArrayList<Vocab>) }
                        vocabsView.adapter = mAdapter
                    } else {
                        vocabsView.visibility = View.GONE
                    }
                }
                else if(s.length==0){
                    val vocabs= context?.let { getVocabs(selectedMajorId) }
                    vocabsView.layoutManager = linearLayoutManager
                    vocabsView.setHasFixedSize(true)
                    if (vocabs?.size !!> 0) {
                        vocabsView.visibility = View.VISIBLE
                        val mAdapter = this?.let { VocabsAdapter(vocabs as ArrayList<Vocab>) }
                        vocabsView.adapter = mAdapter
                    }
                }
            }

        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    fun getVocabs(majorId: Int): ArrayList<Vocab> {
        val cursor = db.getMajorsVocabs(majorId)

        // moving the cursor to first position and
        // appending value in the text view
        cursor!!.moveToFirst()
        var vocabs: ArrayList<Vocab> = arrayListOf()
        while (cursor.moveToNext()) {
            vocabs.add(
                Vocab(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.VOCAB_PERSIAN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.VOCAB_ENGLISH))
                )
            )
        }
        // at last we close our cursor
        cursor.close()
        return vocabs
    }

    fun doSearch(searchedVocab: CharSequence, words: ArrayList<Vocab>?): List<Vocab> {
        if (words != null) {
            Log.d("RESULTS LIST",words.count().toString())
        }
        val searched: List<Vocab> = words!!.filter { s ->
            s.English.contains(searchedVocab) || s.Persian.contains(searchedVocab)
        }
        Log.d("RESULTS LIST",searched.count().toString())
        return searched
    }

    fun getMajors(): ArrayList<Major> {
        val cursor = db.getMajors()

        // moving the cursor to first position and
        // appending value in the text view
        cursor!!.moveToFirst()
        var majors: ArrayList<Major> = arrayListOf()
        while (cursor.moveToNext()) {
            majors.add(
                Major(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.MAJOR_ENGLISHTITLE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DBHelper.MAJOR_PERSIANTITLE))
                )
            )
        }

        // at last we close our cursor
        cursor.close()
        return majors
    }

}