package com.shui.blacktea.ui.chat.presenter;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.shui.blacktea.App;
import com.shui.blacktea.data.RetrofitHelper;
import com.shui.blacktea.entity.UserEntity;
import com.shui.blacktea.ui.BaseRxPresenter;
import com.shui.blacktea.ui.chat.contract.ConnectContract;
import com.yeeyuntech.framework.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by JuiceShui on 2017/8/19.
 * To strive,to seek,to find,and not to give up
 */

public class ConnectPresenter extends BaseRxPresenter<ConnectContract.View>
        implements ConnectContract.Presenter {
    private List<UserEntity> mConnects = new ArrayList<>();
    private List<AVObject> mHostUsers = new ArrayList<>();
    private List<AVObject> mGetHostUser = new ArrayList<>();

    @Inject
    public ConnectPresenter(App mApp, RetrofitHelper mRetrofitHelper) {
        super(mApp, mRetrofitHelper);
    }

    @Override
    public void getUserLists() {
        final AVUser avUser = AVUser.getCurrentUser();//当前用户
        final AVQuery<AVObject> connectQuery = new AVQuery<>("Connect");//当前查询
        connectQuery.whereEqualTo("home", avUser);//查询对象的限制
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<AVObject>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AVObject>> e) throws Exception {
                e.onNext(connectQuery.find());
            }
        }).map(new Function<List<AVObject>, List<AVObject>>() {
            @Override
            public List<AVObject> apply(@NonNull List<AVObject> avObjects) throws Exception {
                for (AVObject connectMap : avObjects) {
                    AVUser host = connectMap.getAVObject("host");
                    mHostUsers.add(host);
                }
                for (AVObject item : mHostUsers) {
                    item = AVObject.createWithoutData("_User", item.getObjectId());
                    item.fetch();
                    mGetHostUser.add(item);
                }
                return mGetHostUser;
            }
        }).compose(RxUtils.<List<AVObject>>applySchedulers())
                .subscribe(new Consumer<List<AVObject>>() {
                    @Override
                    public void accept(@NonNull List<AVObject> connectsMap) throws Exception {
                        mConnects.clear();
                        List<UserEntity> contractList = parseData(connectsMap);
                        mConnects.addAll(contractList);
                        mView.showUserLists(mConnects);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        System.out.println(throwable.toString());
                    }
                });
        addSubscribe(disposable);
    }

    private List<UserEntity> parseData(List<AVObject> list) {
        List<UserEntity> userList = new ArrayList<>();
        for (AVObject item : list) {
            UserEntity user = new UserEntity();
            user.setAvatar(item.getString("avatar"));
            user.setUserName(item.getString("username"));
            user.setObjectId(item.getObjectId());
            user.setSignature(item.getString("signature"));
            userList.add(user);
        }
        return userList;
    }
}