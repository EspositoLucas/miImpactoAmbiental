package dds.grupo4.tpimpacto;

import dds.grupo4.tpimpacto.services.*;
import dds.grupo4.tpimpacto.services.calculodistancias.apidistancias.GeoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Profile("!test")
@Component
@Slf4j
public class DbInitializer implements ApplicationRunner {

    private final UnidadService unidadService;
    private final TipoConsumoService tipoConsumoService;
    private final OrganizacionService organizacionService;
    private final PersonaService personaService;
    private final GeoService geoService;
    private final TransportePublicoService transportePublicoService;

    public DbInitializer(UnidadService unidadService, TipoConsumoService tipoConsumoService, OrganizacionService organizacionService, PersonaService personaService, GeoService geoService, TransportePublicoService transportePublicoService) {
        this.unidadService = unidadService;
        this.tipoConsumoService = tipoConsumoService;
        this.organizacionService = organizacionService;
        this.personaService = personaService;
        this.geoService = geoService;
        this.transportePublicoService = transportePublicoService;
    }

    @Override
    public void run(ApplicationArguments args) {
        log.info("Corriendo el DbInitializer");

        CompletableFuture<Void> geoServiceTask = geoService.seedData();
        CompletableFuture<Void> unidadServiceTask = unidadService.seedData();

        CompletableFuture<Void> tasksTerminadas = CompletableFuture.allOf(geoServiceTask, unidadServiceTask);

        tasksTerminadas.thenRunAsync(transportePublicoService::seedData);

        tasksTerminadas.thenRun(() -> {
            tipoConsumoService.seedData();
            organizacionService.seedData();
            personaService.seedData();
        });
    }
}
