package gustavo.brilhante.infoglobo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Gustavo on 10/04/17.
 */

public class Conteudo implements Serializable {

    @SerializedName("autores")
    @Expose
    public List<String> autores;
    @SerializedName("informePublicitario")
    @Expose
    public Boolean informePublicitario;
    @SerializedName("subTitulo")
    @Expose
    public String subTitulo;
    @SerializedName("texto")
    @Expose
    public String texto;
    @SerializedName("atualizadoEm")
    @Expose
    public String atualizadoEm;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("publicadoEm")
    @Expose
    public String publicadoEm;
    @SerializedName("secao")
    @Expose
    public Secao secao;
    @SerializedName("tipo")
    @Expose
    public String tipo;
    @SerializedName("titulo")
    @Expose
    public String titulo;
    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("urlOriginal")
    @Expose
    public String urlOriginal;
    @SerializedName("imagens")
    @Expose
    public List<Imagen> imagens;

}