package com.example.administrator.sportsFitness.di.component;

import android.app.Activity;

import com.example.administrator.sportsFitness.di.module.FragmentModule;
import com.example.administrator.sportsFitness.di.scope.FragmentScope;
import com.example.administrator.sportsFitness.ui.fragment.CourseFragment;
import com.example.administrator.sportsFitness.ui.fragment.HomeFragment;
import com.example.administrator.sportsFitness.ui.fragment.MineFragment;
import com.example.administrator.sportsFitness.ui.fragment.SearchFragment;
import com.example.administrator.sportsFitness.ui.fragment.SocialFragment;
import com.example.administrator.sportsFitness.ui.fragment.detailsinfo.IntroduceFragment;
import com.example.administrator.sportsFitness.ui.fragment.search.SearchTypeFragment;
import com.example.administrator.sportsFitness.ui.fragment.social.FriendsCircleFragment;
import com.example.administrator.sportsFitness.ui.fragment.social.MessageFragment;

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

}
