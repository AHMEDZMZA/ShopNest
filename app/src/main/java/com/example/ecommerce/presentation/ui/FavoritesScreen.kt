package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.DataX
import com.example.ecommerce.presentation.viewModel.FavoritesViewModel
import com.example.ecommerce.util.PreferencesManager
import com.example.ecommerce.R

@SuppressLint("MutableCollectionMutableState")
class FavoritesScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val viewModel: FavoritesViewModel = hiltViewModel()
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val productsState by viewModel.favorites.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getFavorites()
//            viewModel.favoriteAddOrDelete(
//                productId = 55,
//                onSuccess = {},
//                onError = {}
//            )
        }

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.favorite_products)) },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(id = R.string.back_to_home)
                            )
                        }
                    },
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (productsState?.data?.data?.isNotEmpty() == true) {
                    // Display the favorites grid if there are products
                    FavoritesGridState(
                        context = context,
                        productsState = productsState?.data?.data,
                        onProductClick = { product ->
                            navigator.push(FavoritesProductsDetails(product)) },
                        viewModel = viewModel
                    )
                }  else {
                    when {
                        productsState?.data?.data?.isEmpty() == true -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(id = R.string.no_favorites_yet),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                        else -> {
                            FavoriteLoadingState()
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun FavoritesGridState(
        context: Context,
        productsState: List<DataX>?,
        onProductClick: (DataX) -> Unit,
        viewModel: FavoritesViewModel
    ) {
        when {
            productsState != null -> {
                FavoritesGrid(
                    context = context,
                    products = productsState,
                    onProductClick = onProductClick,
                    viewModel = viewModel
                )
            }
            else -> {
                FavoriteLoadingState()
            }
        }
    }

    @Composable
    fun FavoritesGrid(
        context: Context,
        products: List<DataX>,
        onProductClick: (DataX) -> Unit,
        viewModel: FavoritesViewModel
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(products) { product ->
                key(product.product.id) {
                    FavoriteProductBox(
                        context = context,
                        product = product,
                        onClick = { onProductClick(product) },
                        viewModel = viewModel
                    )
                }
            }
        }
    }

    @Composable
    fun FavoriteProductBox(
        context: Context,
        product: DataX,
        onClick: () -> Unit,
        viewModel: FavoritesViewModel
    ) {
        val isFavoriteState = PreferencesManager.isFavorite(context, product.product.id.toString())
        var isFavorite by remember { mutableStateOf(isFavoriteState) }
        val favoritesViewModel: FavoritesViewModel = hiltViewModel()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(8.dp)
                .background(MaterialTheme.colorScheme.surface)
                .shadow(
                    5.dp,
                    MaterialTheme.shapes.extraSmall,
                    spotColor = Color.Gray.copy(alpha = 0.7f),
                    ambientColor = Color.Gray,
                )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(product.product.image),
                    contentDescription = product.product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${stringResource(id = R.string.price_label, product.product.price.toString())} ${product.product.price}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    IconButton(onClick = {
                        isFavorite = !isFavorite // Toggle favorite state
                        PreferencesManager.setFavorite(
                            context,
                            product.product.id.toString(),
                            isFavorite
                        ) // Save to SharedPreferences
                        favoritesViewModel.favoriteAddOrDelete(product.product.id, onSuccess = {
                            Toast.makeText(
                                context,
                                if (isFavorite)context.getString(R.string.added_to_favorites)
                                else context.getString(R.string.removed_from_favorites),
                                Toast.LENGTH_SHORT
                            ).show()
                            viewModel.getFavorites()
                        }, onError = {
                            isFavorite = !isFavorite // Roll back favorite status on error
                            Toast.makeText(
                                context,
                                it,
                                Toast.LENGTH_SHORT
                            ).show()
                        })
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.Gray // Heart color based on favorite state
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun FavoriteLoadingState() {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color(0xFFFF9800))
        }
    }
}
