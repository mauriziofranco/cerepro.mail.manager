# cerepro.mail.manager
CeRePro mail manager: mail template handler

github.com reporitory path: https://github.com/MaurizioFranco/cerepro.mail.manager.git

    1. clonare il progetto;
    2. controllare il file log4j2,xml all’interno di src/test/resources; verificare effettivamente che il path del file di log, al suo interno, sia corretto per il tuo ambiente;
    3. controllare il file mail.properties all’interno di src/test/resources; popolare le proprietà:
       mail.smtps.host
       mail.smtps.user
       mail.smtps.password
       mail.cc
       mail.ccn
       siano valorizzate correttamente(con I parametri di una tua mail di test, oppure chiedi i parametri della mail di test usata per l’ambiente di sviluppo)
    4. aprire un terminale portarsi nella directory di progetto e digitare: 
       mvn install
