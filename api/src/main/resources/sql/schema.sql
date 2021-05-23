DROP TABLE IF EXISTS to_do;

-- Create the table for storing to dos.
CREATE TABLE IF NOT EXISTS to_do (
    id IDENTITY NOT NULL PRIMARY KEY,
    title VARCHAR NOT NULL,
    description VARCHAR NOT NULL,
    done BOOLEAN NOT NULL
);

INSERT INTO to_do (title, description, done)
VALUES
    ('Do something', 'You must do something', false),
    ('Refactor code', '', true),
    ('Finish homework', 'It has to be done', false);
