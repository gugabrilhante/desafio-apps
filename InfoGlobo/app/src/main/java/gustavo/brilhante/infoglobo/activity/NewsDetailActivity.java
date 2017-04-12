package gustavo.brilhante.infoglobo.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import gustavo.brilhante.infoglobo.R;
import gustavo.brilhante.infoglobo.layout.InfoGloboTextView;
import gustavo.brilhante.infoglobo.models.Conteudo;
import gustavo.brilhante.infoglobo.request.HttpRequest;
import gustavo.brilhante.infoglobo.utils.StringUtils;

import static java.lang.Double.NaN;

@EActivity(R.layout.activity_news_detail)
public class NewsDetailActivity extends AppCompatActivity {

    @ViewById
    LinearLayout rootLayout;

    @ViewById
    RelativeLayout imageLayout;

    @ViewById
    ImageView imageBackground;

    @ViewById
    InfoGloboTextView tituloTextView, subtituleTextView, autorTextView, descricaoTextview;

    @ViewById
    InfoGloboTextView corpoNoticiaTextView;


    @ViewById
    CircularProgressView progressView;

    @Extra
    Conteudo conteudo;

    int rootLayoutWidth, rootLayoutHeight;

    int imageHeight;

    double proportion = NaN;

    @AfterViews
    void afterViews(){
        getLayoutSizes();
        setViews();
        imageBackground.setAlpha(0f);
    }


    void setViews(){
        if(conteudo.imagens!=null && conteudo.imagens.size()>0){
            if(conteudo.imagens.get(0).url!=null)requestImage();
            descricaoTextview.setText(conteudo.imagens.get(0).legenda+"\n"+conteudo.imagens.get(0).fonte);
        }else{

        }
        tituloTextView.setText(conteudo.titulo);
        subtituleTextView.setText(conteudo.subTitulo);
        corpoNoticiaTextView.setText(conteudo.texto);
        autorTextView.setText(StringUtils.getAutorText(conteudo, this));
    }

    @Background(serial = "imageFlow")
    void requestImage(){

        if(conteudo.imagens.size()>0) {
            String url = conteudo.imagens.get(0).url;


            Bitmap bitmap = HttpRequest.getBitmapFromURL(url, rootLayoutWidth, imageHeight);
            setBitmapImage(bitmap);
        }else{
        }

    }

    @UiThread
    void setBitmapImage(Bitmap bitmap){
        if(bitmap!=null)imageBackground.setImageBitmap(bitmap);
        imageBackground.animate().alpha(1f).setDuration(300).start();
        progressView.setVisibility(View.GONE);
    }

    private void getLayoutSizes() {
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                rootLayoutWidth = rootLayout.getWidth();
                rootLayoutHeight = rootLayout.getHeight();

                imageHeight = (int) (rootLayout.getWidth()*0.61);
                if(conteudo.imagens!=null && conteudo.imagens.size()>0 && conteudo.imagens.get(0).url!=null) {
                    imageBackground.getLayoutParams().height = imageHeight;
                }
                    //NewsAdapter.heightToExpand = (int) (NewsAdapter.height * proportion);

            }

        });
    }

}
