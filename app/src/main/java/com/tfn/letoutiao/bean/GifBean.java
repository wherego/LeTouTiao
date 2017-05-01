package com.tfn.letoutiao.bean;

import java.util.List;

/**
 * Created by tf on 2017/5/1.
 */

public class GifBean {

    /**
     * reason : success
     * result : [{"content":"电动车真是太牛逼啦","hashId":"CA1FD09741D0B5C88770893943018AC8","unixtime":"1426867952","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/CA1FD09741D0B5C88770893943018AC8.jpg"},{"content":"真想知道这车还能开不？","hashId":"A4E49E3B50327DD0F02700CC39E94D55","unixtime":"1426869748","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/A4E49E3B50327DD0F02700CC39E94D55.jpg"},{"content":"知道妈妈为什么出门带你了吧","hashId":"8AEAA0857EB08F7EAACA3BC6C1A7ED00","unixtime":"1426871555","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/8AEAA0857EB08F7EAACA3BC6C1A7ED00.jpg"},{"content":"只能说二逼青年欢乐多了。。","hashId":"3CE1366988E008BE37220F13A9E66CDE","unixtime":"1426873379","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/3CE1366988E008BE37220F13A9E66CDE.jpg"},{"content":"今天就在下雨，你还在等什么","hashId":"6ACB5A7860EFC9B2C10B11435909428B","unixtime":"1426875148","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/6ACB5A7860EFC9B2C10B11435909428B.jpg"},{"content":"过儿，姑姑染了个金发还美么","hashId":"C13CE8F49D9A1EB97955FDF15E6E387D","unixtime":"1426877543","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/C13CE8F49D9A1EB97955FDF15E6E387D.jpg"},{"content":"0∶5是什么意思？","hashId":"CB1178EFD2A5A7A643DA48A33EF37A27","unixtime":"1426878781","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/CB1178EFD2A5A7A643DA48A33EF37A27.jpg"},{"content":"居然还能如此淡定的聊天...","hashId":"C35FC5000769941010B319D4DB43FCBC","unixtime":"1426880548","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/C35FC5000769941010B319D4DB43FCBC.jpg"},{"content":"阿三家的爸爸.","hashId":"CD35CE60EE45FB010D4B5803C70EA51A","unixtime":"1426882342","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/CD35CE60EE45FB010D4B5803C70EA51A.jpg"},{"content":"老板。再来十罐","hashId":"BBE61140251816B820320342A9C1C994","unixtime":"1426884160","url":"http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/BBE61140251816B820320342A9C1C994.jpg"}]
     * error_code : 0
     */

    private String reason;
    private int error_code;
    private List<ResultBean> result;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * content : 电动车真是太牛逼啦
         * hashId : CA1FD09741D0B5C88770893943018AC8
         * unixtime : 1426867952
         * url : http://juheimg.oss-cn-hangzhou.aliyuncs.com/joke/201503/21/CA1FD09741D0B5C88770893943018AC8.jpg
         */

        private String content;
        private String hashId;
        private String unixtime;
        private String url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getHashId() {
            return hashId;
        }

        public void setHashId(String hashId) {
            this.hashId = hashId;
        }

        public String getUnixtime() {
            return unixtime;
        }

        public void setUnixtime(String unixtime) {
            this.unixtime = unixtime;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
