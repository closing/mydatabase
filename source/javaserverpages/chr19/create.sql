CREATE TABLE `Employee` (
  `UserName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `FirstName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `LastName` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Dept` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `EmpDate` datetime DEFAULT NULL,
  `EmailAddr` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ModDate` datetime DEFAULT NULL,
  PRIMARY KEY (`UserName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
CREATE TABLE `EmployeeProjects` (
  `UserName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `ProjectName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`UserName`,`ProjectName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci