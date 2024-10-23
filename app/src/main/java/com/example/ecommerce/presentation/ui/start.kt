/*
package com.example.ecommerce.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.google.accompanist.pager.ExperimentalPagerApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.ecommerce.R
import com.uistack.onboarding.OnBoardingData
import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import com.example.ecommerce.ui.theme.ColorBlue
import com.example.ecommerce.ui.theme.ColorGreen
import com.example.ecommerce.ui.theme.ColorYellow
import com.example.ecommerce.ui.theme.ECommerceTheme
import com.example.ecommerce.ui.theme.Poppins
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Start : Screen {
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    override fun Content() {
        /*   val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val onboardingComplete = sharedPreferences.getBoolean("onboarding_complete", false)

        // إذا كان المستخدم قد شاهد شاشات البداية، انتقل إلى شاشة تسجيل الدخول
        if (onboardingComplete) {
            navigator.push(LoginScreen())
        } else {
            // عرض شاشات البداية كما في السابق
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(id = R.string.start_screen1_title),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.start_screen1_subtitle),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.splash_1),
                    contentDescription = "splash_image"
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = { navigator.push(Screen2()) },
                        modifier = Modifier,
                        containerColor = Color(0xFFFF9800),
                        shape = Shapes().extraLarge,

                        ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        }*/
        ECommerceTheme {
            // A surface container using the 'background' color from the theme
            Surface(modifier = Modifier.fillMaxSize()) {

                val items = ArrayList<OnBoardingData>()

                items.add(
                    OnBoardingData(
                        R.drawable.splash_1,
                        "Hmmm, Healthy Food",
                        "A variety of healthy foods made by the best chefs. Ingredients are easy to find. all delicious flavors can only be found at cookbunda",
                        backgroundColor = Color(0xFF0189C5),
                        mainColor = Color(0xFF00B5EA)
                    )
                )

                items.add(
                    OnBoardingData(
                        R.drawable.splash_2,
                        "Fresh Drinks, Stay Fresh",
                        "Not only food. we provide clear healthy drink options for you. Fresh taste always accompanies you",
                        backgroundColor = Color(0xFFE4AF19),
                        mainColor = ColorYellow
                    )
                )

                items.add(
                    OnBoardingData(
                        R.drawable.splash_3,
                        "Let’s Cooking",
                        "Are you ready to make a dish for your friends or family? create an account and cook",
                        backgroundColor = Color(0xFF96E172),
                        mainColor = ColorGreen
                    )
                )


                val pagerState =rememberPagerState(
                    pageCount = items.size,
                    initialOffscreenLimit = 2,
                    infiniteLoop = false,
                    initialPage = 0,
                )


                OnBoardingPager(
                    item = items, pagerState = pagerState, modifier = Modifier
                        .fillMaxWidth()
                        .background(color = ColorBlue)
                )

            }
        }
    }

    /*
    class Screen2 : Screen {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(id = R.string.start_screen1_title),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.start_screen2_subtitle),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.splash_2),
                    contentDescription = "splash_image"
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = { navigator.push(Screen3()) },
                        modifier = Modifier,
                        containerColor = Color(0xFFFF9800),
                        shape = Shapes().extraLarge,

                        ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }

    class Screen3 : Screen {
        @Composable
        override fun Content() {
            val navigator = LocalNavigator.currentOrThrow
            val context = LocalContext.current
            val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(30.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = stringResource(id = R.string.start_screen1_title),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = stringResource(id = R.string.start_screen3_subtitle),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(30.dp))
                Image(
                    painter = painterResource(id = R.drawable.splash_3),
                    contentDescription = "splash_image"
                )
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        // تخزين الحالة في SharedPreferences
                        editor.putBoolean("onboarding_complete", true)
                        editor.apply()

                        navigator.push(LoginScreen())
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFF9800)
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.start_screen3_Button),
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}
*/
    @DelicateCoroutinesApi
    @ExperimentalPagerApi
    @Composable
    fun OnBoardingPager(
        item: List<OnBoardingData>,
        pagerState: PagerState,
        modifier: Modifier = Modifier
    ) {

        Box(modifier = modifier) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(state = pagerState) { page ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(item[page].backgroundColor),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        Image(
                            painter = painterResource(id = item[page].image),
                            contentDescription = item[page].title,
                            modifier = Modifier
                                .fillMaxWidth()
                        )


                    }
                }

            }

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(340.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(0.dp),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ) {
                    Box() {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            PagerIndicator(items = item, currentPage = pagerState.currentPage)
                            Text(
                                text = item[pagerState.currentPage].title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 20.dp, end = 30.dp),
//                            color = Color(0xFF292D32),
                                color = item[pagerState.currentPage].mainColor,
                                fontFamily = Poppins,
                                textAlign = TextAlign.Right,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.ExtraBold
                            )

                            Text(
                                text = item[pagerState.currentPage].desc,
                                modifier = Modifier.padding(
                                    top = 20.dp,
                                    start = 40.dp,
                                    end = 20.dp
                                ),
                                color = Color.Gray,
                                fontFamily = Poppins,
                                fontSize = 17.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.ExtraLight
                            )

                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(30.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {


                                if (pagerState.currentPage != 2) {
                                    TextButton(onClick = {
                                        //skip
                                    }) {
                                        Text(
                                            text = "Skip Now",
                                            color = Color(0xFF292D32),
                                            fontFamily = Poppins,
                                            textAlign = TextAlign.Right,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            GlobalScope.launch {
                                                pagerState.scrollToPage(
                                                    pagerState.currentPage + 1,
                                                    pageOffset = 0f
                                                )
                                            }
                                        },
                                        border = BorderStroke(
                                            14.dp,
                                            item[pagerState.currentPage].mainColor
                                        ),
                                        shape = RoundedCornerShape(50), // = 50% percent
                                        //or shape = CircleShape
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = item[pagerState.currentPage].mainColor),
                                        modifier = Modifier.size(65.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_right_arrow),
                                            contentDescription = "",
                                            tint = item[pagerState.currentPage].mainColor,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                } else {
                                    Button(
                                        onClick = {
                                            //show home screen
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = item[pagerState.currentPage].mainColor
                                        ),
                                        contentPadding = PaddingValues(vertical = 12.dp),
                                        elevation = ButtonDefaults.buttonElevation(
                                            defaultElevation = 0.dp
                                        )
                                    ) {
                                        Text(
                                            text = "Get Started",
                                            color = Color.White,
                                            fontSize = 16.sp
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

    @Composable
    fun PagerIndicator(currentPage: Int, items: List<OnBoardingData>) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            repeat(items.size) {
                Indicator(isSelected = it == currentPage, color = items[it].mainColor)
            }
        }
    }

    @Composable
    fun Indicator(isSelected: Boolean, color: Color) {
        val width = animateDpAsState(targetValue = if (isSelected) 40.dp else 10.dp)

        Box(
            modifier = Modifier
                .padding(4.dp)
                .height(10.dp)
                .width(width.value)
                .clip(CircleShape)
                .background(
                    if (isSelected) color else Color.Gray.copy(alpha = 0.5f)
                )
        )
    }

    @ExperimentalPagerApi
    @Composable
    fun rememberPagerState(
        @androidx.annotation.IntRange(from = 0) pageCount: Int,
        @androidx.annotation.IntRange(from = 0) initialPage: Int = 0,
        @FloatRange(from = 0.0, to = 1.0) initialPageOffset: Float = 0f,
        @androidx.annotation.IntRange(from = 1) initialOffscreenLimit: Int = 1,
        infiniteLoop: Boolean = false
    ): PagerState = rememberSaveable(saver = PagerState.Saver) {
        PagerState(
            pageCount = pageCount,
            currentPage = initialPage,
            currentPageOffset = initialPageOffset,
            offscreenLimit = initialOffscreenLimit,
            infiniteLoop = infiniteLoop
        )
    }


*/

package com.example.ecommerce.presentation.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import com.google.accompanist.pager.ExperimentalPagerApi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.example.ecommerce.R
import com.uistack.onboarding.OnBoardingData
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import com.example.ecommerce.ui.theme.ColorBlue
import com.example.ecommerce.ui.theme.ColorGreen
import com.example.ecommerce.ui.theme.ColorYellow
import com.example.ecommerce.ui.theme.ECommerceTheme
import com.example.ecommerce.ui.theme.Poppins
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.launch
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class Start : Screen {
    @OptIn(ExperimentalPagerApi::class)
    @Composable
    override fun Content() {
        val context = LocalContext.current
        val navigator = LocalNavigator.currentOrThrow
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val onboardingComplete = sharedPreferences.getBoolean("onboarding_complete", false)
        if (onboardingComplete) {
            navigator.push(LoginScreen())
        } else {
            ECommerceTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize()) {
                    val items = listOf(
                        OnBoardingData(
                            R.drawable.splash_1,
                            backgroundColor = Color(0xFF0189C5),
                            mainColor = Color(0xFF00B5EA)
                        ),
                        OnBoardingData(
                            R.drawable.splash_2,
                            backgroundColor = Color(0xFFE4AF19),
                            mainColor = ColorYellow
                        ),
                        OnBoardingData(
                            R.drawable.splash_3,
                            backgroundColor = Color(0xFF96E172),
                            mainColor = ColorGreen
                        )
                    )

                    val pagerState = rememberPagerState(
                        pageCount = items.size,
                        initialOffscreenLimit = 2,
                        infiniteLoop = false,
                        initialPage = 0
                    )

                    OnBoardingPager(
                        items = items,
                        pagerState = pagerState,
                        modifier = Modifier.fillMaxWidth().background(color = ColorBlue)
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    @Composable
    fun OnBoardingPager(
        items: List<OnBoardingData>,
        pagerState: PagerState,
        modifier: Modifier = Modifier
    ) {
        val navigator = LocalNavigator.currentOrThrow
        val context = LocalContext.current
        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        Box(modifier = modifier) {
            // Main Column holding the Horizontal Pager
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                HorizontalPager(state = pagerState) { page ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(items[page].backgroundColor),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = items[page].image),
                            contentDescription = "",
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }

            // Bottom Card containing information and buttons
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    elevation = CardDefaults.cardElevation(0.dp),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Pager Indicator
                        PagerIndicator(currentPage = pagerState.currentPage, items = items)
                        // Bottom Buttons Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            if (pagerState.currentPage != 2) {
                                // Skip Button
                                TextButton(onClick = {
                                    navigator.push(LoginScreen())
                                    editor.putBoolean("onboarding_complete", true)
                                    editor.apply()
                                }) {
                                    Text(
                                        text = "Skip Now",
                                        color = Color(0xFF292D32),
                                        fontFamily = Poppins,
                                        textAlign = TextAlign.Right,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                }

                                // State to trigger scroll
                                val shouldScroll = remember { mutableStateOf(false) }

                                // LaunchedEffect to handle page scrolling
                                LaunchedEffect(shouldScroll.value) {
                                    if (shouldScroll.value) {
                                        pagerState.scrollToPage(pagerState.currentPage + 1)
                                        shouldScroll.value = false // Reset after scrolling
                                    }
                                }

                                // Outlined Button for Next Page
                                OutlinedButton(
                                    onClick = {
                                        // Trigger scrolling to the next page
                                        shouldScroll.value = true
                                    },
                                    border = BorderStroke(
                                        14.dp,
                                        items[pagerState.currentPage].mainColor
                                    ),
                                    shape = RoundedCornerShape(50),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = items[pagerState.currentPage].mainColor),
                                    modifier = Modifier.size(65.dp)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_right_arrow),
                                        contentDescription = null,
                                        tint = items[pagerState.currentPage].mainColor,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            } else {
                                // Get Started Button
                                Button(
                                    onClick = {
                                        navigator.push(LoginScreen())
                                        editor.putBoolean("onboarding_complete", true)
                                        editor.apply()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = items[pagerState.currentPage].mainColor
                                    ),
                                    contentPadding = PaddingValues(vertical = 12.dp),
                                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                                ) {
                                    Text(
                                        text = "Get Started",
                                        color = Color.White,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }




    @Composable
    fun PagerIndicator(currentPage: Int, items: List<OnBoardingData>) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            repeat(items.size) {
                Indicator(isSelected = it == currentPage, color = items[it].mainColor)
            }
        }
    }

    @Composable
    fun Indicator(isSelected: Boolean, color: Color) {
        val width = animateDpAsState(targetValue = if (isSelected) 40.dp else 10.dp)

        Box(
            modifier = Modifier
                .padding(4.dp)
                .height(10.dp)
                .width(width.value)
                .clip(CircleShape)
                .background(if (isSelected) color else Color.Gray.copy(alpha = 0.5f))
        )
    }

    @ExperimentalPagerApi
    @Composable
    fun rememberPagerState(
        pageCount: Int,
        initialPage: Int = 0,
        initialPageOffset: Float = 0f,
        initialOffscreenLimit: Int = 1,
        infiniteLoop: Boolean = false
    ): PagerState = rememberSaveable(saver = PagerState.Saver) {
        PagerState(
            pageCount = pageCount,
            currentPage = initialPage,
            currentPageOffset = initialPageOffset,
            offscreenLimit = initialOffscreenLimit,
            infiniteLoop = infiniteLoop
        )
    }
}
