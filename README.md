## Ce projet a été réalisé en groupe, composé de Luigi Vuachet, Kobla Legbedje et Moustapha Sarr.

# Installation et Exécution
## Prérequis
- **Docker** : Assurez-vous que Docker est installé sur votre machine.
Vous pouvez télécharger Docker ici : [Docker](https://www.docker.com/products/docker-desktop).
- **Java 8 ou supérieur pour l'exécution** : L'application Java nécessite une version de Java compatible.
Vous pouvez télécharger Java ici : [Java](https://www.java.com/fr/download/manual.jsp).

## Étapes d'installation

### 1. Cloner le repository

Clonez le repository GitHub sur votre machine locale :

```bash
git clone https://github.com/lgvua/projet_gestion_reservations.git
```

```bash
cd projet_gestion_reservations
```

### 2. Environnement Docker

Exécutez la commande suivante pour démarrer l'environnement Docker :

```bash
docker-compose up -d
```

Cette commande téléchargera l'image de la base de données PostgreSQL (si elle n'est pas déjà présente) et lancera un conteneur contenant la base de données, tout en initialisant la base de données à l'aide du fichier init.sql (Les données dans ce fichier sont fictives et utilisées à des fins de test) .

### 3. Lancer l'application

Pour lancer l'application Java, cliquez sur le fichier `Application_projet_lkm.jar`.

Si votre système d'exploitation ne reconnaît pas ce fichier comme un programme cliquable, vous pouvez l'exécuter en utilisant la commande suivante :

```bash
java -jar Application_projet_lkm.jar
```

# Principales fonctionnalités 

### Gestion des clients  
- **Ajouter un client :** Informations requises : nom, prénom, email, adresse, téléphone.  
- **Modifier un client :** Mise à jour des informations : nom, prénom, email, adresse, téléphone.  
- **Supprimer un client.**  

### Gestion des conseillers  
- **Ajouter un conseiller :** Informations requises : nom, prénom, email, téléphone.  
- **Modifier un conseiller :** Mise à jour des informations : nom, prénom, email, téléphone.  
- **Supprimer un conseiller.**  

### Gestion des vols et réservations  
- **Ajouter une réservation.**  
- **Ajouter un vol.**  
- **Supprimer une réservation.**  

## Modèle conceptuel de données
<img src="MCD.svg" alt="drawing" width="500"/>

## Modèle physique de données
<img src="MPD.png" alt="drawing" width="500"/>

## Images
<img src="file1.png" alt="drawing" width="500"/>
<img src="file2.png" alt="drawing" width="500"/>
<img src="file3.png" alt="drawing" width="500"/>

# Informations de compilations
Pour compiler et exécuter le programme à partir des sources, assurez-vous d’avoir les éléments suivants (les deux derniers se trouvent dans le dossier `lib`) :  
1. **Java SDK 23 :** Installez le kit de développement Java version 23 ou supérieure.  
2. **Connector PostgreSQL :** Le driver PostgreSQL, nécessaire pour la connexion à la base de données.  
3. **LGoodDatePicker :** Bibliothèque utilisée pour la gestion des sélections de dates et heures.  
