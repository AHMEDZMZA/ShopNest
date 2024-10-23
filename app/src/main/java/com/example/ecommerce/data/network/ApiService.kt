package com.example.ecommerce.data.network

import com.example.ecommerce.data.model.AddCartRequest
import com.example.ecommerce.data.model.AddOrDeleteCartsResponse
import com.example.ecommerce.data.model.AddOrDeleteFavoriteRequest
import com.example.ecommerce.data.model.AddOrDeleteResponse
import com.example.ecommerce.data.model.AddressesRequest
import com.example.ecommerce.data.model.AddressesResponse
import com.example.ecommerce.data.model.Banners
import com.example.ecommerce.data.model.Categories
import com.example.ecommerce.data.model.ChangePasswordRequest
import com.example.ecommerce.data.model.ChangePasswordResponse
import com.example.ecommerce.data.model.ChangeProfileRequest
import com.example.ecommerce.data.model.GetAddresses
import com.example.ecommerce.data.model.GetCarts
import com.example.ecommerce.data.model.GetContactsDataClass
import com.example.ecommerce.data.model.GetFavorites
import com.example.ecommerce.data.model.LoginRequest
import com.example.ecommerce.data.model.LoginResponse
import com.example.ecommerce.data.model.Logout
import com.example.ecommerce.data.model.Products
import com.example.ecommerce.data.model.Profile
import com.example.ecommerce.data.model.SearchRequest
import com.example.ecommerce.data.model.SignUpRequest
import com.example.ecommerce.data.model.SignUpResponse
import com.example.ecommerce.data.model.UpdateAddressesRequest
import com.example.ecommerce.data.model.UpdateCart
import com.example.ecommerce.data.model.addCartsOrDeleteCartsDataClassRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequest) : Response<LoginResponse>

    @POST("register")
    suspend fun register(@Body signUpRequest: SignUpRequest) : Response<SignUpResponse>

    @GET("categories")
    suspend fun getCategories() : Response<Categories>

    @GET("products")
    suspend fun getAllProducts() : Response<Products>

    @GET("banners")
    suspend fun getBanners() : Response<Banners>

    @GET("products")
    suspend fun getProductsByCategory(@Query("category_id") categoryId: Int): Response<Products>

    @GET("products")
    suspend fun getAllProduct():Response<Products>

    @GET("profile")
    suspend fun getProfile(): Response<Profile>

    @POST("products/search")
    suspend fun getSearch(@Body searchRequest: SearchRequest) : Response<Products>

    @POST("logout")
    suspend fun getLogout() : Response<Logout>

    @POST("change-password")
    suspend fun getChangePassword(@Body changePasswordRequest: ChangePasswordRequest) : Response<ChangePasswordResponse>

    @PUT("update-profile")
    suspend fun getUpdateProfile(@Body changeProfileRequest: ChangeProfileRequest)  : Response<Profile>

    @POST("addresses")
    suspend fun getNewAddresses(@Body addressesRequest: AddressesRequest) : Response<AddressesResponse>

    @DELETE("addresses/{id}")
    suspend fun getDeleteAddresses(@Path("id") id: Int) : Response<AddressesResponse>

    @POST("favorites")
    suspend fun addOrDeleteFavorites(@Body id: AddOrDeleteFavoriteRequest) : Response<AddOrDeleteResponse>

    @GET("favorites")
    suspend fun getFavorites() : Response<GetFavorites>

    @GET("carts")
    suspend fun getCarts() : Response<GetCarts>

    @POST("carts")
    suspend fun addCartsOrDeleteCarts(@Body id: addCartsOrDeleteCartsDataClassRequest) : Response<AddOrDeleteCartsResponse>

    @PUT("carts/{id}")
    suspend fun updateCarts(@Path("id") id: Int, @Body quantity: AddCartRequest) : Response<UpdateCart>

    @GET("contacts")
    suspend fun getContacts() : Response<GetContactsDataClass>
}