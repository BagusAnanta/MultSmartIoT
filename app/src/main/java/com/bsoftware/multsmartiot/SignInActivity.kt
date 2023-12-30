package com.bsoftware.multsmartiot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsoftware.multsmartiot.ui.theme.MultSmartIoTTheme

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

    val context : Context = LocalContext.current
    val activity : Activity = (LocalContext.current as Activity)

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
                    painter = painterResource(id = R.drawable.mult_iot),
                    contentDescription = "IconImage",
                    modifier = Modifier.padding(top = 20.dp)
                )
                Column(modifier = Modifier
                    .padding(bottom = 20.dp, top = 20.dp)
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
                    shape = RoundedCornerShape(20.dp)
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
                    shape = RoundedCornerShape(20.dp)
                )

                // button for sign in use email
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    onClick = {
                         if(email == "bagusananta@mult.com" && password == "4dM1nMulT"){
                             context.startActivity(Intent(context,MainMenuActivity::class.java))
                             activity.finish()
                             Toast.makeText(context,"Developer Mode, Welcome :D",Toast.LENGTH_SHORT).show()
                         }
                    },
                ) {
                    Text(text = "Sign In")
                }

                Text(
                    text = "Or SignIn With",
                    style = TextStyle(
                       fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(top = 5.dp, bottom = 5.dp)
                )

                // button for sign in use google email
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 20.dp),
                    onClick = { /*TODO*/ },
                ) {
                    Text(text = "Sign In With Google")
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