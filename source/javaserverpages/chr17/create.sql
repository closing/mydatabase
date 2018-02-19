CREATE TABLE sizes
(
  id integer NOT NULL,
  name text,
  CONSTRAINT sizes_pkey PRIMARY KEY (id)
);
CREATE TABLE toppings
(
  id integer NOT NULL,
  name text,
  CONSTRAINT toppings_pkey PRIMARY KEY (id)
)
