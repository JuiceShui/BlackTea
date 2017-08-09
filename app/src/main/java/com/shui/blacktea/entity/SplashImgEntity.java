package com.shui.blacktea.entity;

/**
 * Description:
 * Created by Juice_ on 2017/8/9.
 */

public class SplashImgEntity {

    /**
     * showapi_res_code : 0
     * showapi_res_error :
     * showapi_res_body : {"ret_code":0,"ret_message":"Success","data":{"title":"大自然的自我治愈","img_1920":"http://api.seqier.com/api/bing/img_1920","description":"连续的降雨把佛罗里达州的迈阿卡河州立公园变成了一个水世界。迈阿卡河州立公园是佛罗里达最古老、最大型的公园之一，绵延3.7万英亩。这里有各种各样的自然特征，包括一片森林湿地，也叫做沼泽生态系统，是由各种各样的树木组成的一种美妙的自然生态系统。","subtitle":"美国，迈阿卡河州立公园","copyright":"迈阿卡河州立公园内的沼泽生态区，美国佛罗里达州 (© Paul Marcellini/Tandem Stills + Motion)","date":"20170809","img_1366":"http://api.seqier.com/api/bing/img_1366"}}
     */

    private int showapi_res_code;
    private String showapi_res_error;
    private ShowapiResBodyBean showapi_res_body;

    public int getShowapi_res_code() {
        return showapi_res_code;
    }

    public void setShowapi_res_code(int showapi_res_code) {
        this.showapi_res_code = showapi_res_code;
    }

    public String getShowapi_res_error() {
        return showapi_res_error;
    }

    public void setShowapi_res_error(String showapi_res_error) {
        this.showapi_res_error = showapi_res_error;
    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * ret_code : 0
         * ret_message : Success
         * data : {"title":"大自然的自我治愈","img_1920":"http://api.seqier.com/api/bing/img_1920","description":"连续的降雨把佛罗里达州的迈阿卡河州立公园变成了一个水世界。迈阿卡河州立公园是佛罗里达最古老、最大型的公园之一，绵延3.7万英亩。这里有各种各样的自然特征，包括一片森林湿地，也叫做沼泽生态系统，是由各种各样的树木组成的一种美妙的自然生态系统。","subtitle":"美国，迈阿卡河州立公园","copyright":"迈阿卡河州立公园内的沼泽生态区，美国佛罗里达州 (© Paul Marcellini/Tandem Stills + Motion)","date":"20170809","img_1366":"http://api.seqier.com/api/bing/img_1366"}
         */

        private int ret_code;
        private String ret_message;
        private DataBean data;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public String getRet_message() {
            return ret_message;
        }

        public void setRet_message(String ret_message) {
            this.ret_message = ret_message;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * title : 大自然的自我治愈
             * img_1920 : http://api.seqier.com/api/bing/img_1920
             * description : 连续的降雨把佛罗里达州的迈阿卡河州立公园变成了一个水世界。迈阿卡河州立公园是佛罗里达最古老、最大型的公园之一，绵延3.7万英亩。这里有各种各样的自然特征，包括一片森林湿地，也叫做沼泽生态系统，是由各种各样的树木组成的一种美妙的自然生态系统。
             * subtitle : 美国，迈阿卡河州立公园
             * copyright : 迈阿卡河州立公园内的沼泽生态区，美国佛罗里达州 (© Paul Marcellini/Tandem Stills + Motion)
             * date : 20170809
             * img_1366 : http://api.seqier.com/api/bing/img_1366
             */

            private String title;
            private String img_1920;
            private String description;
            private String subtitle;
            private String copyright;
            private String date;
            private String img_1366;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getImg_1920() {
                return img_1920;
            }

            public void setImg_1920(String img_1920) {
                this.img_1920 = img_1920;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getSubtitle() {
                return subtitle;
            }

            public void setSubtitle(String subtitle) {
                this.subtitle = subtitle;
            }

            public String getCopyright() {
                return copyright;
            }

            public void setCopyright(String copyright) {
                this.copyright = copyright;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getImg_1366() {
                return img_1366;
            }

            public void setImg_1366(String img_1366) {
                this.img_1366 = img_1366;
            }
        }
    }
}
