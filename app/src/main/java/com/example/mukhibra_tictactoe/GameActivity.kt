package com.example.mukhibra_tictactoe

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

class GameActivity : AppCompatActivity() {

    private lateinit var gameManager: GameManager
    private lateinit var one: ImageView
    private lateinit var two: ImageView
    private lateinit var three: ImageView
    private lateinit var four: ImageView
    private lateinit var five: ImageView
    private lateinit var six: ImageView
    private lateinit var seven: ImageView
    private lateinit var eight: ImageView
    private lateinit var nine: ImageView

    private lateinit var startNewGame: ImageView
    private lateinit var winner: View

    private lateinit var point_player1: TextView
    private lateinit var point_player2: TextView
    private lateinit var player_1:TextView
    lateinit var player_2:TextView

    private lateinit var player_x_y: TextView


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        player_x_y = findViewById(R.id.player_x_y)

        winner = findViewById(R.id.winner)
        var myProg = intent.extras
        var player1_name = myProg!!.getString("player1"," ").toString()
        var player2_name = myProg.getString("player2", " ").toString()
        Toast.makeText(this, player2_name +" " + player2_name, Toast.LENGTH_SHORT).show()
        var player1 = Player(player1_name,0)
        var player2 = Player(player2_name,0)

        gameManager = GameManager(player1, player2, player_x_y)
        player_1 = findViewById(R.id.player_1)
        player_2 = findViewById(R.id.player_2)


        player_1.text = player1.name
        player_2.text = player2.name


        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)

        startNewGame = findViewById(R.id.startNewGame)

        point_player1 = findViewById(R.id.point_player1)
        point_player2 = findViewById(R.id.point_player2)

        one.setOnClickListener { onBoxClicked(one, Position(0, 0)) }
        two.setOnClickListener { onBoxClicked(two, Position(0, 1)) }
        three.setOnClickListener { onBoxClicked(three, Position(0, 2))}
        four.setOnClickListener { onBoxClicked(four, Position(1, 0)) }
        five.setOnClickListener { onBoxClicked(five, Position(1, 1)) }
        six.setOnClickListener { onBoxClicked(six, Position(1, 2)) }
        seven.setOnClickListener { onBoxClicked(seven, Position(2, 0)) }
        eight.setOnClickListener { onBoxClicked(eight, Position(2, 1)) }
        nine.setOnClickListener { onBoxClicked(nine, Position(2, 2)) }
        startNewGame.setOnClickListener {
            winner.visibility = View.GONE
            gameManager.reset()
            resetboxes()
        }



    }


    private fun resetboxes() {
        one.setImageDrawable(null)
        two.setImageDrawable(null)
        three.setImageDrawable(null)
        four.setImageDrawable(null)
        five.setImageDrawable(null)
        six.setImageDrawable(null)
        seven.setImageDrawable(null)
        eight.setImageDrawable(null)
        nine.setImageDrawable(null)

        one.background = null
        two.background = null
        three.background = null
        four.background = null
        five.background = null
        six.background = null
        seven.background = null
        eight.background = null
        nine.background = null

        one.isEnabled = true
        two.isEnabled = true
        three.isEnabled = true
        four.isEnabled = true
        five.isEnabled = true
        six.isEnabled = true
        seven.isEnabled = true
        eight.isEnabled = true
        nine.isEnabled = true
    }

    private fun onBoxClicked(box: ImageView, position: Position) {
        if (box.drawable == null) {
            box.setImageResource(gameManager.currentPlayerMark)
            var anim:Animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_x_y)
            box.startAnimation(anim)
            val winningLine = gameManager.makeMove(position)
            if (winningLine != null) {
                disableBoxes()
                winner.visibility = View.VISIBLE
                winner.startAnimation(anim)
                showWinner(winningLine)
                updatePoints()
            } else if (gameManager.isDraw()) {
                disableBoxes()
                winner.visibility = View.VISIBLE
            }
        }
    }

    private fun disableBoxes() {
        one.isEnabled = false
        two.isEnabled = false
        three.isEnabled = false
        four.isEnabled = false
        five.isEnabled = false
        six.isEnabled = false
        seven.isEnabled = false
        eight.isEnabled = false
        nine.isEnabled = false
    }
    private fun updatePoints() {
        point_player1.text = gameManager.player1.point.toString()
        point_player2.text = gameManager.player2.point.toString()
    }

    private fun showWinner(winningLine: WinningLine) {
        val (winningBoxes, background) = when (winningLine) {
            WinningLine.ROW_0 -> Pair(listOf(one, two, three), R.drawable.horizontal_line)
            WinningLine.ROW_1 -> Pair(listOf(four, five, six), R.drawable.horizontal_line)
            WinningLine.ROW_2 -> Pair(listOf(seven, eight, nine), R.drawable.horizontal_line)
            WinningLine.COLUMN_0 -> Pair(listOf(one, four, seven), R.drawable.vertical_line)
            WinningLine.COLUMN_1 -> Pair(listOf(two, five, eight), R.drawable.vertical_line)
            WinningLine.COLUMN_2 -> Pair(listOf(three, six, nine), R.drawable.vertical_line)
            WinningLine.DIAGONAL_LEFT -> Pair(
                listOf(one, five, nine),
                R.drawable.left_diagonal_line
            )
            WinningLine.DIAGONAL_RIGHT -> Pair(
                listOf(three, five, seven),
                R.drawable.right_diagonal_line
            )
//            WinningLine.DRAW ->
        }

        winningBoxes.forEach { box ->
            box.background = ContextCompat.getDrawable(GameActivity@ this, background)
        }
    }
}