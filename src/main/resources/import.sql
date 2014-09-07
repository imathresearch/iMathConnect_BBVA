--
-- JBoss, Home of Professional Open Source
-- Copyright 2012, Red Hat, Inc., and individual contributors
-- by the @authors tag. See the copyright.txt in the distribution for a
-- full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
-- insert into Member_html5mobi (id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212')

INSERT INTO standardconfiguration(uuid, cpu, ram, stg) VALUES ('stda1', 8, 8, 500);
INSERT INTO standardconfiguration(uuid, cpu, ram, stg) VALUES ('stda2', 1, 0.5, 100);
INSERT INTO standardconfiguration(uuid, cpu, ram, stg) VALUES ('stda3', 2, 2, 300);
INSERT INTO standardconfiguration(uuid, cpu, ram, stg) VALUES ('stda4', 8, 16, 5000);

INSERT INTO userconnect(uuid, creationdate, email, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid1', '2014-01-01', 'ipinyol@gmail.com', '2014-01-01', 'imath', '999999999', '11111111111', 'ipinyolTest');
INSERT INTO userconnect(uuid, creationdate, email, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid2', '2014-01-01', 'pinyol@gmail.com', '2014-01-01', 'imath', '999999999', '11111111111', 'user2');
INSERT INTO userconnect(uuid, creationdate, email, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid3', '2014-01-01', 'iinyol@gmail.com', '2014-01-01', 'imath', '999999999', '11111111111', 'user3');
INSERT INTO userconnect(uuid, creationdate, email, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid4', '2014-01-01', 'ipnyol@gmail.com', '2014-01-01', 'imath', '999999999', '11111111111', 'user4');
INSERT INTO userconnect(uuid, creationdate, email, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid5', '2014-01-01', 'ipiyol@gmail.com', '2014-01-01', 'imath', '999999999', '11111111111', 'user5');

INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, owner_instance) VALUES ('instuuid1', 8, '2014-01-01', 8, 500, '127.0.0.1', null);
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, owner_instance) VALUES ('instuuid2', 1, '2014-01-01', 2, 100, '127.0.0.1', 'useruuid1');
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, owner_instance) VALUES ('instuuid3', 4, '2014-01-01', 3.5, 200, '127.0.0.1', 'useruuid1');
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, owner_instance) VALUES ('instuuid4', 4, '2014-01-01', 8, 300, '127.0.0.1', null);

INSERT INTO project(uuid, creationdate, description, key, name, instance, owner) VALUES ('projuuid1', '2014-01-01', 'My first project', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Cassandra', 'instuuid1', 'useruuid1');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner) VALUES ('projuuid2', '2014-01-01', 'Simulating the Game of Live', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Fenix', 'instuuid1', 'useruuid1');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner) VALUES ('projuuid3', '2014-01-01', 'Matrix implementation algorithms', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Matrix', 'instuuid1', 'useruuid1');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner) VALUES ('projuuid4', '2014-01-01', 'Cartoon automatic drawing ', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Cartoon', 'instuuid1', 'useruuid2');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner) VALUES ('projuuid5', '2014-01-01', 'B-tree new implementation ', 'IV0p34cDmXFCzA3p9V7/Hg==', 'TreePlus', 'instuuid1', 'useruuid3');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner) VALUES ('projuuid6', '2014-01-01', 'Altas experiment', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Atlas', 'instuuid1', 'useruuid3');

INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid1', 'useruuid3');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid1', 'useruuid4');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid1', 'useruuid1');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid2', 'useruuid5');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid4', 'useruuid1');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid4', 'useruuid5');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid5', 'useruuid1');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid5', 'useruuid2');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid5', 'useruuid4');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid5', 'useruuid5');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid6', 'useruuid1');




