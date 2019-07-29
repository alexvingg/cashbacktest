# cashbacktest

Requisitos:

- maven
- docker
- docker-compose

Para rodar o projeto seguir os seguintes passos:

- docker-compose up db (aguardar a criação do banco, na primeira vez demora)
- docker-compose up -d (inicializa o banco e o servidor java)

Para verificar as APIS só acessar o endereço:
http://localhost:8080/swagger-ui.html

Para rodar os testes do projeto:
- mvn test

Fiz o commit da pasta target para facilitar a execução do projeto e visualizar a cobertura dos testes em target/jacoco/index.html

# Atenção:

API do Spotify com problemas e issues abertas no github:

- https://github.com/spotify/web-api/issues/1122
- https://github.com/spotify/web-api/issues/157

Para implementar o teste tive que utilizar outra API e deixar o processo mais lento.
