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

###  Controller RestAPI
***
`ProduitController`
```
@RestController
public class ProduitController {
    @Autowired
    ProduitServiceImpl produitServiceImpl;

    @GetMapping("/produits")
    private List<Produit> getAllProduits(){
        return produitServiceImpl.getAllProduits();
    }

    @PostMapping("/addProduit")
     private Long saveProduit(@RequestBody Produit produit)
    {
        produitServiceImpl.saveProduit(produit);
        return produit.getIdProduit();
    }

    @GetMapping("/produit/{produitId}")
    private Produit getProduit(@PathVariable("produitId") long Id)
    {
        return produitServiceImpl.getProduit(Id);
    }

    @DeleteMapping("/produit/{produitId}")
    private void produitDelete(@PathVariable("produitId") long Id){
        produitServiceImpl.deleteProduitById(Id);
    }

    @PutMapping("/produits")
    private Produit update(@RequestBody Produit produit)
    {
        produitServiceImpl.updateProduit(produit);
        return produit;
    }
}
```

## Créer un fichier.jar 

### configuration d'artéfact pout le JAR 
- Intélij créera le fichier `MANIFEST.MF` dans `src/main/resources/META-INF/MANIFEST.MF`
- Intélij créera le fichier `produit.jar` dans `out/artifacts/produits_jar`
### `Intélij`
****

- `File>project Structure` clicker sur `Artefact`
- cliker `+` pointer sur `JAR>from module with dependances`
- selectionner la classe main du projet 
- Appliquer les modifications 

<img src='https://resources.jetbrains.com/help/img/idea/2022.1/manifest.animated.gif' />

### Construire l'artefact JAR
- Menu principale :` Build>Build artefact> jar `
<img src="https://resources.jetbrains.com/help/img/idea/2022.1/jt-jar-built.png" />