package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.presentation.viewModel.CategoryProductsViewModel

class CategoryProductsScreen(
    private val categoryId: Int,
    private val name: String
) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: CategoryProductsViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val productsState by viewModel.categoryProducts.collectAsState()

        LaunchedEffect(categoryId) {
            viewModel.getProducts(categoryId)
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = name) },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back to Home")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                ProductGridState(
                    productsState = productsState,
                    onProductClick = { product ->
                        navigator.push(ProductDetailsScreen(product))
                    }
                )
            }
        }
    }
}

@Composable
fun ProductGridState(
    productsState: Products?,
    onProductClick: (ProductItemSmall) -> Unit
) {
    when {
        productsState?.data?.data != null && productsState.status -> {
            ProductGrid(
                products = productsState.data.data,
                onProductClick = onProductClick
            )
        }

        productsState?.status == false -> {
            ErrorState(message = "Failed to load products.")
        }

        productsState == null -> {
            LoadingState()
        }

        else -> {
            LoadingState()
        }
    }
}

@Composable
fun ProductGrid(
    products: List<ProductItemSmall>,
    onProductClick: (ProductItemSmall) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2), // Set the number of columns
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp), // Padding around the grid
    ) {
        items(products) { product ->
            // Apply spacing between items directly in ProductBox
            ProductBox(product = product, onClick = { onProductClick(product) })
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun ProductBox(product: ProductItemSmall, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp) // This adds spacing between items
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium) // Added shape
            .shadow(
                5.dp,
                MaterialTheme.shapes.extraSmall,
                spotColor = Color.Gray.copy(alpha = 0.7f),
                ambientColor = Color.Gray,

            ) // Added shadow for elevation effect
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth() // Fill width for consistent alignment
        ) {
            // Product image with discount flag
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(MaterialTheme.shapes.medium) // Rounded corners for image
            ) {
                Image(
                    painter = rememberAsyncImagePainter(product.image),
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize()
                )
                // Discount flag with italic style
                if (product.discount > 0) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .background(MaterialTheme.colorScheme.error, MaterialTheme.shapes.small)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "-${product.discount}%",
                            color = MaterialTheme.colorScheme.onError,
                            style = MaterialTheme.typography.bodySmall.copy(fontStyle = FontStyle.Italic)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp)) // Space between image and product name

            // Product name with dynamic height
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface, // Adjusted color for better contrast
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight() // Adjust height based on content
            )

            Spacer(modifier = Modifier.height(4.dp)) // Space between product name and price section

            // Price section with dynamic height
            Column {
                if (product.discount > 0) {
                    // Old price with strikethrough
                    Text(
                        text = "Price: \$${product.old_price}",
                        style = MaterialTheme.typography.bodyLarge.copy(textDecoration = TextDecoration.LineThrough),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(4.dp)) // Space before the discounted price
                    // New discounted price formatted to one decimal place
                    Text(
                        text = "\$${product.price}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                } else {
                    // Regular price
                    Text(
                        text = "Price: \$${String.format("%.1f", product.price)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}


@Composable
fun LoadingState() {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color(0xFFFF9800))
    }
}

@Composable
fun ErrorState(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}
