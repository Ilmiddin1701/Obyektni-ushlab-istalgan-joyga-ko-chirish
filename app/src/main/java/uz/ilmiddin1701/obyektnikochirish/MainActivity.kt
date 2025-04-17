package uz.ilmiddin1701.obyektnikochirish

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import uz.ilmiddin1701.obyektnikochirish.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.draggableImage.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    true
                }
                MotionEvent.ACTION_MOVE -> {
                    val x = event.rawX - view.width / 2
                    val y = event.rawY - view.height / 2

                    view.animate()
                        .x(x)
                        .y(y)
                        .setDuration(0)
                        .start()
                    true
                }
                else -> false
            }
        }

    }



}