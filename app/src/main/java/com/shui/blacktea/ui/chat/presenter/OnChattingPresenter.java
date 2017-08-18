package com.shui.blacktea.ui.chat.presenter;

import com.shui.blacktea.App;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.entity.type.MessageType;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.chat.contract.OnChattingContract;
import com.shui.blacktea.utils.RxBus;
import com.yeeyuntech.framework.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by JuiceShui on 2017/8/18.
 * To strive,to seek,to find,and not to give up
 */

public class OnChattingPresenter extends BaseRxPresenter<OnChattingContract.View>
        implements OnChattingContract.Presenter {
    @Inject
    public OnChattingPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super(mApp, mRetrofitHelper);
        registerEvent();
    }

    @Override
    public void getMessage(MessageType message) {
        mView.showMessage(message.getMessage());
    }

    @Override
    public void postMessage() {

    }

    private void registerEvent() {
        Disposable disposable = RxBus.getDefault().toFlowable(MessageType.class)
                .compose(RxUtils.<MessageType>rxSchedulerHelper())
                .subscribe(new Consumer<MessageType>() {
                    @Override
                    public void accept(@NonNull MessageType messageType) throws Exception {
                        getMessage(messageType);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        registerEvent();
                    }
                });
        addSubscribe(disposable);
    }
}