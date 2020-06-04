package com.example.tracnghiem.activity.main.mainpractice.mainpoint

import android.widget.Toast
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityCheckPointBinding
import kotlinx.android.synthetic.main.activity_check_point.*
import org.koin.android.viewmodel.ext.android.viewModel

class CheckPointActivity() : BaseActivity<ActivityCheckPointBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_check_point

    private val mViewModel: CheckPointViewModel by viewModel()
    override fun initView() {
        (binding as ActivityCheckPointBinding).viewModel = mViewModel
        try {
            val trueAns = intent.getIntExtra("checkPoint",0)
            val sizeList = intent.getIntExtra("sizeList",0)
            Toast.makeText(this, "" + trueAns.toString() , Toast.LENGTH_SHORT).show()
            sizeListData.text = "/$sizeList"
            sizeListData1.text = "/$sizeList"
            sizeListData2.text = "/$sizeList"
            tvTrue.text = trueAns.toString()

            val num = 10.toDouble() / sizeList
            val point = trueAns*num
            tvPoint.text = point.toString()

        }catch (ex : Exception){
            ex.printStackTrace()
        }

    }

    override fun initViewModel() {

    }

    override fun initData() {

    }

    override fun initListener() {

    }

}