package com.shui.blacktea.entity.BaiduSong;

import java.util.List;

/**
 * Description:
 * Created by Juice_ on 2017/8/10.
 */

public class BaiduSongBillboardListEntity {

    /**
     * song_list : [{"artist_id":"89","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/d59cab8d47b4ae5cd500cbb67de9cc5c/276867491/276867491.jpg@s_1,w_150,h_150","pic_small":"http://musicdata.baidu.com/data2/pic/d59cab8d47b4ae5cd500cbb67de9cc5c/276867491/276867491.jpg@s_1,w_90,h_90","country":"内地","area":"0","publishtime":"2016-11-14","album_no":"1","lrclink":"http://musicdata.baidu.com/data2/lrc/b794e0a41a7806a92746d5ac3652dd8c/543756270/543756270.lrc","copy_type":"1","hot":"581092","all_artist_ting_uid":"1078","resource_type":"0","is_new":"0","rank_change":"0","rank":"1","all_artist_id":"89","style":"流行","del_status":"0","relate_status":"0","toneid":"0","all_rate":"64,128,256,320,flac","file_duration":200,"has_mv_mobile":0,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"lossless","info":"","has_filmtv":"0","si_proxycompany":"北京新奥视讯国际文化传媒有限公司","song_id":"276867440","title":"刚好遇见你","ting_uid":"1078","author":"李玉刚","album_id":"276867491","album_title":"刚好遇见你","is_first_publish":0,"havehigh":2,"charge":0,"has_mv":0,"learn":0,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"0000000000","artist_name":"李玉刚"},{"artist_id":"88","language":"国语","pic_big":"http://musicdata.baidu.com/data2/pic/5dd1ba70bb3e2d9d7fc79cd614130c8c/93104033/93104033.jpg@s_1,w_150,h_150","pic_small":"http://musicdata.baidu.com/data2/pic/5dd1ba70bb3e2d9d7fc79cd614130c8c/93104033/93104033.jpg@s_1,w_90,h_90","country":"内地","area":"0","publishtime":"2013-11-11","album_no":"3","lrclink":"http://musicdata.baidu.com/data2/lrc/238665983/238665983.lrc","copy_type":"1","hot":"766513","all_artist_ting_uid":"2517","resource_type":"0","is_new":"0","rank_change":"0","rank":"2","all_artist_id":"88","style":"流行","del_status":"0","relate_status":"0","toneid":"600907000002830308","all_rate":"24,64,128,160","file_duration":310,"has_mv_mobile":1,"versions":"","bitrate_fee":"{\"0\":\"0|0\",\"1\":\"0|0\"}","biaoshi":"","info":"","has_filmtv":"0","si_proxycompany":"华宇世博音乐文化（北 京）有限公司-海蝶音乐","song_id":"100575177","title":"你还要我怎样","ting_uid":"2517","author":"薛之谦","album_id":"93104033","album_title":"意外","is_first_publish":0,"havehigh":0,"charge":0,"has_mv":1,"learn":1,"song_source":"web","piao_id":"0","korean_bb_song":"0","resource_type_ext":"0","mv_provider":"1100000000","artist_name":"薛之谦"}]
     * billboard : {"billboard_type":"2","billboard_no":"2280","update_date":"2017-08-10","billboard_songnum":"1491","havemore":1,"name":"热歌榜","comment":"该榜单是根据百度音乐平台歌曲每周播放量自动生成的数据榜单，统计范围为百度音乐平台上的全部歌曲，每日更新一次","pic_s192":"http://a.hiphotos.baidu.com/ting/pic/item/09fa513d269759ee4764e3adb1fb43166d22dfa4.jpg","pic_s640":"http://b.hiphotos.baidu.com/ting/pic/item/5d6034a85edf8db1194683910b23dd54574e74df.jpg","pic_s444":"http://d.hiphotos.baidu.com/ting/pic/item/c83d70cf3bc79f3d98ca8e36b8a1cd11728b2988.jpg","pic_s260":"http://a.hiphotos.baidu.com/ting/pic/item/838ba61ea8d3fd1f1326c83c324e251f95ca5f8c.jpg","pic_s210":"http://business.cdn.qianqian.com/qianqian/pic/bos_client_58c1700bf56062108d8d622a95708032.jpg","web_url":"http://music.baidu.com/top/dayhot"}
     * error_code : 22000
     */

    private BaiduSongBillboard billboard;
    private int error_code;
    private List<BaiduSongBillboardEntity> song_list;

    public BaiduSongBillboard getBillboard() {
        return billboard;
    }

    public void setBillboard(BaiduSongBillboard billboard) {
        this.billboard = billboard;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<BaiduSongBillboardEntity> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<BaiduSongBillboardEntity> song_list) {
        this.song_list = song_list;
    }


}
