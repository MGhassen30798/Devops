package com.esprit.examen.entities.dto;

import com.esprit.examen.entities.DetailFacture;
import com.esprit.examen.entities.Fournisseur;
import com.esprit.examen.entities.Reglement;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
public class FactureDTO implements Serializable {
    private float montantRemise;
    private float montantFacture;
    private Date dateCreationFacture;
    private Date dateDerniereModificationFacture;
    private Boolean archivee;
    private Set<DetailFacture> detailsFacture;
    private Fournisseur fournisseur;
    private Set<Reglement> reglements;


}
