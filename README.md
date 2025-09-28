Инструкция по запуску приложения:
1. Склонируйте весь проект
2. Запуск консольного приложения:
   a)Перейдите в директорию IVK_Task1.
   b)mvn clean install
   c)mvn -pl game-engine exec:java
3. Запуск web-сервиса:
   a)Перейдите в директорию IVK_Task1.
   b)mvn clean install
   с)mvn -pl web-service spring-boot:run
4. Запуск web-приложения:
   a)Запустите web-сервис, как в пункте 3. Поднимется backend
   b)В новом терминале перейдите в директорию web-app (cd web-app)
   c)npm install
   d)npm run dev
   Поднимется frontend. Далее перейдите по адресу, который появился, чтобы воспользоваться веб приложением
   
   <img width="351" height="178" alt="image" src="https://github.com/user-attachments/assets/bfe53c77-fdf6-4aab-9d71-d6affa4495b8" />
