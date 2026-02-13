package com.uahb.scolarite.app.domain.entity.annee;

import com.uahb.scolarite.app.domain.event.EventAggregateRoot;
import com.uahb.scolarite.app.domain.event.anneeacademique.AnneeAcademiqueEvent;
import com.uahb.scolarite.app.domain.exception.ScolariteException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class AnneeAcademique  extends EventAggregateRoot {

    private final AnneeAcademiqueId anneeAcademiqueId;
    private  LocalDate dateOuverture;
    private  LocalDate dateFermeture;
    private  LocalDate dateOuvertureInscription;
    private  LocalDate dateFermetureInscription;
    private  LocalDate datePublication;
    private  Statut statut;
    private static final int DUREE_ANNEE_SCOLAIRE_MOIS = 9;
    private List<MoisAcademique> moisAcademiqueList;

    public AnneeAcademique(AnneeAcademiqueId anneeAcademiqueId) {
        this.anneeAcademiqueId = anneeAcademiqueId;
        this.statut=Statut.BROUILLON;
    }

    public void creer(CalendrierScolaire calendrierScolaire){
        initialiserDate(calendrierScolaire);
        verifierNombreDeMoisScolaire();
        moisAcademiqueList = genererMoisAcademique();
       // addEvent(new AnneeAcademiqueEvent(this));
    }

    public void modifier(CalendrierScolaire calendrierScolaire){
        verifierStatut("modifier" ,Statut.BROUILLON);
        initialiserDate(calendrierScolaire);
        verifierNombreDeMoisScolaire();
        moisAcademiqueList = genererMoisAcademique();
    }

    public void publier(LocalDate datePublication){
        verifierStatut("publier", Statut.BROUILLON);
        this.datePublication = datePublication;
        this.statut = Statut.PUBLIEE;
    }

    public void ouvertureInscription(CalendrierScolaire calendrierScolaire){
        verifierStatut("ouvrir inscription", Statut.PUBLIEE);
        this.statut = Statut.INSCRIPTIONS_OUVERTES;
    }

    public void suspendreInscription(CalendrierScolaire calendrierScolaire){
        verifierStatut("suspendre inscription", Statut.INSCRIPTIONS_OUVERTES);
        this.statut = Statut.INSCRIPTION_SUSPENDUES;

    }

    public void reouvrirInscription(CalendrierScolaire calendrierScolaire){
        verifierStatut("Re-ouvrir inscription", Statut.INSCRIPTION_SUSPENDUES);
        this.statut = Statut.INSCRIPTIONS_OUVERTES;
    }

    public void fermerInscription(CalendrierScolaire calendrierScolaire){
        verifierStatut("Fermer inscription", Statut.INSCRIPTIONS_OUVERTES,Statut.INSCRIPTION_SUSPENDUES);
        this.statut = Statut.INSCRIPTIONS_FERMEES;
    }

    public void cloturerAnnee(CalendrierScolaire calendrierScolaire){
        verifierStatut("Cloturer année", Statut.INSCRIPTIONS_FERMEES);
        this.statut = Statut.CLOTUREE;
    }

    private void initialiserDate(CalendrierScolaire calendrierScolaire){
        if (!calendrierScolaire.getDateOuverture().isBefore(calendrierScolaire.getDateFermeture())){
            throw new ScolariteException("La date de debut de l'année doit être antérieure à la date de fermeture de l'année");
        }

        if (!calendrierScolaire.getDateOuvertureInscription().isBefore(calendrierScolaire.getDateFermetureInscription())){
            throw new ScolariteException("La date de debut des inscriptions doit etre antérieure à la date de fermeture des inscriptions");
        }

        if (calendrierScolaire.getDateOuvertureInscription().isAfter(calendrierScolaire.getDateOuverture())){
            throw new ScolariteException("L'ouverture des inscription doit etre avant la date d'ouverture de l'année scolaire");
        }

        if (!calendrierScolaire.getDateFermetureInscription().isAfter(calendrierScolaire.getDateOuverture())){
            throw new ScolariteException("La date d'arret des inscriptions doit etre postérieure au debut de l'année ");
        }

        if (calendrierScolaire.getDateOuvertureInscription().isAfter(calendrierScolaire.getDateFermeture())){
            throw new ScolariteException("La date de d'arret des inscriptions doit etre antérieure ou égale à la fin de l'année");
        }
        dateOuverture = calendrierScolaire.getDateOuverture();
        dateFermeture = calendrierScolaire.getDateFermeture();
        dateOuvertureInscription = calendrierScolaire.getDateOuvertureInscription();
        dateFermetureInscription = calendrierScolaire.getDateFermetureInscription();

    }

    private void verifierNombreDeMoisScolaire(){
        long mois = ChronoUnit.MONTHS.between(
                dateOuverture.withDayOfMonth(1),
                dateFermeture.withDayOfMonth(1)
        );

        if (dateOuverture.plusMonths(mois).isBefore(dateFermeture)) {
            mois++;
        }

        if (mois != DUREE_ANNEE_SCOLAIRE_MOIS) {
            throw new ScolariteException(
                    "Une année scolaire doit couvrir exactement "
                            + DUREE_ANNEE_SCOLAIRE_MOIS + " mois"
            );
        }
    }
    
    private  List<MoisAcademique> genererMoisAcademique(){
        List<MoisAcademique> moisAcademiqueList = new ArrayList<>();
        LocalDate resetDateActuelle = dateOuverture.withDayOfMonth(1);
        for (int i = 0 ; i < DUREE_ANNEE_SCOLAIRE_MOIS ; i++) {
            moisAcademiqueList.add(new MoisAcademique(resetDateActuelle.getMonthValue(), resetDateActuelle.getYear()));
            resetDateActuelle =resetDateActuelle.plusMonths(1);
        }
        return  moisAcademiqueList;
    }



    private void verifierStatut(String action, Statut... statuts){
        for (Statut statutAutorise : statuts) {
            if (this.statut == statutAutorise) {
                return; // OK
            }
        }
        throw new ScolariteException(
                "Action interdite : " + action + " dans l'état " + this.statut
        );
    }

}
