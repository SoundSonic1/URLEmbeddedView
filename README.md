# URLEmbeddedView

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://facebook.com/nguyencse)

    URLEmbeddedView is a Android library which allows you show preview data of your URL.

### You can also:
    Pass URL to view and you can load some data of this URL to preview.
    And of course URLEmbeddedView itself is open source forever.

### Requirements
   - OS: Android
   - minSdkVersion: 15

### Installation
Just add the dependency to your `build.gradle`:

```groovy
dependencies {
    implementation 'com.github.SoundSonic1:urlembeddedview:-SNAPSHOT'
}
```

And DO NOT forget to add internet permission in manifest if already not present

```groovy
    <uses-permission android:name="android.permission.INTERNET" />
```

### Usage
   In xml file:
```groovy
   <com.nguyencse.URLEmbeddedView
        android:id="@+id/uev"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:title="Title"
        app:description="Description"
        app:host="abc.com"
        app:favor="@drawable/ic_url"
        app:thumbnail="@drawable/ic_url"/>
 ```
  In code:

```groovy
   URLEmbeddedView urlEmbeddedView = findViewById(R.id.uev);
   
   urlEmbeddedView.setURL("stackoverflow.com", new URLEmbeddedView.OnLoadURLListener() {
       @Override
       public void onLoadURLCompleted(URLEmbeddedData data) {
           urlEmbeddedView.title(data.getTitle());
           urlEmbeddedView.description(data.getDescription());
           urlEmbeddedView.host(data.getHost());
           urlEmbeddedView.thumbnail(data.getThumbnailURL());
           urlEmbeddedView.favor(data.getFavorURL());
       }
   });
```
  Or you can use your layout to custom it:
  
```groovy
   URLEmbeddedTask urlTask = new URLEmbeddedTask(new URLEmbeddedTask.OnLoadURLListener() {
       @Override
       public void onLoadURLCompleted(URLEmbeddedData data) {
           // handle your code here
       }
   });
   urlTask.execute(url);
```

![Demo](screenshots/stackoverflow.png)
