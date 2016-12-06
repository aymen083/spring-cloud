start "Config-Service"    java -Xmx32m -Dhttps.proxyHost=vipproxy1.prod.extelia.fr -Dhttps.proxyPort=8080 -jar config-server\target\config-server.jar
start "Eureka-Service"    java -Xmx256m -jar eureka-server\target\eureka-server.jar
start                     java -Xmx512m -jar reservation-service\target\reservation-service.jar
start                     java -Xmx256m -jar reservation-client\target\reservation-client.jar
start "Hystrix-Dashboard" java -Xmx256m -jar hystrix-dashboard\target\hystrix-dashboard.jar