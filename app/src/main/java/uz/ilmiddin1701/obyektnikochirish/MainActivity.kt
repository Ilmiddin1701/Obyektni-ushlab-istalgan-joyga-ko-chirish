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

    // Slider tugmasi va konteyneri
    private lateinit var sliderThumb: View
    private lateinit var sliderContainer: FrameLayout

    // Dastlabki bosish va maksimal joylashuv uchun o‘zgaruvchilar
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

        // View'larni ulaymiz
        sliderThumb = binding.sliderThumb
        sliderContainer = binding.sliderContainer

        // Kontayner joylashgach, maksimal joyni hisoblaymiz
        sliderContainer.post {
            maxX = (sliderContainer.width - sliderThumb.width).toFloat()
        }

        // Slider tugmasi bosilsa yoki harakatlantirilsa
        sliderThumb.setOnTouchListener { view, event ->
            when (event.actionMasked) {

                // Foydalanuvchi tugmaga bosganida
                MotionEvent.ACTION_DOWN -> {
                    dX = event.rawX - view.x
                    true
                }

                // Foydalanuvchi barmog‘ini harakatlantirsa
                MotionEvent.ACTION_MOVE -> {
                    var newX = event.rawX - dX

                    // Chegara ichida qolishini ta'minlaymiz
                    newX = newX.coerceIn(0f, maxX)

                    // Tugmani harakatlantiramiz
                    view.x = newX
                    true
                }

                // Barmoqni qo‘yib yuborganda
                MotionEvent.ACTION_UP -> {
                    if (view.x >= maxX - 20) {
                        // Agar oxirigacha yetgan bo‘lsa
                        Toast.makeText(this, "Qulf ochildi!", Toast.LENGTH_SHORT).show()
                    } else {
                        // Yetmagan bo‘lsa, tugmani orqaga qaytaramiz
                        view.animate().x(0f).setDuration(300).start()
                    }
                    true
                }

                else -> false
            }
        }

    }



}