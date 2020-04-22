package com.nencer.thesieure.di

import android.content.Intent
import android.util.Log
import com.nencer.thesieure.BuildConfig
import com.nencer.thesieure.data.http.interceptor.AuthorizationInterceptor
import com.nencer.thesieure.service.exchange.ExchangeViewModel
import com.nencer.thesieure.service.exchange.api.ExchangeApi
import com.nencer.thesieure.service.exchange.repository.ExchangeRepository
import com.nencer.thesieure.service.info.InfoViewModel
import com.nencer.thesieure.service.info.api.InfoApi
import com.nencer.thesieure.service.info.repository.InfoRepository
import com.nencer.thesieure.service.payment.PayViewModel
import com.nencer.thesieure.service.payment.api.PaymentApi
import com.nencer.thesieure.service.payment.repository.PaymentRepository
import com.nencer.thesieure.service.topup.TopupViewModel
import com.nencer.thesieure.service.topup.api.TopupApi
import com.nencer.thesieure.service.topup.repository.TopupRepository
import com.nencer.thesieure.service.user.api.UserApi
import com.nencer.thesieure.service.user.repository.UserRepository
import com.nencer.thesieure.service.user.viewmodel.UserViewModel
import com.nencer.thesieure.service.wallet.WalletViewModel
import com.nencer.thesieure.service.wallet.api.WalletApi
import com.nencer.thesieure.service.wallet.repository.WalletRepository
import com.nencer.thesieure.ui.main.MainActivity
import com.nencer.thesieure.ui.user.SignInActivity
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
const val BASE_URL = "https://thesieure.com/"
private const val TIME_OUT = 30L
private const val TAG = "fmt"

val viewModelModule = module {
    viewModel { UserViewModel(get()) }
    viewModel { WalletViewModel(get()) }
    viewModel { PayViewModel(get()) }
    viewModel { InfoViewModel(get()) }
    viewModel { ExchangeViewModel(get()) }
    viewModel { TopupViewModel(get()) }


}

val reposModule = module {
    factory { UserRepository(get()) }
    factory { PaymentRepository(get()) }
    factory { WalletRepository(get()) }
    factory { InfoRepository(get()) }
    factory { ExchangeRepository(get()) }
    factory { TopupRepository(get()) }



}

val remoteModule = module {

    single {
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Log.e(TAG, message)
            }
        })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpLoggingInterceptor
    }

    single {
        val builder = OkHttpClient.Builder()
            .addInterceptor(AuthorizationInterceptor())
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)

            .addInterceptor(object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    val request = chain.request()
                    val response = chain.proceed(request)
                    when (response.code) {
                        // case error here logout
                        401 -> {

                            if (MainActivity.instance != null && response.code == 401) {
                                val intent = Intent(MainActivity.instance!!.applicationContext, SignInActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                MainActivity.instance!!.startActivity(intent)
                            }
                        }
                    }
                    return response
                }
            })
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(get<HttpLoggingInterceptor>())
        }
        builder.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .baseUrl(BASE_URL)
            .build()
    }

    single<UserApi> { get<Retrofit>().create(UserApi::class.java) }
    single<PaymentApi> { get<Retrofit>().create(PaymentApi::class.java) }
    single<WalletApi> { get<Retrofit>().create(WalletApi::class.java) }
    single<InfoApi> { get<Retrofit>().create(InfoApi::class.java) }
    single<ExchangeApi> { get<Retrofit>().create(ExchangeApi::class.java) }
    single<TopupApi> { get<Retrofit>().create(TopupApi::class.java) }



}

val localModule = module {
    /*single {
        Room.databaseBuilder(AppContext, AppDataBase::class.java, DB_NAME).build()
    }
    single { get<AppDataBase>().getUserDao() }*/
}

val appModule = listOf(viewModelModule, reposModule, remoteModule, localModule)