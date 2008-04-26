package net.java.dev.cejug.classifieds.beans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import net.java.dev.cejug.classifieds.domain.model.entities.Adversiment;

/**
 * 
 * @author Rafael Carneiro [rafaelcarneirob@gmail.com]
 * 
 */
public class AdversimentBean {

	private List<Adversiment> adversiments = new ArrayList<Adversiment>();

	
	public void pesquisar(ActionEvent actionEvent) {
		
	}

	public List<Adversiment> getAdversiments() {

		Adversiment adversiment = new Adversiment();

		adversiment.setAdvertiser("Rafael Carneiro");
		adversiment.setFullText("Vendo um voucher");
		adversiment.setHeadline("Teste");
		adversiment.setShortDescription("SCJP");
		adversiment.setStatus(0);

		adversiments.add(adversiment);

		return adversiments;
	}

	public void setAdversiments(List<Adversiment> adversiments) {
		this.adversiments = adversiments;
	}

}
