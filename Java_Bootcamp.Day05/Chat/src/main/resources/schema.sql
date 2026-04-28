CREATE TABLE IF NOT EXISTS Users(
    id SERIAL PRIMARY KEY,
    login VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
 );

CREATE TABLE IF NOT EXISTS Chatrooms(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    owner_id INT NOT NULL,
    FOREIGN KEY (owner_id) REFERENCES Users(id) ON DELETE CASCADE
 );

CREATE TABLE IF NOT EXISTS Messages(
    id SERIAL PRIMARY KEY,
    author_id INT REFERENCES Users(id) ON DELETE CASCADE,
    chatroom_id INT REFERENCES Chatrooms(id) ON DELETE CASCADE,
    text TEXT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ
 );

-- Many-to-many: which users participate in which chatrooms
CREATE TABLE users_chatrooms (
    user_id INTEGER NOT NULL REFERENCES Users(id) ON DELETE CASCADE,
    chatroom_id INTEGER NOT NULL REFERENCES Chatrooms(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, chatroom_id)
);
