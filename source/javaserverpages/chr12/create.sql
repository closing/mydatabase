CREATE TABLE employee
(
  username text NOT NULL,
  password text,
  firstname text,
  lastname text,
  dept text,
  empdate timestamp with time zone,
  emailaddr text,
  moddate timestamp with time zone,
  CONSTRAINT "Employee_pkey" PRIMARY KEY (username)
)
