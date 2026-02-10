package com.uahb.scolarite.app.domain.event.anneeacademique;

import com.uahb.scolarite.app.domain.entity.annee.AnneeAcademique;
import com.uahb.scolarite.app.domain.event.DomainEvent;

import java.time.LocalDateTime;

public class AnneeAcademiqueEvent  implements DomainEvent {
   private  final AnneeAcademique anneeAcademique;

    public AnneeAcademiqueEvent(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }
// on implement les methodes du domaine
    @Override
    public String aggregateId() {
        return "";
    }

    @Override
    public String aggregateType() {
        return "";
    }

    @Override
    public LocalDateTime occurredAt() {
        return null;
    }

}
