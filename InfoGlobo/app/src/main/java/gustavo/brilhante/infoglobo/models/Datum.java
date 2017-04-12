package gustavo.brilhante.infoglobo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gustavo on 10/04/17.
 */

public class Datum implements Serializable{

    @SerializedName("conteudos")
    @Expose
    public List<Conteudo> conteudos = null;
    @SerializedName("produto")
    @Expose
    public String produto;

}