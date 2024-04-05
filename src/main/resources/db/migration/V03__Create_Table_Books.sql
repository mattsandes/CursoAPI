CREATE TABLE books (
  id SERIAL NOT NULL,
  author text,
  launch_date date NOT NULL,
  price decimal(65,2) NOT NULL,
  title text
);
