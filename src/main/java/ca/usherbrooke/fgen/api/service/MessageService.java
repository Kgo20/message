package ca.usherbrooke.fgen.api.service;

import ca.usherbrooke.fgen.api.business.*;
import ca.usherbrooke.fgen.api.mapper.MessageMapper;
import org.apache.ibatis.annotations.Param;
import org.jsoup.parser.Parser;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;




@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MessageService {

	@Inject
	MessageMapper messageMapper;

	@GET
	@Path("getmessages/{trimester}/{profile}/{unit}")

	public List<Message> getMessages(
			@PathParam("trimester") String trimesterId,
			@PathParam("profile") String profileId,
			@PathParam("unit") String unit
	) {
		List<Message> messages = messageMapper.select(trimesterId, profileId, unit, null);
		return unescapeEntities(messages);
	}


	@GET
	@Path("getallmessages")
	public List<Message> getAllMessages(
	) {
		List<Message> messages = messageMapper.allMessages();
		return this.unescapeEntities(messages);
	}

	@GET
	@Path("getmessage/{id}")
	public Message getMessage(
			@PathParam("id") Integer id
	) {
		Message message = messageMapper.selectOne(id);
		return unescapeEntities(message);
	}

	@DELETE
	@Path("deletemessage/{id}")
	public void deleteMessage(
			@PathParam("id") Integer id
	) {
		messageMapper.deleteOne(id);
		return;
	}


	@PUT
	@Path("putmessage")
	//@RolesAllowed({Roles.TEACHER})
	public void insertMessage(Message message) {
		messageMapper.insertMessage(message);
	}

	@GET
	@Path("getnewid")
	//@RolesAllowed({Roles.TEACHER})
	public Integer getnewid() {
		Integer id = messageMapper.getNewId();
		return id;
	}

	public static Message unescapeEntities(Message message) {
		message.description = Parser.unescapeEntities(message.description, true);
		return message;
	}

	public List<Message> unescapeEntities(List<Message> messages) {
		return messages
				.stream()
				.map(MessageService::unescapeEntities)
				.collect(Collectors.toList());
	}

	@GET
	@Path("listActionCompte")
	@Consumes(MediaType.APPLICATION_JSON) // Ajoutez cette annotation
	public List<Info> insertMessage(@QueryParam("cip") String cip) { // Ajoutez le paramètre
		System.out.println("Insert message called with: " + cip);
		return Database.loadCompte(cip);
	}

	@GET
	@Path("getCompteId")
	@Consumes(MediaType.APPLICATION_JSON) // Ajoutez cette annotation
	public Compte getCompteId(@QueryParam("cip") String cip) { // Ajoutez le paramètre
		return Database.getCompteId(cip);
	}

	@POST
	@Path("createUsager")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createCompte(CreateUsager infoCreate) {
		System.out.println("Infos reçues: " + infoCreate.getCip());
		System.out.println("Infos reçues: " + infoCreate.getPrenom());
		System.out.println("Infos reçues: " + infoCreate.getNom());
		System.out.println("Infos reçues: " + infoCreate.getCourriel());
		Database.creeUsager(infoCreate.getCip(), infoCreate.getPrenom(), infoCreate.getNom(), infoCreate.getCourriel());
		// Vous pouvez ici traiter les données comme nécessaire
		return true;
	}

	@POST
	@Path("createCompte")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean createCompte(CreateCompte infoCreate) {
		System.out.println("Infos reçues: " + infoCreate.getNom());
		System.out.println("Infos reçues: " + infoCreate.getCip());
		System.out.println("Infos reçues: " + infoCreate.getMontant_depart());
		System.out.println("Infos reçues: " + infoCreate.getMontant());
		Database.creeCompte(infoCreate.getNom(), infoCreate.getCip(), Double.parseDouble(infoCreate.getMontant_depart()), Double.parseDouble(infoCreate.getMontant()));
		// Vous pouvez ici traiter les données comme nécessaire
		return true;
	}

	@POST
	@Path("addMontantDepart")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean addMontantDepart(AddMDep infoAdd) {
		System.out.println("Infos reçues: " + infoAdd.getIdCompte());
		System.out.println("Infos reçues: " + infoAdd.getMontant());
		Database.ajoutMontantDepart(infoAdd.getIdCompte(), Double.parseDouble(infoAdd.getMontant()));
		// Vous pouvez ici traiter les données comme nécessaire
		return true;
	}

	@POST
	@Path("buyAction")
	@Consumes(MediaType.APPLICATION_JSON)
	public boolean buyAction(Buy infoBuy) {
		System.out.println("Infos reçues: " + infoBuy.getNom());
		System.out.println("Infos reçues: " + infoBuy.getSymbole());
		System.out.println("Infos reçues: " + infoBuy.getNbAction());
		System.out.println("Infos reçues: " + infoBuy.getPrixCourant());
		System.out.println("Infos reçues: " + infoBuy.getCompte());
		Database.acheterAction(infoBuy.getNom(), infoBuy.getSymbole(), infoBuy.getCompte(), Integer.parseInt(infoBuy.getNbAction()), Double.parseDouble(infoBuy.getPrixCourant()));
		// Vous pouvez ici traiter les données comme nécessaire
		return true;
	}

	@POST
	@Path("sellAction")
	@Consumes(MediaType.APPLICATION_JSON) // Ajoutez cette annotation
	public boolean sellAction(Sell sell) { // Ajoutez le paramètre
		System.out.println("Delete " + sell.getSymbole() + " " + sell.getCip() + " " + sell.getCompte());
		System.out.println(sell.getCip() + " " + sell.getCompte()  + " " + sell.getSymbole() + " " + sell.getPrixVente() + " " + sell.getNbAVendre());
		Database.vendreAction(sell.getSymbole(), sell.getCompte(), Integer.parseInt(sell.getNbAVendre()), Double.parseDouble(sell.getPrixVente()));
		return true;
	}
}
