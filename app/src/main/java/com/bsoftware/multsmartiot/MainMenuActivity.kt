package com.bsoftware.multsmartiot

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bsoftware.multsmartiot.circularprogressbar.CircularProgressBarView
import com.bsoftware.multsmartiot.firebase.FirebaseRealtimeDatabase
import com.bsoftware.multsmartiot.ui.theme.MultSmartIoTTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class MainMenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultSmartIoTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainMenu()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenu(){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val isSystemThemeDark = isSystemInDarkTheme()

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Image(
                        painter = if(isSystemThemeDark) painterResource(id = R.drawable.mult_iot_white) else painterResource(id = R.drawable.mult_iot),
                        contentDescription = "ProductIcon",
                        modifier = Modifier
                            .size(85.dp,85.dp)
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "AccountIcon",
                            modifier = Modifier
                                .size(30.dp,30.dp)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            Icons.Filled.Menu,
                            contentDescription = "MenuIcon",
                            modifier = Modifier
                                .size(30.dp,30.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ){ innerPadding ->
        MainMenuContent(innerPadding)
    }
}

@Composable
fun MainMenuContent(innerPadding : PaddingValues){
    val context : Context = LocalContext.current
    val exampleName = "Bagus"
    FirebaseApp.initializeApp(context)
    val firebaseDatabase = FirebaseDatabase.getInstance()
    val databaseReference = firebaseDatabase.getReference("Humtemp")

    var humidity by remember{ mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var status by remember { mutableStateOf(false) }
    var outputStatus by remember { mutableStateOf("") }
    
    FirebaseRealtimeDatabase().let {
        it.initDatabase()
        it.getHumTempDataList(databasePref = databaseReference).forEach { getData ->
            humidity = getData.humidity.toString()
            temperature = getData.temperature.toString()
            status = getData.status
            outputStatus = getData.output
        }
    }

    LazyColumn(
        contentPadding = innerPadding,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 30.dp, bottom = 20.dp, start = 10.dp, end = 10.dp)
    ){
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier
                        .padding(10.dp)
                ){
                    Text(
                        text = "Welcome \n" +
                                "Home, $exampleName !",
                        style = TextStyle(
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Monitoring You Home From You Hand",
                        style = TextStyle(
                            fontSize = 15.sp
                        ),
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }

                Text(
                    text = "Device Shortcut",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 30.dp, start = 20.dp)
                )

                Spacer(modifier = Modifier.padding(top = 10.dp))

                DeviceCard(
                    deviceName = "Bagus Device",
                    location = "Bedroom",
                    status = status,
                    data1 = humidity,
                    data2 = temperature,
                    data3 = outputStatus
                )
            }
        }
    }
}

@Composable
fun DeviceCard(
    deviceName : String = "Device Name",
    location : String = "Bedroom",
    status : Boolean = true,
    data1Title : String = "Humidity",
    data2Title : String = "Temperature",
    data3Title : String = "Status",
    data1 : String = "78",
    data2 : String = "22.6",
    data3 : String = "Normal",
    data1Icon : String = "%",
    data2Icon : String = "°C",
){

    var expanded by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(false) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp)
            .animateContentSize()
            .height(if (expanded) 400.dp else 200.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                expanded = !expanded
                visible = !visible
            },
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Text(
                    text = if(status) "Active" else "Nonactive",
                    modifier = Modifier
                        .padding(top = 5.dp),
                    style = TextStyle(
                        fontSize = 15.sp
                    )
                )
            }

            Text(
                text = deviceName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp
                )
            )

            Text(
                text = location,
                modifier = Modifier
                    .padding(top = 5.dp)
            )

            AnimatedVisibility(
                visible = visible,
                enter = slideInVertically(),
                exit = fadeOut()
            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 30.dp)
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(50.dp)
                ){
                    // Humidity
                    CircularProgressBarView(
                        size = 100.dp,
                        number = data1.toFloat(),
                        indicator = "%",
                        numberStyle = TextStyle(
                            fontSize = 15.sp
                        ),
                        indicatorThickness = 20.dp
                    )
                    // temperature
                    CircularProgressBarView(
                        size = 100.dp,
                        number = data2.toFloat(),
                        indicator = "°C",
                        numberStyle = TextStyle(
                            fontSize = 15.sp
                        ),
                        indicatorThickness = 20.dp
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxSize()
            ){
                Column{
                    Text(
                        text = data1Title,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = if(status) "$data1$data1Icon" else "0.0",
                        style = TextStyle(
                            fontSize = 20.sp,

                        )
                    )
                }

                Column(modifier = Modifier.padding(start = 20.dp)){
                    Text(
                        text = data2Title,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = if(status) "$data2$data2Icon" else "0.0",
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }

                Column(modifier = Modifier.padding(start = 20.dp)){
                    Text(
                        text = data3Title,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(
                        text = data3,
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainMenuPreview() {
    MultSmartIoTTheme {
       MainMenu()
    }
}