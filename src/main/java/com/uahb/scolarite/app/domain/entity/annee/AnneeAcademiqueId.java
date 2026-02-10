package com.uahb.scolarite.app.domain.entity.annee;

import com.uahb.scolarite.app.domain.shared.BaseEntity;

public final class AnneeAcademiqueId extends BaseEntity<String> {

    public final String annee;
    public AnneeAcademiqueId(String annee) {
        super(annee);
        this.annee=annee;

    }

}
