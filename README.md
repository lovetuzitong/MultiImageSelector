# MultiImageSelector
Image selector for Android device. Support single choice and multi-choice.

[![](https://jitpack.io/v/lovetuzitong/MultiImageSelector.svg)](https://jitpack.io/#lovetuzitong/MultiImageSelector)

[中文文档](README_zh.md)

###ART
![Example1](art/example_1.png) ![Select1](art/select_1.png) ![Select2](art/select_2.png) ![Select3](art/select_3.png)

-------------------

###Run Demo

>./gradlew installDebug

###Quick Start
* Step 0
Add module `multi-image-selector` as your dependence. in your `build.gradle` :
```java
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    compile 'com.github.lovetuzitong:MultiImageSelector:1.2'
}
```

* Step 1 
Set your `AndroidManifest.xml` as below:
```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

<application

    ...

    <!--Image Selector Entry-->
    <activity
        android:configChanges="orientation|screenSize"
        android:name="me.nereo.multi_image_selector.MultiImageSelectorActivity" />
</application>
```

* Step 2
Call image selector simplest in your code, eg. ( From `version-1.1` )

``` java
// Multi image selector form an Activity
MultiImageSelector.create(Context)
        .start(Activity, REQUEST_IMAGE);
```

Detail Api.
``` java
MultiImageSelector.create(Context)
        .showCamera(boolean) // show camera or not. true by default
        .count(int) // max select image size, 9 by default. used width #.multi()
        .single() // single mode
        .multi() // multi mode, default mode;
        .origin(ArrayList<String>) // original select data set, used width #.multi()
        .start(Activity/Fragment, REQUEST_IMAGE);
```

Also support traditional `Intent` :
``` java
Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);
// whether show camera
intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
// max select image amount
intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);
// select mode (MultiImageSelectorActivity.MODE_SINGLE OR MultiImageSelectorActivity.MODE_MULTI)
intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);
// default select images (support array list)
intent.putStringArrayListExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, defaultDataArray);
startActivityForResult(intent, REQUEST_IMAGE);
```

* Step 3
Receive result in your `onActivityResult` Method. eg.
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == REQUEST_IMAGE){
        if(resultCode == RESULT_OK){
	        // Get the result list of select image paths
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            // do your logic ....
        }
    }
}
```

* Step 4
No more steps, just enjoy. :)

-------------------

###Custom Activity Style
* Custome your own Activity
```java
class CustomerActivity extends Activity implements MultiImageSelectorFragment.Callback{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		// customer logic here...
		Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        bundle.putBoolean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        // Add fragment to your Activity
        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();
	}
	@Override
    public void onSingleImageSelected(String path) {
        // When select mode set to MODE_SINGLE, this method will received result from fragment
    }

    @Override
    public void onImageSelected(String path) {
        // You can specify your ActionBar behavior here 
    }

    @Override
    public void onImageUnselected(String path) {
        // You can specify your ActionBar behavior here 
    }

    @Override
    public void onCameraShot(File imageFile) {
        // When user take phone by camera, this method will be called.
    }
}
```
* Take a glance of `MultiImageSelectorActivity.java`

-------------------

###Change Log

* 2016-5-18
    1. Added. `JitPack` support
    2. Added. Convenient way to call image selector. See `Step 2`
    3. Fixed. Some NPE.

* 2016-1-19
    1. Fixed. cannot load some 0-size image
    2. Added. When take a new photo, notify media scanner
    3. Fixed. Can't take photo on RED-MI
    4. Fixed. Performance when show Camera-Icon

* 2015-5-5
    1. Fixed. Can't display some images. (Issue by[sd6352051](https://github.com/sd6352051), [larry](https://github.com/18611480882))
    2. Fixed. `ListPopupWindow` can not fill parent
    3. Added. Add checked mask.

* 2015-4-16
    1. Fixed. Crack when rotate device. (Issue by [@Leminity](https://github.com/Leminity))
    2. Fixed. PopupListView position error. (Issue by [@Slock](https://github.com/Slock))
    3. Change. Demo application shortcut.
    4. Change. Readme file.

* 2015-4-9
    1. Fixed. When set `EXTRA_SHOW_CAMERA` to `true`, the first grid item onclick event were messed.
    2. Add. Support initial selected image list.

-------------------

###Thanks

* [square-picasso](https://github.com/square/picasso) A powerful image downloading and caching library for Android 

-------------------

###License
>The MIT License (MIT)

>Copyright (c) 2015 Nereo

>Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

>The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.