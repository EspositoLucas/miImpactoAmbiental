package dds.grupo4.tpimpacto.entities.trayecto;

import dds.grupo4.tpimpacto.entities.BaseEntity;
import dds.grupo4.tpimpacto.utils.DateTimeUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Trayecto")
@Table(name = "trayectos")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Trayecto extends BaseEntity {

    @ManyToOne
    @JoinColumn(
            name = "inicio",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Trayectos_Inicio")
    )
    private Lugar inicio;

    @ManyToOne
    @JoinColumn(
            name = "fin",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_Trayectos_Fin")
    )
    private Lugar fin;

    @OneToMany(mappedBy = "trayecto", cascade = CascadeType.ALL)
    private List<MiembroPorTrayecto> miembrosPorTrayecto = new ArrayList<>();

    @OneToMany(mappedBy = "trayecto", cascade = CascadeType.ALL)
    private List<Tramo> tramos = new ArrayList<>();

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public Trayecto(Lugar inicio, Lugar fin, LocalDate fechaInicio, LocalDate fechaFin) {
        this.inicio = inicio;
        this.fin = fin;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public double getDistanciaTotalRecorrida() {
        return tramos.stream()
                .mapToDouble(Tramo::getDistanciaRecorrida)
                .sum();
    }

    public boolean seRealizaEnFecha(LocalDate fecha) {
        return DateTimeUtils.isAfterOrEqual(fecha, fechaInicio) && DateTimeUtils.isBeforeOrEqual(fecha, fechaFin);
    }

    public void addTramo(Tramo tramo) {
        tramos.add(tramo);
        tramo.setTrayecto(this);
    }

    public void addTramos(List<Tramo> tramos) {
        tramos.forEach(this::addTramo);
    }

    public void addMiembros(List<MiembroPorTrayecto> miembrosPorTrayecto) {
        miembrosPorTrayecto.forEach(miembroPorTrayecto -> {
            this.miembrosPorTrayecto.add(miembroPorTrayecto);
            miembroPorTrayecto.setTrayecto(this);
        });
    }
}
