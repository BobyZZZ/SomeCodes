package com.bb.network.test.bean;

import java.util.List;

/**
 * Created by Android Studio.
 * User: Boby
 * Date: 2020/12/5
 * Time: 20:40
 */
public class AllSoft {
    @Override
    public String toString() {
        return "AllSoft{" +
                "ok=" + ok +
                ", male=" + male +
                ", female=" + female +
                ", picture=" + picture +
                ", press=" + press +
                '}';
    }

    /**
     * male : [{"name":"玄幻","bookCount":193702,"monthlyCount":25782,"icon":"/icon/玄幻_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3419086%2F3419086_9a68defd6a764a4c8c15a4c30fb474f4.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F175247%2F175247_4da137ef840342c8ac06d159fd96c0c9.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2107590%2F2107590_b052b63dafea4b99aec6ffaf2d758475.jpg%2F"]},{"name":"奇幻","bookCount":26808,"monthlyCount":2369,"icon":"/icon/奇幻_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2251198%2F2251198_d29a158db1634ae9a75020c25dfaeca4.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F713823%2F713823_818033fd9014426193ee5740fbee29f2.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2028848%2F2028848_8ca89602fdff4a968d0c62be99159106.jpg%2F"]},{"name":"武侠","bookCount":18550,"monthlyCount":1729,"icon":"/icon/武侠_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2214174%2F2214174_eba7ec7dfdca413abd267b295de76227.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F608539%2F608539_69218f32675e4be5a1da68ac134eeee2.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F47884%2F47884_42e2646944d7422a9ac04c640d4e6ebc.jpg%2F"]},{"name":"仙侠","bookCount":55928,"monthlyCount":7918,"icon":"/icon/仙侠_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2014980%2F2014980_ea81d2606fc741ee9f6cce207bc69e2c.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3433465%2F3433465_b8e4ff980f0d4f20843561fbf97ec934.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3377379%2F3377379_873ab101c7fe44ea88767d98beb0fe46.jpg%2F"]},{"name":"都市","bookCount":116044,"monthlyCount":23373,"icon":"/icon/都市_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2217899%2F2217899_03f5f02cbbe4415e98c6e3746e4aca50.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F189964%2F189964_74ae972b054c4b4a874d0de35466b886.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1179978%2F1179978_37af2c48f31e47e395b3474258849143.jpg%2F"]},{"name":"职场","bookCount":4407,"monthlyCount":995,"icon":"/icon/职场_.png","bookCover":["/agent/http://img.1391.com/api/v1/bookcenter/cover/1/3447355/3447355_24df9ed7baee4401ba2180c888cd0fff.jpg/","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1418311%2F1418311_da4a9791ef7e4fbb99733ff70615058d.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3434931%2F3434931_90665d9a18ce49298a16d568137d1499.jpg%2F"]},{"name":"历史","bookCount":26365,"monthlyCount":3305,"icon":"/icon/历史_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3449468%2F3449468_cf506dc9ee4244ab86ef877d430d4394.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3447074%2F3447074_7bae61d762b74db1b9ac605f6e8313b9.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3348337%2F3348337_ca6be5b41a824e72a40d63cef72944da.jpg%2F"]},{"name":"军事","bookCount":6345,"monthlyCount":882,"icon":"/icon/军事_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F685415%2F685415_f3c59a7a63dd4f9c843b619fbafdaaa1.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3439223%2F3439223_cb49aee8ad58480896dc3d8b9dd7c376.jpg%2F","/agent/http://img.1391.com/api/v1/bookcenter/cover/1/685389/685389_6d1e638172fa4cb8853931e9e5d0431f.jpg/"]},{"name":"游戏","bookCount":26822,"monthlyCount":2912,"icon":"/icon/游戏_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3417895%2F3417895_f251cf31800a407e8be43bf5a2086f64.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F859483%2F859483_474171a428b14d658f3c7fe8ca9b30cf.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3445630%2F3445630_c02490a0f5334c24b276bc922739ea16.jpg%2F"]},{"name":"竞技","bookCount":1534,"monthlyCount":112,"icon":"/icon/竞技_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3494135%2F3494135_147c6c73245e44aab59dee8d90dbe6e9.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F84885%2F84885_10ef01fc5db24717a89f13ec48447fb7.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2207753%2F2207753_cdd611cf9fed47aa8379fdfb1d4830e0.jpg%2F"]},{"name":"科幻","bookCount":42369,"monthlyCount":3289,"icon":"/icon/科幻_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3418172%2F3418172_ed93b325f58640f493f6e4aa8308e21b.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2214942%2F2214942_9584ef086e1b4d1ba3b4c24802772f1b.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3507290%2F3507290_047cde8a62724e868fbbe4e07cf32137.jpg%2F"]},{"name":"灵异","bookCount":27756,"monthlyCount":2518,"icon":"/icon/灵异_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F859668%2F859668_d256a73bba614c2a8d25153014504ab7.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F857828%2F857828_b3fec132b61d403292209cb11c73f3de.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2305223%2F2305223_44590faabb3f446488ede9aa8e1c99d4.jpg%2F"]},{"name":"同人","bookCount":4976,"monthlyCount":296,"icon":"/icon/同人_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3459391%2F3459391_3697f5925ab64aadb55596467db3297b.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3449780%2F3449780_fc9244d1c821436d88fb9a7f5b1ea7f5.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3449591%2F3449591_7799f6c9c7ed468b8b988d00f1ed528b.jpg%2F"]},{"name":"轻小说","bookCount":21826,"monthlyCount":998,"icon":"/icon/轻小说_.png","bookCover":["/agent/http://img.1391.com/api/v1/bookcenter/cover/1/3436810/3436810_6e465fea51034c63b7bb527df2e0c81f.jpg/","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3497883%2F3497883_7df63a76b5b349a685e8a2130b5f832a.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3504150%2F3504150_9adf95ed617d407996e1a7e91d3ab047.jpg%2F"]}]
     * female : [{"name":"古代言情","bookCount":215160,"monthlyCount":22263,"icon":"/icon/古代言情_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3428140%2F3428140_92c7b9d5722145ca8589c1b4dfecf886.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3382359%2F3382359_680557b737d24c2aac935d6ec48f2665.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3394294%2F3394294_ea97129a55274ce7b1ec6fabf6597051.jpg%2F"]},{"name":"现代言情","bookCount":248937,"monthlyCount":34157,"icon":"/icon/现代言情_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3445814%2F3445814_d0ecf65944324632a21f3b93bffa5cdb.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3445841%2F3445841_685647c6057a4dd0adea63a9a4958be8.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3352940%2F3352940_c3dff866a51f4add88118ed05a7d1c39.jpg%2F"]},{"name":"青春校园","bookCount":66494,"monthlyCount":3186,"icon":"/icon/青春校园_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2329890%2F2329890_8764eac5e6e748bf86fc329021f915c6.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3345845%2F3345845_0affa0e9dad94fc68179249d923ca88e.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3483994%2F3483994_d529105358f8475f85f0c3e9459f47f8.jpg%2F"]},{"name":"纯爱","bookCount":1302,"monthlyCount":289,"icon":"/icon/耽美_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F856757%2F_856757_985516.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F138450%2F_138450_622881.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F856604%2F_856604_528616.jpg%2F"]},{"name":"玄幻奇幻","bookCount":10575,"monthlyCount":398,"icon":"/icon/玄幻奇幻_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3317151%2F3317151_f62454c10c7547c7a3eadc3decbb0dd7.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F863875%2F863875_f9104d3d011a4e8e9d1c8401cdceccf9.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1187278%2F1187278_a6967c28153141f8a21bee3e5dca0412.jpg%2F"]},{"name":"武侠仙侠","bookCount":5811,"monthlyCount":648,"icon":"/icon/武侠仙侠_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F31651%2F31651_7a5efbb1427046b28ab1cf68c35e6be5.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F83337%2F83337_a16ff6b42f554fc28dd46741111e721e.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3364878%2F3364878_be9dba439d5e4c659c8fa61ada43ca94.jpg%2F"]},{"name":"科幻","bookCount":5486,"monthlyCount":256,"icon":"/icon/科幻_.png","bookCover":["/agent/http://img.1391.com/api/v1/bookcenter/cover/1/3445766/3445766_f5e769c2199d4b9e85a942fcf529ac2a.jpg/","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3419111%2F3419111_e871c1c044ba4dcd8ba4da7ad21867de.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3419112%2F3419112_ce9d072411f54b4db6d64932202bfdc1.jpg%2F"]},{"name":"游戏竞技","bookCount":87,"monthlyCount":3,"icon":"/icon/游戏竞技_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F76715%2F_76715_463406.jpg%2F","/agent/http://ww2.sinaimg.cn/mw690/bd00715dgw1edd6lol47nj205k07s752.jpg","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F78992%2F_78992_318995.jpg%2F"]},{"name":"悬疑灵异","bookCount":879,"monthlyCount":241,"icon":"/icon/悬疑灵异_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3511012%2F3511012_17ea263923d94cd4a27247e5b464ec04.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2238006%2F2238006_17850b7f498348bb8aa58180cdce39d5.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3337964%2F3337964_952b7fb2d07e44249e364c335c72b344.jpg%2F"]},{"name":"同人","bookCount":3891,"monthlyCount":111,"icon":"/icon/同人_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3450598%2F3450598_d9f0fed63ac04016a09a46000c5b6176.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2248174%2F2248174_0fcad84c54d740e38fd431ee7f2a1498.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3412916%2F3412916_e5743f77392e430db6fd47b48ffa7da7_default_cover.png%2F"]},{"name":"女尊","bookCount":2018,"monthlyCount":462,"icon":"/icon/女尊_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2241654%2F2241654_c059c1e73282495d9dfb1e9bba9bae07.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2190173%2F2190173_351f1c232f2e495d95cbe4564c32bf5c.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2617445%2F2617445_a39a828659454bb1947f695fd704e5f7.jpg%2F"]},{"name":"莉莉","bookCount":590,"monthlyCount":34,"icon":"/icon/百合_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F71870%2F_71870_587799.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F899513%2F_899513_496651.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F899244%2F_899244_854945.jpg%2F"]}]
     * picture : [{"name":"热血","bookCount":788,"monthlyCount":100,"icon":"/icon/热血_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F168407%2F168407_749f2be713ea464193b3b4d8e314e00a.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F177573%2F177573_d6c9e5819ec3413890b1114f5afb56bb.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2284741%2F2284741_a5c1406da9174f69bf6e52f998e01acc.jpg%2F"]},{"name":"魔幻","bookCount":310,"monthlyCount":35,"icon":"/icon/魔幻_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2225868%2F2225868_b5c60d27274348a98e916eef7d2ce966.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F177398%2F177398_1080bf31750c46fa84606b5b7755dac7.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2225787%2F2225787_b75c7142b03e445898b3079c73859fce.png%2F"]},{"name":"科幻","bookCount":26,"monthlyCount":0,"icon":"/icon/科幻_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F149517%2F0cbbc5b49903426b8b9e3d9ee5318fb0.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2193925%2F2193925_a85da3f425f94f2e8903344c297e474d.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3394970%2F3394970_6a84caa0f9bc4557b03df2db36091f25.png%2F"]},{"name":"恋爱","bookCount":1296,"monthlyCount":127,"icon":"/icon/恋爱_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2183999%2F2183999_8aece9c7f0944830b8c9071350e8d615.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F180377%2F180377_5f2fbf29d81a479c8ac53897259bdb22.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2619211%2F2619211_ef8d78f6d1954912bcd1d3924aae6910.jpg%2F"]},{"name":"搞笑","bookCount":1276,"monthlyCount":54,"icon":"/icon/搞笑_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F173734%2F173734_c6d2e07448574d86801f1ad97b39aa24.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2185190%2F2185190_02b51397c05d48b1baefd51fcaaa6023.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F173726%2F5d77497eb278465d80f7f8209d6e68a1.jpg%2F"]},{"name":"悬疑","bookCount":368,"monthlyCount":59,"icon":"/icon/悬疑_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F180667%2F180667_2c8158fe554f4e06b1911385b6ba721f.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3316463%2F3316463_2e7696feadcd4271a578b6211c2f5e90.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F177507%2F177507_64ab234dd89342fdbdb591aeee63bec4.jpg%2F"]},{"name":"少儿","bookCount":1739,"monthlyCount":12,"icon":"/icon/少儿_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F196713%2F100b2df5c8f44a3d98f587b08a9414a6.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F196724%2F519a34a6d7a14f4ca5fa4502ebc8f112.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2263305%2F2263305_68d461d2e64b43e0add65c43d08bfa26.jpg%2F"]}]
     * press : [{"name":"传记名著","bookCount":405,"monthlyCount":79,"icon":"/icon/传记名著_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2053903%2F2053903_3287435e093d46bbb120b5e15f75246b.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1443395%2F_1443395_783757.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3348591%2F3348591_d5ad226b7ab142b2b856d2977786adaa.jpg%2F"]},{"name":"出版小说","bookCount":42171,"monthlyCount":3998,"icon":"/icon/出版小说_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2259689%2F2259689_780e6dd441124ec680c619435a882f84.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3415240%2F3415240_f44ca0b2a03e48bf8493d45b19c5f0f0.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3343035%2F3343035_ef3214aebf4c4bb388ae0901e33fcc8f.jpg%2F"]},{"name":"人文社科","bookCount":53290,"monthlyCount":5120,"icon":"/icon/人文社科_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2259480%2F2259480_126a00782ad741f0b1390f0063e96004.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2088127%2F2088127_856a60135b3041dea9c50448d14ae046.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1468254%2F_1468254_608300.jpg%2F"]},{"name":"生活时尚","bookCount":4192,"monthlyCount":217,"icon":"/icon/生活时尚_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1128211%2F_1128211_431886.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2330326%2F2330326_c1ecace3a2dd4caca6bfea8e975a60f4.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2612290%2F2612290_cadf5eeb35cf4b2189a4152df5b6d8a8.jpg%2F"]},{"name":"经管理财","bookCount":352,"monthlyCount":272,"icon":"/icon/经管理财_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F858657%2F_858657_029243.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F858521%2F_858521_173996.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F858565%2F_858565_439388.jpg%2F"]},{"name":"青春言情","bookCount":771,"monthlyCount":133,"icon":"/icon/青春言情_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F55915%2F55915_58e2847fb310476b9a90a7865dd3fdcc.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2202149%2F2202149_ab670bc133bb44d9a83264284a97a8bd.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3430403%2F3430403_7f338a2382a94deb978a3a3fade9082e.jpg%2F"]},{"name":"外文原版","bookCount":164,"monthlyCount":17,"icon":"/icon/外文原版_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1121334%2F_1121334_234662.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2098878%2F2098878_2fdd99ed39db448f8bafa23316a5e668.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2101044%2F2101044_615b234679f3409792ff7569a68adc7b_default_cover.png%2F"]},{"name":"政治军事","bookCount":43,"monthlyCount":10,"icon":"/icon/政治军事_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F856279%2F_856279_216231.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2027766%2F2027766_8fc647dbeccc4a3886d5a197b5909719.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2027767%2F2027767_ffc70d2cb4a5466a923cb597c4aa76d9.jpg%2F"]},{"name":"成功励志","bookCount":828,"monthlyCount":345,"icon":"/icon/成功励志_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2594755%2F2594755_1ff3c281f8d5455d93158d12b71b3c11.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F858633%2F858633_0bd615535c5a46b89e414604f47b562f.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F858627%2F_858627_838818.jpg%2F"]},{"name":"育儿健康","bookCount":623,"monthlyCount":109,"icon":"/icon/育儿健康_.png","bookCover":["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F858537%2F_858537_270773.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F576631%2F_576631_709216.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2198336%2F2198336_72e7ac587b424b0e9f627a8177164db8.jpg%2F"]}]
     * ok : true
     */

    private boolean ok;
    private List<MaleBean> male;
    private List<FemaleBean> female;
    private List<PictureBean> picture;
    private List<PressBean> press;

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<MaleBean> getMale() {
        return male;
    }

    public void setMale(List<MaleBean> male) {
        this.male = male;
    }

    public List<FemaleBean> getFemale() {
        return female;
    }

    public void setFemale(List<FemaleBean> female) {
        this.female = female;
    }

    public List<PictureBean> getPicture() {
        return picture;
    }

    public void setPicture(List<PictureBean> picture) {
        this.picture = picture;
    }

    public List<PressBean> getPress() {
        return press;
    }

    public void setPress(List<PressBean> press) {
        this.press = press;
    }

    public static class MaleBean {
        @Override
        public String toString() {
            return "MaleBean{" +
                    "name='" + name + '\'' +
                    ", bookCount=" + bookCount +
                    ", monthlyCount=" + monthlyCount +
                    ", icon='" + icon + '\'' +
                    ", bookCover=" + bookCover +
                    '}';
        }

        /**
         * name : 玄幻
         * bookCount : 193702
         * monthlyCount : 25782
         * icon : /icon/玄幻_.png
         * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3419086%2F3419086_9a68defd6a764a4c8c15a4c30fb474f4.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F175247%2F175247_4da137ef840342c8ac06d159fd96c0c9.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2107590%2F2107590_b052b63dafea4b99aec6ffaf2d758475.jpg%2F"]
         */

        private String name;
        private int bookCount;
        private int monthlyCount;
        private String icon;
        private List<String> bookCover;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public void setMonthlyCount(int monthlyCount) {
            this.monthlyCount = monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getBookCover() {
            return bookCover;
        }

        public void setBookCover(List<String> bookCover) {
            this.bookCover = bookCover;
        }
    }

    public static class FemaleBean {
        @Override
        public String toString() {
            return "FemaleBean{" +
                    "name='" + name + '\'' +
                    ", bookCount=" + bookCount +
                    ", monthlyCount=" + monthlyCount +
                    ", icon='" + icon + '\'' +
                    ", bookCover=" + bookCover +
                    '}';
        }

        /**
         * name : 古代言情
         * bookCount : 215160
         * monthlyCount : 22263
         * icon : /icon/古代言情_.png
         * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3428140%2F3428140_92c7b9d5722145ca8589c1b4dfecf886.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3382359%2F3382359_680557b737d24c2aac935d6ec48f2665.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3394294%2F3394294_ea97129a55274ce7b1ec6fabf6597051.jpg%2F"]
         */

        private String name;
        private int bookCount;
        private int monthlyCount;
        private String icon;
        private List<String> bookCover;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public void setMonthlyCount(int monthlyCount) {
            this.monthlyCount = monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getBookCover() {
            return bookCover;
        }

        public void setBookCover(List<String> bookCover) {
            this.bookCover = bookCover;
        }
    }

    public static class PictureBean {
        @Override
        public String toString() {
            return "PictureBean{" +
                    "name='" + name + '\'' +
                    ", bookCount=" + bookCount +
                    ", monthlyCount=" + monthlyCount +
                    ", icon='" + icon + '\'' +
                    ", bookCover=" + bookCover +
                    '}';
        }

        /**
         * name : 热血
         * bookCount : 788
         * monthlyCount : 100
         * icon : /icon/热血_.png
         * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F168407%2F168407_749f2be713ea464193b3b4d8e314e00a.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F177573%2F177573_d6c9e5819ec3413890b1114f5afb56bb.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2284741%2F2284741_a5c1406da9174f69bf6e52f998e01acc.jpg%2F"]
         */

        private String name;
        private int bookCount;
        private int monthlyCount;
        private String icon;
        private List<String> bookCover;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public void setMonthlyCount(int monthlyCount) {
            this.monthlyCount = monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getBookCover() {
            return bookCover;
        }

        public void setBookCover(List<String> bookCover) {
            this.bookCover = bookCover;
        }
    }

    public static class PressBean {
        @Override
        public String toString() {
            return "PressBean{" +
                    "name='" + name + '\'' +
                    ", bookCount=" + bookCount +
                    ", monthlyCount=" + monthlyCount +
                    ", icon='" + icon + '\'' +
                    ", bookCover=" + bookCover +
                    '}';
        }

        /**
         * name : 传记名著
         * bookCount : 405
         * monthlyCount : 79
         * icon : /icon/传记名著_.png
         * bookCover : ["/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F2053903%2F2053903_3287435e093d46bbb120b5e15f75246b.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F1443395%2F_1443395_783757.jpg%2F","/agent/http%3A%2F%2Fimg.1391.com%2Fapi%2Fv1%2Fbookcenter%2Fcover%2F1%2F3348591%2F3348591_d5ad226b7ab142b2b856d2977786adaa.jpg%2F"]
         */

        private String name;
        private int bookCount;
        private int monthlyCount;
        private String icon;
        private List<String> bookCover;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getBookCount() {
            return bookCount;
        }

        public void setBookCount(int bookCount) {
            this.bookCount = bookCount;
        }

        public int getMonthlyCount() {
            return monthlyCount;
        }

        public void setMonthlyCount(int monthlyCount) {
            this.monthlyCount = monthlyCount;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public List<String> getBookCover() {
            return bookCover;
        }

        public void setBookCover(List<String> bookCover) {
            this.bookCover = bookCover;
        }
    }
}
