package com.zanexu.xuliao.im;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zanexu.xuliao.R;
import com.zanexu.xuliao.data.remote.im.GiftBean;
import com.zanexu.xuliao.data.remote.im.GiftModel;
import com.zanexu.xuliao.data.remote.im.LikeModel;
import com.zanexu.xuliao.data.remote.im.LikePeerBean;
import com.zanexu.xuliao.data.sp.MySharedPre;
import com.zanexu.xuliao.web.GradeWebActivity;

import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zanexu on 2017/7/9.
 */

public class CheatActivity extends AppCompatActivity {

    private static final long POLL_INTERVAL = 1000;
    private static final long GIFT_TIME = 60000;
    private static final long LIKE_TIME = 300000;
    public static final String GRADE_PAPRAM = "grade_param";

    private String title;
    private String targetId;
    private Conversation.ConversationType type;
    private Toolbar toolbar;
    private TextView textTime;
    private ImageView imgLove;
    private ImageView imgLoveMove;
    private ImageView imgGift;
    private ImageView imgGiftMove;

    private ImageView imgSendLove;
    private ImageView imgSendGift;
    private TextView textGiftCount;

    private Calendar calendar = Calendar.getInstance();
    private long allTime = 300000;
    private Subscription subscription;
    private Subscription pollSubscription;
    private boolean isLikePeer = false;
    private boolean ispeerLike = false;
    private int giftCount = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        targetId = getIntent().getData().getQueryParameter("targetId");
        title = getIntent().getData().getQueryParameter("title");
        type = Conversation.ConversationType.valueOf(getIntent().getData().getLastPathSegment().toUpperCase(Locale.getDefault()));

        initView();
        initFragment();
        pollingLikeGift();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_cheat);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textTime = (TextView) findViewById(R.id.text_time);
        imgLove = (ImageView) findViewById(R.id.img_talk_love);
        imgLoveMove = (ImageView) findViewById(R.id.img_talk_move_love);
        textTime.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        startCoutdown();
        imgLove.setOnClickListener(v -> likePeer());

        imgGift = (ImageView) findViewById(R.id.img_talk_gift);
        imgGiftMove = (ImageView) findViewById(R.id.img_talk_move_gift);
        imgGift.setOnClickListener(v -> sendGift());
//
        imgSendGift = (ImageView) findViewById(R.id.img_like_send);
        textGiftCount = (TextView) findViewById(R.id.text_gift_count);

        imgSendLove = (ImageView) findViewById(R.id.img_like_send);
    }

    private void sendGift() {
        GiftModel.getInstance().sendGift(MySharedPre.getInstance().getOpenId(), targetId)
                .subscribe(new Subscriber<GiftBean>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.i("openid", e + "");
                    }

                    @Override
                    public void onNext(GiftBean giftBean) {
                        if (giftBean.getStatus() == 200) {
                            startGiftAnim();
                            startTextAnim();
                            allTime += GIFT_TIME;
                            restartCountDown();
                        }
                    }
                });
    }

    private void likePeer() {
        LikeModel.getInstance().likePeer(MySharedPre.getInstance().getOpenId(), targetId)
                .subscribe(new Subscriber<LikePeerBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        imgLove.setEnabled(true);
                    }

                    @Override
                    public void onNext(LikePeerBean likePeerBean) {
                        if (likePeerBean.getStatus() == 200) {
                            isLikePeer = true;
                            imgLove.setEnabled(false);
                            imgLove.setImageDrawable(getResources().getDrawable(R.drawable.love_1));
                        }
                    }
                });
    }

    private void pollingLikeGift() {
        String openId = MySharedPre.getInstance().getOpenId();
        imgSendGift.setVisibility(View.GONE);
        textGiftCount.setVisibility(View.GONE);
        imgSendLove.setVisibility(View.GONE);

        pollSubscription = Observable.interval(POLL_INTERVAL, TimeUnit.MILLISECONDS)
                .flatMap(aLong -> LikeModel.getInstance().peerLike(openId, targetId))
                .subscribe(peerLikeBean -> {
                    Log.i("poll", peerLikeBean.getData().isIslike() + " " + isLikePeer);
                    if (peerLikeBean.getData().isIslike()) {
                        ispeerLike = true;
                    }

                    if (ispeerLike && isLikePeer) {
                        startLikeAnim();
                        startTextAnim();

                        ispeerLike = false;
                        allTime += LIKE_TIME;
                        restartCountDown();
                        Toast.makeText(this, "恭喜你~双方点赞了～", Toast.LENGTH_SHORT).show();
                    }

                    int moreGiftCount = peerLikeBean.getData().getGiftcount();
                    if (moreGiftCount > 0) {
                        Toast.makeText(this, "对方给你送了 " + moreGiftCount + " 个礼物～", Toast.LENGTH_SHORT).show();
                        giftCount += peerLikeBean.getData().getGiftcount();

                        startTextAnim();
                        allTime += GIFT_TIME * moreGiftCount;
                        restartCountDown();
                    }
                });
    }


    private void startGiftAnim() {
        ObjectAnimator giftAnimator = ObjectAnimator.ofFloat(imgGiftMove, "translationX", 0f, 150f);
        giftAnimator.setDuration(500);
        giftAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                imgGiftMove.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                imgGiftMove.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        giftAnimator.start();
    }

    private void startLikeAnim() {
        ObjectAnimator loveAnimator = ObjectAnimator.ofFloat(imgLoveMove, "translationX", 0f, -150f);
        loveAnimator.setDuration(500);
        loveAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                imgLoveMove.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                imgLoveMove.setVisibility(View.GONE);
            }
            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        loveAnimator.start();
    }

    private void startTextAnim() {
        ObjectAnimator textXAnimator = ObjectAnimator.ofFloat(textTime, "scaleY", 1f, 1.05f, 1f);
        textXAnimator.setDuration(500);
        ObjectAnimator textYAnimator = ObjectAnimator.ofFloat(textTime, "scaleX", 1f, 1.05f, 1f);
        textYAnimator.setDuration(500);

        AnimatorSet textAnimatorSet = new AnimatorSet();
        textAnimatorSet.play(textXAnimator).with(textYAnimator);

        textXAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                textTime.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }
            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        textTime.setTextColor(getResources().getColor(R.color.colorAccent));
        textAnimatorSet.start();
    }

    private void startCoutdown() {
        calendar.setTimeInMillis(allTime);
        subscription = Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> {
            int seconds = calendar.get(Calendar.SECOND);
            String secondsStr;
            if (seconds < 10) {
                secondsStr = "0" + seconds;
            } else {
                secondsStr = String.valueOf(seconds);
            }

            String time = "剩余时间:  " + calendar.get(Calendar.MINUTE) + ":" + secondsStr;
            textTime.setText(time);
            allTime -= 1000;
            calendar.setTimeInMillis(allTime);
            if (allTime == 0) {
                if (pollSubscription != null) {
                    pollSubscription.unsubscribe();
                }

                Intent intent = new Intent(CheatActivity.this, GradeWebActivity.class);
                StringBuilder sb = new StringBuilder("?openidA=");
                sb.append(MySharedPre.getInstance().getOpenId())
                        .append("&openidB=")
                        .append(targetId);
                intent.putExtra(GRADE_PAPRAM, sb.toString());
                startActivity(intent);
                CheatActivity.this.finish();

                //消除聊天记录
                RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, targetId, new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                    }
                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                    }
                });
            }
        });
    }

    private void restartCountDown() {
        subscription.unsubscribe();
        startCoutdown();
    }

    private void initFragment() {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName)
                .buildUpon()
                .appendPath("conversation")
                .appendPath(type.getName().toLowerCase())
                .appendQueryParameter("targetId", targetId)
                .build();

        fragment.setUri(uri);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        subscription.unsubscribe();
        pollSubscription.unsubscribe();
    }
}
