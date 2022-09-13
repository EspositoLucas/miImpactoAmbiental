package dds.grupo4.tpimpacto.dtos;

import dds.grupo4.tpimpacto.entities.organizacion.Sector;
import dds.grupo4.tpimpacto.utils.ListUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SectorDto {
    private long id;
    private String nombre;
    private IdTextPair organizacion;
    private IdTextPair espacio;
    private List<IdTextPair> miembros;
    private List<IdTextPair> solicitudes;

    public static SectorDto from(Sector sector) {
        SectorDto dto = new SectorDto();
        dto.setId(sector.getId());
        dto.setNombre(sector.getNombre());
        dto.setOrganizacion(new IdTextPair(sector.getOrganizacion()));
        dto.setEspacio(new IdTextPair(sector.getEspacio()));
        dto.setMiembros(ListUtils.toIdTextPairList(sector.getMiembros()));
        dto.setSolicitudes(ListUtils.toIdTextPairList(sector.getSolicitudes()));
        return dto;
    }
}
