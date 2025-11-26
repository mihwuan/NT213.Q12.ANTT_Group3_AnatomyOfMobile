package com.example.anatomyofmobile_group3_nt213

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import DatabaseHelper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.ViewCompat

class RegisterActivity : AppCompatActivity(){
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener (findViewById (R.id.register)) { v, insets ->
            val systemBars = insets.getInsets (WindowInsetsCompat.Type.systemBars ())
            v.setPadding (systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        dbHelper = DatabaseHelper(this)

        val edtUser = findViewById<EditText>(R.id.edtUserReg)
        val edtPass = findViewById<EditText>(R.id.edtPassReg)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        //Đăng ký
        btnRegister.setOnClickListener {
            val user = edtUser.text.toString()
            val pass = edtPass.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show()
            } else {
                val success = dbHelper.AddUser(user, pass)
                if (success) {
                    Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

