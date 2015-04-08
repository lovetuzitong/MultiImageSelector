# MultiImageSelector
仿微信实现多图选择。支持单选和多选两张模式

[English Doc](README.md)

###截图
![Example1](art/example_1.png) ![Select1](art/select_1.png) ![Select2](art/select_2.png) ![Select3](art/select_3.png)

-------------------

###快速开始
* 第0步
把模块 `multi-image-selector` 作为你的项目依赖添加到工程中.

* 第1步 
在你的 `AndroidManifest.xml` 文件中添加权限 `android.permission.READ_EXTERNAL_STORAGE`.
别忘了同时在 `AndroidManifest.xml` 中声明 `MultiImageSelectorActivity` 这个Activity.

* 第2步
代码中调用，例如:
``` java
Intent intent = new Intent(mContext, MultiImageSelectorActivity.class);

// 是否显示调用相机拍照
intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);

// 最大图片选择数量
intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, 9);

// 设置模式 (支持 单选/MultiImageSelectorActivity.MODE_SINGLE 或者 多选/MultiImageSelectorActivity.MODE_MULTI)
intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, MultiImageSelectorActivity.MODE_MULTI);

startActivityForResult(intent, REQUEST_IMAGE);
```

* 第3步
在你的 `onActivityResult` 方法中接受结果. 例如:
```java
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode == REQUEST_IMAGE){
        if(resultCode == RESULT_OK){
            // 获取返回的图片列表
            List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
            // 处理你自己的逻辑 ....
        }
    }
}
```

* 第4步
没第4步了，就这样就OK啦~ :)

-------------------

###自定义显示
* 自定义Activity
``` java
class CustomerActivity extends Activity implements MultiImageSelectorFragment.Callback{
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		// 你自己的逻辑...
        Bundle bundle = new Bundle();
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_COUNT, mDefaultCount);
        bundle.putInt(MultiImageSelectorFragment.EXTRA_SELECT_MODE, mode);
        <bundle class="putBo"></bundle>olean(MultiImageSelectorFragment.EXTRA_SHOW_CAMERA, isShow);
        // 添加主Fragment到Activity
        getSupportFragmentManager().beginTransaction()
                .add(R.id.image_grid, Fragment.instantiate(this, MultiImageSelectorFragment.class.getName(), bundle))
                .commit();
	}
	@Override
    public void onSingleImageSelected(String path) {
        // 当选择模式设定为 单选/MODE_SINGLE, 这个方法就会接受到Fragment返回的数据
    }

    @Override
    public void onImageSelected(String path) {
        // 一个图片被选择是触发，这里可以自定义的自己的Actionbar行为
    }

    @Override
    public void onImageUnselected(String path) {
        // 一个图片被反选是触发，这里可以自定义的自己的Actionbar行为
    }

    @Override
    public void onCameraShot(File imageFile) {
        // 当设置了使用摄像头，用户拍照后会返回照片文件
    }
}
```
* 具体可以参考`MultiImageSelectorActivity.java`的实现

-------------------

###感谢

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