package in.gov.ap.igrs.datasync.app.data;

public class DocUser {
	/*
	 * public static void main(String[] args) { List<Document> pipeline1 = null;
	 * List<Document> result = null; MongoCollection<Document> userCollection=null;
	 * 
	 * try (MongoClient mongoClient =
	 * MongoClients.create("mongodb://localhost:27017")) { MongoDatabase database =
	 * mongoClient.getDatabase("PDE");
	 * 
	 * // Join multiple collections with multiple conditions using the aggregate
	 * method MongoCollection<Document> ordersCollection =
	 * database.getCollection("document_details"); for (Document doc :
	 * ordersCollection.find()) { System.out.println(doc.toJson());
	 * 
	 * } userCollection = database.getCollection("users"); for (Document doc :
	 * userCollection.find()) { System.out.println(doc.toJson());
	 * 
	 * } Document query = new Document("$lookup", new Document("from",
	 * "users").append("localField",userCollection.getDocumentClass().get
	 * ).append("foreignField", "userId") .append("as", "user"));
	 * //.append("$match", Filters.eq("$customer.isPresenter", true))); pipeline1 =
	 * Arrays.asList(query); //result =
	 * ordersCollection.aggregate(pipeline).into(new ArrayList<>());
	 * System.out.println("No.of documents:"+query.size());
	 * System.out.println("Documents:"+query.toJson());
	 * 
	 * // Print the result for (Document document : pipeline1) {
	 * System.out.println("new change:"+document.toJson()); ; //
	 * System.out.println(doc.toJson()); // query.append("$match", Filters.and( //
	 * Filters.eq("isPresenter", "true") // Filters.gt("total_amount", 1000), //
	 * Filters.gt("customer.age", 25) // ));
	 * 
	 * // System.out.println(query.toJson());
	 * 
	 * 
	 * 
	 * // Execute the query List <DocPartyPOJO2> docLists = new
	 * ArrayList<DocPartyPOJO2>();
	 * 
	 * 
	 * for (Document finalDoc
	 * :userCollection.aggregate(Collections.singletonList(query))) {
	 * 
	 * System.out.println("Aggregated documents:"+finalDoc.toJson()); Gson gson =
	 * new GsonBuilder().create(); docLists.add(gson.fromJson(finalDoc.toJson(),
	 * DocPartyPOJO2.class));
	 * 
	 * }
	 * 
	 * 
	 * 
	 * // MongoClient mongoClient = new MongoClient(); //MongoDatabase db =
	 * mongoClient.getDatabase("existence"); // MongoCollection<Document> collection
	 * = database.getCollection("ordersCollection"); // Clean up resources
	 * 
	 * } }
	 * 
	 * 
	 * }
	 * 
	 */}
