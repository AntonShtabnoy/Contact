CREATE TABLE `contacts` (
  `contact_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contact_name` varchar(45) CHARACTER SET utf8 NOT NULL,
  `contact_surname` varchar(45) CHARACTER SET utf8 NOT NULL,
  `contact_dob` date NOT NULL,
  `contact_sex` enum('M','F') CHARACTER SET utf8 NOT NULL,
  `contact_nationality` varchar(45) CHARACTER SET utf8 NOT NULL,
  `family_status` enum('Free','Married') CHARACTER SET utf8 NOT NULL,
  `contact_website` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `contact_email` varchar(45) CHARACTER SET utf8 NOT NULL,
  `contact_work` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `contact_country` varchar(45) CHARACTER SET utf8 NOT NULL,
  `contact_city` varchar(45) CHARACTER SET utf8 NOT NULL,
  `contact_street` varchar(45) CHARACTER SET utf8 NOT NULL,
  `contact_home_flat` varchar(45) NOT NULL,
  `contact_index` varchar(45) CHARACTER SET utf8 DEFAULT NULL,
  `contact_status` varchar(45) CHARACTER SET utf8 DEFAULT 'active',
  `contact_image` varchar(45) CHARACTER SET utf8 DEFAULT 'empty_profile_image.jpg',
  PRIMARY KEY (`contact_id`),
  UNIQUE KEY `contact_id_UNIQUE` (`contact_id`),
  KEY `nationality_id_idx` (`contact_nationality`),
  KEY `nationality_id` (`contact_nationality`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `phones` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `contact_id` int(11) unsigned NOT NULL,
  `phone_number` varchar(45) NOT NULL,
  `phone_type` enum('Mobile','Home','Work') NOT NULL,
  `comment` text,
  `phone_status` varchar(45) NOT NULL DEFAULT 'active',
  PRIMARY KEY (`id`),
  KEY `contact_id_idx` (`contact_id`),
  CONSTRAINT `contact_id` FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `attachments` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `contact_id` int(10) unsigned NOT NULL,
  `name` varchar(60) NOT NULL,
  `date` date NOT NULL,
  `comment` text,
  `status` varchar(45) NOT NULL DEFAULT 'active',
  PRIMARY KEY (`id`),
  KEY `contact_id_idx` (`contact_id`),
  KEY `contact_id_idx123` (`contact_id`),
  CONSTRAINT `contact_id123` FOREIGN KEY (`contact_id`) REFERENCES `contacts` (`contact_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;