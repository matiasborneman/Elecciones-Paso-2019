package negocio;

import soporte.TextFile;

import java.util.Collection;

public class Regiones {
    private TextFile fileRegiones;
    private Region pais;

    public Regiones(String path) {
        fileRegiones = new TextFile(path + "\\descripcion_regiones.dsv");
        pais = fileRegiones.identificarRegiones();
    }

    public Collection getDistritos() {
        return pais.getSubregiones();
    }
}
