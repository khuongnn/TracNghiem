package com.example.tracnghiem.activity.pay


import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.tracnghiem.R
import com.example.tracnghiem.base.BaseActivity
import com.example.tracnghiem.databinding.ActivityAddPaymentBinding
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import com.stripe.android.view.CardMultilineWidget
import kotlinx.android.synthetic.main.activity_add_payment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.annotations.NotNull


class AddPaymentActivity : BaseActivity<ActivityAddPaymentBinding>() {
    var cardMultilineWidget: CardMultilineWidget? = null
    private lateinit var stripe: Stripe

    override fun setLayoutId(): Int = R.layout.activity_add_payment


    override fun initView() {

    }

    override fun initViewModel() {

    }

    override fun initData() {

    }

    override fun initListener() {
        btnAddCreditCard.setOnClickListener {
            saveCard()
        }
    }

    private fun saveCard() {
        val card = card_input_widget?.card
        if (card == null) {
            Toast.makeText(applicationContext, "Invalid card", Toast.LENGTH_SHORT).show()
        } else {
            if (!card.validateCard()) {
                // Do not continue token creation.
                Toast.makeText(applicationContext, "Invalid card", Toast.LENGTH_SHORT).show()
            } else {
               // CreateToken(card)
                //   tokenizeCard(card)
                  stripe = Stripe(this,"pk_test_WmIVxUB0YtdM7Ex8qI8J4KQi00uAKS8bzO")
                stripe.createCardToken(card, "sk_test_6RZeAV8aQi2Xo2nWWIuurwyE00M6qvCSBR", object : com.stripe.android.ApiResultCallback<Token> {
                    override fun onError(e: Exception) {
                        Toast.makeText(this@AddPaymentActivity, "Error creating token", Toast.LENGTH_LONG)
                            .show()
                        return
                    }

                    override fun onSuccess(result: Token) {
                        // Send token to your server
                        Log.e("Stripe Token", result.id)
                        val intent = Intent()
                        intent.putExtra("card", result.card?.last4)
                        intent.putExtra("stripe_token", result.id)
                        intent.putExtra("cardtype", result.card?.brand)
                        setResult(63, intent)
                             finish()
                    }

                })

            }
        }
    }

    private fun CreateToken(card: Card) {
        val stripe = Stripe(applicationContext, "pk_test_WmIVxUB0YtdM7Ex8qI8J4KQi00uAKS8bzO")
        stripe.createToken(card, "pk_test_WmIVxUB0YtdM7Ex8qI8J4KQi00uAKS8bzO",object : com.stripe.android.ApiResultCallback<Token> {
            override   fun onSuccess(token: Token) {
                    // Send token to your server
                    Log.e("Stripe Token", token.id)
                    val intent = Intent()
                    intent.putExtra("card", token.card?.last4)
                    intent.putExtra("stripe_token", token.id)
                    intent.putExtra("cardtype", token.card?.brand)
                    setResult(63, intent)
                    finish()
                }

            override    fun onError(error: Exception) {

                    // Show localized error message
                    Toast.makeText(
                        applicationContext,
                        error.localizedMessage,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }


    private fun tokenizeCard(@NotNull card: Card) {
        val stripe = Stripe(applicationContext, "pk_test_WmIVxUB0YtdM7Ex8qI8J4KQi00uAKS8bzO")
        val stripeService = StripeService.create()

        stripe.createCardToken(card, "sk_test_6RZeAV8aQi2Xo2nWWIuurwyE00M6qvCSBR", object : com.stripe.android.ApiResultCallback<Token> {
            override fun onError(e: Exception) {
                Toast.makeText(this@AddPaymentActivity, "Error creating token", Toast.LENGTH_LONG)
                    .show()
                return
            }

            override fun onSuccess(result: Token) {
                // Send token to your server
                Log.e("Stripe Token", result.id)
                val intent = Intent()
                intent.putExtra("card", result.card?.last4)
                intent.putExtra("stripe_token", result.id)
                intent.putExtra("cardtype", result.card?.brand)
                setResult(63, intent)
           //     finish()

//                GlobalScope.launch(Dispatchers.IO) {
//                    val response = stripeService.createCharge(Charge(result.id, 500, "xaf"))
//                    Log.d("TOKEN", result.id)
//                    withContext(Dispatchers.Main) {
//                        if (response.isSuccessful) {
//                            response.body()?.let {
//                                Toast.makeText(
//                                    this@AddPaymentActivity,
//                                    "Payment made successfully",
//                                    Toast.LENGTH_LONG
//                                ).show()
//                                Log.d("CHARGE", "$it")
//                            }
//                        } else {
//                            Toast.makeText(
//                                this@AddPaymentActivity,
//                                "Payment was not  successfully. Please check your details",
//                                Toast.LENGTH_LONG
//                            ).show()
//                            Log.e("TAG--", "${response.errorBody()?.string()} ${response.code()}")
//                        }
//                    }
//                }
            }

        })
    }


}
