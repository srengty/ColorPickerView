## Gradle
Add below codes to your root build.gradle file (not your module build.gradle file).


```gradle
allprojects {
    repositories {
        jcenter()
    }
}
```


And add a dependency code to your module's build.gradle file.

```gradle
dependencies {
    implementation "com.github.srengty:colorpickerview:1.0"
}
```
