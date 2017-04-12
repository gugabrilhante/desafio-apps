package gustavo.brilhante.infoglobo.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import gustavo.brilhante.infoglobo.R;
import gustavo.brilhante.infoglobo.models.Conteudo;

/**
 * Created by Gustavo on 12/04/17.
 */

public class StringUtils {

    public static Spannable makePartTextSelected(String text, String textSelected, String endText, Context context){

        String text2 = text + textSelected
                + endText;

        Spannable spannable = new SpannableString(text2);

        spannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.colorSelectedText)), text.length(), (text + textSelected).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return spannable;

    }

    public static Spannable getAutorText(Conteudo conteudo, Context context){

        if(conteudo.autores!=null && conteudo.autores.size()>0)return makePartTextSelected("Por ", conteudo.autores.get(0),"\n"+DateUtils.convertDateFromService(conteudo.publicadoEm), context);
        else return makePartTextSelected("", "","\n"+DateUtils.convertDateFromService(conteudo.publicadoEm), context);
    }

}
