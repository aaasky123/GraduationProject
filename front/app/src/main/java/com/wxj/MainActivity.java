package com.wxj;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取BottomNavigationView实例
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        //通过navController.getGraph()这个方法获取navigation中的导航配置，并创建appBarConfiguration配置文件
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_work, R.id.navigation_notifications, R.id.navigation_contactpeople, R.id.navigation_me).build();

        //获取Navigation的hostFragment
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        //将appBarConfiguration配置文件和navController关联，此时关联的是ActionBar中的menu
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //装配navController到BottomNavigationView上实现页面切换
        NavigationUI.setupWithNavController(navView, navController);


        //int id=getIntent().getIntExtra("register",0);
        //if(id==555){
        //    //getSupportFragmentManager()
        //    //        .beginTransaction()
        //    //        .replace(R.id.navigation_register,new RegisterFragment())
        //    //        .addToBackStack(null)
        //    //        .commit();
        //    Navigation.findNavController(this,R.id.navigation_register);
        //}
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController=Navigation.findNavController(this,R.id.nav_host_fragment);
        return navController.navigateUp();
        //return super.onSupportNavigateUp();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}