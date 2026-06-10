package com.example.campsitecommader

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.campsitecommader.ui.theme.CampsiteCommaderTheme
////////nemavhola phuluso ST10367073
class view : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CampsiteCommaderTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        // Green banner at the top
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(64.dp)
                                .background(Color(0xFF3FDC85))
                                .statusBarsPadding(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Campsite Commander",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        }
                    }
                ) { innerPadding ->
                    CampingInventoryScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun CampingInventoryScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = context as? Activity

    
    var itemCountInput by remember { mutableStateOf("") }
    var totalItems by remember { mutableIntStateOf(0) }

    
    val itemNames = remember { mutableStateListOf<String>() }
    val categories = remember { mutableStateListOf<String>() }
    val quantities = remember { mutableStateListOf<String>() }
    val comments = remember { mutableStateListOf<String>() }

    var showSummary by remember { mutableStateOf(false) }

    
    val simpleTextFieldColors = OutlinedTextFieldDefaults.colors(
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Transparent,
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "MY CAMPING LIST",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (totalItems == 0) {
            
            OutlinedTextField(
                value = itemCountInput,
                onValueChange = { itemCountInput = it },
                label = { Text("How many items do you want to enter?") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                colors = simpleTextFieldColors
            )
            Spacer(modifier = Modifier.height(16.dp))
            ElevatedButton(onClick = {
                val count = itemCountInput.toIntOrNull() ?: 0
                if (count > 0) {
                    totalItems = count
                    
                    for (i in 0 until count) {
                        itemNames.add("")
                        categories.add("")
                        quantities.add("")
                        comments.add("")
                    }
                }
            }) {
                Text("Enter your items")
            }
        } else if (!showSummary) {
            // user input passing
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(totalItems) { i ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = "Item ${i + 1} of $totalItems", fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = itemNames[i],
                                onValueChange = { itemNames[i] = it },
                                label = { Text("Item Name") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = simpleTextFieldColors
                            )
                            OutlinedTextField(
                                value = categories[i],
                                onValueChange = { categories[i] = it },
                                label = { Text("Category") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = simpleTextFieldColors
                            )
                            OutlinedTextField(
                                value = quantities[i],
                                onValueChange = { quantities[i] = it },
                                label = { Text("Quantity") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth(),
                                colors = simpleTextFieldColors
                            )
                            OutlinedTextField(
                                value = comments[i],
                                onValueChange = { comments[i] = it },
                                label = { Text("Comments") },
                                modifier = Modifier.fillMaxWidth(),
                                colors = simpleTextFieldColors
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { showSummary = true },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3FDC85))
            ) {
                Text("View Summary & Calculate Totals", color = Color.White)
            }
        } else {
            
            var totalQuantity = 0
            for (i in 0 until quantities.size) {
                totalQuantity += (quantities[i].toIntOrNull() ?: 0)
            }

            
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(itemNames) { i, name ->
                    Text(
                        text = "Item: $name, Category: ${categories[i]} , Qty: ${quantities[i]} , Comment: ${comments[i]}",
                        fontSize = 14.sp
                    )
                    HorizontalDivider()
                }

                item {
                    Text("*************************", fontWeight = FontWeight.Bold)
                    Text("Total number of unique items: $totalItems")
                    Text(
                        text = "Total accumulated quantity: $totalQuantity",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF3FDC85),
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    ElevatedButton(
                        onClick = {
                            // Reset state for new entry
                            totalItems = 0
                            itemCountInput = ""
                            itemNames.clear()
                            categories.clear()
                            quantities.clear()
                            comments.clear()
                            showSummary = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enter New List")
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        ElevatedButton(
                            onClick = { activity?.finish() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Back")
                        }

                        ElevatedButton(
                            onClick = { activity?.finishAffinity() },
                            modifier = Modifier.weight(1f)
                        ) {
                            Text("Exit")
                        }
                    }
                }
            }
        }
    }
}
