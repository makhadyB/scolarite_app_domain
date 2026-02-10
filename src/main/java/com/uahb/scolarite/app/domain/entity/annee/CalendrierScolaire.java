package com.uahb.scolarite.app.domain.entity.annee;

import java.time.LocalDate;

public class CalendrierScolaire {
    private final LocalDate dateOuverture;
    private final LocalDate dateFermeture;
    private final LocalDate dateOuvertureInscription;
    private final LocalDate dateFermetureInscription;

    public CalendrierScolaire(LocalDate dateOuverture, LocalDate dateFermeture, LocalDate dateOuvertureInscription, LocalDate dateFermetureInscription) {
        this.dateOuverture = dateOuverture;
        this.dateFermeture = dateFermeture;
        this.dateOuvertureInscription = dateOuvertureInscription;
        this.dateFermetureInscription = dateFermetureInscription;
    }

    public LocalDate getDateOuverture() {
        return dateOuverture;
    }

    public LocalDate getDateFermeture() {
        return dateFermeture;
    }

    public LocalDate getDateOuvertureInscription() {
        return dateOuvertureInscription;
    }

    public LocalDate getDateFermetureInscription() {
        return dateFermetureInscription;
    }
}
