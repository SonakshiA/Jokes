package com.example.jokes


import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getData()
    }

    //enqueue() is a method used to initiate a network request asynchronously.
    // When you use enqueue(), Retrofit automatically handles the creation of a background thread for the
    // network call, preventing it from blocking the main UI thread. This is essential for keeping your
    // app responsive and avoiding ANR (Application Not Responding) errors.

    private fun getData(){
        var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please Wait")
        val nextButton = findViewById<Button>(R.id.next)
        progressDialog.show()
        RetrofitObject.apiInterface.getData().enqueue(object : Callback<ResponseDataClass?> {
            override fun onResponse(
                call: Call<ResponseDataClass?>,
                response: Response<ResponseDataClass?>
            ) {
                progressDialog.dismiss()
                if(count<10){
                    val setup = findViewById<TextView>(R.id.setup)
                    setup.text = response.body()?.get(count)!!.setup

                    val punchline = findViewById<TextView>(R.id.punchline)
                    punchline.text = response.body()?.get(count)!!.punchline

                    val nextButton = findViewById<Button>(R.id.next)
                    nextButton.setOnClickListener {
                        count++
                        getData()
                    }
                }else{
                    val intent = Intent(this@MainActivity, FinalScreen::class.java)
                    startActivity(intent)
                }
            }

            override fun onFailure(call: Call<ResponseDataClass?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "${t.localizedMessage}",Toast.LENGTH_LONG).show()
                progressDialog.dismiss()

            }

        })
    }
}