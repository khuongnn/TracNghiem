package com.example.tracnghiem.activity.main.mainpractice

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.GridView
import androidx.lifecycle.Observer
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.main.mainpractice.fragment.CheckAnswerAdapter
import com.example.tracnghiem.activity.main.mainpractice.fragment.PracticeQuizFragment
import com.example.tracnghiem.adapter.SliderPageAdapter
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.data.model.Questions
import com.example.tracnghiem.databinding.ActivityPracticeBinding
import com.example.tracnghiem.utils.showView
import kotlinx.android.synthetic.main.activity_practice.*
import kotlinx.android.synthetic.main.layout_empty.*
import org.koin.android.viewmodel.ext.android.viewModel


class PracticeActivity : BaseActivity<ActivityPracticeBinding>() {
    private val mViewModel: TrainViewModel by viewModel()
    private lateinit var bundle: Bundle


    private var mListQuestion = ArrayList<Questions>()

    override fun setLayoutId(): Int = R.layout.activity_practice

    override fun initView() {}

    override fun initViewModel() {
        (binding as ActivityPracticeBinding).viewModel = mViewModel

    }

    // example
    private var itemList =  ArrayList<Questions> ()
//        get() = arrayListOf(
//            "Item 1",
//            "Item 2",
//            "Item 3",
//            "Item 4",
//            "Item 5",
//            "Item 6",
//            "Item 7",
//            "Item 8",
//            "Item 9",
//            "Item 10",
//            "Item 11",
//            "Item 12",
//            "Item 13",
//            "Item 14",
//            "Item 15",
//            "Item 16",
//            "Item 17",
//            "Item 18",
//            "Item 19",
//            "Item 20",
//            "Item 21",
//            "Item 22"
//        )


    override fun initData() {
        val quizId = intent.getStringExtra("quizId")
        val quizName = intent.getStringExtra("quizName")
        //PGDpJ5OFShWI89lvWB1U
        quizId?.let {
            if (it == "PGDpJ5OFShWI89lvWB1U") {
                mViewModel.requestListQuestion()
                // Setting time and ?? position
            } else {
                tvEmpty.showView()
            }
        }

    }

    override fun initListener() {
        mViewModel.getListQuestionLiveData().observe(this, Observer { result ->
            result?.let {
                mListQuestion = result
                itemList= mListQuestion
                val mData = mListQuestion.size

                viewpager.offscreenPageLimit = 3
                val mPageAdapter = SliderPageAdapter(supportFragmentManager)
                for (i in 0..mData) {
                    bundle = Bundle()
                    bundle.putSerializable("DATA", mListQuestion)
                    bundle.putInt("POSITION", i)
                    val mFragment = PracticeQuizFragment()
                    mFragment.arguments = bundle
                    mPageAdapter.addFragment(mFragment, "", i)
                }

                mPageAdapter.notifyDataSetChanged()
                viewpager.adapter = mPageAdapter
            }
        })

        object : CountDownTimer(1800000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val hours = seconds / (60 * 60)
                val tempMint = seconds - hours * 60 * 60
                val minutes = tempMint / 60
                seconds = tempMint - minutes * 60

                tvCountDown1.text = "" + minutes
                tvCountDown2.text = "" + seconds
            }

            override fun onFinish() {
                tvCountDown1.visibility = View.GONE
                tvView.visibility = View.GONE
                tvCountDown2.visibility = View.GONE
                tvTimeOut.visibility = View.VISIBLE
                imgAlarmOn.visibility = View.GONE
                imgAlarmOff.visibility = View.VISIBLE
            }
        }.start()

        tvTitle.setOnClickListener {
            checkAnswer()
        }


    }

    private fun checkAnswer() {
        showDialog("name")
    }

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_check_answer)

        val gridview = dialog.findViewById<GridView>(R.id.llGirdView)
        val adapter = CheckAnswerAdapter(this, R.layout.item_check_answer, itemList)
        gridview.adapter = adapter

        val yesBtn = dialog.findViewById(R.id.btnFinish) as Button
        val noBtn = dialog.findViewById(R.id.btnCancel) as Button
        yesBtn.setOnClickListener {
            dialog.dismiss()
        }
        noBtn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    override fun onBackPressed() {
        if (viewpager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // do point ? ? ? show dialog
            super.onBackPressed()
            // viewPager.currentItem = viewPager.currentItem - 1
        }
    }
}
