package gustavo.brilhante.infoglobo.layout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;

import gustavo.brilhante.infoglobo.R;

/**
 * Created by Gustavo on 10/04/17.
 */

public class InfoGloboTextView extends android.support.v7.widget.AppCompatTextView {

    int fontFamily = 0;
    int textSize = 0;

    public InfoGloboTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InfoGloboTextView, 0, 0);

        try{

            fontFamily = typedArray.getInt(R.styleable.InfoGloboTextView_dinFamily, 0);
            textSize = typedArray.getDimensionPixelSize(R.styleable.InfoGloboTextView_customTextSize, 0);

        } finally {

            setupFont(context);

        }

    }

    public void setEllipsized(boolean isEllipsized, int lines){
        if(lines>0) {
            this.setEllipsize(TextUtils.TruncateAt.END);
            this.setLines(lines);
        }else{
            this.setEllipsize(null);
        }
    }

    void setupFont(Context context){

        // Sets the font size. The value is passed as SP, but by the time it is retrieved, it has been already converted to PX.
        if(textSize == 0){
            setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_medium));
        } else {
            setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        }

        switch (fontFamily){

            case 0 : DEFAULT :
            setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/DINPro-Regular.otf"));
                break;

            case 1:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/DINPro-Medium.otf"));
                break;

            case 2:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/DINPro-Light.otf"));
                break;

            case 3:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/DINPro-Black.otf"));
                break;

            case 4:
                setTypeface(Typeface.createFromAsset(getContext().getAssets(), "font/DINPro-Bold.otf"));
                break;

        }

    }

}
