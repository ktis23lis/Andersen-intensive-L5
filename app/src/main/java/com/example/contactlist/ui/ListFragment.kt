package com.example.contactlist.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.example.contactlist.PersonContact
import com.example.contactlist.R

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var listView: ListView
    private lateinit var personContact: PersonContact
    private lateinit var itemSelected: ItemSelected
    private var nameList = arrayListOf<String>()
    private var index = 0

    companion object {
        var contactList = arrayListOf<PersonContact>()
        const val LIST_TAG = "LIST_TAG"
        fun newInstance() = ListFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        itemSelected = context as ItemSelected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("Index")
            contactList =
                    savedInstanceState.getParcelableArrayList<PersonContact>("Array") as ArrayList<PersonContact>
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (contactList.isEmpty()) {
            contactList.add(PersonContact("1", "1", "1"))
            contactList.add(PersonContact("2", "2", "2"))
            contactList.add(PersonContact("3", "3", "3"))
            contactList.add(PersonContact("4", "4", "4"))
        }

        for (i in 0 until contactList.size) {
            nameList.add(contactList[i].lastName)
        }
        listView = view.findViewById(R.id.listView)
        val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_expandable_list_item_1,
                nameList
        )
        listView.adapter = adapter

        listView.onItemClickListener =
                AdapterView.OnItemClickListener { _, _, position, _ ->
                    personContact = contactList[position]
                    itemSelected.onItemSelected(position, personContact)
                }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArrayList("Array", contactList)
        outState.putInt("Index", index)
        super.onSaveInstanceState(outState)
    }

    interface ItemSelected {
        fun onItemSelected(index: Int, personContact: PersonContact)
    }
}