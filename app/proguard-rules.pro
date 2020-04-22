# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#-keep class com.nencer.nencerwallet.service.payment.response.HistoryResponse
#-keep class com.nencer.nencerwallet.service.payment.response.HistoryOrder
#-keep class com.nencer.nencerwallet.service.payment.response.Info
#
#-keep class com.nencer.nencerwallet.service.payment.response.PaymentResponse
#-keep class com.nencer.nencerwallet.service.payment.response.CardList
#-keep class com.nencer.nencerwallet.service.payment.response.Card
#-keep class com.nencer.nencerwallet.ui.OrderData
#-keep class com.nencer.nencerwallet.ui.CardInfo
#
#-keep com.nencer.nencerwallet.service.wallet.response.HistoryDepositResponse
#-keep com.nencer.nencerwallet.service.wallet.response.HistoryInfo
#-keep com.nencer.nencerwallet.service.wallet.response.BankInfo
#-keep com.nencer.nencerwallet.service.wallet.response.PayerInfo
#-keep com.nencer.nencerwallet.service.wallet.response.PayeeInfo
#-keep om.nencer.nencerwallet.service.exchange.response.HistoryChargingResponse
#-keep om.nencer.nencerwallet.service.exchange.response.HistoryChargingInfo

-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-dontwarn sun.misc.**
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class om.nencer.nencerwallet.service.wallet.response.** { <fields>; }
-keep class com.nencer.nencerwallet.service.payment.response.** { <fields>; }
-keep class com.nencer.nencerwallet.service.exchange.response..** { <fields>; }
-keep class com.nencer.nencerwallet.ui.** { <fields>; }
-keep class com.nencer.nencerwallet.service.info.model.** { <fields>; }


# Prevent proguard from stripping interface information from TypeAdapter, TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}





