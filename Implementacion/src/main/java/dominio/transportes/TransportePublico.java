package dominio.transportes;

import java.util.List;

public class TransportePublico extends MedioTransporte {

    private TipoTransportePublico tipoTransportePublico;
    private String linea ;
    private List<Parada> paradas ;

    public TransportePublico(TipoTransportePublico tipoTransportePublico, String linea, List<Parada> paradas) {
        this.tipoTransportePublico = tipoTransportePublico;
        this.linea = linea;
        this.paradas = paradas;
    }

    public TipoTransportePublico getTipoTransportePublico() {
        return tipoTransportePublico;
    }

    public void setTipoTransportePublico(TipoTransportePublico tipoTransportePublico) {
        this.tipoTransportePublico = tipoTransportePublico;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public List<Parada> getParadas() {
        return paradas;
    }

    public void setParadas(List<Parada> paradas) {
        this.paradas = paradas;
    }
}
