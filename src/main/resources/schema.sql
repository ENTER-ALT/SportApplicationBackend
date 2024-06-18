
DROP TABLE IF EXISTS EVENTS_SPORTS;
DROP TABLE IF EXISTS EVENTS;
DROP TABLE IF EXISTS SPORTS;
DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS(
    NAME VARCHAR(255),
    AGE INT,
    EMAIL VARCHAR(255) PRIMARY KEY
);

CREATE TABLE SPORTS(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    TEAM_SPORT BOOLEAN NOT NULL,
    NUM_OF_PLAYERS INT NOT NULL
);

CREATE TABLE EVENTS(
    ID INT AUTO_INCREMENT PRIMARY KEY,
    NAME VARCHAR(255) NOT NULL,
    START_DATE DATETIME NOT NULL,
    END_DATE DATETIME NOT NULL
);

CREATE TABLE EVENTS_SPORTS (
    EVENT_ID BIGINT NOT NULL,
    SPORT_ID BIGINT NOT NULL,
    PRIMARY KEY (EVENT_ID, SPORT_ID),
    FOREIGN KEY (EVENT_ID) REFERENCES EVENTS(ID),
    FOREIGN KEY (SPORT_ID) REFERENCES SPORTS(ID)
);
