package dds.grupo4.tpimpacto.repositories;

import dds.grupo4.tpimpacto.entities.medioTransporte.Combustible;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class CombustibleRepositoryImpl extends BaseRepositoryImpl<Combustible> implements CombustibleRepository {

    public CombustibleRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Combustible> getEntityClass() {
        return Combustible.class;
    }
}
