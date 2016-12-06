TP Rest.x
=========

TP 1.

Mise en place : jars maven + pom.xml
+ squelette de projets
Regarder avec le navigateur :
http://127.0.0.1:8080/api/message?who=toto
Changer le message et relancer avec le navigateur
Les modifications sont prises en compte à chaud (grâce à "restx.app.package")

TP 2.

Mettre en place un premier test HelloResourceSpecTest.
Pour cela écrire un fichier .spec.yaml representant le test.

TP 3.
Créer la classe UserResource permettant de retourner un User (firstname, lastname, email, id, password, last_connection_date)
Utiliser le pattern Builder pour créer le User.
NB : attention à ne pas stocker une liste en local (private) dans la @RestxResource
car chaque appel créé son instance.

Resultat attendus :
```javascript
[
    {
        "id": 123,
        "firstName": "Julien",
        "lastName": "Landuré",
        "email": "julien.landure@zenika.com",
        "password": "julien",
        "lastConnectionDate": " 2014-02-19T12:34:20.000+0000"
    },
    {
        "id": 1,
        "firstName": "Pierre",
        "lastName": "Reliquet",
        "email": "pierre.reliquet@zenika.com",
        "password": "pierre",
        "lastConnectionDate": "2013-03-12T08:33:00.000+0000"
    }
]
```
Le test fonctionne si on enlève un champ. La comparaison JSON est considérée comme bonne
tant que nous avons les champs décrits comme bien remplis.

TP4.
Tester avec l'appli en ligne