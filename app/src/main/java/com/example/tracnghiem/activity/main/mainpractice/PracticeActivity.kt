package com.example.tracnghiem.activity.main.mainpractice

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.Window
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.example.tracnghiem.R
import com.example.tracnghiem.activity.login.LoginActivity
import com.example.tracnghiem.activity.main.mainpractice.fragment.CheckAnswerAdapter
import com.example.tracnghiem.activity.main.mainpractice.fragment.PracticeQuizFragment
import com.example.tracnghiem.activity.main.mainpractice.fragment.PracticeQuizFragment.Companion.checkPoint
import com.example.tracnghiem.activity.main.mainpractice.mainpoint.CheckPointActivity
import com.example.tracnghiem.adapter.SliderPageAdapter
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.data.model.Questions
import com.example.tracnghiem.databinding.ActivityPracticeBinding
import com.example.tracnghiem.utils.dialog.CustomProgressDialog
import com.example.tracnghiem.utils.showView
import kotlinx.android.synthetic.main.activity_practice.*
import kotlinx.android.synthetic.main.layout_empty.*
import org.koin.android.viewmodel.ext.android.viewModel


class PracticeActivity : BaseActivity<ActivityPracticeBinding>() {
    companion object{
        var sizeList : Int = 0
    }
    private val mViewModel: TrainViewModel by viewModel()
    private lateinit var bundle: Bundle
    private var mListQuestion = ArrayList<Questions>()
    private val loadingDialog = CustomProgressDialog()
    private val mPageAdapter = SliderPageAdapter(supportFragmentManager)

    override fun setLayoutId(): Int = R.layout.activity_practice

    override fun initView() {}

    override fun initViewModel() {
        (binding as ActivityPracticeBinding).viewModel = mViewModel

    }

    // example
    private var itemList = ArrayList<Questions>()


    override fun initData() {
        val quizId = intent.getStringExtra("quizId")
        val quizName = intent.getStringExtra("quizName")
        //PGDpJ5OFShWI89lvWB1U
        quizId?.let {
            if (it == "PGDpJ5OFShWI89lvWB1U") {
                mViewModel.requestListQuestion()
                // Setting time and ?? position
            } else if(it == "SdbUiOoJCqJfdTpaUgbe"){
               mViewModel.requestListQuestion2()
            }else{
                tvEmpty.showView()
            }
        }

    }

    var data = ArrayList<Questions>()
    override fun initListener() {
        mViewModel.getListQuestionLiveData().observe(this, Observer { result ->
            result?.let {
                try {
                    for (i in 0..it.size) {
                     //   if (it[i].idQuestion.equals("test1"))
                        data.add(it[i])
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }


                mListQuestion = data
                itemList = mListQuestion
                viewpager.offscreenPageLimit = 3
                sizeList = mListQuestion.size

                for (i in 0..mListQuestion.size) {
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

        val gridView = dialog.findViewById<GridView>(R.id.llGirdView)
        val adapter = CheckAnswerAdapter(this, R.layout.item_check_answer, itemList)
        gridView.adapter = adapter

        gridView.onItemClickListener = OnItemClickListener { _, _, position, _ ->
            viewpager.currentItem = position
            dialog.dismiss()
        }

        val yesBtn = dialog.findViewById(R.id.btnFinish) as TextView
        val noBtn = dialog.findViewById(R.id.tvTitle) as TextView
        yesBtn.setOnClickListener {
            loadingDialog.show(this, "Please Wait")
            intent = Intent(this, CheckPointActivity::class.java)
            intent.putExtra("checkPoint",checkPoint)
            intent.putExtra("sizeList",sizeList)
            loadingDialog.dialog.dismiss()
            startActivity(intent)
            dialog.dismiss()
            finish()
        }
        noBtn.setOnClickListener {
            dialog.dismiss()
        }
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
