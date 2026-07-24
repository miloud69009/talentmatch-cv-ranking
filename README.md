# TalentMatch — Tri explicable de CV

TalentMatch est une application de bureau développée en JavaFX permettant de classer des profils de candidats selon un ensemble de compétences recherchées. Le projet met l’accent sur l’architecture logicielle, la maintenabilité, les tests automatisés et l’utilisation responsable du tri algorithmique.

> Il s’agit d’un outil d’aide à la décision. Le classement des candidats doit toujours être vérifié par un recruteur humain et ne doit jamais être considéré comme une décision automatique d’embauche.


## Fonctionnalités principales

- Ajouter et supprimer des compétences recherchées.
- Charger des profils de candidats depuis des fichiers YAML lisibles.
- Classer les candidats grâce à plusieurs stratégies de sélection interchangeables.
- Appliquer un bonus configurable lié à l’expérience sans modifier les stratégies de base.
- Synchroniser plusieurs vues JavaFX grâce au patron de conception Observer.
- Exécuter automatiquement les tests unitaires dans une pipeline GitLab CI.
- Mettre en évidence les limites éthiques du tri automatisé de CV.

## Architecture logicielle

L’application suit l’architecture MVC présentée dans le rapport :

- **Modèle — `SearchModel`** : charge les candidats, stocke les critères et calcule les classements.
- **Vue — `JfxView`** : affiche l’interface graphique et observe les mises à jour du modèle.
- **Contrôleur — `CvController`** : transforme les actions de l’utilisateur en opérations sur le modèle.

Le projet met également en œuvre plusieurs patrons de conception :

| Patron | Rôle dans l’application |
| --- | --- |
| Observer | Synchronise toutes les vues connectées au même modèle. |
| Strategy | Encapsule chaque règle de sélection des candidats. |
| Decorator | Ajoute un bonus d’expérience à une stratégie existante. |
| Factory | Crée la stratégie choisie sans coupler le contrôleur aux différentes implémentations. |
| Builder | Transforme chaque fichier YAML en objet `Applicant`. |

## Stratégies de classement disponibles

| Stratégie | Règle de sélection |
| --- | --- |
| Toutes les compétences ≥ 50 % | Chaque compétence demandée doit atteindre au moins 50 %. |
| Toutes les compétences ≥ 60 % | Chaque compétence demandée doit atteindre au moins 60 %. |
| Toutes les compétences ≥ 80 % | Filtre strict pour les profils experts. |
| Moyenne ≥ 50 % | La moyenne des compétences demandées doit atteindre au moins 50 %. |
| Moyenne ≥ 50 % + expérience | Ajoute un bonus lié à l’expérience professionnelle pertinente. |
| Tolérante ≥ 50 % | Autorise une compétence demandée sous le seuil. |

## Technologies utilisées

- Java 21
- JavaFX 23
- Maven
- SnakeYAML
- JUnit 5 et Hamcrest
- JaCoCo
- Checkstyle
- GitLab CI

## Structure du projet

```text
.
├── .gitlab-ci.yml
├── README.md
├── rapport.pdf
├── docs/
│   └── talentmatch-preview.png
└── cv-search/
    ├── pom.xml
    ├── applicant1.yaml
    ├── applicant2.yaml
    └── src/
        ├── main/java/.../controller
        ├── main/java/.../model
        ├── main/java/.../view
        ├── main/resources/styles
        └── test/java
```

## Lancer l’application

### Prérequis

- JDK 21
- Maven 3.9 ou une version plus récente

### Démarrage

```bash
cd cv-search
mvn clean javafx:run
```

L’application lit tous les fichiers candidats au format `.yaml` présents dans le dossier courant.

### Vues synchronisées

L’application originale ouvre deux fenêtres JavaFX connectées au même objet `SearchModel`. Toute modification des critères ou de la stratégie dans l’une des fenêtres actualise automatiquement les deux vues grâce au patron Observer, conformément au fonctionnement présenté dans le rapport.

## Format d’un fichier candidat YAML

```yaml
name: Jane Doe
skills:
  java: 85
  docker: 70
  sql: 90
experience:
  Example Company:
    start: 2021
    end: 2025
    keywords:
      - java
      - docker
```

## Tests et qualité du code

Pour lancer les tests unitaires :

```bash
cd cv-search
mvn test
```

Pour exécuter l’ensemble des vérifications et générer les rapports de qualité :

```bash
cd cv-search
mvn verify
```

La pipeline GitLab exécute automatiquement la suite de tests Maven à chaque envoi de code. La configuration Maven comprend également la couverture de code avec JaCoCo, l’analyse Checkstyle et la génération de la documentation Javadoc.

## Considérations éthiques

Le tri automatisé peut reproduire ou amplifier des biais injustes. TalentMatch propose donc une stratégie plus tolérante et une comparaison des compétences insensible à la casse. Toutefois, ces mécanismes ne suppriment pas tous les risques.

La prise en compte de l’expérience peut notamment désavantager les parcours non linéaires, les interruptions de carrière ou les profils atypiques. La décision finale doit rester humaine et prendre en compte davantage d’éléments qu’un simple score basé sur des mots-clés.

## Documentation

Le rapport universitaire complet présente la refactorisation, les patrons de conception, la stratégie de tests et l’analyse éthique : [consulter le rapport](rapport.pdf).

## Auteurs

Projet universitaire réalisé par **Miloud Meziane** et **Dahir Alhussin Mahmoud**.
