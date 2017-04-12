package gustavo.brilhante.infoglobo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gustavo on 10/04/17.
 */

public class Secao implements Serializable {

    @SerializedName("nome")
    @Expose
    public String nome;
    @SerializedName("url")
    @Expose
    public String url;

}
