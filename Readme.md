# Project Produit RestAPI
Projet SpringBoot pour la gestion des produits assisté par ordinateurs 
TP SpringBoot construir un rest API 

***

## Pre-requis:
- Java 11 
- Intelij Community 
- Postman
- SpringBoot 2.6.7
- MySQL 8
- Ubuntu Linux 

## Initialisation du projet
### Dépendances Spring 
- Spring Boot Dev Tools
- Spring Data JPA
- MySQL Driver
- Spring Web
- Spring Web Services 
### `pom.xml`
```
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web-services</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>
```

***
## Créer la classe Produit.java dans le package `com.gpao.demo.entities`
### `Produit.java`
```
@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProduit;
    private String nomProduit;
    private Double prixProduit;
    private Date dateCreation;

    public Produit() {
        super();
    }

    public Produit(String nomProduit, Double prixProduit, Date dateCreation) {
        this.nomProduit = nomProduit;
        this.prixProduit = prixProduit;
        this.dateCreation = dateCreation;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public String getNomProduit() {
        return nomProduit;
    }

    public Double getPrixProduit() {
        return prixProduit;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public void setNomProduit(String nomProduit) {
        this.nomProduit = nomProduit;
    }

    public void setPrixProduit(Double prixProduit) {
        this.prixProduit = prixProduit;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "idProduit=" + idProduit +
                ", nomProduit='" + nomProduit + '\'' +
                ", prixProduit=" + prixProduit +
                ", dateCreation=" + dateCreation +
                '}';
    }
}

```
- Constructeur sans paramètre qui hérite de la classe Object
- Constructeur paramétré ( à l’exception du champ idProduit qui est auto
  incrémenté)
- méthode toString
- Générez les Getters, Setters,
- Ajouter les Annotations nécessaires à l’ORM hibernate

## Créez l’interface ProduitRepository dans le package com.gpao.demo.dao

- cette interface étend l'interface `JpaRepository<Produit, Long>`
###  `ProduitRepository`
```
public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
```
### Configuration du fichier `application.properties`
- creation de la base de données `GPAO` dans mysql
- modifier le fichier `/resources/application.properties`
###`application.properties`
```
spring.datasource.url=jdbc:mysql://localhost:3306/GPAO?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
```

### Tester les opérations CRUD sur l’entité Produit

- Editer le fichier test/java/package/`ProduitsApplicationTests`
#### `ProduitsApplicationTests`
```
	@Autowired
	private ProduitRepository produitRepository;

	@Test
	public void testCreateProduit(){
		Produit prod = new Produit("PC Dell", 2200.500, new Date());
		produitRepository.save(prod);
	}

	@Test
	public void testUpdateProduit(){
		Produit p = produitRepository.findById(1l).get();
		System.out.println(p);
	}

	@Test
	public void testDeleteProduit(){
		produitRepository.deleteById(1l);
	}



	@Test
	public void testListerTousProduits(){
		List<Produit> prods = produitRepository.findAll();
		for(Produit p : prods){
			System.out.println(p);
		}
	}
```


## Création de la couche service 
***
- Créer l'interface package.service/`ProduitService`
```

public interface ProduitService {
    Produit saveProduit(Produit produit);
    Produit updateProduit(Produit produit);
    void deleteProduit(Produit produit);
    void deleteProduitById(Long id);
    Produit getProduit(Long id);
    List<Produit> getAllProduits();
    
}

```
- Créer la classe package.service/`ProduitServiceImpl` qui va implémenter notre service Produit
```
@Service
public class ProduitServiceImpl implements ProduitService {
    @Autowired
    ProduitRepository produitRepository;


    @Override
    public Produit saveProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public Produit updateProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    @Override
    public void deleteProduit(Produit produit) {
        produitRepository.delete(produit);

    }

    @Override
    public void deleteProduitById(Long id) {
        produitRepository.deleteById(id);

    }

    @Override
    public Produit getProduit(Long id) {
        return produitRepository.findById(id).get();
    }

    @Override
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }
}

```
## Couche controller 
***

### Etape1 - Controller avec JSP 
***
#### `controllerJSP`
```
public class ProduitControllerJSP {
    @Autowired
    ProduitService produitService;

    @RequestMapping("/showCreate")
    public String showCreate()
    {
        return "createProduit";
    }

    @RequestMapping("/saveProduit")
    public String saveProduit(@ModelAttribute("produit") Produit produit,
                              @RequestParam("date") String date,
                              ModelMap modelMap) throws ParseException
    {
        //conversion de la date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation = dateformat.parse(String.valueOf(date));
        produit.setDateCreation(dateCreation);

        Produit saveProduit = produitService.saveProduit(produit);
        String msg ="produit enregistré avec Id "+saveProduit.getIdProduit();
        modelMap.addAttribute("msg", msg);
        return "createProduit";
    }

    @RequestMapping("/ListeProduits")
    public String listeProduits(ModelMap modelMap)
    {
        List<Produit> prods = produitService.getAllProduits();
        modelMap.addAttribute("produits", prods);
        return "listeProduits";
    }

    @RequestMapping("/supprimerProduit")
    public String supprimerProduit(@RequestParam("id") Long id,ModelMap modelMap)
    {
        Produit p= new Produit();
        p.setIdProduit(id);
        produitService.deleteProduit(p);
        List<Produit> prods = produitService.getAllProduits();
        modelMap.addAttribute("produits", prods);
        return "listeProduits";
    }

    @RequestMapping("/modifierProduit")
    public String editerProduit(@RequestParam("id") Long id,ModelMap modelMap)
    {
        Produit p= 	produitService.getProduit(id);
        modelMap.addAttribute("produit", p);
        return "editerProduit";
    }

    @RequestMapping("/updateProduit")
    public String updateProduit(@ModelAttribute("produit") Produit produit,
                                @RequestParam("date") String date,
                                ModelMap modelMap) throws ParseException
    {

        //conversion de la date
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date dateCreation = dateformat.parse(String.valueOf(date));
        produit.setDateCreation(dateCreation);

        produitService.updateProduit(produit);
        List<Produit> prods = produitService.getAllProduits();
        modelMap.addAttribute("produits", prods);

        return "listeProduits";
    }

}
```

#### `createProduit.jsp`
```
<body>
<div class="container">
<div class="card-body">
<form action="saveProduit" method="post">
   <div class="form-group">
      <label class="control-label">Nom Produit :</label>
       <input type="text" name="nomProduit" class="form-control"/>
    </div>
     <div class="form-group">
      <label class="control-label">Prix Produit :</label>
       <input type="text" name="prixProduit" class="form-control"/>
    </div>
     <div class="form-group">
      <label class="control-label">date création :</label>
       <input type="date" name="date" class="form-control"/>
    </div>

    <div>
 		  <button type="submit" class="btn btn-primary">Ajouter</button>
 	</div>
</form>
</div>
${msg}
<br/>
<br/>
<a href="ListeProduits">Liste Produits</a>
</div>
</body>
```

#### `editerProduit.jsp`
```
<body>
<div class="container">
<div class="card-body">
<form action="updateProduit" method="post">
   <div class="form-group">
      <label class="control-label">ID Produit :</label>
       <input type="text" name="idProduit" value="${produit.idProduit}" readonly class="form-control"/>
    </div>
   <div class="form-group">
      <label class="control-label">Nom Produit :</label>
       <input type="text" name="nomProduit" value="${produit.nomProduit}" class="form-control"/>
    </div>
     <div class="form-group">
      <label class="control-label">Prix Produit :</label>
       <input type="text" name="prixProduit" value="${produit.prixProduit}" class="form-control"/>
    </div>
    <div class="form-group">
      <label class="control-label"> Date création :</label>
       <fmt:formatDate pattern="yyyy-MM-dd" value="${produit.dateCreation}" var="formatDate" />
       <input type="date" name="date" value="${formatDate}" class="form-control"/>
    </div>
    <div>
 		  <button type="submit" class="btn btn-primary">Modifier</button>
 	</div>
</form>
</div>
<br/>
<br/>
<a href="ListeProduits">Liste Produits</a>
</div>
</body>
```

#### `listerProduit.jsp`
```
<body>
<div class="container">
<div class="card">
  <div class="card-header">
    Liste des Produits
  </div>
  <div class="card-body">

      <table class="table table-striped">
        <tr>
          <th>ID</th><th>Nom Produit</th><th>Prix</th><th>Date Création</th><th>Suppression<th>Edition</th>
         </tr>
         <c:forEach items="${produits}" var="p">
           <tr>
              <td>${p.idProduit }</td>
              <td>${p.nomProduit }</td>
              <td>${p.prixProduit }</td>
                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${p.dateCreation}" /></td>
              <td><a onclick="return confirm('Etes-vous sûr ?')" href="supprimerProduit?id=${p.idProduit }">Supprimer</a></td>
               <td><a href="modifierProduit?id=${p.idProduit }">Edit</a></td>
           </tr>
         </c:forEach>
      </table>
  </div>
</div>
</div>
</body>
```

### Etape2 - Controller RestAPI
***