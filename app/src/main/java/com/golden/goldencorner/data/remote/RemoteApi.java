package com.golden.goldencorner.data.remote;


import com.golden.goldencorner.data.Qustions.QustionsModel;
import com.golden.goldencorner.data.model.AboutResponse;
import com.golden.goldencorner.data.model.ActivateUserResponse;
import com.golden.goldencorner.data.model.AddressResponse;
import com.golden.goldencorner.data.model.AdsResponse;
import com.golden.goldencorner.data.model.AuthResponse;
import com.golden.goldencorner.data.model.BranchesResponse;
import com.golden.goldencorner.data.model.CardResponse;
import com.golden.goldencorner.data.model.CategoryResponse;
import com.golden.goldencorner.data.model.CheckoutResponse;
import com.golden.goldencorner.data.model.DeviceTokenRecords;
import com.golden.goldencorner.data.model.EditProfileResponse;
import com.golden.goldencorner.data.model.FavResponse;
import com.golden.goldencorner.data.model.HomeSliderResponse;
import com.golden.goldencorner.data.model.LimitResponse;
import com.golden.goldencorner.data.model.ListResponse;
import com.golden.goldencorner.data.model.LoginResponse;
import com.golden.goldencorner.data.model.OrderDetailResponse;
import com.golden.goldencorner.data.model.OrderResponse;
import com.golden.goldencorner.data.model.ProductDetailResponse;
import com.golden.goldencorner.data.model.ProductResponse;
import com.golden.goldencorner.data.model.ProfileResponse;
import com.golden.goldencorner.data.model.ResponseCardType;
import com.golden.goldencorner.data.model.ResponseTerms;
import com.golden.goldencorner.data.model.SimpleResponse;
import com.golden.goldencorner.ui.main.DiscounteCode.Model.ResponseDiscounteCode;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface RemoteApi {

    String BASE_URL = "https://goldencorner.com.sa/";

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/login")
    Flowable<AuthResponse> getLogin(@Field("username") String username,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/signup")
    Flowable<SimpleResponse> getSignUp(@Field("name") String name,
                                       @Field("family_name") String familyName,
                                       @Field("mobile") String mobile,
                                       @Field("password") String password,
                                       @Field("newPasswordConfirm") String newPasswordConfirm,
                                       @Field("email") String email);

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/password-reset")
    Flowable<SimpleResponse> passwordReset(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/reset-password")
    Flowable<SimpleResponse> resetPassword(@Field("token") String token,
                                           @Field("password") String password);

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/profile")
    Flowable<EditProfileResponse> editProfile(@Field("name") String name,
                                              @Field("family_name") String family_name,
                                              @Field("email") String email,
                                              @Field("mobile") String mobile,
                                              @Field("passwordupdate") String passwordUpdate,
                                              @Field("city") String city,
                                              @Field("region") String region,
                                              @Field("address") String address,
                                              //@Field("file") String file,
                                              @Part MultipartBody.Part userPhoto);

    @Multipart
    @POST("api/User/EditUserProfile")
    Flowable<LoginResponse> getEditUserProfile(@PartMap Map<String, RequestBody> params,
                                               @Part MultipartBody.Part userPhoto);

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/profile")
    Flowable<ProfileResponse> editProfile(@Query("access-token") String access_token,
                                          @Field("name") String name,
                                          @Field("family_name") String family_name,
                                          @Field("email") String email,
                                          @Field("city") String city,
                                          @Field("region") String region,
                                          @Field("address") String address);

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/profile")
    Flowable<ProfileResponse> editPassword(@Query("access-token") String access_token,
                                           @Field("passwordupdate") String password);

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/profile")
    Flowable<ProfileResponse> editMobile(@Query("access-token") String access_token,
                                         @Field("mobile") String mobile);

    @GET("golden/api/web/v1/site/branch")
    Observable<BranchesResponse> getBranches();

    @POST("golden/api/web/v1/site/device-token")
    Flowable<DeviceTokenRecords> getDeviceToken(@Query("access-token") String token);

    @POST("golden/api/web/v1/site/activate")
    Flowable<ActivateUserResponse> getActivateUser(@Field("mobile") String mobile,
                                                   @Field("activation_code") String activation_code);

    @GET("golden/api/web/v1/site/term")
    Single<ResponseTerms> getTermsAndConditions();

    @GET("golden/api/web/v1/site/about")
    Single<AboutResponse> getAboutUs();

    @GET("golden/api/web/v1/site/ads")
    Single<AdsResponse> getAds();


    @FormUrlEncoded
    @POST("golden/api/web/v1/product/search")
    Flowable<ProductResponse> getProductsBySearch(@Field("title") String title,
                                                  @Field("branch_id") long branch_id,
                                                  @Query("page") long page);
//    @FormUrlEncoded
//    @POST("golden/api/web/v1/product/search")
//    Flowable<ProductResponse> getProductsBySearch(
//            @Field("title") String title,
//            @Field("branch_id") int branch_id,
//            @Query("page") int page,
//            @Query("access-token") String token
//    );

//    @GET("golden/api/web/v1/product/category")
//    Flowable<CategoryResponse> getCategories();

    @GET("golden/api/web/v1/product/category")
    Flowable<CategoryResponse> getCategories(@Query("page") long page);

    @GET("golden/api/web/v1/site/slider")
    Flowable<HomeSliderResponse> getHomeSlider();

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/contact")
    Flowable<SimpleResponse> contact(@Field("subject") String subject,
                                     @Field("email") String email,
                                     @Field("body") String body);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/sub-category")
    Flowable<CategoryResponse> getSubCategories(@Field("category_id") long category_id,
                                                @Query("page") long page);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/category-product")
    Flowable<ProductResponse> getProductsBySubCategories(@Field("category_id") long category_id,
                                                         @Field("branch_id") int branch_id);
//    @FormUrlEncoded
//    @POST("golden/api/web/v1/product/category-product")
//    Flowable<ProductResponse> getProductsBySubCategories(@Field("category_id") int category_id,
//                                                         @Field("branch_id") int branch_id,
//                                                         @Query("page") int page,
//                                                         @Query("access-token") String access_token);

    @GET("golden/api/web/v1/product/favorite")
    Flowable<FavResponse> getProductsFavs(@Query("page") long page,
                                          @Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/addto-favorite")
    Flowable<SimpleResponse> addProductsToFavs(@Query("access-token") String access_token,
                                               @Field("id") long id);

    @Headers("Content-Type: multipart/form-data")
    @Multipart
    @POST("golden/api/web/v1/product/favorite-delete")
    Flowable<SimpleResponse> deleteProductFromFav(@Query("access-token") String access_token,
                                                  @Part("id") long id);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/view-order")
    Flowable<OrderDetailResponse> getOrderById(@Field("id") long id,
                                               @Query("access-token") @NotNull String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/order-delete")
    Flowable<SimpleResponse> cancelOrder(@Field("id") long id,
                                         @Query("access-token") String access_token);


    @GET("golden/api/web/v1/product/order")
    Flowable<OrderResponse> getOrders(@Query("page") long page,
                                      @Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/comment")
    Flowable<SimpleResponse> addComment(@Field("product_id") long product_id,
                                        @Field("comment") String comment,
                                        @Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/new")
    Flowable<ProductResponse> getNewProducts(@Field("branch_id") long branch_id,
                                             @Query("page") int page);

//    @FormUrlEncoded
//    @POST("golden/api/web/v1/product/new")
//    Flowable<ProductResponse> getNewProducts(@Field("branch_id") int branch_id,
//                                             @Query("page") int page,
//                                             @Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/offer")
    Flowable<ProductResponse> getOffersProducts(@Field("branch_id") long branch_id,
                                                @Query("page") int page);

//    @FormUrlEncoded
//    @POST("golden/api/web/v1/product/offer")
//    Flowable<ProductResponse> getOffersProducts(@Field("branch_id") int branch_id,
//                                                @Query("page") int page,
//                                                @Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/best-seller")
    Flowable<ProductResponse> getMostWantedProducts(@Field("branch_id") int branch_id);
//    @FormUrlEncoded
//    @POST("golden/api/web/v1/product/best-seller")
//    Flowable<ProductResponse> getMostWantedProducts(@Field("branch_id") int branch_id,
//                                                    @Query("page") int page,
//                                                    @Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/product-view")
    Flowable<ProductDetailResponse> getProductDetail(@Field("id") long id);


    @FormUrlEncoded
    @POST("golden/api/web/v1/product/rate")
    Flowable<SimpleResponse> addRate(@Query("access-token") String access_token,
                                     @Field("product_id") long product_id,
                                     @Field("rate") long rate);

    @FormUrlEncoded
    @POST("golden/api/web/v1/site/branchinregion")
    Flowable<BranchesResponse> getBranchesByCity(@Field("id") int id);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/checkout")
    Flowable<CheckoutResponse> checkout(@Query("access-token") String access_token, @Field("name") String name,
                                        @Field("country") String country,
                                        @Field("city") String city,
                                        @Field("region") String region,
                                        @Field("address") String address,
                                        @Field("mobile") String mobile,
                                        @Field("description") String description,
                                        @Field("status") int status,
                                        @Field("order_time") String order_time,
                                        @Field("branch_id") String branch_id,
                                        @Field("delivery_status") int delivery_status,
                                        @Field("totalprice") double totalprice,
                                        @Field("totalquantity") int totalquantity,
                                        @Field("number_of_items") int number_of_items,
                                        @Field("product_ids") String product_ids,
                                        @Field("product_name") String product_name,
                                        @Field("product_price") String product_price,
                                        @Field("product_quantity") String product_quantity,
                                        @Field("product_extension") String product_extension,
                                        @Field("product_size") String product_size,
                                        @Field("product_rice") String product_rice,
                                        @Field("product_dish") String product_dish,
                                        @Field("product_totalPrice") String product_totalPrice,
                                        @Field("lat") String lat,
                                        @Field("lang") String lang,
                                        @Field("copun") String copun);

    @GET("golden/api/web/v1/list")
    Flowable<ListResponse> getCities();

    @GET("golden/api/web/v1/product/order-limit")
    Flowable<LimitResponse> getLimitMinOrderValue();

    @GET("golden/api/web/v1/list/card-type")
    Flowable<ResponseCardType> getCreditCardType();

    @GET("golden/api/web/v1/product/card")
    Flowable<CardResponse> getCreditCard(@Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/add-card")
    Flowable<SimpleResponse> addCreditCard(@Query("access-token") String access_token,
                                           @Field("name") String name,
                                           @Field("card_number") String card_number,
                                           @Field("expired") String expired,
                                           @Field("type") String type);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/delete-card")
    Flowable<SimpleResponse> deleteCreditCard(@Query("access-token") String access_token,
                                              @Field("id") long id);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/update-card")
    Flowable<SimpleResponse> updateCreditCardAddress(@Query("access-token") String access_token,
                                                     @Field("id") long id,
                                                     @Field("name") String name,
                                                     @Field("card_number") long card_number,
                                                     @Field("expired") String expired);

    @GET("golden/api/web/v1/product/address")
    Flowable<AddressResponse> getAddress(@Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/add-address")
    Flowable<SimpleResponse> addAddress(@Query("access-token") String access_token,
                                        @Field("name") String name,
                                        @Field("address") String address,
                                        @Field("state") String state,
                                        @Field("late") long late,
                                        @Field("lang") long lang,
                                        @Field("is_default") long is_default,
                                        @Field("map_location") String map_location);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/update-address")
    Flowable<SimpleResponse> updateAddress(@Query("access-token") String access_token,
                                           @Field("id") long id,
                                           @Field("name") String name,
                                           @Field("address") String address,
                                           @Field("city") String city,
                                           @Field("state") String state,
                                           @Field("late") String late,
                                           @Field("lang") String lang,
                                           @Field("is_default") long is_default,
                                           @Field("map_location") String map_location);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/delete-address")
    Flowable<SimpleResponse> deleteAddress(@Query("access-token") String access_token,
                                           @Field("id") long id);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/check-code")
    Flowable<ResponseDiscounteCode> checkCode(@Query("access-token") String access_token,
                                              @Field("code") String code);


    @GET("golden/api/web/v1/product/rate-question")
    Flowable<QustionsModel> rateQuestion(@Query("access-token") String access_token);

    @FormUrlEncoded
    @POST("golden/api/web/v1/product/order-rate")
    Flowable<SimpleResponse> rateAnswer(@Query("access-token") String access_token
            , @Field("order_id") String order_id
            , @Field("driver_id") String driver_id
            , @Field("question_id") String question_id
            , @Field("rate") String rate
            , @Field("type") String type);


}