package com.esprit.examen.entities.dto;

import com.esprit.examen.entities.CategorieFournisseur;
import com.esprit.examen.entities.DetailFournisseur;
import com.esprit.examen.entities.Facture;
import com.esprit.examen.entities.SecteurActivite;
import lombok.*;
import java.util.Set;

@Getter
@Setter
@Builder
public class FournisseurDTO  {
	private Long idFournisseur;
	private String code;
	private String libelle;
	private CategorieFournisseur categorieFournisseur;
	private Set<Facture> factures;
    private Set<SecteurActivite> secteurActivites;
    private DetailFournisseur detailFournisseur;


}
