package com.uahb.scolarite.app.domain.entity.annee;

import java.util.Objects;

public final class MoisAcademique {

    private final int mois;
    private final int annee;

    public MoisAcademique(int mois, int annee) {
        if(mois<1 || mois>12){
            throw new IllegalArgumentException("Mois invalide");
        }
        this.mois = mois;
        this.annee = annee;

    }

    public int getMois() {
        return mois;
    }

    public int getAnnee() {
        return annee;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MoisAcademique that = (MoisAcademique) o;
        return mois == that.mois && annee == that.annee;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mois, annee);
    }
}
