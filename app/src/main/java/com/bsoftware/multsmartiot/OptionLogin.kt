package com.bsoftware.multsmartiot

import android.os.Bundle
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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

                }
            }
        }
    }
}

@Composable
fun OptionLoginUser(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
    ) {
        // in here have a 1 imageview and 2 button
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Icon",
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth()
        )

        Column{
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .size(60.dp)
                    .padding(bottom = 5.dp, top = 10.dp)
            ) {
                Text(text = "Sign In")
            }
            Button(
                onClick = { /*TODO*/ },
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