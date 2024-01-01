package com.bsoftware.multsmartiot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsoftware.multsmartiot.dataclass.UserLoginDataClass
import com.bsoftware.multsmartiot.datastore.UserLoginDataStore
import com.bsoftware.multsmartiot.firebase.FirebaseAuthentication
import com.bsoftware.multsmartiot.ui.theme.MultSmartIoTTheme
import androidx.lifecycle.lifecycleScope
import com.bsoftware.multsmartiot.sharepref.StatusSharePreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultSmartIoTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignUp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val context : Context = LocalContext.current
    val activity : Activity = (LocalContext.current as Activity)

    val storeLogin = UserLoginDataStore(context)
    val statusSharePref = StatusSharePreference(activity)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.mult_iot) ,
                    contentDescription = "IconImage",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(130.dp, 130.dp)
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = "Hi There !",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 35.sp
                        )
                    )
                    Text(
                        text = "Make you home smart with IoT Product us!",
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                }

                //email outline field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .fillMaxWidth(),
                    label = {Text(text = "Enter You Email")},
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.MailOutline,
                            contentDescription = "EmailIcon"
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )

                //password outline field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = "Enter You Password")},
                    leadingIcon = {
                        Icon(
                            Icons.Outlined.Lock,
                            contentDescription = "PasswordIcon"
                        )
                    },
                    shape = RoundedCornerShape(20.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {focusManager.clearFocus()}
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                // button for sign in use email
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, bottom = 30.dp),
                    onClick = {
                       FirebaseAuthentication().apply {
                           initFirebaseAuth()
                           createDataUser(
                               email = email,
                               password = password,
                               onSuccess = {
                                   // intent at here and save a data store in here
                                   context.startActivity(Intent(context,MainMenuActivity::class.java))
                                   activity.finish()

                                   // save into datastore
                                   CoroutineScope(Dispatchers.IO).launch {
                                       // store a data
                                       storeLogin.storeUserData(UserLoginDataClass(email, password))
                                   }

                                   statusSharePref.setStatus(true)
                               },
                               onFailed = {
                                   // message at here
                                   Log.e("SignUpActivity() Error At :", "Error a SignUpActivity")
                               },
                               activity = activity
                           )
                       }
                    },
                ) {
                    Text(text = "Sign Up")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignUpPreview() {
    MultSmartIoTTheme {
        SignUp()
    }
}