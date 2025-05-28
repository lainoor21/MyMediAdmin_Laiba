package com.fuchsia.mymedicaladmin.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.fuchsia.mymedicaladmin.R
import com.fuchsia.mymedicaladmin.presentation.nav.Routes
import com.fuchsia.mymedicaladmin.viewmodel.ProductViewmodel


@Composable
fun ProductsScreen(
    navController: NavController,
    viewModel: ProductViewmodel = hiltViewModel()
) {
    val state = viewModel.getAllProductsState.collectAsState()
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getAllProducts()
    }

    when {
        state.value.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.value.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.value.error!!)
            }
        }

        state.value.data != null -> {

            val data = state.value.data

            if (data!!.isNotEmpty()) {

                val categories = data.map { it.category }.distinct()

                Scaffold(modifier = Modifier.fillMaxSize(),
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            navController.navigate(Routes.AddProductScreen)

                        }) {
                            Icon(
                                tint = Color.Blue,
                                imageVector = Icons.Default.Add, contentDescription = "Add Contact"
                            )
                        }
                    }

                    ) { innerPadding ->

                    Column(modifier = Modifier.fillMaxSize()) {

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .border(
                                    width = 1.dp,
                                    brush = Brush.linearGradient(
                                        colors = listOf(Color.Blue, Color.Blue)
                                    ),
                                    shape = RoundedCornerShape(8.dp)
                                )
                        ) {
                            items(categories) { category ->
                                Card(
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable { selectedCategory = category }
                                    ,
                                    colors = CardDefaults.cardColors(
                                        containerColor = if (selectedCategory == category)
                                            Color(0xFF6793BE)
                                        else
                                            MaterialTheme.colorScheme.surface


                                    )
                                ) {
                                    Text(
                                        text = category,
                                        fontFamily = FontFamily.Serif,
                                        modifier = Modifier.padding(10.dp),
                                        color = if (selectedCategory == category)
                                            MaterialTheme.colorScheme.onPrimary
                                        else
                                            MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        val filteredProducts = data.filter {
                            selectedCategory == null || it.category == selectedCategory
                        }

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(filteredProducts) { product ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 10.dp, vertical = 5.dp),
                                    elevation = CardDefaults.cardElevation(4.dp),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(10.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {

                                        Image(
                                            painter = painterResource(id = R.drawable.pdtimage),
                                            contentDescription = "Product Image",
                                            modifier = Modifier
                                                .height(100.dp)
                                                .width(100.dp)
                                                .padding(end = 10.dp)
                                                .clip(RoundedCornerShape(8.dp)),
                                            contentScale = ContentScale.Crop
                                        )

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Text(
                                                text = product.product_name,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily.Serif,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "Category: ${product.category}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontFamily = FontFamily.Serif,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = "Price: ₹${product.price}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Medium,
                                                color = MaterialTheme.colorScheme.primary,
                                                fontFamily = FontFamily.Serif,
                                            )
                                            Text(
                                                text = "Stock: ${product.stock}",
                                                fontFamily = FontFamily.Serif,
                                                style = MaterialTheme.typography.bodySmall,
                                                color = if (product.stock > 0) Color(0xFF4CAF50) else Color(
                                                    0xFFFF5722
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

            }
        }
    }
}