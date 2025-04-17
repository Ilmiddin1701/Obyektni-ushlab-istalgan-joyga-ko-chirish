package uz.ilmiddin1701.obyektnikochirish

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import uz.ilmiddin1701.obyektnikochirish.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var sliderThumb: View
    private lateinit var sliderContainer: FrameLayout
    private var dX = 0f
    private var maxX = 0f

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
                    val parent = binding.rootLayout

                    // Barmoq koordinatalari (ekran bo‘yicha)
                    val rawX = event.rawX
                    val rawY = event.rawY

                    // Chegaralarni hisoblaymiz
                    val maxX = parent.width - view.width
                    val maxY = parent.height - view.height

                    // Obyekt markaziga moslab joylashtiramiz
                    var x = rawX - view.width / 2
                    var y = rawY - view.height / 2

                    // Obyekt ekrandan chiqib ketmasligi uchun cheklaymiz
                    x = x.coerceIn(0f, maxX.toFloat())
                    y = y.coerceIn(0f, maxY.toFloat())

                    // Harakatlantirish
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

        sliderThumb = binding.sliderThumb
        sliderContainer = binding.sliderContainer

        sliderContainer.post {
            maxX = (sliderContainer.width - sliderThumb.width).toFloat()
        }

        sliderThumb.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    dX = event.rawX - view.x
                    true
                }

                MotionEvent.ACTION_MOVE -> {
                    var newX = event.rawX - dX
                    newX = newX.coerceIn(0f, maxX)
                    view.x = newX
                    true
                }

                MotionEvent.ACTION_UP -> {
                    if (view.x >= maxX - 20) {
                        Toast.makeText(this, "Qulf ochildi!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Sekin orqaga qaytadi
                        view.animate().x(0f).setDuration(300).start()
                    }
                    true
                }

                else -> false
            }
        }

    }



}