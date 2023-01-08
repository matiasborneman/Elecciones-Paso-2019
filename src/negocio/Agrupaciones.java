package negocio;

import soporte.TSB_OAHashtable;
import soporte.TextFile;

import java.util.Collection;

public class Agrupaciones {

    private static TSB_OAHashtable inicial;
    private TSB_OAHashtable conteo;

    public Agrupaciones() {
        conteo = new TSB_OAHashtable();
        for (Object o:inicial.values()  ) {
            Agrupacion a =(Agrupacion) o;
            conteo.put(a.getCodigo(),new Agrupacion(a.getCodigo(),a.getNombre()));

        }
    }
    public static void leerAgrupaciones(String path)
    {
        TextFile fileAgrupaciones = new TextFile(path + "\\descripcion_postulaciones.dsv");
        inicial= fileAgrupaciones.identificarAgrupaciones();
    }
    public Collection getResultados() {
        return conteo.values();
    }
    public Agrupacion getAgrupacion(String codigo)
    {
        return (Agrupacion) conteo.get(codigo);
    }
}
