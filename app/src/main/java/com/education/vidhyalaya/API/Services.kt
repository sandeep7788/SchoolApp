package com.education.vidhyalaya.API

import com.education.vidhyalaya.model.AnswerSheetModel
import com.education.vidhyalaya.model.HomeWorkModel
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit


class Services {
    //    vidhyalaya.co.in
//    http://vidhyalaya.co.in/sspanel/API/holiday/parentlogin?mobno=9636045430&password=1234&remember_token=test123
//    val baseurl="http://vidhyalaya.co.in/sspanel/API/holiday/"
    val baseurl = "http://vidhyalaya.co.in/sspanel/API/holiday/"
    val loginurl = "parentlogin"

    var okHttpClient = OkHttpClient().newBuilder()
        .connectTimeout(40, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    var dashbord_menu: Load_DashBord_Menu? = null
    fun get_dashbord_menu(): Load_DashBord_Menu? {
        if (dashbord_menu == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            dashbord_menu = retrofit.create(Load_DashBord_Menu::class.java)
        }
        return dashbord_menu
    }

    var removeFromCart: Login_api? = null
    fun getServices(): Login_api? {
        if (removeFromCart == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            removeFromCart = retrofit.create(Login_api::class.java)
        }
        return removeFromCart
    }

    var loadUserlist: Load_UserList? = null
    fun getUserliost(): Load_UserList? {
        if (loadUserlist == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            loadUserlist = retrofit.create(Load_UserList::class.java)
        }
        return loadUserlist
    }

    var accountinfo: Account? = null
    fun getAccountinformation(): Account? {
        if (accountinfo == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            accountinfo = retrofit.create(Account::class.java)
        }
        return accountinfo
    }


    var data: updatedata? = null
    fun getaccountupdate(): updatedata? {
        if (data == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            data = retrofit.create(updatedata::class.java)
        }
        return data
    }

    var notifaction: Notifaction? = null
    fun getNotifactionservice(): Notifaction? {
        if (notifaction == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            notifaction = retrofit.create(Notifaction::class.java)
        }
        return notifaction
    }

    var homework: Homework? = null
    fun gethomeworkservices(): Homework? {
        if (homework == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            homework = retrofit.create(Homework::class.java)
        }
        return homework
    }

    var holiday: Holiday? = null
    fun getholidayservices(): Holiday? {
        if (holiday == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://vidhyalaya.co.in/sspanel/API/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            holiday = retrofit.create(Holiday::class.java)
        }
        return holiday
    }

    var answersheet: Answersheet? = null
    fun getanswersheetservices(): Answersheet? {
        if (answersheet == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            answersheet = retrofit.create(Answersheet::class.java)
        }
        return answersheet
    }

    var attendanceList: Atendence? = null
    fun getAttendenceList(): Atendence? {
        if (attendanceList == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            attendanceList = retrofit.create(Atendence::class.java)
        }
        return attendanceList
    }

    var leave: Leave? = null
    fun getLeaveServices(): Leave? {
        if (leave == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            leave = retrofit.create(Leave::class.java)
        }
        return leave
    }

    var changePassword: ChangePassword? = null
    fun getchange_password(): ChangePassword? {
        if (changePassword == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            changePassword = retrofit.create(ChangePassword::class.java)
        }
        return changePassword
    }

    var feesdet: Feedet? = null
    fun getfee_details(): Feedet? {
        if (feesdet == null) {
            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseurl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            feesdet = retrofit.create(Feedet::class.java)
        }
        return feesdet
    }

    interface Login_api {
        @GET("parentlogin?")
        fun
                getProducts(
            @Query("mobno") one: String?,
            @Query("password") two: String?,
            @Query("remember_token") key: String?
        ): Call<List<UserLogin>>//Call<UserLogin?>?
    }

    interface Load_DashBord_Menu {
        @GET("readmovements?")
        fun
                getmenu(
            @Query("school_id") school_id: String?
        ): Call<List<MomentApi>>//Call<UserLogin?>?
    }

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

    interface updatedata {
        @Multipart
        @POST("profileupdate?")
        fun account(
            @Part("school_id") school_id: RequestBody?,
            @Part("userid") userid: RequestBody?,
            @Part("address") address: RequestBody?,
            @Part pro_image: MultipartBody.Part?
        ): Call<Profileupdateapi>
    }

    //school_id=28&stdid=43344&classid=720&section=0
    interface Notifaction {
        @GET("readmsg?")
        fun getNotifaction(
            @Query("school_id") school_id: String?,
            @Query("stdid") stdid: String?,
            @Query("classid") classid: String?,
            @Query("section") section: String?
        ): Call<List<Notifactionpojo>>//Call<UserLogin?>?
    }
//    http://vidhyalaya.co.in/sspanel/API/holiday/homework?school_id=28&stdid=43344&classid=720&section=0

    interface Homework {
        @GET("homework?")
        fun gethomework(
            @Query("school_id") school_id: Int?,
            @Query("stdid") stdid: Int?,
            @Query("classid") classid: Int?,
            @Query("section") section: Int?
        ): Call<List<HomeWorkModel>>//Call<UserLogin?>?
    }

    //    http://www.skoolstarr.com/sspanel/API/holiday?school_id=28
    interface Holiday {
        @GET("holiday?")
        fun getholiday(
            @Query("school_id") school_id: String?
        ): Call<List<Holadaypojo>>//Call<UserLogin?>?
    }

    //http://vidhyalaya.co.in/sspanel/API/holiday/answersheet?school_id=32&studentid=43344
    interface Answersheet {
        @GET("answersheet?")
        fun getanswersheet(
            @Query("school_id") school_id: String?,
            @Query("studentid") studentid: String?
        ): Call<List<AnswerSheetModel>>//Call<UserLogin?>?
    }

    interface Services {
        @GET("files/Node-Android-Chat.zip")
        @Streaming
        fun downloadFile(): Call<ResponseBody?>?
    }

    interface Leave {
        @FormUrlEncoded
        @POST("insertleaverequest")
        fun getLandingPageReport(
            @Field("school_id") school_id: String?,
            @Field("student_id") student_id: String?,
            @Field("reason") reason: String?,
            @Field("description") description: String?,
            @Field("start_date") start_date: String?,
            @Field("end_date") end_date: String?
        )
                : Call<Profileapi>?
    }

    interface Atendence {
        @GET("studentattendance?")
        fun get_atendenceList(
            @Query("school_id") school_id: Int?,
            @Query("stdid") stdid: Int?,
            @Query("month") month: String,
            @Query("year") year: String
        ): Call<Attendentapi>
    }

    interface ChangePassword {
        @GET("passwordchange?")
        fun getchancepasswordservices(
            @Query("userid") userid: String?,
            @Query("old_password") old_password: String?,
            @Query("new_password") new_password: String?
        ): Call<Changepasswordapi>
    }

    interface Feedet {
        @GET("feedet?")
        fun getfeedetails(
            @Query("school_id") school_id: String?,
            @Query("stdid") stdid: String?
        ): Call<List<Feesdet>>
    }
}