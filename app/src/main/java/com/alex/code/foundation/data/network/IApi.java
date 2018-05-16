package com.alex.code.foundation.data.network;

import com.alex.code.foundation.base.BaseResponse;
import com.alex.code.foundation.bean.AddressBean;
import com.alex.code.foundation.bean.AffirmOrderBean;
import com.alex.code.foundation.bean.AlipayBean;
import com.alex.code.foundation.bean.AtteGoodsBean;
import com.alex.code.foundation.bean.CategoryBean;
import com.alex.code.foundation.bean.CommentBean;
import com.alex.code.foundation.bean.CommentDetailBean;
import com.alex.code.foundation.bean.CommentTypeBean;
import com.alex.code.foundation.bean.CreateOrderBean;
import com.alex.code.foundation.bean.EmailBean;
import com.alex.code.foundation.bean.FavoriteBean;
import com.alex.code.foundation.bean.FlashSaleBean;
import com.alex.code.foundation.bean.FootPrintBean;
import com.alex.code.foundation.bean.ForgetPWBean;
import com.alex.code.foundation.bean.ForgetSMSBean;
import com.alex.code.foundation.bean.GoodSpeBean;
import com.alex.code.foundation.bean.GoodsDetailBean;
import com.alex.code.foundation.bean.GoodsTypeBean;
import com.alex.code.foundation.bean.HomeBean;
import com.alex.code.foundation.bean.HomePageBean;
import com.alex.code.foundation.bean.HotGoodsBean;
import com.alex.code.foundation.bean.HotSearchBean;
import com.alex.code.foundation.bean.ImageCode;
import com.alex.code.foundation.bean.LoginInfo;
import com.alex.code.foundation.bean.LogisticsBean;
import com.alex.code.foundation.bean.OrderBean;
import com.alex.code.foundation.bean.OrderDetailBean;
import com.alex.code.foundation.bean.PaySuccessBean;
import com.alex.code.foundation.bean.ProfileInfo;
import com.alex.code.foundation.bean.RefundedListBean;
import com.alex.code.foundation.bean.RegisterBean;
import com.alex.code.foundation.bean.ReplyBean;
import com.alex.code.foundation.bean.SearchFilterBean;
import com.alex.code.foundation.bean.SecondSearchBean;
import com.alex.code.foundation.bean.ShopBean;
import com.alex.code.foundation.bean.ShopCartBean;
import com.alex.code.foundation.bean.ShopFocusBean;
import com.alex.code.foundation.bean.ShopTypeBean;
import com.alex.code.foundation.bean.StatisticsBean;
import com.alex.code.foundation.bean.TodoCommentBean;
import com.alex.code.foundation.bean.TotalShopGoodsBean;
import com.alex.code.foundation.bean.UploadPicBean;
import com.alex.code.foundation.bean.UserInfo;
import com.alex.code.foundation.bean.WxpayInfo;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

@Singleton
public interface IApi {
    // 所有后台 Api 在此声明


    /**
     * 注册接口
     * @param username 登录名称	3310
     * @param password 登录密码	Password1
     * @param email 邮箱	149234676@qq.com
     * @param phone 注册手机号	17702836079
     * @param code 手机验证码	836079
     * @return
     */
    @FormUrlEncoded
    @POST("http://open.yinchuanhitech.com/app/login/register")
    Observable<BaseResponse<RegisterBean>> register(@Field("username") String username, @Field("password") String password, @Field("email") String email,
                                      @Field("phone") String phone, @Field("code") String code);

    /**
     * 登录接口
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("http://open.yinchuanhitech.com/app/login/login")
    Observable<BaseResponse<LoginInfo>> login(@Field("username") String username, @Field("password") String password);

    /**
     *
     * @param type  登录方式
     * @param payload 第三方json信息
     * @return
     */
    @FormUrlEncoded
    @POST("http://open.yinchuanhitech.com/app/login/oauthLogin")
    Observable<BaseResponse<LoginInfo>> oauthLogin(@Field("type") String type, @Field("payload") String payload);

    /**
     * 发送图形验证码
     * @param phone 手机号
     * @param code 图形验证码
     * @return
     */
    @FormUrlEncoded
    @POST("/app/sms/send")
    Observable<BaseResponse<ImageCode>> getImageCode(@Field("phone") String phone, @Field("code") String code);

    /**
     *注销
     * @return
     */
    @POST("/app/user/logout")
    Observable<BaseResponse> logout();

    /**
     * 获取分类信息
     * @return
     */
    @POST("/app/category/select")
    Observable<BaseResponse<CategoryBean>> getCategoryInfo();

    /**
     * 获取个人中心主页信息
     * @param token
     * @return
     */
    @POST("/app/user/index")
    Observable<BaseResponse<UserInfo>> getUserInfo();


    /**
     * 获取收货地址列表
     * @return
     */
    @POST("/app/address")
    Observable<BaseResponse<AddressBean>> getAddressList();

    /**
     *添加地址
     * @param consigner 姓名
     * @param mobile    电话
     * @param province  省
     * @param city      市
     * @param district  区
     * @param address   街道地址
     * @param is_default    是否是默认地址
     * @return
     */
    @FormUrlEncoded
    @POST("/app/address/add")
    Observable<BaseResponse<AddressBean>> postAddress(@Field("consigner") String consigner, @Field("mobile") String mobile, @Field("province") String province, @Field("city") String city,
                                                      @Field("district") String district, @Field("address") String address, @Field("is_default") String is_default);

    /**
     * 更新地址
     * @param consigner
     * @param mobile
     * @param province
     * @param city
     * @param district
     * @param address
     * @param is_default
     * @return
     */
    @FormUrlEncoded
    @POST("/app/address/update")
    Observable<BaseResponse<AddressBean>> updateAddress(@Field("id") String id, @Field("consigner") String consigner, @Field("mobile") String mobile, @Field("province") String province, @Field("city") String city,
                                                      @Field("district") String district, @Field("address") String address, @Field("is_default") String is_default);

    /**
     * 更新收货默认地址
     * @param id 收货地址id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/address/updatedefault")
    Observable<BaseResponse> updateAddressDefault(@Field("id") String id);

    /**
     * 删除收货地址
     * @param id 收货地址id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/address/delete")
    Observable<BaseResponse> deleteAddress(@Field("id") String id);

    /**
     * 修改手机号
     * @param code 验证码
     * @param mobile 新手机号
     * @return
     */
    @FormUrlEncoded
    @POST("/app/user/modifyMobile")
    Observable<BaseResponse> updatePhone(@Field("code") String code, @Field("mobile") String mobile);

    /**
     * 修改密码
     * @param old_password
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("/app/user/modifyPassword")
    Observable<BaseResponse> updatePassword(@Field("old_password") String old_password, @Field("password") String password);

    /**
     * 找回密码短信验证码
     * @param mobile 	注册手机号
     * @param code  短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST("/app/user/forgetPwd")
    Observable<BaseResponse<ForgetSMSBean>> findPasswordSMS(@Field("mobile") String mobile, @Field("code") String code);

    /**
     * 找回密码
     * @param mobile 注册手机号
     * @param token 重置密码的验证token
     * @param password  	新密码
     * @return
     */
    @FormUrlEncoded
    @POST("/app/user/forgetPwdset")
    Observable<BaseResponse<ForgetPWBean>> findPassword(@Field("mobile") String mobile, @Field("token") String token, @Field("password") String password);

    /**
     * 获取个人中心信息
     * @return
     */
    @POST("/app/user/profile")
    Observable<BaseResponse<ProfileInfo>> getProfileInfo();

    /**
     * 修改个人信息
     * @param nick_name
     * @param sex
     * @param birthday
     * @return
     */
    @FormUrlEncoded
    @POST("/app/user/modify")
    Observable<BaseResponse<ProfileInfo>> updateProfileInfo(@Field("nick_name") String nick_name, @Field("sex") String sex,@Field("birthday") String birthday);

    @FormUrlEncoded
    @POST("/app/User/editUserHead")
    Observable<BaseResponse> updateHead(@Field("head") String head);

    /**
     * 获取商品收藏列表
     * @param id 商铺分类ID  获取全部传空
     * @return
     */
    @FormUrlEncoded
    @POST("/app/user/goodsCollections")
    Observable<BaseResponse<FavoriteBean>> getGoodsList(@Field("category") String id);

    /**
     * 收藏商品分类
     * @return
     */
    @POST("/app/user/goodsCategory")
    Observable<BaseResponse<GoodsTypeBean>> getGoodsCategory();

    /**
     * 获取店铺收藏列表
     * @param id 商铺分类ID  获取全部传空
     * @return
     */
    @FormUrlEncoded
    @POST("/app/user/shopCollections")
    Observable<BaseResponse<ShopBean>> getShopList(@Field("group") String id);

    /**
     * 收藏店铺分类
     * @return
     */
    @POST("/app/user/shopGroup")
    Observable<BaseResponse<ShopTypeBean>> getShopGroup();

    /**
     *删除收藏数据
     * @param type 收藏类型,shop,goods
     * @param id 收藏数据的id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/user/deleteCollections")
    Observable<BaseResponse<FavoriteBean>> deleteFavoriteList(@Field("type") String type, @Field("id") String... id);

    /**
     * 商品页面
     * @param id 商品id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/goodsDetail")
    Observable<BaseResponse<GoodsDetailBean>> getGoodsDetail(@Field("id") String id);

    /**
     * 获取商品评论总类型
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/getGoodsCount")
    Observable<BaseResponse<CommentTypeBean>> getGoodsCommentType(@Field("id") String id);

    /**
     * 获取商品评论
     * @param id 商品id
     * @param comments_type 评论类型
     * @param page_index 当前页码
     * @param page_size  每页条数
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/getGoodsComments")
    Observable<BaseResponse<CommentBean>> getGoodsComment(@Field("id") String id, @Field("comments_type") String comments_type, @Field("page_index") String page_index, @Field("page_size") String page_size);

    /**
     * 获取评论详情
     * @param id 评论id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/getEvalInfo")
    Observable<BaseResponse<CommentDetailBean>> getCommentDetail(@Field("id") String id);

    /**
     * 回复评论  返回所有回复列表
     * @param id 评论详情id
     * @param content  回复内容
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/getUserReply")
    Observable<BaseResponse<ReplyBean>> getReplyList(@Field("id") String id, @Field("content") String content);

    /**
     * 获取购物车列表
     * @return
     */
    @POST("/app/Cart/lists")
    Observable<BaseResponse<ShopCartBean>> getShopCartList();

    /**
     *删除购物车
     * @param cart_ids 购物车id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Cart/delCart")
    Observable<BaseResponse<ShopCartBean>> deleteCartGoods(@Field("cart_ids") String... cart_ids);

    /**
     *更新购物车商品数量
     * @param cart_id 购物车id
     * @param goods_num num
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Cart/changeNum")
    Observable<BaseResponse<ShopCartBean>> updateCartNum(@Field("cart_id") String cart_id, @Field("goods_num") String goods_num);

    /**
     * 更新购物车商品规格
     * @param cart_id
     * @param sku_id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Cart/editSku")
    Observable<BaseResponse<ShopCartBean>> updateGoodsSpe(@Field("cart_id") String cart_id, @Field("sku_id") String sku_id);

    /**
     *提交订单 如果是从购物车购买只需要穿is_cart 和 cart_ids
     * @param is_cart 是否是购物车里面支付 1是0不是
     * @param cart_ids 购物车id
     * @param goods_id 直接购买需要传递 商品id
     * @param goods_num 直接购买需要传递 商品数量
     * @param sku_id 直接购买需要传递 商品规格id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Cart/confirmOrderList")
    Observable<BaseResponse<AffirmOrderBean>> confirmOrderList(@Field("is_cart") String is_cart, @Field("cart_ids") String cart_ids, @Field("goods_id") String goods_id, @Field("goods_num") String goods_num, @Field("sku_id") String sku_id);

    /**
     * 获取商品规格
     * @param goods_id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Cart/getGoodSpec")
    Observable<BaseResponse<GoodSpeBean>> getGoodSpec(@Field("goods_id") String goods_id);

    /**
     * 添加购物车
     * @param goods_id 商品id
     * @param goods_num 商品数量
     * @param sku_id 商品规格id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Cart/addCart")
    Observable<BaseResponse> addShopCart(@Field("goods_id") String goods_id,@Field("goods_num") String goods_num,@Field("sku_id") String sku_id);

    /**
     * 获取订单列表
     * @param order_type type 0全部,1待付款,2待发货,3待收货,4待评价
     * @param page_index 当前页码
     * @param page_size 每页条数
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/memberOrderList")
    Observable<BaseResponse<OrderBean>> getOrderList(@Field("order_type") String order_type, @Field("page_index") String page_index, @Field("page_size") String page_size);

    /**
     * 获取订单详情
     * @param order_no 订单号
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/memberOrderInfo")
    Observable<BaseResponse<OrderDetailBean>> getOrderDetail(@Field("order_no") String order_no);

    /**
     * 获取热门搜索标签
     * @return
     */
    @POST("/app/goods/hotSearchList")
    Observable<BaseResponse<HotSearchBean>> getHotSearchList();

    /**
     * 快递100  物流信息 详细参数见文档
     * @param url
     * @param param
     * @param sign
     * @param customer
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<LogisticsBean> getLogisticsInfo(@Url String url, @Field("param") String param, @Field("sign") String sign, @Field("customer") String customer);

    /**
     * 首页数据
     * @return
     */
    @POST("/app/goods/shopIndex")
    Observable<BaseResponse<HomeBean>> getHomePageData();

    /**
     * 搜索列表
     * @param ser_type 搜索内型
     * @param condition 搜索内容
     * @param sort 排序方式
     * @param page_index 显示条数
     * @param page_size 	页码
     * @param low_par   最低价
     * @param tall_par 最高价
     * @param json 筛选条件json
     * @param cate 商品分类名字  从分类进入才需要
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/showGoods")
    Observable<BaseResponse<SecondSearchBean>> getSecondSearchList(@Field("ser_type") String ser_type, @Field("condition") String condition,
                                                                   @Field("sort") String sort, @Field("page_index") String page_index,
                                                                   @Field("page_size") String page_size, @Field("low_par") String low_par,
                                                                   @Field("tall_par") String tall_par,@Field("json") String json,
                                                                   @Field("cate") String cate);


    /**
     * 创建订单
     * @param is_cart   是否是购物车数据[0否1是]
     * @param phone 电话
     * @param address   	地址
     * @param user_name 收货人姓名
     * @param cart_ids  购物车id[购物车数据]
     * @param shop_id   店铺id[立即购买数据]
     * @param goods_id  商品id[立即购买数据]
     * @param goods_num 商品数量[立即购买数据]
     * @param sku_id    规格id[立即购买数据,没有规格传0]
     * @param shipping_money    运费
     * @param message   留言
     * @param goods_money   商品总价
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/createOrder")
    Observable<BaseResponse<CreateOrderBean>> createOrder(@Field("is_cart") String is_cart, @Field("phone") String phone,
                                                          @Field("address") String address, @Field("user_name") String user_name,
                                                          @Field("cart_ids") String cart_ids, @Field("shop_id") String shop_id,
                                                          @Field("goods_id") String goods_id, @Field("goods_num") String goods_num,
                                                          @Field("sku_id") String sku_id, @Field("shipping_money") String shipping_money,
                                                          @Field("message") String message, @Field("goods_money") String goods_money);

    /**
     * 获取筛选条件
     * @param ser_type  搜索类型
     * @param condition 搜索内容
     * @param cate 商品分类名称   分类进入才需要
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/twoSearch")
    Observable<BaseResponse<SearchFilterBean>> getSearchFilterList(@Field("ser_type") String ser_type, @Field("condition") String condition, @Field("cate") String cate);

    /**
     * 获取店铺首页
     * @param shop_id   店铺id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/shop/shopIndex")
    Observable<BaseResponse<HomePageBean>> getShopHomePage(@Field("shop_id") String shop_id);

    /**
     * 关注店铺
     * @param shop_id 店铺id
     * @param favo_type 	1：关注，0：取消关注
     * @return
     */
    @FormUrlEncoded
    @POST("/app/shop/atteShop")
    Observable<BaseResponse<ShopFocusBean>> updateShopFocus(@Field("fav_id") String shop_id, @Field("state") String favo_type);

    /**
     * 获取店铺全部商品
     * @param shop_id   店铺id
     * @param condition 搜索内容 没有传""
     * @param sort  排序
     * @param page_index 页码
     * @param page_size 每页数据
     * @return
     */
    @FormUrlEncoded
    @POST("/app/shop/shopGoodsList")
    Observable<BaseResponse<TotalShopGoodsBean>> getTotalShopGoods(@Field("shop_id") String shop_id, @Field("condition") String condition, @Field("sort") String sort, @Field("page_index") String page_index, @Field("page_size") String page_size);

    /**
     * 获取热门商品
     * @param sort 排序 1：综合，2:24小时热卖，3：销量
     * @param page_index 页码
     * @param page_size 每页数据
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/hotGoods")
    Observable<BaseResponse<HotGoodsBean>> getHotGoods(@Field("sort") String sort, @Field("page_index") String page_index, @Field("page_size") String page_size);

    /**
     * 多图上传接口
     * @param partList
     * @return
     */
    @Multipart
    @POST("/app/upload/multiUploadFile")
    Observable<BaseResponse<UploadPicBean>> uploadPic(@Part List<MultipartBody.Part> partList);

    /**
     * 获取新品列表
     * @param sort 排序方式
     * @param page_index 显示条数
     * @param page_size 	页码
     * @param low_par   最低价
     * @param tall_par 最高价
     * @param json 筛选条件json
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/newProducts")
    Observable<BaseResponse<HotGoodsBean>> getNewGoods(@Field("sort") String sort, @Field("page_index") String page_index,
                                                       @Field("page_size") String page_size, @Field("low_par") String low_par,
                                                       @Field("tall_par") String tall_par,@Field("json") String json);

    /**
     * 获取筛选条件 新品
     * @return
     */
    @POST("/app/goods/nowFiltrate")
    Observable<BaseResponse<SearchFilterBean>> getSearchFilterList();

    /**
     * 上传评论
     * @param goods_id  商品id
     * @param order_no  订单id
     * @param order_goods_id  	订单商品id
     * @param content   评价内容
     * @param image 图片链接
     * @param anon  是否匿名 	0 不是 1 匿名
     * @param scores    	店铺评价星级
     * @param physical  物流评分
     * @param explain_type  评价等级
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/userEval")
    Observable<BaseResponse<TodoCommentBean>> postComment(@Field("goods_id") String goods_id, @Field("order_no") String order_no,
                                                          @Field("order_goods_id") String order_goods_id, @Field("content") String content,
                                                          @Field("anon") String anon, @Field("explain_type") String explain_type,
                                                          @Field("scores") String scores, @Field("physical") String physical,
                                                          @Field("image") String image);

    /**
     * 限时抢购
     * @param start_time 开始时间
     * @param end_time  结束时间
     * @param page_index
     * @param page_size
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/timeGoodList")
    Observable<BaseResponse<FlashSaleBean>> getFlashSaleList(@Field("start_time") String start_time, @Field("end_time") String end_time, @Field("page_index") String page_index,
                                                             @Field("page_size") String page_size);

    /**
     * 店铺收藏
     * @param id
     * @param is_fav
     * @return
     */
    @FormUrlEncoded
    @POST("/app/goods/atteGoods")
    Observable<BaseResponse<AtteGoodsBean>> updateGoodsFocus(@Field("id") String id, @Field("is_fav") String is_fav);

    /**
     * 微信支付
     * @param order_no 订单号
     * @param payment 支付方式 ALIPAY,WXPAY,UNIONPAY
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Orderpay/pay")
    Observable<BaseResponse<WxpayInfo>> performWechatPay(@Field("order_no") String order_no, @Field("payment") String payment);

    /**
     * 支付宝支付
      * @param order_no
     * @param payment
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Orderpay/pay")
    Observable<BaseResponse<AlipayBean>> performAliPay(@Field("order_no") String order_no, @Field("payment") String payment);

    /**
     * 支付成功后信息
     * @param order_no
     * @return
     */
    @FormUrlEncoded
    @POST("/app/orderPay/finish")
    Observable<BaseResponse<PaySuccessBean>> getPaySuccess(@Field("order_no") String order_no);


    /**
     * 我的足迹
     * @return
     */
    @POST("/app/history/getViewHistory")
    Observable<BaseResponse<FootPrintBean>> getFootPrint();

    /**
     * 删除足迹
     * @param goods_id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/history/deleteViewHistory")
    Observable<BaseResponse<FavoriteBean>> deleteFootPrint(@Field("goods_id") String... goods_id);

    /**
     * 提醒发货
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/remindDelivery")
    Observable<BaseResponse> remindDelivery(@Field("order_no") String order_no);

    /**
     * 延长收货
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/extendReceive")
    Observable<BaseResponse> extendReceive(@Field("order_no") String order_no);

    /**
     * 确认收货
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/confirmReceive")
    Observable<BaseResponse> confirmReceive(@Field("order_no") String order_no);

    /**
     * 取消订单
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/cancel")
    Observable<BaseResponse> cancelOrder(@Field("order_no") String order_no);

    /**
     * 退款 单个商品
     * @param reason    	退款原因
     * @param phone 电话
     * @param money 退款金额
     * @param content   内容
     * @param order_no  订单号
     * @param order_goods_id 订单商品id
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/applyRefund")
    Observable<BaseResponse> postRefund(@Field("reason") String reason, @Field("phone") String phone, @Field("money") String money,
                                                          @Field("content") String content, @Field("order_no") String order_no,
                                                          @Field("order_goods_id") String order_goods_id,@Field("refund_num") String refund_num);

    /**
     * 退款 整个订单
     * @param reason
     * @param phone
     * @param money
     * @param content
     * @param order_no
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/refundOrder")
    Observable<BaseResponse> postOrderRefund(@Field("reason") String reason, @Field("phone") String phone, @Field("money") String money,
                                        @Field("content") String content, @Field("order_no") String order_no);

    /**
     *退款列表
     * @param page_index
     * @param page_size
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Order/refundList")
    Observable<BaseResponse<RefundedListBean>> getRefundedList(@Field("page_index") String page_index, @Field("page_size") String page_size);

    /**
     * 绑定邮箱
     * @param emali
     * @return
     */
    @FormUrlEncoded
    @POST("/app/Captcha/verifyEmail")
    Observable<BaseResponse<EmailBean>> bindEmail(@Field("emali") String emali);

    /**
     * 获取消费统计
     * @param year 年份  不传为默认当前年份
     * @return
     */
    @FormUrlEncoded
    @POST("/app/History/userOrderStatistics")
    Observable<BaseResponse<StatisticsBean>> getConsumeStatistics(@Field("year") String year);
}
