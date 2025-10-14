[![Maven CI](https://github.com/AtalantaK/Final_Certification_4/actions/workflows/maven.yml/badge.svg)](https://github.com/AtalantaK/Final_Certification_4/actions/workflows/maven.yml)
<h1> Промежуточная аттестация 4</h1> 
<h3> Напишите автотесты на сервис https://www.saucedemo.com </h3>
<h3> Сценарии:</h3>

1. Успешная авторизация (**standard_user**)
2. Авторизация заблокированного пользователя (**locked_out_user**)
3. E2E-сценарий (под пользователями **standard_user** и **performance_glitch_user**):
   - авторизоваться
   - добавить в коризину товары:
     - Sauce Labs Backpack
     - Sauce Labs Bolt T-Shirt
     - Sauce Labs Onesie
   - перейти в корзину
     - проверить отображение 3 товаров
   - нажать Checkout
   - заполнить форму валидными данными
   - нажать Continue
     - проверить, что сумма заказа = 58.29$
   - нажать Finish

**Требования:**
1.	Работа принимается в виде github-репозитория
2.	Используется Selenide или Selenium
3.	Применен подход PageObject
4.	Формируется Allure-отчет

**Критерии оценивания:**
1.	Код компилируется и запускается
2.	Запускается браузер и выполняются автотесты
3.	Автотесты проходят в зеленом статусе (не падают)
4.	Формируется понятный читаемый Allure-отчет
5.	В репозитории нет лишних файлов/папок



