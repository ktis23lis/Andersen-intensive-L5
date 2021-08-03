package com.example.contactlist.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.contactlist.PersonContact
import com.example.contactlist.R

class ItemFragment : Fragment(R.layout.fragment_item) {

    private lateinit var nameTextView: TextView
    private lateinit var lastNameTextView: TextView
    private lateinit var numberTextView: TextView
    private lateinit var nameEdinText: EditText
    private lateinit var lastNameEdinText: EditText
    private lateinit var numberEdinText: EditText
    private lateinit var changeBtn: Button
    private lateinit var okBtn: Button
    private lateinit var personContact: PersonContact
    private lateinit var onClickBottomChange: OnClickBottomChange

    private var index = 0

    companion object {
        fun newInstance(personContact: PersonContact, index: Int) = ItemFragment().apply {
            this.personContact = personContact
            this.index = index
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onClickBottomChange = context as OnClickBottomChange
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            personContact = arguments?.getParcelable(ListFragment.LIST_TAG)!!
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        nameTextView.text = personContact.name
        lastNameTextView.text = personContact.lastName
        numberTextView.text = personContact.number
        nameEdinText.setText(personContact.name)
        lastNameEdinText.setText(personContact.lastName)
        numberEdinText.setText(personContact.number)

        changeBtn.setOnClickListener {
            nameTextView.visibility = View.GONE
            lastNameTextView.visibility = View.GONE
            numberTextView.visibility = View.GONE
            changeBtn.visibility = View.GONE
            nameEdinText.visibility = View.VISIBLE
            lastNameEdinText.visibility = View.VISIBLE
            numberEdinText.visibility = View.VISIBLE
            okBtn.visibility = View.VISIBLE

            okBtn.setOnClickListener {
                nameTextView.visibility = View.VISIBLE
                lastNameTextView.visibility = View.VISIBLE
                numberTextView.visibility = View.VISIBLE
                changeBtn.visibility = View.VISIBLE
                nameEdinText.visibility = View.GONE
                lastNameEdinText.visibility = View.GONE
                numberEdinText.visibility = View.GONE
                okBtn.visibility = View.GONE

                val newName = nameEdinText.text.toString()
                val newLastName = lastNameEdinText.text.toString()
                val newNumber = numberEdinText.text.toString()

                personContact.name = newName
                personContact.lastName = newLastName
                personContact.number = newNumber

                nameTextView.text = newName
                lastNameTextView.text = newLastName
                numberTextView.text = newNumber

                ListFragment.contactList[index].name = newName
                ListFragment.contactList[index].lastName = newLastName
                ListFragment.contactList[index].number = newNumber

                onClickBottomChange.onClickedBottomChange()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().findViewById<FrameLayout>(R.id.listContainer).visibility = View.VISIBLE
    }

    private fun initView(view: View) {
        nameTextView = view.findViewById(R.id.nameTextView)
        lastNameTextView = view.findViewById(R.id.lastNameTextView)
        numberTextView = view.findViewById(R.id.numberTextView)
        nameEdinText = view.findViewById(R.id.nameEditText)
        lastNameEdinText = view.findViewById(R.id.lastNameEditText)
        numberEdinText = view.findViewById(R.id.numberEditText)
        changeBtn = view.findViewById(R.id.changeBtn)
        okBtn = view.findViewById(R.id.okBtn)
    }

    interface OnClickBottomChange {
        fun onClickedBottomChange()
    }
}