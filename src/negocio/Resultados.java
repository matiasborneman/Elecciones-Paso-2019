package negocio;
import soporte.TSB_OAHashtable;
import soporte.TextFile;

import java.util.Collection;

public class Resultados {
    private TSB_OAHashtable tabla;
    private TextFile fileMesas;

    public Resultados(String path)
    {
        tabla= new TSB_OAHashtable();
        fileMesas= new TextFile(path+"\\mesas_totales_agrp_politica.dsv");
        fileMesas.sumarVotosPorRegion(this);
    }

    public void sumarVotos(String codRegion,String codAgrupacion, int votos)
    {
        // Buscamos la region en la tabla y la creamos si no existe
        if(tabla.get(codRegion)==null)
            tabla.put(codRegion,new Agrupaciones());
        //Actualizamos el total de votos
        Agrupaciones a= (Agrupaciones)tabla.get(codRegion);
        a.getAgrupacion(codAgrupacion).sumarVotos(votos);
    }
    public Collection getResultadosRegion(String codRegion)
    {
        Agrupaciones a = (Agrupaciones) tabla.get(codRegion);
        return a.getResultados();
    }
}
