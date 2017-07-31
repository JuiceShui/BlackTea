/*
 * Created by lsy on 2017/7/21.
 * Copyright (c) 2017 yeeyuntech. All rights reserved.
 */

package com.shui.blacktea.data;

/**
 * Created by adu on 2017/3/30.
 */

public interface APIPath {

    /**
     * 注册
     * <p>
     * account 账号
     * phoneNum	 手机号码
     * idCard 身份证
     * password	密码
     * <p>
     * POST
     */
    String API_REGISTER = "/app/pre/register";

    /**
     * 登录接口
     * <p>
     * account 账号
     * password 密码
     * <p>
     * POST
     */
    String API_LOGIN = "/app/pre/login";

    /**
     * 忘记密码
     * <p>
     * idCard
     * password
     * <p>
     * POST
     */
    String API_FORGET_PWD = "/app/pre/forgetpwd";

    /**
     * 获取个人资料
     * <p>
     * GET
     */
    String API_USERINFO = "/app/front/mine/userinfo";

    /**
     * 获取顶级栏目
     * <p>
     * GET
     */
    String API_NEWS_TOPCATES = "/app/front/news/topcates";

    /**
     * 获取首页轮换图
     * <p>
     * GET
     */
    String API_NEWS_SLIDES = "/app/front/news/slides";

    /**
     * 某个栏目下的栏目ID
     * <p>
     * cateID 栏目ID
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_NEWS_LIST = "/app/front/news/list";

    /**
     * 获取新闻详情页
     * <p>
     * newsId  新闻ID
     * <p>
     * GET
     */
    String API_NEWS_DETAIL = "/app/front/news/detail";

    /**
     * 获取党员风采列表
     * <p>
     * pageSize
     * offset
     * GET
     */
    String API_FLAG_MEMBERLIST = "/app/front/flag/memberlist";

    /**
     * 获取党员风采详情页
     * <p>
     * memberId 党员风采ID
     * <p>
     * GET
     */
    String API_FLAG_MEMBERDETAIL = "/app/front/flag/memberdetail";

    /**
     * 获取支部新闻列表
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_FLAG_BRANCGNEWSLIST = "/app/front/flag/branchnewslist";

    /**
     * 获取支部新闻详情
     * <p>
     * newsId 新闻ID
     * <p>
     * GET
     */
    String API_FLAG_NEWSDETAIL = "/app/front/flag/newsdetail";

    /**
     * 获取活动列表
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_FLAG_ACTIVITYLIST = "/app/front/flag/activitylist";

    /**
     * 获取组织生活列表
     */
    String API_FLAG_ORGLIFELIST = "/app/front/flag/orglifelist";
    /**
     * 活动详情页
     * <p>
     * activityId 活动ID
     * <p>
     * GET
     */
    String API_FLAG_ACTIVITY_DETAIL = "/app/front/flag/activitydetail";
    String API_FLAG_ORGLIFENEWSDETAIL = "/app/front/flag/orglifenewsdetail";
    /**
     * 点赞或取消点赞新闻
     * <p>
     * NewsId 新闻ID
     * type 两种 String（star  unstar）
     * type
     * <p>
     * POST
     */
    String API_NEWS_STAR = "/app/front/news/star";

    /**
     * 收藏或取消收藏新闻
     * <p>
     * newsID  新闻ID
     * type 两种 String（collect  uncollect）
     * <p>
     * POST
     */
    String API_NEWS_COLLECT = "/app/front/news/collect";

    /**
     * 点赞或取消点赞支部新闻
     * <p>
     * newsID 新闻ID
     * type 两种 String（star  unstar）
     * <p>
     * POST
     */
    String API_FLAG_STARNEWS = "/app/front/flag/starnews";

    /**
     * 收藏或取消收藏支部新闻
     * <p>
     * newsID  新闻ID
     * type 两种 String（collect  uncollect）
     * <p>
     * POST
     */
    String API_FLAG_COLLECT = "/app/front/flag/collectnews";

    /**
     * 活动报名
     * <p>
     * activityId 活动ID
     * <p>
     * POST
     */
    String API_FLAG_SIGNUP = "/app/front/flag/signup";

    /**
     * 点赞或取消点赞活动
     * <p>
     * activityId 新闻ID
     * type 两种 String（star  unstar）
     * type
     * <p>
     * POST
     */
    String API_FLAG_STAR_ACTIVITY = "/app/front/flag/staractivity";

    /**
     * 收藏或取消收藏活动
     * <p>
     * activityId  新闻ID
     * type 两种 String（collect  uncollect）
     * <p>
     * POST
     */
    String API_FLAG_COLLECT_ACTIVITY = "/app/front/flag/collectactivity";

    /**
     * 获取活动评论列表
     * <p>
     * activityId 活动ID
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_FLAG_COMMENT_LIST = "/app/front/flag/commentlist";

    /**
     * 获取评论回复列表
     * <p>
     * commentId 评论Id
     * pageSize
     * pageSize
     * <p>
     * GET
     */
    String API_FLAG_RELLY_LIST = "/app/front/flag/replylist";

    /**
     * 评论活动（一级评论）
     * <p>
     * activityId 活动ID
     * content 内容
     * <p>
     * POST
     */
    String API_FLAG_COMMENT = "/app/front/flag/comment";

    /**
     * 评论活动的评论（二级回复）
     * <p>
     * activityId 活动ID
     * parentId 一级评论id
     * toUserId
     * content 内容
     * <p>
     * POST
     */
    String API_FLAG_REPLY = "/app/front/flag/reply";

    /**
     * 点赞/取消点赞评论
     * <p>
     * commentId 评论Id
     * pageSize
     * pageSize
     * <p>
     * GET
     */
    String API_FLAG_START_COMMENT = "/app/front/flag/starcomment";


    /* ---- user部分 start ---- */

    /**
     * 获取我收藏的新闻
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_USER_GET_MY_COLLECT_NEWS_LIST = "/app/front/mine/collectnews";

    /**
     * 获取我收藏的组织生活
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_USER_GET_MY_COLLECT_ORG_LIFE_LIST = "/app/front/mine/collectorglife";

    /**
     * 获取我收藏的党员论坛
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_USER_GET_MY_COLLECT_PARTY_FORUM_LIST = "/app/front/mine/collectpost";

    /**
     * 获取我收藏的学习资料
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_USER_GET_MY_COLLECT_STUDY_DATA_LIST = "/app/front/mine/collectstudydata";

    /**
     * 获取我的消息列表
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_USER_GET_MY_MESSAGE_LIST = "/app/front/mine/msglist";

    /**
     * 获取我的发帖列表
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_USER_GET_MY_POST_LIST = "/app/front/mine/postlist";

    /**
     * 获取我的跟帖列表
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_USER_GET_MY_COMMENT_LIST = "/app/front/mine/commentlist";

    /**
     * 获取我的考试列表
     * <p>
     * pageSize
     * offset
     * <p>
     * GET
     */
    String API_USER_GET_MY_EXAM_LIST = "/app/front/mine/examlist";

    /**
     * 获取我的考试详情
     * <p>
     * recordId  考试记录Id
     * <p>
     * GET
     */
    String API_USER_GET_MY_EXAM_DETAI = "/app/front/mine/examdetail";

    /**
     * 获取全部党支部信息
     * GET
     */
    String API_USER_GET_ALL_PARTY_BRANCH = "/app/front/mine/partybranch";

    /**
     * 获取全部项目支部信息
     * GET
     */
    String API_USER_GET_ALL_PROJECT_BRANCH = "/app/front/mine/projectbranch";

    /**
     * 获取我的党支部信息
     * GET
     */
    String API_USER_GET_MY_PARTY_BRANCH = "/app/front/mine/mypartybranch";

    /**
     * 获取我的项目支部信息
     * GET
     */
    String API_USER_GET_MY_PROJECT_BRANCH = "/app/front/mine/myprojectbranch";

    /**
     * 申请转入其他的党支部
     * <p>
     * toId 要转入的党支部id
     * contact  联系人
     * </p>
     * POST
     */
    String API_USER_CHANGE_PARTY_BRANCH = "/app/front/mine/transferpartybranch";

    /**
     * 申请转入其他的项目支部
     * <p>
     * toId 要转入的项目支部id
     * contact  联系人
     * </p>
     * POST
     */
    String API_USER_CHANGE_PROJECT_BRANCH = "/app/front/mine/transferprojectbranch";

    /**
     * 获取入党流程
     * GET
     */
    String API_USER_GET_JOIN_PARTY_PROCESS = "/app/front/mine/joinpartyprocess";

    /**
     * 判断是否可以入党
     * GET
     */
    String API_USER_CAN_JOIN_PARTY = "/app/front/mine/canjoinparty";

    /**
     * 提交入党申请表
     * <p>
     * name 姓名
     * sex 性别
     * address 地址
     * education 学历
     * company 单位
     * idCard 身份证号
     * phoneNum 手机号码
     * birthday 出生日期
     * </p>
     * POST
     */
    String API_USER_JOIN_PARTY = "/app/front/mine/joinparty";

    /**
     * 修改密码
     * <p>
     * idCard 身份证号
     * password 密码
     * </p>
     * POST
     */
    String API_USER_UPDATE_PASSWORD = "/app/front/mine/updatepwd";

    /**
     * 提交意见反馈
     * <p>
     * content 内容
     * </p>
     * POST
     */
    String API_USER_SUBMIT_FEEDBACK = "/app/front/mine/feedback";

    /**
     * 批量删除收藏的内容
     * <p>
     * delIds 要删除的内容的id，以英文","隔开，例如"1,2,3"
     * </p>
     * POST
     */
    String API_USER_BATCH_DELETE_COLLECT = "/app/front/mine/batchdelcollect";

    /**
     * 上传头像
     * POST
     */
    String API_USER_UPLOAD_AVATAR = "/app/front/mine/uploadicon";

    /**
     * 修改头像
     * <p>
     * icon 头像地址
     * </p>
     * POST
     */
    String API_USER_UPDATE_AVATAR = "/app/front/mine/updateicon";

    /**
     * 修改姓名
     * <p>
     * userName
     * </p>
     * POST
     */
    String API_USER_UPDATE_NAME = "/app/front/mine/updatename";

    /**
     * 修改性别
     * <p>
     * sex 性别，男/女
     * </p>
     * POST
     */
    String API_USER_UPDATE_SEX = "/app/front/mine/updatesex";

    /**
     * 修改民族
     * <p>
     * nation 民族
     * </p>
     * POST
     */
    String API_USER_UPDATE_NATION = "/app/front/mine/updatenation";

    /**
     * 修改籍贯
     * <p>
     * nativePlace 籍贯
     * </p>
     * POST
     */
    String API_USER_UPDATE_NATIVE_PLACE = "/app/front/mine/updatenativeplace";

    /* ---- user部分 end ---- */


    /**
     * 获取在线考试题库列表
     */
    String API_STUDY_EXAM_LIST = "/app/front/study/examlist";

    /**
     * 获取某题库试题
     */
    String API_STUDY_EXAM_DETAIL = "/app/front/study/examdetail";

    /**
     * 提交答案
     */
    String API_STUDY_SUBMIT = "/app/front/study/submit";

    /**
     * 查看答案
     */
    String API_STUDY_ANSWER = "/app/front/study/answer";

    /**
     * 获取学习资料列表
     */
    String API_STUDY_DATALIST = "/app/front/study/studydatalist";

    /**
     * 获取学习资料详情
     */
    String API_STUDY_DETAIL = "/app/front/study/studydetail";

    /**
     * 学习资料详情的点赞/取消
     */
    String API_STUDY_STAR = "/app/front/study/starstudy";

    /**
     * 学习资料详情的收藏|取消
     */
    String API_STUDY_COLLECT = "/app/front/study/collectstudy";

    /**
     * 获取党员论坛列表
     */
    String API_STUDY_POST_LIST = "/app/front/study/postlist";

    /**
     * 上传帖子图片
     */
    String API_UPLOAD_IMAGE = "/app/front/post/upload/image";

    /**
     *
     */
    String API_POST_FORUM = "/app/front/study/post";

    /**
     *
     */
    String API_STUDY_STAR_POST = "/app/front/study/starpost";

    /**
     * 帖子详情
     */
    String API_POST_DETAIL = "/app/front/study/postdetail";

    /**
     * 帖子的一级评论列表
     */
    String API_STUDY_COMMENT_LIST = "/app/front/study/commentlist";

    /**
     * 收藏或取消帖子收藏
     */
    String API_STUDY_COLLECT_POST = "/app/front/study/collectpost";

    /**
     * 帖子的以及评论
     */
    String API_STUDY_COMMENT = "/app/front/study/comment";

    /**
     * 帖子的二级评论
     */
    String API_STUDY_REPLY = "/app/front/study/reply";

    /**
     * 帖子的二级评论列表
     */
    String API_STUDY_REPLY_LIST = "/app/front/study/replylist";

    /**
     * 帖子的评论点赞
     */
    String API_STUDY_STAR_COMMENT = "app/front/study/starcomment";

    /**
     * 批量删除我的收藏
     */
    String API_DELETE_MY_COLLECT = "/app/front/mine/batchdelcollect";

    /**
     * 我的发帖列表
     */
    String API_MINE_POST_LIST = "/app/front/mine/postlist";

    /**
     * 批量删除我的发帖列表
     */
    String API_MINE_DELETE_POST = "/app/front/mine/batchdelpost";

    /**
     * 我的跟帖列表
     */
    String API_MINE_COMMENT_LIST = "/app/front/mine/commentlist";

    /**
     * 批量删除我的跟帖列表
     */
    String API_MINE_DELETE_COMMENT = "/app/front/mine/batchdelcomment";

    /**
     * 退出登录
     */
    String API_LOGOUT = "/app/front/logout";

    /**
     * 分享接口
     * <p>
     * 如果分享的是帖子，则传入帖子id
     */
    String API_SHARE = "/app/front/share";

    /**
     * 党费缴纳信息
     */
    String API_PARTY_DUES = "/app/front/mine/party/dues";

    /**
     * 获取最新版本信息
     */
    String API_GET_LATEST_VERSION = "/app/front/androidversion";

    /**
     * 下载最新版本的apk
     */
    String API_DOWNLOAD_APK = "/app/front/pre/downloadapk";
}
