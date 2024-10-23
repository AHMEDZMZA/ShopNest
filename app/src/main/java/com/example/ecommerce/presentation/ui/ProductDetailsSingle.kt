/*
package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.ecommerce.util.PreferencesManager
import com.example.ecommerce.R
import com.example.ecommerce.presentation.viewModel.FavoritesViewModel
import com.example.ecommerce.presentation.viewModel.GetCartsViewModel

class ProductDetailsScreen(
    private val product: ProductItemSmall // Directly using product instead of products
) : Screen {
    @Composable
    override fun Content() {
        ProductDetailCard(product)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailCard(product: ProductItemSmall) {
    val navigator = LocalNavigator.currentOrThrow
    val context = LocalContext.current
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    val cartViewModel: GetCartsViewModel = hiltViewModel()
    val viewModel: GetCartsViewModel = hiltViewModel()
    // State to manage toast messages
    var toastMessage by remember { mutableStateOf("") }
    val cartsState by viewModel.carts.collectAsState()
    // State to manage if description is expanded or not
    var isExpanded by remember { mutableStateOf(false) }
    // Load favorite state from SharedPreferences
    val isFavoriteState = PreferencesManager.isFavorite(context, product.id.toString())
    var isFavorite by remember { mutableStateOf(isFavoriteState) }
    // Handle showing the toast messages
    LaunchedEffect(toastMessage) {
        if (toastMessage.isNotEmpty()) {
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            toastMessage = "" // Clear message after showing
        }
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(23.dp))
            // Back Arrow
            IconButton(
                onClick = { navigator.pop() },
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_to_home)
                )
            }

            // Product Image Carousel using HorizontalPager
            val pagerState = rememberPagerState(pageCount = { product.images.size })

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) { page ->
                Image(
                    painter = rememberAsyncImagePainter(product.images[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            // Dots Indicator
            DotsIndicator(
                totalDots = product.images.size,
                selectedIndex = pagerState.currentPage,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // Product Information and Favorite Icon
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Product Name and Favorite Icon in a Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        PreferencesManager.setFavorite(
                            context,
                            product.id.toString(),
                            isFavorite
                        ) // Convert to String and save to SharedPreferences
                        favoritesViewModel.favoriteAddOrDelete(product.id, onSuccess = {
                            toastMessage =
                                if (isFavorite) "Added to favorites" else "Removed from favorites"
                            favoritesViewModel.getFavorites()
                        }, onError = {
                            isFavorite = !isFavorite // Roll back favorite status on error
                            toastMessage = "Error occurred while updating favorites."
                        })
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = stringResource(id = R.string.favorite),
                            tint = if (isFavorite) Color.Red else Color.Gray, // Change color based on favorite status

                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

                // Product Description with "Read more"
                val formattedDescription =
                    product.description.replace(Regex("\\. (?=[A-Za-z])"), ".\n")

                Text(
                    text = formattedDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                if (product.description.length > 100) {
                    Text(
                        text = if (isExpanded) stringResource(id = R.string.read_less) else stringResource(
                            id = R.string.read_more
                        ),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable { isExpanded = !isExpanded }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "${stringResource(id = R.string.price_label)}: ${product.price}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Add to Cart Button
            Button(
                onClick = {
                    cartViewModel.addCartsOrDeleteCarts(product.id, onSuccess = {
                        Toast.makeText(
                            context,
                            "Added to cart",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, onError = {
                        Toast.makeText(
                            context,
                            "Error occurred while adding to cart.",
                            Toast.LENGTH_SHORT
                        ).show()
                    })
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(text = "Add to Cart")
            }
        }
    }
}

@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically // لضمان محاذاة العناصر عموديًا
    ) {
        repeat(totalDots) { index ->
            // تغيير حجم النقطة باستخدام الانيميشن
            val size by animateDpAsState(
                targetValue = if (index == selectedIndex) 24.dp else 8.dp,
                animationSpec = tween(durationMillis = 300) // مدة الانيميشن
            )

            if (index == selectedIndex) {
                // الشرط المحدد
                Box(
                    modifier = Modifier
                        .size(size, 4.dp) // حجم الشرط (عرض، ارتفاع)
                        .background(Color.Gray) // لون الشرط
                        .shadow(4.dp, CircleShape) // إضافة ظل
                )
            } else {
                // النقطة العادية
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .padding(4.dp)
                        .shadow(2.dp, CircleShape) // إضافة ظل
                )
            }

            // إضافة مسافة بين العناصر
            if (index < totalDots - 1) {
                Spacer(modifier = Modifier.width(8.dp)) // المسافة بين العناصر
            }
        }
    }
}
*/
package com.example.ecommerce.presentation.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.navigator.LocalNavigator
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce.data.model.ProductItemSmall
import cafe.adriel.voyager.navigator.currentOrThrow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.ecommerce.util.PreferencesManager
import com.example.ecommerce.R
import com.example.ecommerce.presentation.viewModel.FavoritesViewModel
import com.example.ecommerce.presentation.viewModel.GetCartsViewModel

class ProductDetailsScreen(
    private val product: ProductItemSmall // Directly using product instead of products
) : Screen {
    @Composable
    override fun Content() {
        ProductDetailCard(product)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "DefaultLocale")
@Composable
fun ProductDetailCard(product: ProductItemSmall) {
    val context = LocalContext.current
    val cartViewModel: GetCartsViewModel = hiltViewModel()
    // Load favorite state from SharedPreferences
    val isFavoriteState = PreferencesManager.isFavorite(context, product.id.toString())
    var isFavorite by remember { mutableStateOf(isFavoriteState) }
    val favoritesViewModel: FavoritesViewModel = hiltViewModel()
    // Load cart state from SharedPreferences
    val isInCartState = PreferencesManager.isProductInCart(context, product.id.toString())
    var isInCart by remember { mutableStateOf(isInCartState) }
    var toastMessage by remember { mutableStateOf("") }
    val navigator = LocalNavigator.currentOrThrow

    Scaffold(
        bottomBar = {
            // Add or Remove from Cart Button (fixed at the bottom)
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White
            ) {
                Button(
                    onClick = {
                        isInCart = !isInCart // Toggle cart status
                        PreferencesManager.setCartStatus(context, product.id.toString(), isInCart)

                        if (isInCart) {
                            cartViewModel.addCartsOrDeleteCarts(product.id, onSuccess = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.item_added_to_cart),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }, onError = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_adding_to_cart),
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                        } else {
                            cartViewModel.addCartsOrDeleteCarts(product.id, onSuccess = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.item_removed_from_cart),
                                    Toast.LENGTH_SHORT
                                ).show()                            }, onError = {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.error_adding_to_cart),
                                    Toast.LENGTH_SHORT
                                ).show()
                            })
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.White)
                        // Padding for better UI appearance,
                    ,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800))

                ) {
                    Text(text = if (isInCart) stringResource(R.string.remove_from_cart) else stringResource(R.string.cart_added))
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding) // Apply inner padding from Scaffold
        ) {
            // Back Arrow
            IconButton(
                onClick = { navigator.pop() },
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 16.dp) // أضف padding من الجانب الأيسر
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.back_to_home)
                )
            }

            // Product Image Carousel using HorizontalPager
            val pagerState = rememberPagerState(pageCount = { product.images.size })
            Spacer(modifier = Modifier.height(25.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) { page ->
                Image(
                    painter = rememberAsyncImagePainter(product.images[page]),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                )
            }

            // Dots Indicator
            DotsIndicator(
                totalDots = product.images.size,
                selectedIndex = pagerState.currentPage,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            // Product Information and Favorite Icon
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Product Name and Favorite Icon in a Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    IconButton(onClick = {
                        isFavorite = !isFavorite
                        PreferencesManager.setFavorite(
                            context,
                            product.id.toString(),
                            isFavorite
                        ) // Save to SharedPreferences
                        favoritesViewModel.favoriteAddOrDelete(product.id, onSuccess = {
                            toastMessage =
                                if (isFavorite) "Added to favorites" else "Removed from favorites"
                            favoritesViewModel.getFavorites()
                        }, onError = {
                            isFavorite = !isFavorite // Roll back on error
                            toastMessage = "Error occurred while updating favorites."
                        })
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = stringResource(id = R.string.favorite),
                            tint = if (isFavorite) Color.Red else Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Product Description with "Read more"
                val formattedDescription =
                    product.description.replace(Regex("\\. (?=[A-Za-z])"), ".\n")

                var isExpanded by remember { mutableStateOf(false) }

                Text(
                    text = formattedDescription,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = if (isExpanded) Int.MAX_VALUE else 3,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                if (product.description.length > 50) {
                    Text(
                        text = if (isExpanded) stringResource(id = R.string.read_less) else stringResource(
                            id = R.string.read_more
                        ),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.clickable { isExpanded = !isExpanded }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text ="\$${product.price}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

@Composable
fun DotsIndicator(totalDots: Int, selectedIndex: Int, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically // لضمان محاذاة العناصر عموديًا
    ) {
        repeat(totalDots) { index ->
            // تغيير حجم النقطة باستخدام الانيميشن
            val size by animateDpAsState(
                targetValue = if (index == selectedIndex) 24.dp else 8.dp,
                animationSpec = tween(durationMillis = 300) // مدة الانيميشن
            )

            if (index == selectedIndex) {
                // الشرط المحدد
                Box(
                    modifier = Modifier
                        .size(size, 4.dp) // حجم الشرط (عرض، ارتفاع)
                        .background(Color.Gray) // لون الشرط
                        .shadow(4.dp, CircleShape) // إضافة ظل
                )
            } else {
                // النقطة العادية
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(Color.LightGray)
                        .padding(4.dp)
                        .shadow(2.dp, CircleShape) // إضافة ظل
                )
            }

            // إضافة مسافة بين العناصر
            if (index < totalDots - 1) {
                Spacer(modifier = Modifier.width(8.dp)) // المسافة بين العناصر
            }
        }
    }
}
