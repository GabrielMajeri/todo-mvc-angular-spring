-- Create the table for storing to dos.
CREATE TABLE IF NOT EXISTS to_do (
    id IDENTITY NOT NULL PRIMARY KEY,
    text VARCHAR NOT NULL
);
