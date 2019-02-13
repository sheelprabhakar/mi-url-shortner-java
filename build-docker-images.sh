cd url-shortner
docker build -t url-shortner .
cd ..
cd account-service
docker build -t account-service .
cd ..
cd api-gateway
docker build -t api-gateway .
cd ..
cd configure-service
docker build -t configure-service .

cd ..
cd eureka-service
docker build -t eureka-service .

cd ..

cd memcached
docker build -t memcached-image .

docker network create --subnet=172.18.0.0/16 mynet123