package com.example.administrator.sportsfitness.di.component;

import android.app.Activity;

import com.example.administrator.sportsfitness.di.module.FragmentModule;
import com.example.administrator.sportsfitness.di.scope.FragmentScope;
import com.example.administrator.sportsfitness.ui.fragment.CourseFragment;
import com.example.administrator.sportsfitness.ui.fragment.HomeFragment;
import com.example.administrator.sportsfitness.ui.fragment.MineFragment;
import com.example.administrator.sportsfitness.ui.fragment.SearchFragment;
import com.example.administrator.sportsfitness.ui.fragment.SocialFragment;
import com.example.administrator.sportsfitness.ui.fragment.collection.CollectionFragment;
import com.example.administrator.sportsfitness.ui.fragment.detailsinfo.IntroduceFragment;
import com.example.administrator.sportsfitness.ui.fragment.im.FriendsCircleRelatedFragment;
import com.example.administrator.sportsfitness.ui.fragment.order.OrderFormFragment;
import com.example.administrator.sportsfitness.ui.fragment.search.SearchTypeFragment;
import com.example.administrator.sportsfitness.ui.fragment.social.FriendsCircleFragment;
import com.example.administrator.sportsfitness.ui.fragment.social.MessageFragment;

import dagger.Component;


@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(HomeFragment homeFragment);

    void inject(SocialFragment socialFragment);

    void inject(CourseFragment courseFragment);

    void inject(MineFragment mineFragment);

    void inject(FriendsCircleFragment friendsCircleFragment);

    void inject(MessageFragment messageFragment);

    void inject(SearchFragment searchFragment);

    void inject(SearchTypeFragment searchTypeFragment);

    void inject(IntroduceFragment introduceFragment);

    void inject(FriendsCircleRelatedFragment friendsCircleRelatedFragment);

    void inject(OrderFormFragment orderFormFragment);

    void inject(CollectionFragment collectionFragment);

}
