package com.shui.blacktea.ui.video;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shui.blacktea.R;
import com.shui.blacktea.databinding.FragmentVideoBinding;
import com.shui.blacktea.entity.NewsEntity;
import com.shui.blacktea.ui.BaseFragment;
import com.shui.blacktea.ui.news.contract.NewsContract;
import com.shui.blacktea.ui.news.presenter.NewsPresenter;
import com.shui.blacktea.utils.StatusBarUtils;
import com.yeeyuntech.framework.ui.IYYPresenter;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.yokeyword.fragmentation.SupportActivity;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * Description:
 * Created by Juice_ on 2017/8/1.
 */

public class VideoFragment extends BaseFragment implements NewsContract.View {
    @Inject
    FragmentVideoBinding mBinding;
    @Inject
    NewsPresenter mPresenter;
    //private NewsAdapter mAdapter;
    private QuickAdapter mAdapter;
    private List<NewsEntity> mData = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected View initBinding() {
        return mBinding.getRoot();
    }

    @Override
    protected void initInject() {
        getFragmentComponent().Inject(this);
    }

    @Override
    protected IYYPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    public void initViews() {
        ((SupportActivity) mActivity).setSupportActionBar(mBinding.toolbar);
        setHasOptionsMenu(true);
        mAdapter = new QuickAdapter();

        mBinding.recycler.addItemDecoration(new DividerItemDecoration(mActivity, VERTICAL));
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mBinding.recycler.setAdapter(mAdapter);
        mBinding.refreshLayout.autoRefresh();
        mBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<Movie> movies = new Gson().fromJson(JSON_MOVIES, new TypeToken<ArrayList<Movie>>() {
                        }.getType());
                        mAdapter.replaceData(movies);
                        refreshlayout.finishRefresh();
                    }
                }, 2000);
            }
        });
        View header = LayoutInflater.from(mActivity).inflate(R.layout.item_news_header, mBinding.recycler, false);
        Banner banner = (Banner) header;
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(BANNER_ITEMS);
        banner.start();
        mAdapter.addHeaderView(banner);

        //状态栏透明和间距处理
        StatusBarUtils.immersive(mActivity);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.toolbar);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.recycler);
        StatusBarUtils.setMargin(mActivity, mBinding.header);
        StatusBarUtils.setPaddingSmart(mActivity, mBinding.blurView);
    }

    @Override
    public void getData() {
        /*mPresenter.getNewsList();*/
    }

    @Override
    public void showNewsResult(List<NewsEntity> list, boolean isLoadMore) {
        /*mData.addAll(list);
        mAdapter.replaceData(list);
        mBinding.refreshLayout.finishRefresh();
        //mAdapter.notifyDataSetChanged();*/
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //imageView.setImageResource(((BannerItem) path).pic);
            Glide.with(context).load(((BannerItem) path).pic).into(imageView);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    public static class BannerItem {

        public String pic;
        public String title;

        public BannerItem() {
        }

        public BannerItem(String title, String pic) {
            this.pic = pic;
            this.title = title;
        }
    }

    public class QuickAdapter extends BaseQuickAdapter<Movie, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_news);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, Movie item) {
            viewHolder.setText(R.id.news_tv_title, item.filmName)
                    .setText(R.id.news_tv_actor, item.actors)
                    .setText(R.id.news_tv_time, item.grade)
                    .setText(R.id.news_tv_describe, item.shortinfo);
            Glide.with(mContext).load(item.picaddr).into((ImageView) viewHolder.getView(R.id.news_iv_thumb));
        }
    }

    public static class Movie {
        public String actors;
        public String filmName;
        public String grade;
        public String info;
        public String picaddr;
        public String shortinfo;
    }

    public static List<BannerItem> BANNER_ITEMS = new ArrayList<BannerItem>() {{
        add(new BannerItem("最后的骑士", "http://pic49.nipic.com/file/20140927/19617624_230415502002_2.jpg"));
        add(new BannerItem("三生三世十里桃花", "http://pic49.nipic.com/file/20140927/19617624_230415502002_2.jpg"));
        add(new BannerItem("豆福传", "http://pic49.nipic.com/file/20140927/19617624_230415502002_2.jpg"));
    }};
    public static String JSON_MOVIES = "[" +
            "{\"actors\":\"丹尼斯·威缇可宁|Emma|Nikki|Jiayao|Wang|Maggie|Mao|Gang-yun|Sa\",\"filmName\":\"神灵寨\",\"grade\":\"5.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3823.jpg\",\"releasedate\":\"2017-07-31\",\"shortinfo\":\"父亲忽病危 新娘真够黑\",\"type\":\"剧情|喜剧\"}," +
            "{\"actors\":\"刘亦菲|杨洋|彭子苏|严屹宽|罗晋\",\"filmName\":\"三生三世十里桃花\",\"grade\":\"9.2\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3566.jpg\",\"releasedate\":\"2017-08-03\",\"shortinfo\":\"虐心姐弟恋 颜值要逆天\",\"type\":\"剧情|爱情|奇幻\"}," +
            "{\"actors\":\"尹航|代旭|李晨浩|衣云鹤|张念骅\",\"filmName\":\"谁是球王\",\"grade\":\"10.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3750.jpg\",\"releasedate\":\"2017-08-03\",\"shortinfo\":\"足球变人生 再战可辉煌\",\"type\":\"剧情|喜剧\"}," +
            "{\"actors\":null,\"filmName\":\"大象林旺之一夜成名\",\"grade\":\"10.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3757.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"大象参二战 一生好伙伴\",\"type\":\"动作|动画|战争|冒险\"}," +
            "{\"actors\":\"薛凯琪|陈意涵|张钧甯|迈克·泰森\",\"filmName\":\"闺蜜2：无二不作\",\"grade\":\"8.3\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3776.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"闺蜜团出战 会一会新娘\",\"type\":\"喜剧|爱情\"}," +
            "{\"actors\":\"彭禺厶|王萌|周凯文|曹琦|孟子叶\",\"filmName\":\"诡井\",\"grade\":\"5.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3824.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"午夜深井中 怨魂欲现形\",\"type\":\"恐怖|惊悚\"}," +
            "{\"actors\":\"旺卓措|刘承宙|高欣生|段楠|来钰\",\"filmName\":\"荒野加油站\",\"grade\":\"5.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3821.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"夜半拉乘客 结果遇不测\",\"type\":\"惊悚|悬疑\"}," +
            "{\"actors\":\"刘佩琦|曹云金|罗昱焜\",\"filmName\":\"龙之战\",\"grade\":\"5.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3778.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"持倭刀屹立 抗外敌救国\",\"type\":\"动作|战争|历史\"}," +
            "{\"actors\":\"金巴|曲尼次仁|夏诺.扎西敦珠|索朗尼玛|益西旦增\",\"filmName\":\"皮绳上的魂\",\"grade\":\"5.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3801.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"走完朝圣路 又上降魔旅\",\"type\":\"剧情\"}," +
            "{\"actors\":\"严丽祯|李晔|王衡|李传缨|李心仪\",\"filmName\":\"玩偶奇兵\",\"grade\":\"10.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3779.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"玩偶战数码 一头两个大\",\"type\":\"动画|冒险|奇幻\"}," +
            "{\"actors\":\"斯蒂芬·马布里|吴尊|何冰|郑秀妍|王庆祥\",\"filmName\":\"我是马布里\",\"grade\":\"0.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3810.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"吴尊助冠军 热血灌篮魂\",\"type\":\"剧情|运动\"}," +
            "{\"actors\":\"周鹏雨|穆建荣|陈泽帆|鹿露|宋星成\",\"filmName\":\"原罪的羔羊\",\"grade\":\"5.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3802.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"古镇来戏班 往事不一般\",\"type\":\"悬疑\"}," +
            "{\"actors\":\"王大陆|张天爱|任达华|盛冠森|王迅\",\"filmName\":\"鲛珠传\",\"grade\":\"7.1\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3777.jpg\",\"releasedate\":\"2017-08-04\",\"shortinfo\":\"改编热IP 杠杠号召力\",\"type\":\"喜剧|动作|奇幻\"}," +
            "{\"actors\":\"成龙|罗伯特·雷德福\",\"filmName\":\"地球：神奇的一天\",\"grade\":\"10.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3803.jpg\",\"releasedate\":\"2017-08-11\",\"shortinfo\":\"史诗纪录片 十年再相见\",\"type\":\"纪录片\"}," +
            "{\"actors\":\"刘德华|舒淇|杨祐宁|张静初|让·雷诺|曾志伟|沙溢\",\"filmName\":\"侠盗联盟\",\"grade\":\"10.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3592.jpg\",\"releasedate\":\"2017-08-11\",\"shortinfo\":\"侠盗三剑客 越洋逃恐吓\",\"type\":\"动作|冒险\"}," +
            "{\"actors\":\"廖凡|李易峰|万茜|李纯|张国柱\",\"filmName\":\"心理罪\",\"grade\":\"10.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3795.jpg\",\"releasedate\":\"2017-08-11\",\"shortinfo\":\"侦探两搭档 真相背后藏\",\"type\":\"悬疑|犯罪\"}," +
            "{\"actors\":\"徐瑞阳|赵倩|姜启杨|徐万学|韩靓|韦安\",\"filmName\":\"隐隐惊马槽之绝战女僵尸\",\"grade\":\"5.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3825.jpg\",\"releasedate\":\"2017-08-11\",\"shortinfo\":\"阴兵来借道 尸占惊马槽\",\"type\":\"惊悚|动作|冒险|悬疑\"}," +
            "{\"actors\":\"宋睿|王良|张佳浩|叶常清\",\"filmName\":\"左眼阴阳\",\"grade\":\"10.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3804.jpg\",\"releasedate\":\"2017-08-11\",\"shortinfo\":\"左眼见到鬼 是诡还是魅\",\"type\":\"恐怖|惊悚|悬疑\"}," +
            "{\"actors\":null,\"filmName\":\"二十二\",\"grade\":\"10.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3811.jpg\",\"releasedate\":\"2017-08-14\",\"shortinfo\":\"二战女俘虏 讲述心中苦\",\"type\":\"纪录片\"}," +
            "{\"actors\":\"郭富城|王千源|刘涛|余皑磊|冯嘉怡\",\"filmName\":\"破·局\",\"grade\":\"5.0\",\"picaddr\":\"http://app.infunpw.com/commons/images/cinema/cinema_films/3812.jpg\",\"releasedate\":\"2017-08-18\",\"shortinfo\":\"影帝硬碰硬 迷局谁怕谁\",\"type\":\"动作|犯罪\"}" +
            "]";

}
