package com.education.schoolapp

import com.education.schoolapp.API.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


class Services
{

//    http://skoolstarr.com/sspanel/API/holiday/parentlogin?mobno=9636045430&password=1234&remember_token=test123
    val baseurl="http://skoolstarr.com/sspanel/API/holiday/"
    public final val loginurl="parentlogin"

    var dashbord_menu:Load_DashBord_Menu? = null
    fun get_dashbord_menu():Load_DashBord_Menu? {
        if (dashbord_menu == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            dashbord_menu = retrofit.create(Load_DashBord_Menu::class.java)
        }
        return dashbord_menu
    }

    var removeFromCart:Login_api? = null
    fun getServices():Login_api? {
        if (removeFromCart == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            removeFromCart = retrofit.create(Login_api::class.java)
        }
        return removeFromCart
    }

    var loadUserlist:Load_UserList? = null
    fun getUserliost():Load_UserList? {
        if (loadUserlist == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            loadUserlist = retrofit.create(Load_UserList::class.java)
        }
        return loadUserlist
    }

    var accountinfo:Account? = null
    fun getAccountinformation():Account? {
        if (accountinfo == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            accountinfo = retrofit.create(Account::class.java)
        }
        return accountinfo
    }


    var data:updatedata? = null
    fun getaccountupdate():updatedata? {
        if (data == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            data = retrofit.create(updatedata::class.java)
        }
        return data
    }


//http://skoolstarr.com/sspanel/API/holiday/liststudent?mobno=9636045430&school_session=2019-2020
    interface Login_api {
        @GET("parentlogin?")
        fun
                getProducts(
            @Query("mobno") one: String?,
            @Query("password") two: String?,
            @Query("remember_token") key: String?
        ): Call<List<UserLogin>>//Call<UserLogin?>?
    }
//    http://skoolstarr.com/sspanel/API/holiday/readmovements?school_id=28
    interface Load_DashBord_Menu {
        @GET("readmovements?")
        fun
                getmenu(
            @Query("school_id") school_id: String?
        ): Call<List<MomentApi>>//Call<UserLogin?>?
    }
//    http://skoolstarr.com/sspanel/API/holiday/liststudent?mobno=9636045430&school_session=2019-2020
    interface Load_UserList {
        @GET("liststudent?")
        fun getUserlist(
            @Query("mobno") mobno: String?,
            @Query("school_session") school_session: String?
        ): Call<List<User_List>>//Call<UserLogin?>?
    }
    interface Account {
        @GET("profile?")
        fun getAccountinfo(
            @Query("school_id") school_id: String?,
            @Query("userid") userid: String?
        ): Call<List<Accountapi>>//Call<UserLogin?>?
    }
    //http://skoolstarr.com/sspanel/API/holiday/profileupdate?school_id=32&userid=42920&address=jaipur&file=/upload/profile/test.jpg
    interface updatedata {
        @Multipart
        @POST("profileupdate?")
        fun account(
            @Part("school_id") school_id: RequestBody?,@Part("userid") userid: RequestBody?,@Part("address") address: RequestBody?, @Part pro_image: MultipartBody.Part?
        ): Call<Profileupdateapi>
    }
}