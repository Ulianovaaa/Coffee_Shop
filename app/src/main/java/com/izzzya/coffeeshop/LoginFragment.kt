package com.izzzya.coffeeshop

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import com.izzzya.coffeeshop.api.NetworkService
import com.izzzya.coffeeshop.api.response.Token
import com.izzzya.coffeeshop.classes.SessionManager
import com.izzzya.coffeeshop.classes.currentUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class LoginFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<TextView>(R.id.headerTV).text = getString(R.string.log_in)
        val emailField = view.findViewById<EditText>(R.id.emailET_login)
        val passwordField = view.findViewById<EditText>(R.id.password_login)


        view.findViewById<TextView>(R.id.regBtn).setOnClickListener {
            findNavController().navigate(R.id.action_global_registerFragment)
        }

        view.findViewById<Button>(R.id.logInBtn).setOnClickListener {
            if (emailField.text.isNotEmpty() && passwordField.text.isNotEmpty()){
                //логин идёт
                try {
                    if (login(
                            emailField.text.toString(),
                            passwordField.text.toString() )){
                       // findNavController().navigate(R.id.action_global_shopsFragment)
                    }

                }catch (e: java.lang.Exception){
                    e.message?.let { it1 -> Log.e("LOGIN FAILED EXC", it1) }
                    Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
                    emailField.text.clear()
                    passwordField.text.clear()

                }
            }else{
                Toast.makeText(requireContext(), "Нужно заполнить все поля", Toast.LENGTH_SHORT).show()
            }
        }

    }

    var responseResult = false
    fun login(userLogin: String, password: String): Boolean {
            //потом из полей берём логин+пароль
            //и фигачим вход по функции, сама она ниже
            LogInWToken(userLogin, password)
            Log.d("LVM","LogInWToken PASSED")
            val token = SessionManager.getToken()
            if (token.isNotEmpty()) {
                //функция теста токена ниже
                //testToken(token)
                //проверка не истёк ли токен
                if (SessionManager.getTokenExpires() > Date().time) {
                    //проверяется результат теста токена в переменной
                    if(responseResult){
                        //здесь я написала return true, потому что тут
                        // алгоритм входа
                        //а кнопка проверяет сработал ли метод
                        return true

                    }
                    else {
                        //если истёк то пусть пока ошибку показывает
//                        _loginResult.value = LoginResult(error = R.string.token_expired)
                    }
                }
            }

        //тут если условия не исполняются то всё фиг ты войдёшь
        return false
    }

    private fun LogInWToken (login: String, password: String){
        Log.d("LVM","LogInWToken STARTED")
        NetworkService.getInstanceRetrofit().getJSONApi()
            .login(currentUser(login, password)).enqueue(object: Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>)
                {
                    if(response.isSuccessful && response.code() == 200){
                        //если нет ответа
                        if(response.body() == null) {
                            Toast.makeText(requireContext(), "No Response", Toast.LENGTH_SHORT).show()

                        }
                        //если есть то всё норм
//                        Log.d( "OK", "RESP VAL 200")
                        //запоминаем токен и все данные юзера
                        val token = response.body()!!
//                        if (token.expireDateTime.isNotEmpty()) {
//                            val expDate = SimpleDateFormat(
//                                resources.getString(R.string.parse_expire_date_format), Locale.ENGLISH)
//                                .parse(token.expireDateTime)!!.time
//                            SessionManager.setTokenExpires(expDate)
//                        }
                        if (token.tokenType.isEmpty()) {
                            SessionManager.setToken("bearer", token.token)
                            Log.d( "AUTH", "TOKEN SET BEARER")
                        } else {
                            SessionManager.setToken(token.tokenType!!, token.token)
                            Log.d( "AUTH", "TOKEN SET -/-")
                        }
//                        SessionManager.setUsername(login)
//                        SessionManager.setPassword(password)
                        //переходим дальше после логина
                        Toast.makeText(requireContext(), "success!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_global_shopsFragment)
                    } else when (response.code()){
                        400 ->  Toast.makeText(requireContext(), response.code().toString() , Toast.LENGTH_SHORT).show()
                        401 -> Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
                        404 -> Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
                        500 -> Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Token>, t: Throwable) {
//                    Log.println(Log.INFO, "EXC", "Не удаётся соединиться с сервером")
                    t.message?.let { Log.println(Log.INFO, "EXC", it) }
                    //val e = java.lang.Exception("Не удаётся соединиться с сервером")
                    Toast.makeText(requireContext(), "Не удаётся соединиться с сервером", Toast.LENGTH_SHORT).show()

                }
            })
    }


}