package dependencies

object Dependency {
}

object DI{

    object Hilt{
        private const val version = "2.38.1"
        const val classpath = "com.google.dagger:hilt-android-gradle-plugin:$version"

        const val kapt = "com.google.dagger:hilt-compiler:$version"
        const val api = "com.google.dagger:hilt-android:$version"

        const val test = "com.google.dagger:hilt-android-testing:$version"
        const val annotation = "com.google.dagger:hilt-compiler:$version"
    }
}

object Gson{
    private const val version = "2.8.9"
    const val api = "com.google.code.gson:gson:$version"
}

object WebSocket{
    private const val version = "4.9.0"
    const val api = "com.squareup.okhttp3:okhttp:$version"
}