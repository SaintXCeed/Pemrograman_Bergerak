package com.example.bab5kalkulatorui

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.ArrayDeque

class MainActivity : AppCompatActivity() {

    private lateinit var tvExpression: TextView
    private lateinit var tvResult: TextView

    private var expr: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvExpression = findViewById(R.id.tvExpression)
        tvResult = findViewById(R.id.tvResult)

        // Supaya tombol GridLayout ukurannya rapi (mirip UI contoh)
        sizeGridButtons()

        // Angka
        bindDigit(R.id.btn0, "0")
        bindDigit(R.id.btn1, "1")
        bindDigit(R.id.btn2, "2")
        bindDigit(R.id.btn3, "3")
        bindDigit(R.id.btn4, "4")
        bindDigit(R.id.btn5, "5")
        bindDigit(R.id.btn6, "6")
        bindDigit(R.id.btn7, "7")
        bindDigit(R.id.btn8, "8")
        bindDigit(R.id.btn9, "9")
        bindDigit(R.id.btnDot, ".")

        // Operator
        bindOp(R.id.btnPlus, "+")
        bindOp(R.id.btnMinus, "-")
        bindOp(R.id.btnMultiply, "*")
        bindOp(R.id.btnDivide, "/")

        // AC
        findViewById<View>(R.id.btnAC).setOnClickListener {
            expr = ""
            tvExpression.text = ""
            tvResult.text = "0"
        }

        // %
        findViewById<View>(R.id.btnPercent).setOnClickListener {
            // Convert last number to percent
            expr = applyPercent(expr)
            render()
        }

        // +/-
        findViewById<View>(R.id.btnPlusMinus).setOnClickListener {
            expr = toggleSign(expr)
            render()
        }

        // =
        findViewById<View>(R.id.btnEquals).setOnClickListener {
            val res = safeEval(expr)
            tvResult.text = formatResult(res, withEquals = true)
        }

        render()
    }

    private fun bindDigit(id: Int, s: String) {
        findViewById<View>(id).setOnClickListener {
            expr = appendDigit(expr, s)
            render()
        }
    }

    private fun bindOp(id: Int, op: String) {
        findViewById<View>(id).setOnClickListener {
            expr = appendOp(expr, op)
            render()
        }
    }

    private fun render() {
        tvExpression.text = prettify(expr)
        val res = safeEval(expr)
        tvResult.text = formatResult(res, withEquals = false)
    }

    // ====== Helpers input ======

    private fun appendDigit(current: String, s: String): String {
        if (s == ".") {
            // prevent multiple dots in the current number
            val lastNum = current.takeLastWhile { it.isDigit() || it == '.' }
            if (lastNum.contains('.')) return current
            if (lastNum.isEmpty()) return current + "0."
        }
        return current + s
    }

    private fun appendOp(current: String, op: String): String {
        if (current.isEmpty()) {
            // allow negative start
            return if (op == "-") "-" else ""
        }
        val last = current.last()
        if (last in charArrayOf('+', '-', '*', '/')) {
            // replace operator
            return current.dropLast(1) + op
        }
        return current + op
    }

    private fun toggleSign(current: String): String {
        // Toggle sign of the last number
        val (head, tailNum) = splitLastNumber(current)
        if (tailNum.isEmpty()) return current
        return if (tailNum.startsWith("-")) head + tailNum.drop(1) else head + "-" + tailNum
    }

    private fun applyPercent(current: String): String {
        val (head, tailNum) = splitLastNumber(current)
        if (tailNum.isEmpty()) return current
        return try {
            val bd = BigDecimal(tailNum)
            val p = bd.divide(BigDecimal("100"), 10, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
            head + p
        } catch (_: Exception) {
            current
        }
    }

    private fun splitLastNumber(s: String): Pair<String, String> {
        if (s.isEmpty()) return "" to ""
        var i = s.length - 1
        while (i >= 0 && (s[i].isDigit() || s[i] == '.')) i--
        // handle negative sign attached to number
        if (i >= 0 && s[i] == '-' && (i == 0 || s[i - 1] in charArrayOf('+', '-', '*', '/'))) {
            i--
        }
        val head = s.substring(0, i + 1)
        val tail = s.substring(i + 1)
        return head to tail
    }

    // ====== Evaluator (supports + - * /) with precedence ======

    private fun safeEval(raw: String): BigDecimal? {
        return try {
            val cleaned = raw.trim()
            if (cleaned.isEmpty()) return BigDecimal.ZERO
            evalExpression(cleaned)
        } catch (_: Exception) {
            null
        }
    }

    private fun evalExpression(s: String): BigDecimal {
        val tokens = tokenize(s)
        val output = ArrayDeque<String>()
        val ops = ArrayDeque<String>()

        fun prec(op: String) = when (op) {
            "+", "-" -> 1
            "*", "/" -> 2
            else -> 0
        }

        for (t in tokens) {
            if (t.isNumberToken()) {
                output.addLast(t)
            } else {
                while (ops.isNotEmpty() && prec(ops.last()) >= prec(t)) {
                    output.addLast(ops.removeLast())
                }
                ops.addLast(t)
            }
        }
        while (ops.isNotEmpty()) output.addLast(ops.removeLast())

        val stack = ArrayDeque<BigDecimal>()
        for (t in output) {
            if (t.isNumberToken()) {
                stack.addLast(BigDecimal(t))
            } else {
                val b = stack.removeLast()
                val a = stack.removeLast()
                val r = when (t) {
                    "+" -> a.add(b)
                    "-" -> a.subtract(b)
                    "*" -> a.multiply(b)
                    "/" -> a.divide(b, 10, RoundingMode.HALF_UP)
                    else -> BigDecimal.ZERO
                }
                stack.addLast(r)
            }
        }
        return stack.last()
    }

    private fun tokenize(s: String): List<String> {
        val res = mutableListOf<String>()
        var i = 0
        while (i < s.length) {
            val c = s[i]
            if (c.isWhitespace()) { i++; continue }
            if (c.isDigit() || c == '.' || (c == '-' && (i == 0 || s[i - 1] in charArrayOf('+', '-', '*', '/')))) {
                val start = i
                i++
                while (i < s.length && (s[i].isDigit() || s[i] == '.')) i++
                res.add(s.substring(start, i))
            } else if (c in charArrayOf('+', '-', '*', '/')) {
                res.add(c.toString())
                i++
            } else {
                i++
            }
        }
        return res
    }

    private fun String.isNumberToken(): Boolean =
        this.isNotEmpty() && (this[0].isDigit() || this[0] == '-' || this[0] == '.')

    // ====== Formatting UI ======

    private fun prettify(s: String): String =
        s.replace("*", "×").replace("/", "÷")

    private fun formatResult(v: BigDecimal?, withEquals: Boolean): String {
        if (v == null) return if (withEquals) "=Error" else "Error"
        val plain = v.stripTrailingZeros().toPlainString()
        val shown = plain.toBigDecimalOrNull()?.let {
            // limit long decimals
            if (plain.contains(".") && plain.length > 14) it.setScale(6, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()
            else plain
        } ?: plain
        return if (withEquals) "=$shown" else "=$shown"
    }

    private fun sizeGridButtons() {
        val grid = findViewById<GridLayout>(R.id.grid)

        grid.post {
            val cols = 4
            val rows = 5

            val cellW = grid.width / cols
            val cellH = grid.height / rows

            for (i in 0 until grid.childCount) {
                val v = grid.getChildAt(i)
                val lp = v.layoutParams as GridLayout.LayoutParams

                // Default: 1 kolom
                var span = 1

                // Tombol 0 kita buat 2 kolom (karena di XML pakai layout_columnSpan="2")
                if (v.id == R.id.btn0) span = 2

                lp.width = cellW * span - dp(12)
                lp.height = cellH - dp(12)
                v.layoutParams = lp
            }
        }
    }


    private fun dp(x: Int): Int =
        (x * resources.displayMetrics.density).toInt()
}
