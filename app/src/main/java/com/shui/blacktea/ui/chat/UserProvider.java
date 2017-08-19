package com.shui.blacktea.ui.chat;

import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.yeeyuntech.framework.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Description:
 * Created by Juice_ on 2017/8/19.
 */

public class UserProvider implements LCChatProfileProvider {
    private static UserProvider customUserProvider;

    public synchronized static UserProvider getInstance() {
        if (null == customUserProvider) {
            customUserProvider = new UserProvider();
        }
        return customUserProvider;
    }

    private UserProvider() {
    }

    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

    @Override
    public void fetchProfiles(final List<String> list, final LCChatProfilesCallBack lcChatProfilesCallBack) {
        final AVQuery<AVUser> userAVQuery = new AVQuery<>("_User");
        final AVUser avUser = AVUser.getCurrentUser();
        userAVQuery.whereEqualTo("contract", avUser);
        Disposable disposable = Observable.create(new ObservableOnSubscribe<List<AVUser>>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<List<AVUser>> e) throws Exception {
                e.onNext(userAVQuery.find());
            }
        }).compose(RxUtils.<List<AVUser>>applySchedulers())
                .subscribe(new Consumer<List<AVUser>>() {
                    @Override
                    public void accept(@NonNull List<AVUser> avUsers) throws Exception {
                        for (AVUser item : avUsers) {
                            partUsers.add(new LCChatKitUser(item.getObjectId(), item.getString("username"), item.getString("avatar")));
                        }
                        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
                        for (String userId : list) {
                            for (LCChatKitUser user : partUsers) {
                                if (user.getUserId().equals(userId)) {
                                    userList.add(user);
                                    break;
                                }
                            }
                        }
                        lcChatProfilesCallBack.done(userList, null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }

    public List<LCChatKitUser> getAllUsers() {
        return partUsers;
    }
}
