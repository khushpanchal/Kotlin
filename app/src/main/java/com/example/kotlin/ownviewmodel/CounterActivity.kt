package com.example.kotlin.ownviewmodel

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.MainApplication
import com.example.kotlin.R
import com.example.kotlin.ownviewmodel.core.MyViewModelProvider
import com.example.kotlin.ownviewmodel.core.MyViewModelStore
import com.example.kotlin.ownviewmodel.core.MyViewModelStoreOwner

class CounterActivity : AppCompatActivity(), MyViewModelStoreOwner {

    private lateinit var counterTv: TextView
    private lateinit var counterIncreaseBtn: Button

    private val viewModel: CounterViewModel by lazy {
        MyViewModelProvider(this, CounterViewModelFactory()).get(CounterViewModel::class)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activitiy_vm_testing)
        counterTv = findViewById(R.id.textView)
        counterIncreaseBtn = findViewById(R.id.button)

        counterTv.text = viewModel.getCount().toString()

        counterIncreaseBtn.setOnClickListener {
            viewModel.incrementCount()
            counterTv.text = viewModel.getCount().toString()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        if (!isChangingConfigurations) {
            (application as MainApplication).clearViewModelStore(this.javaClass.canonicalName!!)
        }
    }

    override fun getMyViewModelStore(): MyViewModelStore {
        return (application as MainApplication).getViewModelStore(this@CounterActivity.javaClass.canonicalName!!)
    }
}
