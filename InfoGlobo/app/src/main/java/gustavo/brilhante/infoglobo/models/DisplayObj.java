package gustavo.brilhante.infoglobo.models;

import android.graphics.Bitmap;

/**
 * Created by Gustavo on 11/04/17.
 */

public class DisplayObj {

    public DisplayObj(Conteudo conteudo){
        this.secaoNome = conteudo.secao.nome;
        this.titulo = conteudo.titulo;
    }

    public String secaoNome;

    public String titulo;

    public Bitmap imageNew;

    public boolean collapsed = false;

    public Bitmap getImageNew() {
        return imageNew;
    }

    public void setImageNew(Bitmap imageNew) {
        this.imageNew = imageNew;
    }


    /*public void setImageNew(Bitmap imageNew) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        imageNew.compress(Bitmap.CompressFormat.PNG, 100, stream);
        this.imageNew = stream.toByteArray();
    }

    public Bitmap getImageNew(){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        Bitmap bmp = BitmapFactory.decodeByteArray(this.imageNew, 0, this.imageNew.length, options);
        return bmp;
    }*/

}
