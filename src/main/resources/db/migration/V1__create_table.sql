CREATE TABLE ORGANIZATIONS
(
    organization_id VARCHAR(13) PRIMARY KEY,
    name            VARCHAR(120)
);

CREATE TABLE USERS
(
    user_id         VARCHAR(13) PRIMARY KEY,
    email           VARCHAR(120),
    name            VARCHAR(120),
    organization_id VARCHAR(13)
        CONSTRAINT fk_organization_id REFERENCES ORGANIZATIONS (organization_id)
);

CREATE TABLE USER_ROLE
(
    role_id VARCHAR(13) PRIMARY KEY ,
    user_id VARCHAR(13)
        CONSTRAINT fk_organization_id REFERENCES USERS (user_id)
);