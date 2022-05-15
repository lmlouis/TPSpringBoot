# Project Produit RestAPI
Projet SpringBoot pour la gestion des produits assisté par ordinateurs 
TP SpringBoot construit un rest API 

***

## Pre-requis:
- Java 11 
- Intelij Community 
- Postman
- SpringBoot 2.6.7
- MySQL 8
- Ubuntu Linux 

## Installation du projet 
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

