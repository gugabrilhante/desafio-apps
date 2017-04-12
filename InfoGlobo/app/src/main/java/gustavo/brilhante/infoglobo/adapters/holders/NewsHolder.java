package gustavo.brilhante.infoglobo.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import gustavo.brilhante.infoglobo.R;
import gustavo.brilhante.infoglobo.adapters.NewsAdapter;
import gustavo.brilhante.infoglobo.animations.ResizeAnimation;
import gustavo.brilhante.infoglobo.interfaces.AdapterCallback;
import gustavo.brilhante.infoglobo.layout.InfoGloboTextView;
import gustavo.brilhante.infoglobo.models.DisplayObj;

import static gustavo.brilhante.infoglobo.adapters.NewsAdapter.imageWitdh;
import static java.lang.Double.NaN;

/**
 * Created by Gustavo on 10/04/17.
 */

public class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
    DisplayObj conteudo;

    LinearLayout newsAdapterRootLayout, shrinkedLayout;

    RelativeLayout collapsedLayout;

    ImageView imageBackground;

    ImageView newsAdapterImageView, arrowDownImageView;
    InfoGloboTextView newsAdapterTextview01, newsAdapterTextview02, newsAdapterTextview03, newsAdapterTextview04;

    View view;


    int position;

    Double proportion = NaN;

    boolean viewWasAnimated = false;

    AdapterCallback callback;


    public NewsHolder(View itemView, AdapterCallback callback) {
        super(itemView);
        this.view = itemView;
        this.callback = callback;
        shrinkedLayout = (LinearLayout) itemView.findViewById(R.id.shrinkedLayout);
        newsAdapterRootLayout = (LinearLayout) itemView.findViewById(R.id.newsAdapterRootLayout);
        newsAdapterImageView = (ImageView) itemView.findViewById(R.id.newsAdapterImageView);
        arrowDownImageView = (ImageView) itemView.findViewById(R.id.arrowDownImageView);
        newsAdapterTextview01 = (InfoGloboTextView) itemView.findViewById(R.id.newsAdapterTextview01);
        newsAdapterTextview02 = (InfoGloboTextView) itemView.findViewById(R.id.newsAdapterTextview02);
        newsAdapterTextview03 = (InfoGloboTextView) itemView.findViewById(R.id.newsAdapterTextview03);
        newsAdapterTextview04 = (InfoGloboTextView) itemView.findViewById(R.id.newsAdapterTextview04);


        collapsedLayout = (RelativeLayout) itemView.findViewById(R.id.collapsedLayout);
        imageBackground = (ImageView) itemView.findViewById(R.id.imageBackground);

    }

    public void bind(DisplayObj conteudo, int position){
        this.conteudo = conteudo;
        this.position = position;
        if(conteudo.imageNew!=null) {
            newsAdapterImageView.setImageBitmap(conteudo.getImageNew());
            imageBackground.setImageBitmap(conteudo.getImageNew());
        }else{
            newsAdapterImageView.setImageBitmap(null);
            imageBackground.setImageBitmap(null);
        }
        newsAdapterRootLayout.setOnClickListener(this);
        newsAdapterTextview01.setText(conteudo.secaoNome);
        newsAdapterTextview01.setOnClickListener(this);
        newsAdapterTextview02.setText(conteudo.titulo);
        newsAdapterTextview02.setOnClickListener(this);
        newsAdapterTextview03.setText(conteudo.titulo);
        newsAdapterTextview03.setOnClickListener(this);
        newsAdapterTextview04.setText(conteudo.secaoNome.toUpperCase());
        newsAdapterTextview04.setOnClickListener(this);
        newsAdapterImageView.setOnClickListener(this);
        imageBackground.setOnClickListener(this);

        if(!conteudo.collapsed){
            newsAdapterTextview02.setEllipsized(true, 2);
            if(NewsAdapter.height!=null){
                collapsedLayout.setAlpha(0f);
                collapsedLayout.setVisibility(View.GONE);
                shrinkedLayout.setVisibility(View.VISIBLE);
                shrinkedLayout.setAlpha(1f);
                newsAdapterRootLayout.getLayoutParams().height = NewsAdapter.height;
                //shrinkView(0,0);
            }
        }
        else {
            newsAdapterTextview02.setEllipsized(false, 0);
            if(NewsAdapter.heightToExpand!=null){
                shrinkedLayout.setAlpha(0f);
                shrinkedLayout.setVisibility(View.GONE);
                collapsedLayout.setVisibility(View.VISIBLE);
                collapsedLayout.setAlpha(1f);
                newsAdapterRootLayout.getLayoutParams().height = NewsAdapter.heightToExpand;
                //collapseView(0,0);
            }
        }

        if(NewsAdapter.height==null)getLayoutSizes();

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.newsAdapterImageView || v.getId()==R.id.imageBackground || v.getId()==R.id.arrowDownImageView) {
            if (!conteudo.collapsed) {
                collapseView(300, 200);
            } else {
                shrinkView(300, 250);
            }
        }else{
            callback.onListClick(conteudo, position);
        }
    }

    void collapseView(final int resizeDuration, final int fadeDuration){
        newsAdapterRootLayout.clearAnimation();
        ResizeAnimation animation = new ResizeAnimation(newsAdapterRootLayout, 0, 0, NewsAdapter.height, NewsAdapter.heightToExpand);
        animation.setFillEnabled(true);
        animation.setDuration(resizeDuration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                shrinkedLayout.animate().alpha(0.0f).setDuration(resizeDuration).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                conteudo.collapsed = true;
                shrinkedLayout.setVisibility(View.GONE);
                collapsedLayout.setVisibility(View.VISIBLE);
                collapsedLayout.setAlpha(0f);
                collapsedLayout.animate().alpha(1f).setDuration(fadeDuration).start();


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        newsAdapterRootLayout.startAnimation(animation);
    }

    void shrinkView(final int resizeDuration, final int fadeDuration){
        newsAdapterRootLayout.clearAnimation();
        ResizeAnimation animation = new ResizeAnimation(newsAdapterRootLayout, 0, 0, NewsAdapter.heightToExpand, NewsAdapter.height);
        animation.setFillEnabled(true);
        animation.setDuration(resizeDuration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                collapsedLayout.animate().alpha(0.0f).setDuration(fadeDuration).start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                conteudo.collapsed = false;
                collapsedLayout.setVisibility(View.GONE);
                shrinkedLayout.setVisibility(View.VISIBLE);
                shrinkedLayout.setAlpha(0f);
                shrinkedLayout.animate().alpha(1f).setDuration(fadeDuration).start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        newsAdapterRootLayout.startAnimation(animation);
    }

    private void getLayoutSizes() {
        newsAdapterRootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if(NewsAdapter.height==null) {
                    NewsAdapter.layoutWitdh = newsAdapterRootLayout.getWidth();
                    NewsAdapter.height = newsAdapterRootLayout.getHeight();

                    if (imageWitdh != null) {
                        proportion = NewsAdapter.layoutWitdh / (double) NewsAdapter.imageWitdh;
                        NewsAdapter.heightToExpand = (int) (newsAdapterRootLayout.getWidth()*0.61);
                        if(conteudo!=null && conteudo.collapsed){
                            shrinkedLayout.setAlpha(0f);
                            shrinkedLayout.setVisibility(View.GONE);
                            collapsedLayout.setVisibility(View.VISIBLE);
                            collapsedLayout.setAlpha(1f);
                            newsAdapterRootLayout.getLayoutParams().height = NewsAdapter.heightToExpand;
                        }
                        //NewsAdapter.heightToExpand = (int) (NewsAdapter.height * proportion);
                    }
                }
            }
        });
        newsAdapterImageView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if(NewsAdapter.imageWitdh==null) {
                    NewsAdapter.imageWitdh = newsAdapterImageView.getWidth();

                    if (NewsAdapter.layoutWitdh != null) {
                        proportion = NewsAdapter.layoutWitdh / (double) NewsAdapter.imageWitdh;
                        NewsAdapter.heightToExpand = (int) (newsAdapterRootLayout.getWidth()*0.61);
                        if(conteudo!=null && conteudo.collapsed){
                            shrinkedLayout.setAlpha(0f);
                            shrinkedLayout.setVisibility(View.GONE);
                            collapsedLayout.setVisibility(View.VISIBLE);
                            collapsedLayout.setAlpha(1f);
                            newsAdapterRootLayout.getLayoutParams().height = NewsAdapter.heightToExpand;
                        }
                        //NewsAdapter.heightToExpand = (int) (NewsAdapter.height * proportion);
                    }
                }
            }
        });
    }


    public static NewsHolder build(ViewGroup parent, AdapterCallback callback){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_news, parent, false);
        NewsHolder holder = new NewsHolder(view, callback);
        return holder;
    }
}
