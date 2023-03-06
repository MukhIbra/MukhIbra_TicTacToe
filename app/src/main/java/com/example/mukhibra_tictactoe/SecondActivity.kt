package com.example.mukhibra_tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast

class SecondActivity : AppCompatActivity() {

    private lateinit var name_X: EditText
    private lateinit var name_O: EditText
    private lateinit var button: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        name_X = findViewById(R.id.name_X)
        name_O = findViewById(R.id.name_O)
        button = findViewById(R.id.button)


        button.setOnClickListener {
            if (name_X.text.toString() != "" && name_O.text.toString() != ""){
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("player1", name_X.text.toString())
                intent.putExtra("player2", name_O.text.toString())

                startActivity(intent)
            }else{
                Toast.makeText(applicationContext,"Fill the names",Toast.LENGTH_LONG).show()
            }
        }
    }
}