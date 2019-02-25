##### setResult和finish的顺序关系
1.  startActivityForResult对应setResult。
2.  界面A跳到界面B，若界面B需要手动触发才需要返回值，这时候先setResult，然后Finish当前页面就可以了(只有当前Activity被Finish，结果才会被发送给parent的setActivityResult处理)。
3.  按BACK键从一个Activity退出来的，，android就会自动调用Activity的finish()方法，然后设置resultCode为RESULT_CANCELED，也就不会返回任何数据了。解决方法就是在Activity里面捕获按BACK的事件，捕获到之后先setResult，然后自己来调用finish。
3.  如果界面B是列表，一组数据需要处理，这时候只能在页面关闭的时候setResult时，直接重写Finish(setResult一定要在super.finish之前)。
```
 @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra("key","123");
        setResult(RESULT_OK,intent);
        super.finish();
    }
```
##### onSaveInstanceState()和onRestoreInstanceState()
1.  Activity非正常退出（屏幕方向切换,内存低）而被破坏，系统还是会记住它的存在，如果用户导航回到它，系统会创建新实例的Activity使用一组保存的数据来描述Activity在被销毁时的状态。系统用于恢复以前状态的已保存数据称为“实例状态”，是存储在Bundle对象中的键值对的集合。
2.  如果是用户自动按下返回键，或程序调用finish()退出程序，属于正常退出，是不会触发onSaveInstanceState()和onRestoreInstanceState()的。
3.  每次用户旋转屏幕时，Activity将被破坏并重新创建。当屏幕改变方向时，系统会破坏并重新创建前台Activity，因为屏幕配置已更改的Activity可能需要加载替代资源（例如布局）。即会执行onSaveInstanceState()和onRestoreInstanceState()的。
5.  如果系统在被销毁之后必须重新创建Activity实例，它会将相同的Bundle对象传递给Activity的onRestoreInstanceState()方法以及onCreate() 方法。
6.  数据保存：onSaveInstanceState()调用在onStop之前，和onPause之间时间关系不确定。
7.  数据恢复：onRestoreInstanceState()有空判断，onCreate没有空判断。
    例子：
```
      protected void onSaveInstanceState(Bundle outState) {      //重写该方法
            super.onSaveInstanceState(outState);                            //第一句不可以删除
            outState.putCharSequence("text", text.getText());         //bundle类似map集合
        }
        onCreate()方法中添加以下语句：
         if (savedInstanceState != null) {
            CharSequence charSequence = savedInstanceState.getCharSequence("text");
            text.setText(charSequence);
            }
```
8. 设备横屏的两种解决方法：
        1.设置屏幕方向：
          ```
          <activity
                android:name=".MainActivity"
                android:screenOrientation="portrait">.....</activity>
                ```
        2. 编写两个布局： 可以在res目录下建立layout-land两个目录。

##### onNewIntent()和onConfigurationChanged(newConfig)
1.  新的设备配置信息，当系统的配置信息发生改变时，系统会调用此方法。注意，**只有在配置文件AndroidManifest中处理了configChanges属性对应的设备配置，该方法才会被调用。**如果发生设备配置与在配置文件中设置的不一致，则Activity会被销毁并使用新的配置重建。
2.  设备配置的更改会导致Acitivity销毁重建，通过设置android:configChanges可以避免Activity销毁重建，系统仍然会回调onConfigurationChanged方法。

##### 其他
1.  **onPause内不能执行耗时操作**，onPause执行完，新的Activity的onResume才执行。
2.  **onStart和onStop是从Activity是否可见来回调的，onPause和onResume是从Activity是否位于前台这个角度来回调的**。
3.  启动模式：standard标准模式，singleTop栈顶复用(onNewIntent),singleTask栈内复用(clearTop,onNewIntent)，singleInstance(单实例模式)。
4.  启动模式可以静态设置动态设置，动态设置优先级高，静态设置无法设置CLEAR_TOP标识，动态设置无法指定singleInstance。
5.  Activity可以设置标记位来指定启动模式和运行状态。
6.  隐式调用(常用于系统界面的调用)：一个Activity可以有多个intent-filter（可能包含action,categry,data）,一个Intent只要能匹配任意一组intent-filter，就能成功启动对应Activity。
