package com.example.mukhibra_tictactoe

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView

class GameManager(var player1: Player, var player2: Player, var player_x_y: TextView) {

    private var currentPlayer = 1

    val currentPlayerMark: Int
        get() {
            return if (currentPlayer == 1) R.drawable.x else R.drawable.y
        }

    private var state = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0)
    )

    fun makeMove(position: Position): WinningLine? {

        state[position.row][position.column] = currentPlayer
        val winningLine = hasGameEnded()
//        var anim2: Animation = AnimationUtils.loadAnimation(GameActivity(), R.anim.anim_player_x_y)
        if (winningLine == null) {
            if (currentPlayer == 1) player_x_y.text = "Player O"
            else player_x_y.text = "Player X"
//            player_x_y.startAnimation(anim2)
            currentPlayer = 3 - currentPlayer
        }else{
            when(currentPlayer){
                1 -> player1.point++
                2 -> player2.point++
            }
        }
        return winningLine
    }

    fun reset() {
        state = arrayOf(
            intArrayOf(0, 0, 0),
            intArrayOf(0, 0, 0),
            intArrayOf(0, 0, 0)
        )
        currentPlayer = 1
    }

    private fun hasGameEnded(): WinningLine? {
        if (state[0][0] == currentPlayer && state[0][1] == currentPlayer && state[0][2] == currentPlayer) return WinningLine.ROW_0
        if (state[1][0] == currentPlayer && state[1][1] == currentPlayer && state[1][2] == currentPlayer) return WinningLine.ROW_1
        if (state[2][0] == currentPlayer && state[2][1] == currentPlayer && state[2][2] == currentPlayer) return WinningLine.ROW_2
        if (state[0][0] == currentPlayer && state[1][0] == currentPlayer && state[2][0] == currentPlayer) return WinningLine.COLUMN_0
        if (state[0][1] == currentPlayer && state[1][1] == currentPlayer && state[2][1] == currentPlayer) return WinningLine.COLUMN_1
        if (state[0][2] == currentPlayer && state[1][2] == currentPlayer && state[2][2] == currentPlayer) return WinningLine.COLUMN_2
        if (state[0][0] == currentPlayer && state[1][1] == currentPlayer && state[2][2] == currentPlayer) return WinningLine.DIAGONAL_LEFT
        if (state[0][2] == currentPlayer && state[1][1] == currentPlayer && state[2][0] == currentPlayer) return WinningLine.DIAGONAL_RIGHT
        return null
    }

    fun isDraw(): Boolean {
        return (state[0][0] != 0 && state[0][1] != 0 && state[0][2] != 0 && state[1][0] != 0 && state[1][1] != 0 && state[1][2] != 0 && state[2][0] != 0 && state[2][1] != 0 && state[2][2] != 0)
    }

}