package com.shui.blacktea.ui.home;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.ActivityLoginBinding;
import com.shui.blacktea.ui.BaseActivity;
import com.shui.blacktea.utils.SharedPreferenceUtils;
import com.yeeyuntech.framework.utils.ToastUtils;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/16.
 */

public class LoginActivity extends BaseActivity implements View.OnFocusChangeListener, View.OnClickListener {
    @Inject
    ActivityLoginBinding mBinding;
    private boolean isNameEmpty = true;
    private boolean isPassWordEmpty = true;
    private boolean mUserNameSignUp = false;
    private boolean mPhoneNumberSignUp = false;
    private AVUser mUser;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initInject() {
        getActivityComponent().Inject(this);
    }

    @Override
    protected void initBinding() {
        super.initBinding();
        mBinding.setClick(this);
    }

    @Override
    public void initListeners() {
        mBinding.actvUsername.setOnFocusChangeListener(this);
        mBinding.editPassword.setOnFocusChangeListener(this);
        mBinding.actvUsername.addTextChangedListener(new myNameTextWatcher(0));
        mBinding.editPassword.addTextChangedListener(new myNameTextWatcher(1));
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.actv_username:
                if (hasFocus) {
                    mBinding.rlLoginUserName.setVisibility(View.VISIBLE);
                    mBinding.rlLoginPassword.setVisibility(View.INVISIBLE);
                }
                break;
            case R.id.edit_password:
                if (hasFocus) {
                    mBinding.rlLoginUserName.setVisibility(View.INVISIBLE);
                    mBinding.rlLoginPassword.setVisibility(View.VISIBLE);
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                final String UserNameOrPhone = mBinding.actvUsername.getText().toString().trim();
                final String Password = mBinding.editPassword.getText().toString().trim();
                System.out.println(UserNameOrPhone + "--" + Password);
                AVUser.logInInBackground(UserNameOrPhone, Password, new LogInCallback<AVUser>() {
                    @Override
                    public void done(AVUser avUser, AVException e) {
                        if (e == null) {
                            mUserNameSignUp = true;
                            mUser = avUser;
                        } else {
                            AVUser.loginByMobilePhoneNumberInBackground(UserNameOrPhone, Password, new LogInCallback<AVUser>() {
                                @Override
                                public void done(AVUser avUser, AVException e) {
                                    if (e == null) {
                                        mPhoneNumberSignUp = true;
                                        mUser = avUser;
                                    } else {
                                        ToastUtils.showToast("账号或密码错误");
                                    }
                                }
                            });
                        }
                    }
                });
                if (mUserNameSignUp || mPhoneNumberSignUp) {
                    ToastUtils.showToast("登录成功");
                    SharedPreferenceUtils.setUserIsLogin(true);
                    SharedPreferenceUtils.setUser(mUser.getObjectId());
                }
                break;
            case R.id.register_button:
                Intent intent = new Intent(mActivity, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private class myNameTextWatcher implements TextWatcher {
        int type;

        public myNameTextWatcher(int type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String re = charSequence.toString();
            if (re.length() == 0) {
                if (type == 0) {
                    isNameEmpty = true;
                } else {
                    isPassWordEmpty = true;
                }
            }
            if (re.length() >= 1) {
                if (type == 0) {
                    isNameEmpty = false;
                } else {
                    isPassWordEmpty = false;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (!isNameEmpty && !isPassWordEmpty) {
                //signInButton.setClickable(true);
                //signInButton.setBackgroundColor(AppUtils.getColor(R.color.colorAccent));
            } else {
                //signInButton.setClickable(false);
                //signInButton.setBackgroundColor(AppUtils.getColor(R.color.button_unenable));
            }
        }
    }
}
