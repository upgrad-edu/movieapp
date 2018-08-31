echo "******* Building *******"
mvn clean install -DskipTests

cd movieapp-db
echo "******* Setting up database *******"
mvn install -Psetup,data

cd ../movieapp-api
echo " *******Starting server "
mvn spring-boot:run