# Smoothy

Builder Bundle for activities, fragments, services for Android

# Example

Field binding for Android bundle which uses annotation processing (@BindExtra).

Smoothy is inspired of [Butterknife][0] By [@JakeWharton][1]

```java
class SecondActivity extends Activity {
  @BindExtra String username;
  @BindExtra int count;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    SmoothyBundle.bind(this);
    // TODO Use fields...
  }
}
```

# Used generated Builder 

```java
class FirstActivity extends Activity {

  public void displaySecondActivity() {
    // SecondActivityBuilder is generated by Smoothy with @BindExtra
    
    Intent intent = new SecondActivityBuilder()
                .username("mslimani")
                .count(10000)
                .build(this);

    startActivity(intent);
  }
  
}
```

Next Steps for v1.0.5
--------
- Add optional fields
- Add default value
- Add @BindExtra for others types : SparseArray, List<String>, BooleanArray, ByteArray, CharArray, DoubleArray, IntArray, StringArray, IBinder


Download
--------

Download via Gradle:

```groovy
buildscript {
  dependencies {
    classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
  }
}

apply plugin: 'com.neenbedankt.android-apt'

dependencies {
  compile 'com.github.mslimani:smoothy:1.0.4'
  apt 'com.github.mslimani:smoothy-compiler:1.0.4'
}
```

License
-------

    Copyright 2015 Mehdi Slimani

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
[0]: http://jakewharton.github.com/butterknife/    
[1]: https://github.com/JakeWharton
