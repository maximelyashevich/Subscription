-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema subscription_db
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `subscription_db` ;

-- -----------------------------------------------------
-- Schema subscription_db
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `subscription_db` DEFAULT CHARACTER SET utf8 ;
USE `subscription_db` ;

-- -----------------------------------------------------
-- Table `subscription_db`.`address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription_db`.`address` ;

CREATE TABLE IF NOT EXISTS `subscription_db`.`address` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор адреса проживания, являющийся первичным ключом',
  `country` VARCHAR(150) NOT NULL COMMENT 'Идентификатор страны проживания, состоящий из 3 символов, например, \'BEL\'',
  `post_index` VARCHAR(20) NOT NULL COMMENT 'Почтовый индекс, состоящий из 6 символов',
  `city` VARCHAR(150) NOT NULL COMMENT 'Город проживания',
  `detail_address` VARCHAR(250) NOT NULL COMMENT 'Конкретный адрес: например, (улица, дом, номер квартиры) пользователя',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица содержит адрес проживания пользователя.  Использование: для отправки заказа пользователя';


-- -----------------------------------------------------
-- Table `subscription_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription_db`.`user` ;

CREATE TABLE IF NOT EXISTS `subscription_db`.`user` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Первичный автоинкрементируемый id для пользователя',
  `user_name` VARCHAR(40) NOT NULL COMMENT 'Уникальное имя пользователя в системе',
  `password` VARCHAR(40) NOT NULL COMMENT 'Пароль пользователя. Ограничение на 40 символов обуславливается алгоритмом хеширования пароля - SHA-1',
  `role` ENUM('user', 'admin') NOT NULL DEFAULT 'user' COMMENT 'Роль пользователя: enum: \'user\' (обычный пользователь)  и \'admin\' (админ)',
  `email` VARCHAR(45) NOT NULL COMMENT 'Уникальная электронная почта пользователя',
  `is_active` TINYINT(4) NOT NULL DEFAULT '1' COMMENT 'Индикатор состояния пользователя: например, администратор может заблокировать пользователя (в том числе за неуплату выданного кредита)',
  `first_name` VARCHAR(150) NOT NULL COMMENT 'Имя пользователя, ограниченное 150 символами ',
  `last_name` VARCHAR(150) NOT NULL COMMENT 'Фамилия пользователя, ограниченная 150 символами',
  `address_id` INT(11) NOT NULL COMMENT 'Идентификатор адреса проживания - внешний ключ, связывающий с данной таблицей таблицу \'address\' (с целью получения данных о месте проживания пользователя)',
  `dob` DATE NOT NULL COMMENT 'Дата рождения (с целью возрастного ограничения)',
  `amount` DECIMAL(10,0) UNSIGNED NOT NULL COMMENT 'Общая денежная сумма у пользователя',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица для аутентификации и авторизации пользователя (возможны две роли \'обычный пользователь\'=\'user\' и \'администратор\'=admin\' - задаются enumom) +  описываtn личные данные пользователя';

CREATE UNIQUE INDEX `username_UNIQUE` ON `subscription_db`.`user` (`user_name` ASC);

CREATE UNIQUE INDEX `email_UNIQUE` ON `subscription_db`.`user` (`email` ASC);


-- -----------------------------------------------------
-- Table `subscription_db`.`credit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription_db`.`credit` ;

CREATE TABLE IF NOT EXISTS `subscription_db`.`credit` (
  `id` INT(11) NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор таблицы',
  `user_id` INT UNSIGNED NOT NULL COMMENT 'Идентификатор \'кредитной истории\' - внешний ключ, связывающий с данной таблицей таблицу \'credit\' (с целью получения информации о кредитовании)',
  `debt` DECIMAL(10,3) UNSIGNED NOT NULL COMMENT 'Сумма в кредит',
  `interest_rate` DECIMAL(10,3) UNSIGNED NOT NULL COMMENT 'Процентная ставка',
  `payoff` INT(11) NOT NULL COMMENT 'Количество дней, выделенное для оплаты. В случае невыплаты суммы кредита (ограничение будет иметь вид payoff - DAY(CURDATE())<0 && remaining_amount>0, система, к примеру, запретит пользователю выдавать кредит',
  `is_available` TINYINT(1) UNSIGNED NOT NULL DEFAULT '1' COMMENT 'Доступен кредит или нет',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица описывает процесс кредитования. Содержит пользователя, сумму кредита, период выплаты, процентную ставку и доступность кредитования. 1 пользователь -> n - кредитов';


-- -----------------------------------------------------
-- Table `subscription_db`.`papers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription_db`.`papers` ;

CREATE TABLE IF NOT EXISTS `subscription_db`.`papers` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор печатного издания',
  `type` ENUM('magazine', 'newspaper', 'book') NOT NULL,
  `title` VARCHAR(150) NOT NULL COMMENT 'Именование печатного издания с ограничением на 150 символа',
  `price` DECIMAL(10,3) UNSIGNED NOT NULL COMMENT 'Стоимость подписки за полугодие',
  `description` TEXT NOT NULL COMMENT 'Краткое содержание с ограничением на 1000 символов',
  `publishing_periodicity` SMALLINT(3) UNSIGNED NOT NULL COMMENT 'Периодичность выпуска печатной серии за полугодие',
  `age_restriction` SMALLINT(2) UNSIGNED NOT NULL DEFAULT 0 COMMENT ' Возрастное ограничение (по принципу: YEAR(DATEDIFF(CURDATE(), user_age))>serie_book.age_restriction)',
  `is_available` TINYINT(1) NOT NULL DEFAULT 1 COMMENT 'В наличии или нет',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Печатное издание (журнал/газета/книга), Описывается  названием, кратким содержанием, жанром, стоимостью подписки за полугодие, периодичностью выпуска за полугодие, возрастным ограничением и наличием';


-- -----------------------------------------------------
-- Table `subscription_db`.`subscription`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription_db`.`subscription` ;

CREATE TABLE IF NOT EXISTS `subscription_db`.`subscription` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор таблицы, являющийся первичным ключом и внешним - с целью связать таблицу \'subscription_item\' (элемент подписки) с данной таблицей',
  `user_id` INT(10) UNSIGNED NOT NULL COMMENT 'Внешний ключ, связывающий данную таблицу с таблицей \'user_info\' (личные данные пользователя) с целью получение информации о пользователе (возраст, место проживание и т.д.)',
  `registration_date` DATETIME NOT NULL COMMENT 'Время оформление заказа: будет использоваться для хранения информации об истории оформления подписки',
  `price` DECIMAL(10,0) UNSIGNED NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица содержит в себе элемент подписки с указанной стоимостью, а также пользовательские данные (совершивший заказ)';


-- -----------------------------------------------------
-- Table `subscription_db`.`subscription_papers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription_db`.`subscription_papers` ;

CREATE TABLE IF NOT EXISTS `subscription_db`.`subscription_papers` (
  `subscription_id` INT(10) UNSIGNED NOT NULL,
  `papers_id` INT(10) UNSIGNED NOT NULL,
  `finish_date` DATETIME NOT NULL COMMENT 'До какого периода времени подписка')
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица содержит id заказа и печатного издания (1 заказ - n изданий, n заказов - 1 издание)';


-- -----------------------------------------------------
-- Table `subscription_db`.`genres`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription_db`.`genres` ;

CREATE TABLE IF NOT EXISTS `subscription_db`.`genres` (
  `id` INT(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Идентификатор жанра, являющийся первичным ключом',
  `name` VARCHAR(150) NOT NULL COMMENT 'Название жанра с ограничением на 150 символа',
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8
COMMENT = 'Таблица является описанием жанров периодических изданий';


-- -----------------------------------------------------
-- Table `subscription_db`.`genres_papers`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `subscription_db`.`genres_papers` ;

CREATE TABLE IF NOT EXISTS `subscription_db`.`genres_papers` (
  `genre_id` INT UNSIGNED NOT NULL,
  `papers_id` INT UNSIGNED NOT NULL)
ENGINE = InnoDB
COMMENT = 'Таблица содержит id печатного издания и жанра (1 жанр - n изданий, n жанров - 1 издание)';

SET SQL_MODE = '';
GRANT USAGE ON *.* TO user1;
 DROP USER user1;
SET SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';
CREATE USER 'user1';


-- -----------------------------------------------------
-- Data for table `subscription_db`.`address`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription_db`;
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (1, 'Беларусь', '122685', 'Минск', 'ул. Стоителей, д.25');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (2, 'Беларусь', '142321', 'Витебск', 'ул. Ленина, д.31а');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (3, 'Беларусь', '522552', 'Минск', 'ул. Машерова, д.15');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (4, 'Россия', '858421', 'Екатеринбург', 'ул. Первомайская, д.124, кв. 195');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (5, 'Украина', '223552', 'Львов', 'ул. Немига, д.10, кв.32');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (6, 'Беларусь', '152482', 'Гродно', 'ул. Курчатого, д.5б');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (7, 'Беларусь', '129542', 'Гродно', 'ул. Заслонова, д.120, кв.8');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (8, 'Россия', '354649', 'Владивосток', 'ул. Притыцкого, д.3');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (9, 'Беларусь', '512856', 'Могилёв', 'ул. Октябрьская, д.23/1, кв.64');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (10, 'Россия', '184523', 'Санкт-Петербург', 'ул. Георгиевская, д.17, кв.81');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (11, 'Беларусь', '194268', 'Витебск', 'ул. 17 Сентября, д. 91/1, кв.121');
INSERT INTO `subscription_db`.`address` (`id`, `country`, `post_index`, `city`, `detail_address`) VALUES (12, 'Беларусь', '122684', 'Минск', 'ул. Скорины, д.28, кв.21');

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription_db`.`user`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription_db`;
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (1, 'max98', '122684qw', 'user', 'max98@mail.ru', 1, 'Максим', 'Гуринович', 1, '1998-12-31', 50);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (2, 'zhenya97', 'qwerty28', 'user', 'zhenya97@mail.ru', 1, 'Евгений', 'Зарембин', 2, '1997-11-22', 15);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (3, 'maxIm98', '1512df221', 'user', 'maxIm98@gmail.com', 1, 'Максим', 'Петров', 3, '1998-02-05', 25);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (4, 'userstar', 'dgnksgdg6', 'user', 'userstar@mail.ru', 1, 'Ольга', 'Дмитириева', 4, '2007-10-01', 30);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (5, 'superpuperuser', '5615f1gsf', 'user', 'superpuperuser@gmail.com', 1, 'Екатерина', 'Голевич', 4, '2005-02-14', 12);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (6, 'uniquelogin', '1616gdgd', 'user', 'uniquelogin@mail.ru', 1, 'Геннадий', 'Павлов', 5, '1998-05-31', 25);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (7, 'unnamed_origin', 'password2017', 'user', 'unnamed_origin@bsu.by', 1, 'Иван', 'Иванов', 6, '1997-06-24', 24);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (8, 'unnamed_2017', 'cckvclvs15', 'user', 'unnamed_2017@mail.ru', 1, 'Кристина', 'Федорова', 7, '1992-09-30', 19);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (9, 'anton98', '616164dfd', 'user', 'anton98@mail.ru', 1, 'Антон', 'Штабной', 9, '1998-01-25', 28);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (10, 'what17', 'jbgdfs123', 'user', 'what17what17@gmail.com', 1, 'Алёна', 'Шуневич', 9, '1989-03-05', 51);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (11, 'not_admin', 'fgfgf1234', 'user', 'not_admin@gamil.com', 1, 'Ольга', 'Макарова', 10, '2003-07-08', 10);
INSERT INTO `subscription_db`.`user` (`id`, `user_name`, `password`, `role`, `email`, `is_active`, `first_name`, `last_name`, `address_id`, `dob`, `amount`) VALUES (12, 'admin', 'hello2017', 'admin', 'elyashevich1998@gmail.com', 1, 'Максим', 'Эльяшевич', 12, '1998-08-26', 500);

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription_db`.`credit`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription_db`;
INSERT INTO `subscription_db`.`credit` (`id`, `user_id`, `debt`, `interest_rate`, `payoff`, `is_available`) VALUES (1, 5, 5.25, 25, 10, 0);
INSERT INTO `subscription_db`.`credit` (`id`, `user_id`, `debt`, `interest_rate`, `payoff`, `is_available`) VALUES (2, 6, 0, 20, 15, 0);
INSERT INTO `subscription_db`.`credit` (`id`, `user_id`, `debt`, `interest_rate`, `payoff`, `is_available`) VALUES (3, 6, 7.545, 25, 20, 1);
INSERT INTO `subscription_db`.`credit` (`id`, `user_id`, `debt`, `interest_rate`, `payoff`, `is_available`) VALUES (4, 7, 1.254, 20, 12, 1);
INSERT INTO `subscription_db`.`credit` (`id`, `user_id`, `debt`, `interest_rate`, `payoff`, `is_available`) VALUES (5, 8, 12.854, 25, 30, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription_db`.`papers`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription_db`;
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (1, 'book', '\"Все автомобили\"', 2.68, 'Представьте себе все виды автомобилей, и даже те, которые не существуют в реальности! Книга шведского иллюстратора Карла Йохансона \"Все АВТОмобили\" - это удивительная визуальная коллекция самых причудливых видов транспорта. ', 3, 6, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (2, 'magazine', '\"АвтоМир\"', 1.26, '\"АвтоМир\" — единственный в России еженедельный автомобильный журнал. Самые свежие новости из мира автомобилей. Сравнительные тест-драйвы автомобилей-одноклассников. ', 7, 12, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (3, 'magazine', '\"За рулем + Мото. Комплект\"', 1.45, '\"За рулём\" — советский и российский русскоязычный журнал об автомобилях и автомобилестроении.', 6, 12, 0);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (4, 'newspaper', '\"Авторевю\"', 1.69, 'Авторевю — газета, что отражено в названии компании ООО «Газета Авторевю». Издание выходит с 1990 года. Тираж — 125 тысяч экземпляров', 12, 6, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (5, 'newspaper', '\"Делаем сами\"', 0.85, '«Делаем сами» — газета по декору и рукоделию для тех, кто любит украшать мир вокруг себя.', 4, 6, 0);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (6, 'newspaper', '\"Идеи Вашего Дома\"', 1.23, 'На страницах газеты вы найдете идеи, оригинальные решения, проекты планировки и оформления интерьера вашего дома', 5, 4, 0);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (7, 'book', '\"Ландшафтный дизайн\"', 2.58, 'Первое белорусское книжное издание о декоративном садоводстве. Каждая часть книжной серии знакомит читателей с наиболее известными садами и парками мира, лучшими работами по ландшафтному дизайну.', 3, 4, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (8, 'book', '\"МОЙ ЛЮБИМЫЙ ДОМ\"', 2.15, 'Книжная серия \"Мой любимый дом\" расскажет владельцам квартир и частных домов как сделать пространство дома максимально удобным и красивым, используя при этом как квалифицированную помощь специалистов, так и свои собственные силы.', 3, 6, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (9, 'book', '\"Автономные учреждения: бухгалтерский учет и налогообложение\"', 2.74, 'В книжной серии предметно освещаются актуальные вопросы и специфика финансово-хозяйственной деятельности автономного учреждения; особенности ведения бухгалтерского учета, налогообложения и составления отчетности', 3, 18, 0);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (10, 'newspaper', '\"Business Excellence / Деловое совершенство\"', 1.51, 'Business Excellence — ежемесячная деловая газета, издаваемая «РИА Стандарты и качество». Ранее называлась Деловое совершенство. Газета позиционируется издателями как газета креативных идей для бизнеса, содержащая практический опыт специалистов мирового уровня', 6, 18, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (11, 'magazine', '\"FORBES\"', 1.67, 'Forbes («Форбс») — американский финансово-экономический журнал, одно из наиболее авторитетных и известных экономических печатных изданий в мире. Основан в 1917 году Берти Чарлзом Форбсом. Слоган журнала: «Инструмент капиталиста» — «The Capitalist Tool».', 6, 16, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (12, 'magazine', '\"Детективы \"Сельской молодежи\"', 0.64, ' Ежемесячный журнал «Детективы СМ» Редкие захватывающие детективы под одной обложкой', 3, 16, 0);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (13, 'magazine', '\"Чудеса и приключения\"', 1.83, 'Литературно-художественный журнал приключений, путешествий, научных гипотез и фантастики. Выпускается с 1991 года, распространяется по всей России, в странах СНГ и дальнего зарубежья ', 6, 6, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (14, 'book', '\"Классические решения для гиперболических уравнений в 4 частях\"', 1.57, 'Книжная серия посвящена решениям дифференциальных уравнений волнового типа ', 5, 18, 0);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (15, 'newspaper', '\"1000 секретов\"', 0.57, 'Газета \"1000 секретов - это энциклопедия домашнего хозяйства, в которой можно найти советы на все случаи жизни. Обустройство квартиры, красота и стиль, сад и дача, психология, кулинарные рецепты, рукоделие и многое другое. ', 12, 12, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (16, 'magazine', '\" Cosmopolitan\"', 1.86, 'Последние модные тренды и бьюти-советы на каждый день, обзоры и мнения экспертов моды, красоты и психологии, новости звезд и эксклюзивные интервью', 3, 18, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (17, 'newspaper', '\"Мастерская на дому\"', 0.48, 'Мастерская на дому. Для мужчин. №1.', 6, 12, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (18, 'magazine', '\" Ваши заготовки\"', 1.15, 'Журнал «ВАШИ ЗАГОТОВКИ» — в нем собраны простые, доступные, аппетитные, классические (бабушек и дедушек) и современные (присланные читателями) рецепты домашних заготовок на зиму', 6, 6, 1);
INSERT INTO `subscription_db`.`papers` (`id`, `type`, `title`, `price`, `description`, `publishing_periodicity`, `age_restriction`, `is_available`) VALUES (19, 'newspaper', '\"Варим, солим, маринуем\"', 2.12, 'Здесь собраны простые, доступные, аппетитные, классические (бабушек и дедушек) и современные (присланные читателями) рецепты домашних блюд', 4, 6, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription_db`.`subscription`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription_db`;
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (1, 1, '2017-10-01', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (2, 1, '2017-11-25', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (3, 3, '2017-10-18', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (4, 4, '2017-11-19', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (5, 5, '2017-12-01', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (6, 6, '2017-08-02', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (7, 6, '2017-12-11', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (8, 1, '2017-12-18', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (9, 2, '2017-09-08', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (10, 3, '2017-12-14', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (11, 2, '2017-10-28', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (12, 8, '2017-12-21', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (13, 7, '2017-10-11', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (14, 10, '2017-11-01', DEFAULT);
INSERT INTO `subscription_db`.`subscription` (`id`, `user_id`, `registration_date`, `price`) VALUES (15, 11, '2017-12-21', DEFAULT);

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription_db`.`subscription_papers`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription_db`;
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (1, 1, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (2, 2, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (2, 3, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (3, 4, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (3, 5, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (3, 6, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (4, 7, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (5, 8, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (6, 9, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (7, 1, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (8, 10, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (9, 11, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (10, 12, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (11, 13, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (11, 14, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (11, 15, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (11, 16, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (12, 17, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (13, 2, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (14, 3, '2017-12-31');
INSERT INTO `subscription_db`.`subscription_papers` (`subscription_id`, `papers_id`, `finish_date`) VALUES (15, 4, '2017-12-31');

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription_db`.`genres`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription_db`;
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (1, 'Автомобили. Мотоциклы');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (2, 'Семья. Дом. Быт. Досуг');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (3, 'Научно-популярные издания');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (4, 'Архитектура. Интерьер. Дизайн');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (5, 'Мода. Стиль. Дизайн');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (6, 'Бизнес. Предпринимательство. Менеджмент');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (7, 'Детектив. Фантастика. Приключения. Мистика');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (8, 'Издания для женщин');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (9, 'Издания для мужчин');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (10, 'Кулинария');
INSERT INTO `subscription_db`.`genres` (`id`, `name`) VALUES (11, 'История');

COMMIT;


-- -----------------------------------------------------
-- Data for table `subscription_db`.`genres_papers`
-- -----------------------------------------------------
START TRANSACTION;
USE `subscription_db`;
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (1, 2);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (2, 2);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (1, 3);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (2, 3);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (1, 1);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (3, 5);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (4, 5);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (4, 6);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (4, 7);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (2, 8);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (4, 8);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (5, 8);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (6, 9);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (6, 10);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (6, 11);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (7, 12);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (7, 13);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (7, 14);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (8, 5);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (8, 15);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (2, 15);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (8, 16);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (5, 15);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (9, 2);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (9, 3);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (9, 1);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (10, 17);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (2, 17);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (11, 18);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (2, 18);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (8, 18);
INSERT INTO `subscription_db`.`genres_papers` (`genre_id`, `papers_id`) VALUES (11, 19);

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
