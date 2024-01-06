package com.bsoftware.multsmartiot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import com.bsoftware.multsmartiot.sharepref.StatusSharePreference
import com.bsoftware.multsmartiot.ui.theme.MultSmartIoTTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultSmartIoTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SignIn()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignIn(){
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    val context : Context = LocalContext.current
    val activity : Activity = (LocalContext.current as Activity)

    val storeLogin = UserLoginDataStore(context)
    val statusSharePref = StatusSharePreference(activity)

    val isSystemThemeDark = isSystemInDarkTheme()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(20.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = if(isSystemThemeDark) painterResource(id = R.drawable.mult_icon_white) else painterResource(id = R.drawable.mult_icon_black),
                    contentDescription = "IconImage",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .size(110.dp, 110.dp)
                )
                Column(modifier = Modifier
                    .padding(bottom = 20.dp, top = 10.dp)
                    .fillMaxWidth()
                ) {
                    Text(
                        text = "Hello",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 35.sp
                        )
                    )
                    Text(
                        text = "Welcome",
                        style = TextStyle(
                            fontSize = 25.sp
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
                         if(email == "bagusananta@mult.com" && password == "4dM1nMulT"){
                             // intent in here and save a data in datastore
                             context.startActivity(Intent(context,MainMenuActivity::class.java))
                             activity.finish()
                             Toast.makeText(context,"Developer Mode, Welcome :D",Toast.LENGTH_SHORT).show()

                             // save a data and status for developer option
                             CoroutineScope(Dispatchers.IO).launch {
                                 storeLogin.storeUserData(UserLoginDataClass("bagusananta@mult.com","4dM1nMulT"))
                             }

                             // save status at sharepreverence
                             statusSharePref.setStatus(true)
                         } else if(email != "bagusananta@mult.com" && password != "4dM1nMulT"){
                             // sign in using firebase
                             FirebaseAuthentication().apply {
                                 initFirebaseAuth()
                                 signInDataUser(
                                     email = email,
                                     password = password,
                                     onSuccess = {
                                         // intent into mainActivity
                                         context.startActivity(Intent(context,MainMenuActivity::class.java))
                                         activity.finish()

                                         // set a status
                                         // save status at sharepreverence
                                         statusSharePref.setStatus(true)
                                     },
                                     onFailed = {
                                         // if a password or username fail
                                         Toast.makeText(context,"You Username or Password Incorrect, please try again", Toast.LENGTH_SHORT).show()
                                     },
                                     activity = activity
                                 )
                             }
                         } else {
                             Toast.makeText(context,"You Username or Password Incorrect, please try again", Toast.LENGTH_SHORT).show()
                         }
                    },
                ) {
                    Text(text = "Sign In")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SignInPreview() {
    MultSmartIoTTheme {
        SignIn()
    }
}