package gustavo.brilhante.infoglobo.activity;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import gustavo.brilhante.infoglobo.R;
import gustavo.brilhante.infoglobo.adapters.NewsAdapter;
import gustavo.brilhante.infoglobo.constants.Constants;
import gustavo.brilhante.infoglobo.interfaces.AdapterCallback;
import gustavo.brilhante.infoglobo.layout.InfoGloboTextView;
import gustavo.brilhante.infoglobo.models.Datum;
import gustavo.brilhante.infoglobo.models.DisplayObj;
import gustavo.brilhante.infoglobo.request.HttpRequest;
import gustavo.brilhante.infoglobo.request.MakeRequest;
import okhttp3.Response;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements AdapterCallback{

    @ViewById
    RecyclerView recyclerView;

    @ViewById
    CircularProgressView progressView;

    @ViewById
    LinearLayout rootLayout, errorMessageLayout;

    @ViewById
    InfoGloboTextView errorMessageTextView;

    NewsAdapter adapter;

    Datum data;

    boolean isLoadingSpinning = false;

    Gson gson;

    int bitmapCount = 0;

    int rootLayoutWidth, imageHeight;

    ArrayList<DisplayObj> displayObjs;

    @AfterViews
    public void afterViews(){
        getLayoutSizes();
        gson = new Gson();
        makeRequest();
    }

    @UiThread
    void createDisplayObjList(){
        displayObjs = new ArrayList<DisplayObj>();
        for(int i=0; i<data.conteudos.size(); i++){
            displayObjs.add(new DisplayObj(data.conteudos.get(i)));
        }
    }

    @Click(R.id.errorMessageTextView)
    void errorMessageTextViewClick(){
        makeRequest();
    }

    @Background
    void makeRequest(){
        setLoading(true, false);

        String url = Constants.baseUrl;

        try {
            Response response = MakeRequest.get(url, null);

            if(response.isSuccessful()){
                String resp = response.body().string();
                Type listType = new TypeToken<ArrayList<Datum>>(){}.getType();
                ArrayList<Datum> list = gson.fromJson(resp, listType);
                if(!list.isEmpty()) {
                    data = list.get(0);
                    createDisplayObjList();
                    setImagesRequest();
                }

            }else{
                setLoading(false, true);
            }

        }
        catch (JsonParseException e) {
            setLoading(false, true);
        } catch (IOException e) {
            e.printStackTrace();
            setLoading(false, true);
        }

    }

    @UiThread
    void setLoading(boolean isLoading, boolean error){
        if(isLoading && progressView.getVisibility()== View.GONE){
            isLoadingSpinning = isLoading;
            progressView.setVisibility(View.VISIBLE);
            progressView.startAnimation();
            recyclerView.setVisibility(View.GONE);
            errorMessageLayout.setVisibility(View.GONE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,  WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        }else if(!isLoading && progressView.getVisibility() == View.VISIBLE && !error){
            isLoadingSpinning = isLoading;
            progressView.setVisibility(View.GONE);
            progressView.stopAnimation();
            recyclerView.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }else if(!isLoading && progressView.getVisibility() == View.VISIBLE && error){
            isLoadingSpinning = isLoading;
            progressView.setVisibility(View.GONE);
            progressView.stopAnimation();
            errorMessageLayout.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }



    @UiThread
    void setRecyclerView(){
        adapter = new NewsAdapter(displayObjs, this);
        setAdapter();

    }
    @UiThread
    void setAdapter(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Background(serial = "imageFlow")
    void setImagesRequest(){
        bitmapCount = 0;
        for (int i=0; i<data.conteudos.size(); i++){
            requestImage(i);
        }
    }

    @Background(serial = "imageFlow")
    void requestImage(int index){

        if(data.conteudos.get(index).imagens.size()>0) {
            String url = data.conteudos.get(index).imagens.get(0).url;


            Bitmap bitmap = HttpRequest.getBitmapFromURL(url, rootLayoutWidth, imageHeight);
            setImageToSearch(index, bitmap);
        }else{
            setImageToSearch(index, null);
        }

    }

    @Background
    void setImageToSearch(int index, Bitmap bitmap){
        bitmapCount++;
        if(bitmap!=null){
            displayObjs.get(index).setImageNew(bitmap);
            if(index==0)displayObjs.get(index).collapsed = true;
        }
        if(bitmapCount>=data.conteudos.size()){
            setRecyclerView();
            setLoading(false, false);
        }
        //adapter.notifyDataSetChanged();
    }


    @Override
    public void onListClick(DisplayObj displayObj, int position) {
        NewsDetailActivity_.intent(this).conteudo(data.conteudos.get(position)).start();
        //NewsDetailActivity_.intent(MainActivity.this).secao(data.conteudos.get(position).secao).start();
    }

    private void getLayoutSizes() {
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                rootLayoutWidth = rootLayout.getWidth();

                imageHeight = (int) (rootLayout.getWidth()*0.61);
                //NewsAdapter.heightToExpand = (int) (NewsAdapter.height * proportion);

            }

        });
    }
}
