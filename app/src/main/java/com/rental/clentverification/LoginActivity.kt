package com.rental.clentverification

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Hawk.init(this).build();

        var password = "Ramesh"
        var customerId = "rameshCustomerId"
        var email = "rameshpokhrel.3c@gmail.com"
        var salt = Utils.generateHashWithHmac(customerId, Utils.SHA256(password))//20bytes
        var pseudoPasword = Utils.sha1Hash(password + salt)

        pseudoPasword?.let {
            var signiningKey = Utils.generateHashWithHmac(email, pseudoPasword)


            login.setOnClickListener({
                var obj = JSONObject()
                var arr = JSONArray()
                arr.put(username.text.toString())
                arr.put(username.text.toString())
                var obj2 = JSONObject()
                obj2.put("key", "value")
                obj.put("name", username.text.toString())
                obj.put("address", username.text.toString())
                obj.put("age", 24)
                obj.put("obj2", obj2)
                obj.put("arr", arr)
                makeRequest(signiningKey!!, obj)
            })

        }
    }

    fun makeRequest(signingKey: String, obj: JSONObject) {

        var encryptedEncodeText = Aes.encrypt(signingKey, "fedcba9876543210", obj.toString())
        val retrofitService = EcommerceRestApi.getRetrofitClient()?.create(EcommerceApi::class.java)
        val getInitialAccountResponseUtility = retrofitService?.loginUser(encryptedEncodeText)

        getInitialAccountResponseUtility?.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    textView.setText(response.body()!!.string())
                } else {

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    //Response{protocol=http/1.1, code=200, message=OK, url=http://192.168.1.100/auth/?data=ONrXq9EKQQBI9HTjppEJ7a12Bw8GYjBOpxVd4MYYznETa%2BBBdem9QQqA8QXcsmsvGtakRofQj1eG%0AsRzOayJcA1mAejB67LoS4271iP83qXbR19Ix%2B6%2F%2BzTkqXB171Q3%2BwEjxlk7NIHiA%2BQ%2BdJiI%2BidbE%0AtS8e5WfVqz2OMOMpjt8%3D%0A%3AZmVkY2JhOTg3NjU0MzIxMA%3D%3D%0A}


}
