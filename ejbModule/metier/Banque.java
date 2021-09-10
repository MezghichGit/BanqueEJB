package metier;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import metier.entities.Compte;

/**
 * Session Bean implementation class Banque
 */
@Stateless(name="BK")
@LocalBean
public class Banque implements BanqueRemote,BanqueLocal {

  @PersistenceContext(unitName="BanqueEJB")  // injection de dépendences, le name c'est pour indiquer dans quelle data source se fait la persistance
  EntityManager em;
  
    public Banque() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public Compte addCompte(Compte compte) {
		em.persist(compte);
		return compte;
	}

	@Override
	public Compte getCompte(Long code) {
		Compte compte = em.find(Compte.class,code);
		return compte;
	}

	@Override
	public List<Compte> listComptes() {
	Query query = em.createQuery("SELECT c FROM Compte c", Compte.class);
		return query.getResultList();
	}

	@Override
	public void verser(Long code, double mnt) {
		Compte c = getCompte(code);
		c.setSolde(c.getSolde()+mnt);
		
	}

	@Override
	public void retirer(Long code, double mnt) {
		Compte c = getCompte(code);
		c.setSolde(c.getSolde()-mnt);
		
	}

	@Override
	public void virement(Long code1, Long code2, double mnt) {
		retirer(code1, mnt);
		verser(code2,mnt);
		
	}

}
