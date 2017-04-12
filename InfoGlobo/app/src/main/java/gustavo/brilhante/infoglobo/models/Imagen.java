package gustavo.brilhante.infoglobo.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Gustavo on 10/04/17.
 */

public class Imagen implements Serializable {

    @SerializedName("autor")
    @Expose
    public String autor;
    @SerializedName("fonte")
    @Expose
    public String fonte;
    @SerializedName("legenda")
    @Expose
    public String legenda;
    @SerializedName("url")
    @Expose
    public String url;


}
