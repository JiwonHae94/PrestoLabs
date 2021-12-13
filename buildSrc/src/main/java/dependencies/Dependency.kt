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


object Ktx {
    const val activity = "androidx.activity:activity-ktx:1.1.0"
    const val fragment = "androidx.fragment:fragment-ktx:1.3.5"
}

object Gson{
    private const val version = "2.8.9"
    const val api = "com.google.code.gson:gson:$version"
}

object WebSocket{
    private const val version = "4.9.0"
    const val api = "com.squareup.okhttp3:okhttp:$version"
}


object UnitTest{
    private const val truthVersion = "1.1.3"
    const val googleTruth = "com.google.truth:truth:$truthVersion"

    private const val junitVersion = "4.+"
    const val junit = "junit:junit:$junitVersion"

    const val robolectric = "org.robolectric:robolectric:4.4"

    //For runBlockingTest, CoroutineDispatcher etc.
    private const val coroutineVersion = "1.4.2"
    const val coroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
}