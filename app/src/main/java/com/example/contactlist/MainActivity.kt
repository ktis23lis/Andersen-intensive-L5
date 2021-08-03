package com.example.contactlist

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.contactlist.ui.ItemFragment
import com.example.contactlist.ui.ListFragment


class MainActivity : AppCompatActivity(R.layout.activity_main), ListFragment.ItemSelected,
        ItemFragment.OnClickBottomChange {

    private lateinit var listFrameLayout: FrameLayout
    private lateinit var contactFrameLayout: FrameLayout
    var isLandscape: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()

        isLandscape = (resources.configuration.orientation
                == Configuration.ORIENTATION_LANDSCAPE)

        if (savedInstanceState == null) {
            if (isLandscape) {
                initListFragment()
            } else {
                contactFrameLayout.visibility = View.GONE
                initListFragment()
            }
        }
    }

    private fun initView() {
        contactFrameLayout = findViewById(R.id.contactContainer)
        listFrameLayout = findViewById(R.id.listContainer)
    }

    private fun initListFragment() {
        supportFragmentManager.beginTransaction().run {
            val listFragment = ListFragment.newInstance()
            replace(R.id.listContainer, listFragment)
            commit()
        }
    }

    override fun onItemSelected(index: Int, personContact: PersonContact) {
        if (isLandscape) {
            contactFrameLayout.visibility = View.VISIBLE

            val bundle = Bundle()
            bundle.putParcelable(ListFragment.LIST_TAG, personContact)

            supportFragmentManager.beginTransaction().run {
                val itemFragment = ItemFragment.newInstance(personContact, index)
                itemFragment.arguments = bundle
                replace(R.id.contactContainer, itemFragment)
                commit()
            }
        } else {
            contactFrameLayout.visibility = View.VISIBLE
            listFrameLayout.visibility = View.GONE
            val bundle = Bundle()
            bundle.putParcelable(ListFragment.LIST_TAG, personContact)

            supportFragmentManager.beginTransaction().run {
                val itemFragment = ItemFragment.newInstance(personContact, index)
                itemFragment.arguments = bundle
                replace(R.id.contactContainer, itemFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    override fun onClickedBottomChange() {
        initListFragment()
    }
}