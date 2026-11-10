package com.zbinmy.folkplaytv

import android.app.Activity
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.*

class MainActivity : Activity() {
    private val games = listOf("Tic Tac Toe", "Connect Four", "Reversi", "Chess", "Checkers", "Sudoku", "2048", "Memory Match", "Minesweeper", "æ›´å¤š games coming soon")
    private var selected = 0
    private lateinit var list: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        val root = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL; setPadding(64, 34, 64, 30); setBackgroundColor(Color.rgb(16,24,32)) }
        root.addView(TextView(this).apply { text = "FOLKPLAY"; textSize = 34f; setTextColor(Color.rgb(114,224,180)); typeface = Typeface.DEFAULT_BOLD })
        root.addView(TextView(this).apply { text = "TV EDITION  â€¢  PLAY WITH YOUR REMOTE"; textSize = 14f; setTextColor(Color.LTGRAY); setPadding(0,4,0,20) })
        list = LinearLayout(this).apply { orientation = LinearLayout.VERTICAL }
        games.forEachIndexed { index, title ->
            val b = TextView(this).apply { text = "  ${index + 1}.  $title"; textSize = 22f; setTextColor(Color.WHITE); gravity = Gravity.CENTER_VERTICAL; isFocusable = true; isFocusableInTouchMode = true; setPadding(22,0,22,0); minHeight = 58; setBackgroundColor(if(index==selected) Color.rgb(39,73,76) else Color.rgb(27,40,49)); setOnFocusChangeListener { v, has -> v.setBackgroundColor(if(has) Color.rgb(39,73,76) else Color.rgb(27,40,49)) }; setOnClickListener { launchGame(index) } }
            val lp = LinearLayout.LayoutParams(-1, 58); lp.bottomMargin = 8; list.addView(b, lp)
        }
        root.addView(list, LinearLayout.LayoutParams(-1, 0, 1f))
        root.addView(TextView(this).apply { text = "D-pad: navigate     OK: select     BACK: return"; textSize = 13f; setTextColor(Color.GRAY); setPadding(0,12,0,0) })
        setContentView(k¢‹JNÂˆBˆš]˜]H[ˆ][˜ÚØ[YJ[™^ˆ[
HÂˆYˆ
[™^HJHİ\Xİ]š]J[™›ÚY˜ÛÛ[’[[
\ËØ[YPXİ]š]N˜Û\ÜËš˜]˜JKœ]^˜J™Ø[YH‹[™^
JBˆ[ÙH[™›ÚY˜\[\X[ÙËZ[\Š\ÊKœÙ]]JØ[Y\ÖÚ[™^JKœÙ]Y\ÜØYÙJ•\ÈØ[YH\È›İ[\[Y[Y[ˆ\È[™\[™[ˆY][ÛˆY]ˆŠKœÙ]ÜÚ]]™P]ÛŠ“ÒÈ‹[
KœÚİÊ
BˆBŸB