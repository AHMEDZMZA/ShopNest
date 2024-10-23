package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.R
import com.example.ecommerce.data.model.ProductItemSmall
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.presentation.viewModel.SearchViewModel

class SearchScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val searchViewModel: SearchViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        var searchText by remember { mutableStateOf("") }
        var isFocused by remember { mutableStateOf(false) }
        val focusManager = LocalFocusManager.current
        val searchState by searchViewModel.productSearch.collectAsState()
        val isLoading by searchViewModel.isLoading.collectAsState()

        LaunchedEffect(Unit) {
            searchViewModel.clearSearchResults()
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.search)) },
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
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText ->
                        searchText = newText
                        if (newText.isBlank()) {
                            searchViewModel.clearSearchResults()
                        }
                    },
                    keyboardActions = KeyboardActions(onSearch = {
                        searchViewModel.getProductsSearch(searchText)
                        focusManager.clearFocus()
                    }),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused
                        },
                    placeholder = { Text(stringResource(id = R.string.search_placeholder)) },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(30.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Show loading state if loading
                if (isLoading) {
                    SearchLoadingState()
                } else if (searchText.isNotBlank()) {
                    // Show "This product is not available" message if search text is entered but no results
                    if (searchState?.data?.data.isNullOrEmpty() && searchState?.status == true) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.search_no_products),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    } else {
                        // Display search results when there are results
                        SearchProductGridState(
                            productsState = searchState,
                            onProductClick = { product ->
                                navigator.push(ProductDetailsScreen(product))
                            }
                        )
                    }
                } else if (!isFocused) {
                    // Show initial message when no search is performed yet and text field is not focused
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.search_initial_message),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchErrorState(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
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

// Update the other functions similarly to use strings from the resource file

@Composable
fun SearchProductGridState(
    productsState: Products?,
    onProductClick: (ProductItemSmall) -> Unit
) {
    when {
        // If the search returned products
        productsState?.data?.data != null && productsState.data.data.isNotEmpty() && productsState.status -> {
            SearchProductGrid(
                products = productsState.data.data,
                onProductClick = onProductClick
            )
        }

        // If the search was successful, but no products were found
        productsState?.data?.data?.isEmpty() == true && productsState.status -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.search_no_products),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Handle error case
        productsState?.status == false -> {
            SearchErrorState(message = stringResource(id = R.string.error_loading))
        }

        // Show initial message when no search is performed yet
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = stringResource(id = R.string.search_instruction),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun SearchProductGrid(
    products: List<ProductItemSmall>,
    onProductClick: (ProductItemSmall) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) { product ->
            SearchProductBox(product = product, onClick = { onProductClick(product) })
        }
    }
}

@Composable
fun SearchProductBox(product: ProductItemSmall, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(product.image),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = product.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Price: \$${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun SearchLoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}


