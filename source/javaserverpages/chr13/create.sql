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
  CONSTRAINT employee_pkey PRIMARY KEY (username)
);
CREATE TABLE employeeprojects
(
  username text NOT NULL,
  projectname text NOT NULL,
  CONSTRAINT employeeprojects_pkey PRIMARY KEY (username, projectname)
)
