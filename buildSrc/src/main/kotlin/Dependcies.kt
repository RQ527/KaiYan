import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/**
 * ...
 * @author 1799796122 (Ran Sixiang)
 * @email 1799796122@qq.com
 * @date 2022/7/14
 * @Description:            虽然不知道为啥报错，但是不影响运行
 */
fun Project.dependAndroid(){
    dependencies {
        "api"(BuildConfig.appcompat)
        "api"(BuildConfig.ktx)
        "api"(BuildConfig.livedata)
        "api"(BuildConfig.viewmodel)
        "api"(BuildConfig.constraintlayout)
        "api"(BuildConfig.material)
    }
}
fun Project.dependNetWork(){
    dependencies {
        "implementation"(BuildConfig.retrofitGson)
        "implementation"(BuildConfig.retrofit)
        "implementation"(BuildConfig.okhttpInter)
    }
}
fun Project.dependCoroutines(){
    dependencies {
        "implementation"(BuildConfig.coroutinesCore)
        "implementation"(BuildConfig.coroutines)
    }
}
fun Project.dependGlide(){
    dependencies {
        "implementation"(BuildConfig.glide)
        "annotationProcessor"(BuildConfig.glideAnnotation)
    }
}