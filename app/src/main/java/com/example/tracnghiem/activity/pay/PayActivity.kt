package com.example.tracnghiem.activity.pay

import android.content.Intent
import android.view.View
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityPayBinding
import kotlinx.android.synthetic.main.activity_pay.*


class PayActivity : BaseActivity<ActivityPayBinding>() {
    override fun setLayoutId(): Int = R.layout.activity_pay
    var REQUEST_CODE = 63
    override fun initView() {

    }

    override fun initViewModel() {

    }

    override fun initData() {

    }

    override fun initListener() {
        addPayment.setOnClickListener {
            val intent = Intent(applicationContext, AddPaymentActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == resultCode) {
            credit_card.visibility = View.VISIBLE
            val stripe_token = data?.getStringExtra("stripe_token")
            credit_card_text.text = data?.getStringExtra(stripe_token)
        }

    }

}