package dds.grupo4.tpimpacto.repositories;

import dds.grupo4.tpimpacto.entities.TipoConsumo;

import java.util.Optional;

public interface TipoConsumoRepository extends BaseRepository<TipoConsumo> {
    Optional<TipoConsumo> getByNombre(String nombreTipo);
}
