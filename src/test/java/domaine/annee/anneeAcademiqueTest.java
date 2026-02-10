package domaine.annee;

import com.uahb.scolarite.app.domain.entity.annee.AnneeAcademique;
import com.uahb.scolarite.app.domain.entity.annee.AnneeAcademiqueId;
import com.uahb.scolarite.app.domain.entity.annee.CalendrierScolaire;
import com.uahb.scolarite.app.domain.exception.ScolariteException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class anneeAcademiqueTest {
    @Test
    void creation_annee_en_brouillon_Test(){
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        assertDoesNotThrow(()->anneeAcademique.creer(datesAnneeScolarite()));
    }

    @Test
    void modification_annee_en_brouillon_Test(){
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        assertDoesNotThrow(()->anneeAcademique.modifier(datesAnneeScolariteModifier()));
    }


    @Test
    void publication_annee_en_brouillon_Test(){
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        assertDoesNotThrow(()->anneeAcademique.publier(LocalDate.of(2025,9,1)));
    }

    @Test
    void ouverture_inscription_avec_echec_Test(){
        // Echec: ouverture interdite si l'annee car l'année vient detre creer avec le STATUT BROUILLON
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026")); // on cree l'annee
        anneeAcademique.creer(datesAnneeScolarite()); //on appelle la fonction de creation
        assertThrows(ScolariteException.class, () -> anneeAcademique.ouvertureInscription(datesAnneeScolarite()));
    }

    @Test
    void ouverture_inscription_avec_succes_cas1_Test(){
        // Succes: ouverture apres publication
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        //publier l'annee
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        assertDoesNotThrow(() -> anneeAcademique.ouvertureInscription(datesAnneeScolarite()));
    }

    @Test
    void ouverture_inscription_avec_succes_cas2_Test(){
        // Succes: ouverture apres modification puis publication
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.modifier(datesAnneeScolariteModifier());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        assertDoesNotThrow(() -> anneeAcademique.ouvertureInscription(datesAnneeScolarite()));
    }

    @Test
    void fermer_inscription_avec_echec_Test(){
        // Echec: fermeture interdite si inscriptions non ouvertes
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        assertThrows(ScolariteException.class, () -> anneeAcademique.fermerInscription(datesAnneeScolarite()));
    }

    @Test
    void fermer_inscription_avec_succes_cas1_Test(){
        // Succes: fermeture apres ouverture
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        anneeAcademique.ouvertureInscription(datesAnneeScolarite());
        assertDoesNotThrow(() -> anneeAcademique.fermerInscription(datesAnneeScolarite()));
    }

    @Test
    void fermer_inscription_avec_succes_cas2_Test(){
        // Succes: fermeture apres suspension puis reouverture
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        anneeAcademique.ouvertureInscription(datesAnneeScolarite());
        anneeAcademique.suspendreInscription(datesAnneeScolarite());
        anneeAcademique.reouvrirInscription(datesAnneeScolarite());
        assertDoesNotThrow(() -> anneeAcademique.fermerInscription(datesAnneeScolarite()));
    }

    @Test
    void suspendre_inscription_avec_echec_Test(){
        // Echec: suspension interdite si inscriptions non ouvertes
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        assertThrows(ScolariteException.class, () -> anneeAcademique.suspendreInscription(datesAnneeScolarite()));
    }

    @Test
    void suspendre_inscription_avec_succes_cas1_Test(){
        // Succes: suspension apres ouverture
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        anneeAcademique.ouvertureInscription(datesAnneeScolarite());
        assertDoesNotThrow(() -> anneeAcademique.suspendreInscription(datesAnneeScolarite()));
    }

    @Test
    void suspendre_inscription_avec_succes_cas2_Test(){
        // Succes: suspension apres reouverture
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        anneeAcademique.ouvertureInscription(datesAnneeScolarite());
        anneeAcademique.suspendreInscription(datesAnneeScolarite());
        anneeAcademique.reouvrirInscription(datesAnneeScolarite());
        assertDoesNotThrow(() -> anneeAcademique.suspendreInscription(datesAnneeScolarite()));
    }

    @Test
    void cloturer_inscription_avec_echec_Test(){
        // Echec: cloture interdite si inscriptions non fermees
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        anneeAcademique.ouvertureInscription(datesAnneeScolarite());
        assertThrows(ScolariteException.class, () -> anneeAcademique.cloturerAnnee(datesAnneeScolarite()));
    }

    @Test
    void cloturer_inscription_avec_succes_cas1_Test(){
        // Succes: cloture apres fermeture
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        anneeAcademique.ouvertureInscription(datesAnneeScolarite());
        anneeAcademique.fermerInscription(datesAnneeScolarite());
        assertDoesNotThrow(() -> anneeAcademique.cloturerAnnee(datesAnneeScolarite()));
    }

    @Test
    void cloturer_inscription_avec_succes_cas2_Test(){
        // Succes: cloture apres suspension puis reouverture et fermeture
        AnneeAcademique anneeAcademique = new AnneeAcademique(new AnneeAcademiqueId("2025-2026"));
        anneeAcademique.creer(datesAnneeScolarite());
        anneeAcademique.publier(LocalDate.of(2025,9,1));
        anneeAcademique.ouvertureInscription(datesAnneeScolarite());
        anneeAcademique.suspendreInscription(datesAnneeScolarite());
        anneeAcademique.reouvrirInscription(datesAnneeScolarite());
        anneeAcademique.fermerInscription(datesAnneeScolarite());
        assertDoesNotThrow(() -> anneeAcademique.cloturerAnnee(datesAnneeScolarite()));
    }

    private CalendrierScolaire datesAnneeScolarite(){
        return new CalendrierScolaire(
                LocalDate.of(2025,10,1),
                LocalDate.of(2026,6,30),
                LocalDate.of(2025,9,1),
                LocalDate.of(2025,10,15)
        );
    }

    private CalendrierScolaire datesAnneeScolariteModifier(){
        return new CalendrierScolaire(
                LocalDate.of(2025,10,1),
                LocalDate.of(2026,6,20),
                LocalDate.of(2025,9,1),
                LocalDate.of(2025,10,15)
        );
    }

}
