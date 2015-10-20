# smoothy
Builder Bundle activities, fragments, services for Android

# déclaration

```java
class Example2Activity extends Activity {
  @BindExtra String username;
  @BindExtra int years;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.simple_activity);
    SmoothyBundle.bind(this);
    // TODO Use fields...
  }
}
```

# utilisation

```java
class Example1Activity extends Activity {

  public void displayExample2() {
    Intent detailIntent = new DetailActivityBuilder()
                .username("mslimani")
                .years(10000)
                .build(this);

    startActivity(detailIntent);
  }
  
}
```

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
  compile 'mslimani:smoothy:1.0.0'
  apt 'mslimani:smoothy-compiler:1.0.0'
}
```

License
-------

    Copyright 2013 Mehdi Slimani

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
