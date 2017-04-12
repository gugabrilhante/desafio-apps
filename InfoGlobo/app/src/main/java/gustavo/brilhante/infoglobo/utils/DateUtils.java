package gustavo.brilhante.infoglobo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Gustavo on 12/04/17.
 */

public class DateUtils {

    public static String convertDateFromService(String date_s){
        String dateStr = date_s;

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = null;
        try {
            date = dt.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateStr;
        }
        SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dt1.format(date);
    }

}
