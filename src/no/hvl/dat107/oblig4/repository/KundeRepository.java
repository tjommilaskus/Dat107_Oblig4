package no.hvl.dat107.oblig4.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;

import static com.mongodb.client.model.Filters.eq;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import no.hvl.dat107.oblig4.entity.Kunde;

public class KundeRepository {
	private MongoClient client;
	private MongoDatabase db;
	private MongoCollection<Kunde> kunder;
	
	public KundeRepository(MongoClient client) {
		super();
		this.client = client;
		this.db = client.getDatabase("oblig4-db");
		this.kunder = db.getCollection("kunde", Kunde.class);
	}
	
	public Kunde findByKnr(int knr) {
		Bson filter = eq("knr", knr);

		return kunder.find(filter).first();
	}

	public Kunde save(Kunde kunde) {
		kunder.insertOne(kunde);

		return kunde;
	}

	public Kunde delete(int knr) {
		Bson filter = eq("knr", knr);
		Kunde kunde = kunder.find(filter).first();

		if (kunde != null) {
			kunder.deleteOne(filter);
		}
		return kunde;
	}

	public Kunde update(String id, Kunde kunde) {
		ObjectId objectId = new ObjectId(id);
		kunde.setId(objectId);

		Bson filter = eq("id", objectId);

		FindOneAndReplaceOptions returnDoc = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);
		return kunder.findOneAndReplace(filter, kunde, returnDoc);
	}
}
