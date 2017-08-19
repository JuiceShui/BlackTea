package com.shui.blacktea.ui.chat;

import android.support.v7.widget.LinearLayoutManager;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.ActivityChattingBinding;
import com.shui.blacktea.entity.IMMessage;
import com.shui.blacktea.ui.BaseActivity;
import com.shui.blacktea.ui.chat.contract.OnChattingContract;
import com.shui.blacktea.utils.StatusBarUtils;
import com.yeeyuntech.framework.utils.RxUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Created by Juice_ on 2017/8/18.
 */

public class OnChattingActivity extends BaseActivity implements OnChattingContract.View {
    @Inject
    ActivityChattingBinding mBinding;
    private String mTarget;//通讯对象
    private AVIMClient mSelfClient;
    private AVIMConversation mConversation;
    private ChatAdapter mAdapter;
    private List<IMMessage> mMessageData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_chatting;
    }

    @Override
    protected void initInject() {
        getActivityComponent().Inject(this);
    }

    @Override
    public void initParams() {
        mTarget = getIntent().getStringExtra("target");
        mSelfClient = AVIMClient.getInstance(AVUser.getCurrentUser());
        mSelfClient.open(new AVIMClientCallback() {//创建服务器连接
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (e == null) {
                    //创建服务器链接成功
                    //创建对话
                    createSingleConversation();
                }
            }
        });
    }

    @Override
    public void initViews() {
        mAdapter = new ChatAdapter(mMessageData);
        StatusBarUtils.immersive(mActivity);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.toolbar);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.blurView);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.recycler);
        getTargetAvatar();
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.recycler.setAdapter(mAdapter);
    }

    /**
     * 接收到信息
     *
     * @param message
     */
    @Override
    public void showMessage(AVIMMessage message) {
        //判断信息类型
        IMMessage imMessage = new IMMessage(message);
        imMessage.setMessageType(IMMessage.TYPE_COMMING);
    }

    /**
     * 创建对话
     */
    private void createSingleConversation() {
        mSelfClient.createConversation(Arrays.asList(mTarget), "me&you", null, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation avimConversation, AVIMException e) {
                if (e == null) {
                    //创建对话成功
                    mConversation = avimConversation;
                }
            }
        });
    }

    /**
     * 发送信息
     *
     * @param message
     */
    private void postMessage(AVIMMessage message) {
        mConversation.sendMessage(message, new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    //发送成功
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSelfClient != null) {
            mSelfClient.close(new AVIMClientCallback() {
                @Override
                public void done(AVIMClient avimClient, AVIMException e) {
                    if (e == null) {
                        mSelfClient = null;
                    }
                }
            });
        }
    }

    private void getTargetAvatar() {
        final AVQuery<AVObject> targetUserQuery = new AVQuery<>("_User");
        targetUserQuery.whereEqualTo("username", mTarget);
        Observable.create(new ObservableOnSubscribe<List<AVObject>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AVObject>> e) throws Exception {
                List<AVObject> list = targetUserQuery.find();
                e.onNext(list);
            }
        }).compose(RxUtils.<List<AVObject>>applySchedulers()).subscribe(new Consumer<List<AVObject>>() {
            @Override
            public void accept(@NonNull List<AVObject> avObjects) throws Exception {
                AVObject target = avObjects.get(0);
                String objectId = target.getObjectId();
                String avatarUrl = target.getString("avatar");
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {

            }
        });
    }
}
