package com.shui.blacktea.ui.home;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.ActivityRegisterBinding;
import com.shui.blacktea.ui.BaseActivity;
import com.shui.blacktea.utils.SharedPreferenceUtils;
import com.yeeyuntech.framework.utils.ToastUtils;

import javax.inject.Inject;

/**
 * Description:
 * Created by Juice_ on 2017/8/16.
 */

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @Inject
    ActivityRegisterBinding mBinding;
    private static final int TYPE_PHONE = 1;
    private static final int TYPE_CODE = 2;
    private static final int TYPE_NAME = 3;
    private static final int TYPE_PASSWORD = 4;
    private static final int TYPE_REPASSWORD = 5;
    private String passwordCheck = "";
    private boolean PASSWORD_CHECK = false, SMS_CHECK = false, NAME_CHECK = false, PASSWORD_LENGTH_CHECK = false;
    private boolean phoneEmpty = true, codeEmpty = true, nameEmpty = true, passEmpty = true, rePassEmpty = true;
    private String phoneNumber;
    private String code;
    private String userName;
    private String password;
    private String rePassword;
    private String email;
    private boolean codeSuccess = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initInject() {
        getActivityComponent().Inject(this);
    }

    @Override
    public void initListeners() {
        // mBinding.registerBtnCode.setOnClickListener(this);
        //mBinding.registerBtnCode.setClickable(false);
        //initEventHandler();//初始化短信回调
        //setToolbar(normalToolbar,"注册");//设置toolbar
        mBinding.registerEtPhone.addTextChangedListener(new myTextChange(TYPE_PHONE));
        mBinding.registerEtCode.addTextChangedListener(new myTextChange(TYPE_CODE));
        mBinding.registerEtNickname.addTextChangedListener(new myTextChange(TYPE_NAME));
        mBinding.registerEtPassword.addTextChangedListener(new myTextChange(TYPE_PASSWORD));
        mBinding.registerEtRepassword.addTextChangedListener(new myTextChange(TYPE_REPASSWORD));
        //mBinding.registerBtnSuccess.setOnClickListener(this);//注册按钮
        //mBinding.registerBtnSuccess.setClickable(false);
    }

    @Override
    protected void initBinding() {
        super.initBinding();
        mBinding.setClick(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn_code:
                phoneNumber = mBinding.registerEtPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(phoneNumber)) {
                    AVOSCloud.requestSMSCodeInBackground(phoneNumber, new RequestMobileCodeCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                ToastUtils.showToast("验证码已发送");
                            } else {
                                ToastUtils.showToast(e.getCode());
                            }
                        }
                    });
                }
                break;
            case R.id.register_btn_success:
                code = mBinding.registerEtCode.getText().toString().trim();
                userName = mBinding.registerEtNickname.getText().toString().trim();
                password = mBinding.registerEtPassword.getText().toString().trim();
                rePassword = mBinding.registerEtRepassword.getText().toString().trim();
                email = mBinding.registerEtEmail.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)) {
                    ToastUtils.showToast("账号密码，邮箱均不能为空");
                    return;
                }
                if (!password.equals(rePassword)) {
                    ToastUtils.showToast("两次密码输入不一致");
                }
                if (!TextUtils.isEmpty(code)) {
                    AVUser.verifyMobilePhoneInBackground(code, new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                // 验证成功
                                codeSuccess = true;
                            } else {
                                ToastUtils.showToast("验证码错误");
                                codeSuccess = false;
                                return;
                            }
                        }
                    });
                }
                AVUser user = new AVUser();
                user.setUsername(userName);
                user.setEmail(email);
                user.setPassword(password);
                user.setMobilePhoneNumber(phoneNumber);
                user.put("passWord", password);
                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            ToastUtils.showToast("注册成功");
                            SharedPreferenceUtils.setUserIsLogin(true);//设置用户登录成功
                        } else {
                            if (e.getCode() == 202) {
                                ToastUtils.showToast("账号已被注册");
                            } else if (e.getCode() == 203) {
                                ToastUtils.showToast("邮箱已被注册");
                            } else if (e.getCode() == 214) {
                                ToastUtils.showToast("手机号码已被注册");
                            }
                        }
                    }
                });

                break;
        }
    }

    private class myTextChange implements TextWatcher {
        private int type;

        public myTextChange(int type) {
            this.type = type;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String input = charSequence.toString();
            switch (type) {
                case TYPE_PHONE://长度不为11不可点击
                    if (input.length() == 11) {
                        mBinding.registerBtnCode.setClickable(true);
                        phoneEmpty = false;
                    } else {
                        mBinding.registerBtnCode.setClickable(false);
                    }
                    if (input.length() <= 0) {
                        phoneEmpty = true;
                    }
                    break;
                case TYPE_CODE:
                    if (input.length() > 0) {
                        codeEmpty = false;
                        if (input.length() == 4) {
                            //SMSSDK.submitVerificationCode("86", registerEtPhone.getText().toString(), input.trim());
                        }
                    } else {
                        codeEmpty = true;
                    }
                    break;
                case TYPE_NAME:
                    if (input.length() > 0) {
                        nameEmpty = false;
                    } else {
                        nameEmpty = true;
                    }
                    break;
                case TYPE_PASSWORD:
                    if (input.length() > 0) {
                        passEmpty = false;
                    } else {
                        passEmpty = true;
                    }
                    break;
                case TYPE_REPASSWORD:
                    if (input.length() > 0) {
                        rePassEmpty = false;
                    } else {
                        rePassEmpty = true;
                    }
                    break;
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
            mBinding.registerTvRepassword.setVisibility(View.INVISIBLE);
            mBinding.registerTvNick.setVisibility(View.INVISIBLE);
            if (!nameEmpty && !codeEmpty && !nameEmpty && !passEmpty && !rePassEmpty) {
                mBinding.registerBtnSuccess.setClickable(true);
            } else {
                mBinding.registerBtnSuccess.setClickable(false);
            }
            switch (type) {
                case TYPE_PHONE://长度不为11不可点击
                    break;
                case TYPE_CODE:
                    break;
                case TYPE_NAME:
                    if (mBinding.registerEtNickname.getText().toString().length() >= 4 && mBinding.registerEtNickname.getText().toString().length() <= 14) {
                        NAME_CHECK = true;
                    } else if (mBinding.registerEtNickname.getText().toString().length() < 4) {
                        NAME_CHECK = false;
                        mBinding.registerTvNick.setVisibility(View.VISIBLE);
                        mBinding.registerTvNick.setText("昵称长度不够哦");
                    } else {
                        NAME_CHECK = false;
                        mBinding.registerTvNick.setVisibility(View.VISIBLE);
                        mBinding.registerTvNick.setText("昵称长度太长了！！！！");
                    }
                    break;
                case TYPE_PASSWORD:
                    int passwordLength = mBinding.registerEtPassword.getText().toString().trim().length();
                    if (passwordLength >= 6 && passwordLength <= 16) {
                        PASSWORD_LENGTH_CHECK = true;
                    } else {
                        PASSWORD_LENGTH_CHECK = false;
                        mBinding.registerTvPassword.setText("密码长度不合适哦~~");
                    }
                    break;
                case TYPE_REPASSWORD:
                    String pass = mBinding.registerEtPassword.getText().toString();
                    String repass = mBinding.registerEtRepassword.getText().toString();
                    if (pass == repass || repass.equals(pass)) {
                        PASSWORD_CHECK = true;//密码验证成功
                    } else {
                        mBinding.registerTvRepassword.setVisibility(View.VISIBLE);
                        mBinding.registerTvRepassword.setText("两次密码不一致哦~~~~~~~~~");
                        PASSWORD_CHECK = false;//密码不一致
                    }
                    break;
            }
        }
    }
}
