# FirstSpringProject

Для запуска проекта: 
1) Создать базу данных PostgreSQL (или использовать существующую).
2) В базе данных создать таблицу по приложенному скрипту createTable.sql (в корне проекта).
3) В файле проекта application.properties изменить строчки:
* spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
* spring.datasource.username=postgres
* spring.datasource.password=admin

указать адрес базы данных из пункта 1, а также имя и пароль пользователя.

4) Запустить src/main/java/com.example.FisrtSpringProject/FirstSpringProjectApplication (предполагается, что запуск будет через IDE).

Приложение работает локально.

После запуска проекта нужно в браузере перейти по адресу http://localhost:8080/index.html
