docker run --net mynet123 --ip 172.18.0.18 --hostname=memcached-image -d -p 11211:11211 -t memcached-image

docker run --net mynet123 --ip 172.18.0.19 -d -p 8888:8888 -t configure-service 

docker run --net mynet123 --ip 172.18.0.20 -d -p 8761:8761 -t eureka-service

docker run --net mynet123 --ip 172.18.0.21 -d -p 8082:8082 -t api-gateway

docker run --net mynet123 --ip 172.18.0.22 -d -t account-service

docker run --net mynet123 --ip 172.18.0.23 -d -t url-shortner
