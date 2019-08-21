package com.android.edittextrecycelrview

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var number: Int? = null
    private var list = ArrayList<Count>()
    private var mCustomAdapter: CustomAdapter? = null
    private var mFilterList = ArrayList<Count>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        rxSearch(edt_Values)

        btn_Ok.setOnClickListener {
            for (language in list) {
                if (language.isHide) {
                    mFilterList.add(language)
                }
            }
            Log.d("Tag", Gson().toJson(mFilterList))
        }
    }

    @SuppressLint("CheckResult", "SetTextI18n")
    private fun rxSearch(searchView: AppCompatEditText) {
        val sharedTextChanges: Observable<CharSequence> = RxTextView.textChanges(searchView).share()
        sharedTextChanges.debounce(1000, TimeUnit.MILLISECONDS) // use debounce
            .observeOn(AndroidSchedulers.mainThread()).subscribe { onNext ->
                if (!onNext.isNullOrEmpty()) {
                    list.clear()
                    number = onNext.toString().toInt()
                    if (number!! > 20) {
                        number = 20
                    }
                    for (i in 0 until number!!.toInt()) {
                        list.add(Count(i + 1, false))
                    }
                    txt_TotalCount.visibility = View.VISIBLE
                    txt_TotalCount.text = "Total Count " + recyclerView.adapter!!.itemCount.toString()
                    mCustomAdapter!!.notifyDataSetChanged()
                    recyclerView.scheduleLayoutAnimation()
                }
            }
    }

    private fun initRecyclerView() {
        val controller: LayoutAnimationController =
            AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = controller
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        mCustomAdapter = CustomAdapter(this, list, object : CustomAdapter.ItemChecked {
            override fun selectedItems(position: Int, isChecked: Boolean) {
                list[position].isHide = isChecked
                list[position].flag = position
            }
        })
        recyclerView.adapter = mCustomAdapter
    }
}
