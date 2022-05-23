package com.atr.tiptime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.atr.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    // 최상위 변수 선언 : MainActivity 클래스의 여러 메소드에서 사용 됨
    // lateinit : 코드가 변수를 사용하기 전에 먼저 초기화 할 것임을 확인. 변수 초기화하지 않으면 앱 비정상 종료
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)   // activity_main.xml 레이아웃에서 Views에 액세스하는 데 사용할 biding 객체 초기화
        setContentView(binding.root)    // Activity의 콘텐츠 뷰를 설정하는데, R.layout.activity_main 대신에 앱의 뷰 계층 구조 루트인 binding.root 지정

        // 이제 View에 대한 참조가 필요한 경우 findViewById() 대신, binding 객체에서 뷰 참조 가져올 수 있음


        // binding.calculateButton.text = "A Button"   // 뷰 참조 이름은 소문자, 언더바 표기에서 카멜표기법으로 변환되어 생성됨
        binding.calculateButton.setOnClickListener { calculateTip() }



    }


    private fun calculateTip() {
        val stringInTextField = binding.costOfService.text.toString()

        // Null 이 반환되는 경우 다른 방식으로 처리할 수 있음을 나타내기 toDoubleOrNull()
        val cost = stringInTextField.toDoubleOrNull()
        Log.i("MainActivity", cost.toString())

        // cost가 null 이거나 0.0 일 경우,displayTip() 메소드로 0.0 표시
        if(cost == null || cost == 0.0){
            displayTip(0.0)
            return          // 금액을 적지 않았을 때, 이전 팁 내역 지우기
        }

        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost      // 반올림을 해야해서 값이 바뀔 수 있기 때문에, var 로 선언함
        if(binding.roundUpSwitch.isChecked) tip = kotlin.math.ceil(tip) // 반올림 스위치값 체크해 반올림 함

        displayTip(tip)
    }

    private fun displayTip(tip : Double){
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }

}