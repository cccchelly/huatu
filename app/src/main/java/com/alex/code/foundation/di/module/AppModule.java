package com.alex.code.foundation.di.module;

import android.content.Context;

import com.alex.code.foundation.App;
import com.alex.code.foundation.AppConstants;
import com.alex.code.foundation.data.AppDataManager;
import com.alex.code.foundation.data.IDataManager;
import com.alex.code.foundation.data.preference.AppPreference;
import com.alex.code.foundation.data.preference.IPreference;
import com.alex.code.foundation.di.annotation.ApplicationContext;
import com.alex.code.foundation.di.annotation.PerActivity;
import com.alex.code.foundation.di.annotation.PreferenceInfo;
import com.alex.code.foundation.main.MainActivity;
import com.alex.code.foundation.main.MainActivityModule;
import com.alex.code.foundation.ui.address.AddressActivity;
import com.alex.code.foundation.ui.address.AddressActivityModule;
import com.alex.code.foundation.ui.address.NewAddressActivity;
import com.alex.code.foundation.ui.address.NewAddressActivityModule;
import com.alex.code.foundation.ui.affirmorder.AffirmOrderActivity;
import com.alex.code.foundation.ui.affirmorder.AffirmOrderActivityModule;
import com.alex.code.foundation.ui.comment.CommentDetailsActivity;
import com.alex.code.foundation.ui.comment.CommentDetailsActivityModule;
import com.alex.code.foundation.ui.comment.todo.ToDoCommentActivity;
import com.alex.code.foundation.ui.comment.todo.ToDoCommentActivityModule;
import com.alex.code.foundation.ui.details.GoodsDetailActivity;
import com.alex.code.foundation.ui.details.GoodsDetailActivityModule;
import com.alex.code.foundation.ui.favorite.FavoriteActivity;
import com.alex.code.foundation.ui.favorite.FavoriteActivityModule;
import com.alex.code.foundation.ui.flashsale.FlashSaleActicity;
import com.alex.code.foundation.ui.flashsale.FlashSaleActicityModule;
import com.alex.code.foundation.ui.focus.FocusActivity;
import com.alex.code.foundation.ui.focus.FocusActivityModule;
import com.alex.code.foundation.ui.footprint.FootprintActivity;
import com.alex.code.foundation.ui.footprint.FootprintActivityModule;
import com.alex.code.foundation.ui.forget.ForgetPWActivity;
import com.alex.code.foundation.ui.forget.ForgetPWActivityModule;
import com.alex.code.foundation.ui.forget.one.ForgetOneActivity;
import com.alex.code.foundation.ui.forget.one.ForgetOneActivityModule;
import com.alex.code.foundation.ui.homesearch.HomeSearchActivity;
import com.alex.code.foundation.ui.homesearch.HomeSearchActivityModule;
import com.alex.code.foundation.ui.hotgoods.HotGoodsActivity;
import com.alex.code.foundation.ui.hotgoods.HotGoodsActivityModule;
import com.alex.code.foundation.ui.login.LoginActivity;
import com.alex.code.foundation.ui.login.LoginActivityModule;
import com.alex.code.foundation.ui.logistics.LogisticsActivity;
import com.alex.code.foundation.ui.logistics.LogisticsActivityModule;
import com.alex.code.foundation.ui.newgoods.NewGoodsActivity;
import com.alex.code.foundation.ui.newgoods.NewGoodsActivityModule;
import com.alex.code.foundation.ui.notice.NoticeActivity;
import com.alex.code.foundation.ui.notice.NoticeActivityModule;
import com.alex.code.foundation.ui.order.OrderActivity;
import com.alex.code.foundation.ui.order.OrderActivityModule;
import com.alex.code.foundation.ui.orderdetail.OrderDetailActivity;
import com.alex.code.foundation.ui.orderdetail.OrderDetailActivityModule;
import com.alex.code.foundation.ui.pay.PayActivity;
import com.alex.code.foundation.ui.pay.PayActivityModule;
import com.alex.code.foundation.ui.paysuccess.PaySuccessActivity;
import com.alex.code.foundation.ui.paysuccess.PaySuccessModule;
import com.alex.code.foundation.ui.refund.RefundActivity;
import com.alex.code.foundation.ui.refund.RefundActivityModule;
import com.alex.code.foundation.ui.refunded.RefundedActivity;
import com.alex.code.foundation.ui.refunded.RefundedActivityModule;
import com.alex.code.foundation.ui.register.RegisterActivity;
import com.alex.code.foundation.ui.register.RegisterActivityModule;
import com.alex.code.foundation.ui.safety.SafetyActivity;
import com.alex.code.foundation.ui.safety.SafetyActivityModule;
import com.alex.code.foundation.ui.safety.changephone.ChangePhoneActivity;
import com.alex.code.foundation.ui.safety.changephone.ChangePhoneModule;
import com.alex.code.foundation.ui.safety.changepw.ChangePassWordActivity;
import com.alex.code.foundation.ui.safety.changepw.ChangePassWordModule;
import com.alex.code.foundation.ui.safety.email.EmailActivity;
import com.alex.code.foundation.ui.safety.email.EmailActivityModule;
import com.alex.code.foundation.ui.secondsearch.SecondSearchActivity;
import com.alex.code.foundation.ui.secondsearch.SecondSearchActivityModule;
import com.alex.code.foundation.ui.setting.SettingActivity;
import com.alex.code.foundation.ui.setting.SettingActivityModule;
import com.alex.code.foundation.ui.shopgoods.SearchGoodsInShopActivity;
import com.alex.code.foundation.ui.shopgoods.SearchGoodsInShopActivityModule;
import com.alex.code.foundation.ui.shopgoods.ShopGoodsActivity;
import com.alex.code.foundation.ui.shopgoods.ShopGoodsActivityModule;
import com.alex.code.foundation.ui.shophome.ShopHomeActivity;
import com.alex.code.foundation.ui.shophome.ShopHomeActivityModule;
import com.alex.code.foundation.ui.shopsearch.ShopSearchActivity;
import com.alex.code.foundation.ui.shopsearch.ShopSearchActivityModule;
import com.alex.code.foundation.ui.splash.SplashActivity;
import com.alex.code.foundation.ui.splash.SplashActivityModule;
import com.alex.code.foundation.ui.statistics.StatisticsActivity;
import com.alex.code.foundation.ui.statistics.StatisticsActivityModule;
import com.alex.code.foundation.ui.userinfo.UserInfoActivity;
import com.alex.code.foundation.ui.userinfo.UserInfoActivityModule;
import com.alex.code.foundation.ui.userinfo.change.ChangeNameActivity;
import com.alex.code.foundation.ui.userinfo.change.ChangeNameActivityModule;
import com.alex.code.foundation.ui.waitcomment.WaitCommentActivity;
import com.alex.code.foundation.ui.waitcomment.WaitCommentActivityModule;
import com.alex.code.foundation.utils.ToastInstance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import io.reactivex.disposables.CompositeDisposable;

@Module(includes = {
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class,
        NetworkModule.class})
public abstract class AppModule {

    @Provides
    @ApplicationContext
    static Context provideApplicationContext(App app) {
        return app.getApplicationContext();
    }

    /**
     * @return SharedPreference Name
     */
    @Provides
    @PreferenceInfo
    static String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    static IPreference providePreference(AppPreference appPreference) {
        return appPreference;
    }

    /**
     * @param appDataManager
     * @return DataManager
     */
    @Provides
    @Singleton
    static IDataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    static ToastInstance provideToastInstance(@ApplicationContext Context context) {
        return new ToastInstance(context);
    }

    @Provides
    static CompositeDisposable providerCompositeDisposable() {
        return new CompositeDisposable();
    }


    @PerActivity
    @ContributesAndroidInjector(modules = StatisticsActivityModule.class)
    abstract StatisticsActivity statisticsActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = PaySuccessModule.class)
    abstract PaySuccessActivity paySuccessActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = EmailActivityModule.class)
    abstract EmailActivity emailActivityInjector();


    @PerActivity
    @ContributesAndroidInjector(modules = ForgetOneActivityModule.class)
    abstract ForgetOneActivity forgetOneActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ForgetPWActivityModule.class)
    abstract ForgetPWActivity forgetPWActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = RefundedActivityModule.class)
    abstract RefundedActivity refundedActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = SettingActivityModule.class)
    abstract SettingActivity settingActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = NoticeActivityModule.class)
    abstract NoticeActivity noticeActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = RefundActivityModule.class)
    abstract RefundActivity refundActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = WaitCommentActivityModule.class)
    abstract WaitCommentActivity waitCommentActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = FlashSaleActicityModule.class)
    abstract FlashSaleActicity flashSaleActicityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ToDoCommentActivityModule.class)
    abstract ToDoCommentActivity toDoCommentActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = SearchGoodsInShopActivityModule.class)
    abstract SearchGoodsInShopActivity searchGoodsInShopActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ShopGoodsActivityModule.class)
    abstract ShopGoodsActivity shopGoodsActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ShopHomeActivityModule.class)
    abstract ShopHomeActivity shopHomeActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ShopSearchActivityModule.class)
    abstract ShopSearchActivity shopSearchActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = HotGoodsActivityModule.class)
    abstract HotGoodsActivity hotGoodsActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = NewGoodsActivityModule.class)
    abstract NewGoodsActivity newGoodsActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = PayActivityModule.class)
    abstract PayActivity payActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = AffirmOrderActivityModule.class)
    abstract AffirmOrderActivity affirmOrderActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = SecondSearchActivityModule.class)
    abstract SecondSearchActivity secondSearchActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = HomeSearchActivityModule.class)
    abstract HomeSearchActivity homeSearchActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = LogisticsActivityModule.class)
    abstract LogisticsActivity logisticsActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = OrderActivityModule.class)
    abstract OrderActivity orderActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = OrderDetailActivityModule.class)
    abstract OrderDetailActivity orderDetailActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = CommentDetailsActivityModule.class)
    abstract CommentDetailsActivity commentDetailsActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = GoodsDetailActivityModule.class)
    abstract GoodsDetailActivity goodsDetailActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = FootprintActivityModule.class)
    abstract FootprintActivity footprintActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = FocusActivityModule.class)
    abstract FocusActivity focusActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = FavoriteActivityModule.class)
    abstract FavoriteActivity favoriteActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ChangeNameActivityModule.class)
    abstract ChangeNameActivity changeNameActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ChangePassWordModule.class)
    abstract ChangePassWordActivity changePassWordActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = ChangePhoneModule.class)
    abstract ChangePhoneActivity changePhoneActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = SafetyActivityModule.class)
    abstract SafetyActivity safetyActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = NewAddressActivityModule.class)
    abstract NewAddressActivity newAddressActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = AddressActivityModule.class)
    abstract AddressActivity addressActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = UserInfoActivityModule.class)
    abstract UserInfoActivity userInfoActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = RegisterActivityModule.class)
    abstract RegisterActivity registerActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity spalshActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = LoginActivityModule.class)
    abstract LoginActivity loginActivityInjector();

    @PerActivity
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity mainActivityInjector();
}
