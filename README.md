# twitter-project
Twitter like project for Spring

простое приложение сделанное с целью изучения Spring

вэб - Spring (mvc, security)\
база данных - Spring jpa+Hibernate+Mysql\
почта - Spring (spring, javax.mail)

реализованный функционал:\
пользователь - авторизация, регистрация, подтверждение и восстановление аккаунта(через почту), смена ника\
темы(твиты) - добавление/редактирование, просмотр на главной с постраничностью, лайки\
комментарии - добавление/редактирование, просмотр (древовидная структура - nested set), лайки\
страница пользователя - просмотр всех сообщений(твиты и комментарии) пользователя\
профиль пользователя - возможность сменить ник

// todo
срок действия токенов регистрации/восстановления
проверка прав на добавление/редактирование сообщения
автоматическое тестирование