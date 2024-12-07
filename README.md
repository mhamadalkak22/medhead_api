# Installer et faire fonctionner la API
<ol>
  <li>Suivre les étapes pour l'intégration de la base de donées medhead api sur https://github.com/mhamadalkak22/medheadDatabase.git </li>
  <li> faire <code>git clone https://github.com/mhamadalkak22/medhead_api.git</code></li>
  <li>Exécuter <code>docker inspect LE_NOM_DU_RESEAU</code> pour récupérer l'adresse IP du conteneur SQL SERVER créé lors de l'intégration de la base de donées</li>
  <li>Dans le repository clôné, accéder à /src/main/resources et modifier le fichier application.properties: mettre à jour la valeur <code>spring.datasource.url</code> avec <code>jdbc:sqlserver://<sql_container_ip_address>:1433;databaseName=medhead;encrypt=true;trustServerCertificate=true;</code>
    puis mettre votre mot de passe SQL SERVER dans le champ <code>spring.datasource.password</code> 
    </li>
  <li> Créer un fichier jar à partir de l'application existante en utilisant <code> mvn package -Dmaven.test.skip</code></li>
  <li>construire l'image docker <code>docker build -t medhead_api .</code> </li>
   <li>exécuter le conteneur à partir de l'image construite <code>docker run -d -p8080:8080 --network VOTRE_RESEAU --name medhead_api medhead_api </code>
  <li>Vous pourrez ensuite faire <code>docker attach medhead_api</code> pour afficher dans votre terminal les logs de l'application ou y accéder directmenet via docker desktop
</ol>
