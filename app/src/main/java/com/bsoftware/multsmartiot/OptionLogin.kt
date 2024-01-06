package com.bsoftware.multsmartiot

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.bsoftware.multsmartiot.datastore.UserLoginDataStore
import com.bsoftware.multsmartiot.sharepref.StatusSharePreference
import com.bsoftware.multsmartiot.ui.theme.MultSmartIoTTheme

class OptionLogin : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultSmartIoTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val context : Context = LocalContext.current
                    val activity : Activity = (LocalContext.current as Activity)
                    val statusSharePref = StatusSharePreference(activity)

                    if(statusSharePref.getStatus()){
                        // if a status true into mainactivity
                        context.startActivity(Intent(context,MainMenuActivity::class.java))
                        activity.finish()
                    } else {
                        OptionLoginUser()
                    }
                }
            }
        }
    }
}

@Composable
fun OptionLoginUser(){
    val context : Context = LocalContext.current
    val activity : Activity = (LocalContext.current as Activity)
    val isSystemThemeDark = isSystemInDarkTheme()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        // in here have a 1 imageview and 2 button
        Image(
            painter = if(isSystemThemeDark) painterResource(id = R.drawable.mult_icon_white) else painterResource(id = R.drawable.mult_icon_black),
            contentDescription = "Icon",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .size(110.dp, 110.dp)
        )

        Column{
            Button(
                onClick = {
                    // intent into SignInActivity
                          context.startActivity(Intent(context,SignInActivity::class.java))
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp)
                    .padding(bottom = 5.dp, top = 10.dp)
            ) {
                Text(text = "Sign In")
            }
            Button(
                onClick = {
                          context.startActivity(Intent(context,SignUpActivity::class.java))
                },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp)
                    .padding(top = 10.dp, bottom = 5.dp)
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OptionLoginPreview() {
    MultSmartIoTTheme {
        OptionLoginUser()
    }
}