package com.zbinmy.folkplaytv

import android.app.Activity
import android.os.Bundle
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    private val titles = listOf("Tic Tac Toe", "Connect Four", "Reversi", "Chess", "Checkers", "Sudoku", "2048", "Memory Match", "Minesweeper")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = 5894
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(56, 30, 56, 24)
            setBackgroundColor(Color.rgb(16, 24, 32))
        }
        root.addView(TextView(this).apply {
            text = "FOLKPLAY TV"
            textSize = 32f
            setTextColor(Color.rgb(114, 224, 180))
        })
        root.addView(TextView(this).apply {
            text = "Use the Fire TV remote: D-pad to move, OK to select"
            textSize = 16f
            setTextColor(Color.LTGRAY)
            setPadding(0, 8, 0, 20)
        })
        titles.forEachIndexed { index, title ->
            val item = TextView(this).apply {
                text = "  $title"
                textSize = 22f
                gravity = Gravity.CENTER_VERTICAL
                setTextColor(Color.WHITE)
                isFocusable = true
                isFocusableInTouchMode = true
                setPadding(18, 0, 18, 0)
                setBackgroundColor(Color.rgb(32, 48, 58))
                setOnFocusChangeListener { v, focused ->
                    v.setBackgroundColor(if (focused) Color.rgb(48, 91, 86) else Color.rgb(32, 48, 58))
                }
                setOnClickListener {
                    if (index == 0) startActivity(android.content.Intent(this@MainActivity, GameActivity::class.java))
                    else android.app.AlertDialog.Builder(this@MainActivity)
                        .setTitle(title)
                        .setMessage("This game is not implemented in this independent TV edition yet.")
                        .setPositiveButton("OK", null).show()
                }
            }
            val lp = LinearLayout.LayoutParams(-1, 58)
            lp.bottomMargin = 7
            root.addView(item, lp)
            if (index == 0) item.post { item.requestFocus() }
        }
        setContentView(root)
    }
}

class GameActivity : Activity() {
    private lateinit var board: TicTacToeView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = 5894
        board = TicTacToeView(this)
        setContentView(board)
        board.requestFocus()
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) { finish(); return true }
        if (keyCode == KeyEvent.KEYCODE_MENU) { board.reset(); return true }
        return board.move(keyCode) || super.onKeyDown(keyCode, event)
    }
}

private class TicTacToeView(context: android.content.Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val cells = IntArray(9)
    private var cursor = 4
    private var player = 1
    private var finished = false
    private var status = "Player 1's turn"
    fun reset() { cells.fill(0); cursor = 4; player = 1; finished = false; status = "Player 1's turn"; invalidate() }
    fun move(key: Int): Boolean {
        when (key) {
            KeyEvent.KEYCODE_DPAD_LEFT -> cursor = if (cursor % 3 == 0) cursor else cursor - 1
            KeyEvent.KEYCODE_DPAD_RIGHT -> cursor = if (cursor % 3 == 2) cursor else cursor + 1
            KeyEvent.KEYCODE_DPAD_UP -> cursor = (cursor - 3).coerceAtLeast(0)
            KeyEvent.KEYCODE_DPAD_DOWN -> cursor = (cursor + 3).coerceAtMost(8)
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_BUTTON_A -> {
                if (!finished && cells[cursor] == 0) {
                    cells[cursor] = player
                    if (hasWon()) { status = "Player $player wins! Press MENU to restart"; finished = true }
                    else if (cells.all { it != 0 }) { status = "Draw! Press MENU to restart"; finished = true }
                    else { player = 3 - player; status = "Player $player's turn" }
                }
            }
            else -> return false
        }
        invalidate()
        return true
    }
    private fun hasWon(): Boolean {
        val lines = listOf(listOf(0,1,2), listOf(3,4,5), listOf(6,7,8), listOf(0,3,6), listOf(1,4,7), listOf(2,5,8), listOf(0,4,8), listOf(2,4,6))
        return lines.any { line -> line.all { cells[it] == player } }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.rgb(16, 24, 32))
        paint.color = Color.WHITE
        paint.textSize = 34f
        canvas.drawText("TIC TAC TOE", 42f, 52f, paint)
        paint.textSize = 20f
        canvas.drawText(status, 42f, 88f, paint)
        val side = minOf(width - 100f, height - 140f) / 3f
        val left = (width - side * 3) / 2f
        val top = (height - side * 3) / 2f + 45f
        for (i in 0..8) {
            val x = left + (i % 3) * side
            val y = top + (i / 3) * side
            paint.color = if (i == cursor) Color.rgb(48, 91, 86) else Color.rgb(32, 48, 58)
            canvas.drawRoundRect(x + 4, y + 4, x + side - 4, y + side - 4, 12f, 12f, paint)
            if (cells[i] != 0) {
                paint.color = if (cells[i] == 1) Color.rgb(114, 224, 180) else Color.rgb(244, 184, 96)
                paint.textSize = side * 0.55f
                canvas.drawText(if (cells[i] == 1) "X" else "O", x + side * 0.32f, y + side * 0.68f, paint)
            }
        }
        paint.color = Color.LTGRAY
        paint.textSize = 15f
        canvas.drawText("D-pad: move   OK: place   BACK: exit   MENU: restart", 42f, height - 24f, paint)
    }
}
