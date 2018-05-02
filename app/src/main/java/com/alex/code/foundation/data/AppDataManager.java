package com.alex.code.foundation.data;

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
import com.alex.code.foundation.data.network.IApi;
import com.alex.code.foundation.data.preference.IPreference;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.Part;
import retrofit2.http.Url;

@Singleton
public final class AppDataManager implements IDataManager {

    private final IPreference mIPreference;

    private final IApi mIApi;

    @Inject
    public AppDataManager(IPreference IPreference, IApi IApi) {
        mIPreference = IPreference;
        mIApi = IApi;
    }

    @Override
    public IPreference getPreferenceHelper() {
        return mIPreference;
    }

    @Override
    public IApi getApi() {
        return mIApi;
    }

    @Override
    public void setIsFirstRun(boolean isFirstRun) {
        mIPreference.setIsFirstRun(isFirstRun);
    }

    @Override
    public boolean isFirstRun() {
        return mIPreference.isFirstRun();
    }

    @Override
    public void setToken(String token) {
        mIPreference.setToken(token);
    }

    @Override
    public String getToken() {
        return mIPreference.getToken();
    }

    @Override
    public void setSearchHistory(String searchHistory) {
        mIPreference.setSearchHistory(searchHistory);
    }

    @Override
    public String getSearchHistory() {
        return mIPreference.getSearchHistory();
    }

    @Override
    public Observable<BaseResponse<RegisterBean>> register(@Field("username") String username, @Field("password") String password, @Field("email") String email, @Field("phone") String phone, @Field("code") String code) {
        return mIApi.register(username,password,email,phone,code);
    }

    @Override
    public Observable<BaseResponse<LoginInfo>> login(@Field("username") String username, @Field("password") String password) {
        return mIApi.login(username,password);
    }

    @Override
    public Observable<BaseResponse<LoginInfo>> oauthLogin(@Field("type") String type, @Field("payload") String payload) {
        return mIApi.oauthLogin(type,payload);
    }

    @Override
    public Observable<BaseResponse<ImageCode>> getImageCode(@Field("phone") String phone, @Field("code") String code) {
        return mIApi.getImageCode(phone,code);
    }

    @Override
    public Observable<BaseResponse> logout() {
        return mIApi.logout();
    }

    @Override
    public Observable<BaseResponse<CategoryBean>> getCategoryInfo() {
        return mIApi.getCategoryInfo();
    }

    @Override
    public Observable<BaseResponse<UserInfo>> getUserInfo() {
        return mIApi.getUserInfo();
    }

    @Override
    public Observable<BaseResponse<AddressBean>> getAddressList() {
        return mIApi.getAddressList();
    }

    @Override
    public Observable<BaseResponse> updateAddressDefault(@Field("id") String id) {
        return mIApi.updateAddressDefault(id);
    }

    @Override
    public Observable<BaseResponse> deleteAddress(@Field("id") String id) {
        return mIApi.deleteAddress(id);
    }

    @Override
    public Observable<BaseResponse> updatePhone(@Field("code") String code, @Field("mobile") String mobile) {
        return mIApi.updatePhone(code,mobile);
    }

    @Override
    public Observable<BaseResponse> updatePassword(@Field("old_password") String old_password, @Field("password") String password) {
        return mIApi.updatePassword(old_password,password);
    }

    @Override
    public Observable<BaseResponse<ForgetSMSBean>> findPasswordSMS(@Field("mobile") String mobile, @Field("code") String code) {
        return mIApi.findPasswordSMS(mobile,code);
    }

    @Override
    public Observable<BaseResponse<ForgetPWBean>> findPassword(@Field("mobile") String mobile, @Field("token") String token, @Field("password") String password) {
        return mIApi.findPassword(mobile,token,password);
    }

    @Override
    public Observable<BaseResponse<ProfileInfo>> getProfileInfo() {
        return mIApi.getProfileInfo();
    }

    @Override
    public Observable<BaseResponse<ProfileInfo>> updateProfileInfo(@Field("nick_name") String nick_name, @Field("sex") String sex, @Field("birthday") String birthday) {
        return mIApi.updateProfileInfo(nick_name,sex,birthday);
    }

    @Override
    public Observable<BaseResponse> updateHead(@Field("head") String head) {
        return mIApi.updateHead(head);
    }

    @Override
    public Observable<BaseResponse<FavoriteBean>> getGoodsList(@Field("id") String id) {
        return mIApi.getGoodsList(id);
    }

    @Override
    public Observable<BaseResponse<GoodsTypeBean>> getGoodsCategory() {
        return mIApi.getGoodsCategory();
    }

    @Override
    public Observable<BaseResponse<ShopBean>> getShopList(@Field("id") String id) {
        return mIApi.getShopList(id);
    }

    @Override
    public Observable<BaseResponse<ShopTypeBean>> getShopGroup() {
        return mIApi.getShopGroup();
    }

    @Override
    public Observable<BaseResponse<FavoriteBean>> deleteFavoriteList(@Field("type") String type, @Field("id") String... id) {
        return mIApi.deleteFavoriteList(type,id);
    }

    @Override
    public Observable<BaseResponse<GoodsDetailBean>> getGoodsDetail(@Field("id") String id) {
        return mIApi.getGoodsDetail(id);
    }

    @Override
    public Observable<BaseResponse<CommentTypeBean>> getGoodsCommentType(@Field("id") String id) {
        return mIApi.getGoodsCommentType(id);
    }

    @Override
    public Observable<BaseResponse<CommentBean>> getGoodsComment(@Field("id") String id, @Field("comments_type") String comments_type, @Field("page_index") String page_index, @Field("page_size") String page_size) {
        return mIApi.getGoodsComment(id,comments_type,page_index,page_size);
    }

    @Override
    public Observable<BaseResponse<CommentDetailBean>> getCommentDetail(@Field("id") String id) {
        return mIApi.getCommentDetail(id);
    }

    @Override
    public Observable<BaseResponse<ReplyBean>> getReplyList(@Field("id") String id, @Field("content") String content) {
        return mIApi.getReplyList(id,content);
    }

    @Override
    public Observable<BaseResponse<ShopCartBean>> getShopCartList() {
        return mIApi.getShopCartList();
    }

    @Override
    public Observable<BaseResponse<ShopCartBean>> deleteCartGoods(@Field("cart_ids") String... cart_ids) {
        return mIApi.deleteCartGoods(cart_ids);
    }

    @Override
    public Observable<BaseResponse<ShopCartBean>> updateCartNum(@Field("cart_id") String cart_id, @Field("goods_num") String goods_num) {
        return mIApi.updateCartNum(cart_id,goods_num);
    }

    @Override
    public Observable<BaseResponse<ShopCartBean>> updateGoodsSpe(@Field("cart_id") String cart_id, @Field("sku_id") String sku_id) {
        return mIApi.updateGoodsSpe(cart_id,sku_id);
    }

    @Override
    public Observable<BaseResponse<AffirmOrderBean>> confirmOrderList(@Field("is_cart") String is_cart, @Field("cart_ids") String cart_ids, @Field("goods_id") String goods_id, @Field("goods_num") String goods_num, @Field("sku_id") String sku_id) {
        return mIApi.confirmOrderList(is_cart,cart_ids,goods_id,goods_num,sku_id);
    }

    @Override
    public Observable<BaseResponse<GoodSpeBean>> getGoodSpec(@Field("goods_id") String goods_id) {
        return mIApi.getGoodSpec(goods_id);
    }

    @Override
    public Observable<BaseResponse> addShopCart(@Field("goods_id") String goods_id, @Field("goods_num") String goods_num, @Field("sku_id") String sku_id) {
        return mIApi.addShopCart(goods_id,goods_num,sku_id);
    }

    @Override
    public Observable<BaseResponse<OrderBean>> getOrderList(@Field("order_type") String order_type, @Field("page_index") String page_index, @Field("page_size") String page_size) {
        return mIApi.getOrderList(order_type,page_index,page_size);
    }

    @Override
    public Observable<BaseResponse<OrderDetailBean>> getOrderDetail(@Field("order_no") String order_no) {
        return mIApi.getOrderDetail(order_no);
    }

    @Override
    public Observable<BaseResponse<HotSearchBean>> getHotSearchList() {
        return mIApi.getHotSearchList();
    }

    @Override
    public Observable<LogisticsBean> getLogisticsInfo(@Url String url, @Field("param") String param, @Field("sign") String sign, @Field("customer") String customer) {
        return mIApi.getLogisticsInfo(url,param,sign,customer);
    }

    @Override
    public Observable<BaseResponse<HomeBean>> getHomePageData() {
        return mIApi.getHomePageData();
    }

    @Override
    public Observable<BaseResponse<SecondSearchBean>> getSecondSearchList(@Field("ser_type") String ser_type, @Field("condition") String condition, @Field("sort") String sort, @Field("page_index") String page_index, @Field("page_size") String page_size, @Field("low_par") String low_par,
                                                                          @Field("tall_par") String tall_par,@Field("json") String json, @Field("cate") String cate) {
        return mIApi.getSecondSearchList(ser_type,condition,sort,page_index,page_size,low_par,tall_par,json,cate);
    }

    @Override
    public Observable<BaseResponse<CreateOrderBean>> createOrder(@Field("is_cart") String is_cart, @Field("phone") String phone, @Field("address") String address, @Field("user_name") String user_name, @Field("cart_ids") String cart_ids, @Field("shop_id") String shop_id, @Field("goods_id") String goods_id, @Field("goods_num") String goods_num, @Field("sku_id") String sku_id, @Field("shipping_money") String shipping_money, @Field("message") String message, @Field("goods_money") String goods_money) {
        return mIApi.createOrder(is_cart,phone,address,user_name,cart_ids,shop_id,goods_id,goods_num,sku_id,shipping_money,message,goods_money);
    }

    @Override
    public Observable<BaseResponse<SearchFilterBean>> getSearchFilterList(@Field("ser_type") String ser_type, @Field("condition") String condition,@Field("cate") String cate) {
        return mIApi.getSearchFilterList(ser_type,condition,cate);
    }

    @Override
    public Observable<BaseResponse<HomePageBean>> getShopHomePage(@Field("shop_id") String shop_id) {
        return mIApi.getShopHomePage(shop_id);
    }

    @Override
    public Observable<BaseResponse<ShopFocusBean>> updateShopFocus(@Field("fav_id") String shop_id, @Field("state") String favo_type) {
        return mIApi.updateShopFocus(shop_id, favo_type);
    }

    @Override
    public Observable<BaseResponse<TotalShopGoodsBean>> getTotalShopGoods(@Field("shop_id") String shop_id, @Field("condition") String condition, @Field("sort") String sort, @Field("page_index") String page_index, @Field("page_size") String page_size) {
        return mIApi.getTotalShopGoods(shop_id,condition,sort,page_index,page_size);
    }

    @Override
    public Observable<BaseResponse<HotGoodsBean>> getHotGoods(@Field("sort") String sort, @Field("page_index") String page_index, @Field("page_size") String page_size) {
        return mIApi.getHotGoods(sort,page_index,page_size);
    }

    @Override
    public Observable<BaseResponse<UploadPicBean>> uploadPic(@Part List<MultipartBody.Part> partList) {
        return mIApi.uploadPic(partList);
    }

    @Override
    public Observable<BaseResponse<HotGoodsBean>> getNewGoods(@Field("sort") String sort, @Field("page_index") String page_index, @Field("page_size") String page_size, @Field("low_par") String low_par, @Field("tall_par") String tall_par, @Field("json") String json) {
        return mIApi.getNewGoods(sort,page_index,page_size,low_par,tall_par,json);
    }

    @Override
    public Observable<BaseResponse<SearchFilterBean>> getSearchFilterList() {
        return mIApi.getSearchFilterList();
    }

    @Override
    public Observable<BaseResponse<TodoCommentBean>> postComment(@Field("goods_id") String goods_id, @Field("order_no") String order_no,@Field("order_goods_id") String order_goods_id, @Field("content") String content, @Field("anon") String anon, @Field("explain_type") String explain_type, @Field("scores") String scores, @Field("physical") String physical, @Field("image") String image) {
        return mIApi.postComment(goods_id,order_no,order_goods_id,content,anon,explain_type,scores,physical,image);
    }

    @Override
    public Observable<BaseResponse<FlashSaleBean>> getFlashSaleList(@Field("start_time") String start_time, @Field("end_time") String end_time, @Field("page_index") String page_index, @Field("page_size") String page_size) {
        return mIApi.getFlashSaleList(start_time,end_time,page_index,page_size);
    }

    @Override
    public Observable<BaseResponse<AtteGoodsBean>> updateGoodsFocus(@Field("id") String id, @Field("is_fav") String is_fav) {
        return mIApi.updateGoodsFocus(id,is_fav);
    }

    @Override
    public Observable<BaseResponse<WxpayInfo>> performWechatPay(@Field("order_no") String order_no, @Field("payment") String payment) {
        return mIApi.performWechatPay(order_no,payment);
    }

    @Override
    public Observable<BaseResponse<AlipayBean>> performAliPay(@Field("order_no") String order_no, @Field("payment") String payment) {
        return mIApi.performAliPay(order_no,payment);
    }

    @Override
    public Observable<BaseResponse<PaySuccessBean>> getPaySuccess(@Field("order_no") String order_no) {
        return mIApi.getPaySuccess(order_no);
    }

    @Override
    public Observable<BaseResponse<FootPrintBean>> getFootPrint() {
        return mIApi.getFootPrint();
    }

    @Override
    public Observable<BaseResponse<FavoriteBean>> deleteFootPrint(@Field("goods_id") String... goods_id) {
        return mIApi.deleteFootPrint(goods_id);
    }

    @Override
    public Observable<BaseResponse> remindDelivery(@Field("order_no") String order_no) {
        return mIApi.remindDelivery(order_no);
    }

    @Override
    public Observable<BaseResponse> extendReceive(@Field("order_no") String order_no) {
        return mIApi.extendReceive(order_no);
    }

    @Override
    public Observable<BaseResponse> confirmReceive(@Field("order_no") String order_no) {
        return mIApi.confirmReceive(order_no);
    }

    @Override
    public Observable<BaseResponse> cancelOrder(@Field("order_no") String order_no) {
        return mIApi.cancelOrder(order_no);
    }

    @Override
    public Observable<BaseResponse> postRefund(@Field("reason") String reason, @Field("phone") String phone, @Field("money") String money, @Field("content") String content, @Field("order_no") String order_no, @Field("order_goods_id") String order_goods_id,@Field("refund_num") String refund_num) {
        return mIApi.postRefund(reason,phone,money,content,order_no,order_goods_id,refund_num);
    }

    @Override
    public Observable<BaseResponse> postOrderRefund(@Field("reason") String reason, @Field("phone") String phone, @Field("money") String money, @Field("content") String content, @Field("order_no") String order_no) {
        return mIApi.postOrderRefund(reason,phone,money,content,order_no);
    }

    @Override
    public Observable<BaseResponse<RefundedListBean>> getRefundedList(@Field("page_index") String page_index, @Field("page_size") String page_size) {
        return mIApi.getRefundedList(page_index,page_size);
    }

    @Override
    public Observable<BaseResponse<EmailBean>> bindEmail(@Field("emali") String emali) {
        return mIApi.bindEmail(emali);
    }

    @Override
    public Observable<BaseResponse<StatisticsBean>> getConsumeStatistics(@Field("year") String year) {
        return mIApi.getConsumeStatistics(year);
    }

    @Override
    public Observable<BaseResponse<AddressBean>> postAddress(@Field("consigner") String consigner, @Field("mobile") String mobile, @Field("province") String province, @Field("city") String city, @Field("district") String district, @Field("address") String address, @Field("is_default") String is_default) {
        return mIApi.postAddress(consigner,mobile,province,city,district,address,is_default);
    }

    @Override
    public Observable<BaseResponse<AddressBean>> updateAddress(@Field("id") String id, @Field("consigner") String consigner, @Field("mobile") String mobile, @Field("province") String province, @Field("city") String city, @Field("district") String district, @Field("address") String address, @Field("is_default") String is_default) {
        return mIApi.updateAddress(id,consigner,mobile,province,city,district,address,is_default);
    }

}
