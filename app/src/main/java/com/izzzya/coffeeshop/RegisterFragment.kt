package com.izzzya.coffeeshop

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
import androidx.navigation.fragment.findNavController
import com.izzzya.coffeeshop.api.NetworkService
import com.izzzya.coffeeshop.api.response.Token
import com.izzzya.coffeeshop.classes.SessionManager
import com.izzzya.coffeeshop.classes.currentUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date


class RegisterFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().findViewById<TextView>(R.id.headerTV).text = getString(R.string.register)

        val emailField = view.findViewById<EditText>(R.id.emailET_ger)
        val passwordField = view.findViewById<EditText>(R.id.password_reg)
        val passwordFieldRep = view.findViewById<EditText>(R.id.rep_password_reg)

        view.findViewById<Button>(R.id.logInBtn).setOnClickListener {
            if ((emailField.text.isNotEmpty() && passwordField.text.isNotEmpty()) && (passwordField.text.toString() == passwordFieldRep.text.toString())){
                try {
                    if (login(
                            emailField.text.toString(),
                            passwordField.text.toString() )){
                        findNavController().navigate(R.id.action_global_shopsFragment)
                    }

                }catch (e: java.lang.Exception){
                    e.message?.let { it1 -> Log.e("LOGIN FAILED EXC", it1) }
                    Toast.makeText(requireContext(),e.message,Toast.LENGTH_SHORT).show()
                    emailField.text.clear()
                    passwordField.text.clear()

                }
            }else{
                Toast.makeText(requireContext(), "Есть пустые поля, либо пароли не совпадают", Toast.LENGTH_SHORT).show()
            }
        }

    }

    var responseResult = false
    fun login(userLogin: String, password: String): Boolean {
        //потом из полей берём логин+пароль
        //и фигачим вход по функции, сама она ниже
        RegWToken(userLogin, password)
        Log.d("LVM","LogInWToken PASSED")
        val token = SessionManager.getToken()
        if (token.isNotEmpty()) {
            //функция теста токена ниже
            //testToken(token)
            //проверка не истёк ли токен
            if (SessionManager.getTokenExpires() > Date().time) {
                //проверяется результат теста токена в переменной
                if(responseResult){
                    return true

                }
            }
        }

        //тут если условия не исполняются то всё фиг ты войдёшь
        return false
    }

    private fun RegWToken (login: String, password: String){
        Log.d("LVM","LogInWToken STARTED")
        NetworkService.getInstanceRetrofit().getJSONApi()
            .register(currentUser(login, password)).enqueue(object: Callback<Token> {
                override fun onResponse(call: Call<Token>, response: Response<Token>)
                {
                    if(response.isSuccessful && response.code() == 200){
                        //если нет ответа
                        if(response.body() == null) {
                            Toast.makeText(requireContext(), "No Response", Toast.LENGTH_SHORT).show()

                        }
                        //запоминаем токен и все данные юзера
                        val token = response.body()!!
                        if (token.tokenType.isEmpty()) {
                            SessionManager.setToken("bearer", token.token)
                            Log.d( "AUTH", "TOKEN SET BEARER")
                        } else {
                            SessionManager.setToken(token.tokenType!!, token.token)
                            Log.d( "AUTH", "TOKEN SET -/-")
                        }
                        Toast.makeText(requireContext(), "success!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_global_shopsFragment)
                    } else when (response.code()){
                        400 ->  Toast.makeText(requireContext(), response.code() , Toast.LENGTH_SHORT).show()
                        401 -> Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
                        404 -> Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
                        406 -> Toast.makeText(requireContext(), response.code(), Toast.LENGTH_SHORT).show()
                        500 -> Toast.makeText(requireContext(), response.code().toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<Token>, t: Throwable) {
                    t.message?.let { Log.println(Log.INFO, "EXC", it) }
                    Toast.makeText(requireContext(), "Не удаётся соединиться с сервером", Toast.LENGTH_SHORT).show()

                }
            })
    }
}