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

INSERT INTO userconnect(uuid, creationdate, email, currentconnection, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid1', '2014-01-01', 'ipinyol@gmail.com', '2014-02-01', '2014-01-01', 'imath', '999999999', '11111111111', 'ipinyolTest');
INSERT INTO userconnect(uuid, creationdate, email, currentconnection, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid2', '2014-01-01', 'pinyol@gmail.com', '2014-02-01', '2014-01-01', 'imath', '999999999', '11111111111', 'user2');
INSERT INTO userconnect(uuid, creationdate, email, currentconnection, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid3', '2014-01-01', 'iinyol@gmail.com', '2014-02-01', '2014-01-01', 'imath', '999999999', '11111111111', 'user3');
INSERT INTO userconnect(uuid, creationdate, email, currentconnection, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid4', '2014-01-01', 'ipnyol@gmail.com', '2014-02-01', '2014-01-01', 'imath', '999999999', '11111111111', 'user4');
INSERT INTO userconnect(uuid, creationdate, email, currentconnection, lastconnection, organization, phone1, phone2, username) VALUES ('useruuid5', '2014-01-01', 'ipiyol@gmail.com', '2014-02-01', '2014-01-01', 'imath', '999999999', '11111111111', 'user5');

--AMMARTINEZ
INSERT INTO userconnect(uuid, creationdate, email, currentconnection, lastconnection, organization, phone1, phone2, username) VALUES ('amtuuid1', '2014-01-01', 'ammartinez@imathresearch.com', '2014-02-01', '2014-01-01', 'imath', '999999999', '11111111111', 'amtTest');

--IZUBIZARRETA
INSERT INTO userconnect(uuid, creationdate, email, currentconnection, lastconnection, organization, phone1, phone2, username) VALUES ('iztuuid1', '2014-01-01', 'izubizarreta@imathresearch.com', '2014-02-01', '2014-01-01', 'imath', '999999999', '11111111111', 'izubizarreta');

INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid1', 8, '2014-01-01', 8, 500, 'http://127.0.0.1:8080','Public Instance 1', null);
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid2', 1, '2014-01-01', 2, 100, 'http://127.0.0.1:8080', 'My First Instance','useruuid1');
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid3', 4, '2014-01-01', 3.5, 200, 'http://127.0.0.1:8080','Death Star', 'useruuid1');
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid4', 4, '2014-01-01', 8, 300, 'http://127.0.0.1:8080', 'Public Instance 2', null);

--AMMARTINEZ
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid5', 4, '2014-01-01', 8, 300, '127.0.0.1', 'Barcelona', 'amtuuid1');
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid6', 1, '2014-01-01', 8, 300, '127.0.0.1', 'Granada', 'amtuuid1');

--IZUBIZARRETA
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid7', 4, '2014-01-01', 8, 300, '127.0.0.1', 'Barcelona', 'iztuuid1');
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid8', 1, '2014-01-01', 8, 300, '127.0.0.1', 'Granada', 'iztuuid1');

INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid1', '2014-01-01', 'My first project', 'TD+oizX1YBbqtvO4RkKL8Q==', 'Cassandra', 'instuuid1', 'useruuid1', 'conanc');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid2', '2014-01-01', 'Simulating the Game of Live', 'TD+oizX1YBbqtvO4RkKL8Q==', 'Fenix', 'instuuid1', 'useruuid1', 'conanc');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid3', '2014-01-01', 'Matrix implementation algorithms', 'TD+oizX1YBbqtvO4RkKL8Q==', 'Matrix', 'instuuid1', 'useruuid1', 'conanc');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid4', '2014-01-01', 'Cartoon automatic drawing ', 'TD+oizX1YBbqtvO4RkKL8Q==', 'Cartoon', 'instuuid1', 'useruuid2', 'conanc');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid5', '2014-01-01', 'B-tree new implementation ', 'TD+oizX1YBbqtvO4RkKL8Q==', 'TreePlus', 'instuuid1', 'useruuid3', 'conanc');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid6', '2014-01-01', 'Altas experiment', 'TD+oizX1YBbqtvO4RkKL8Q==', 'Atlas', 'instuuid1', 'useruuid3', 'conanc');

--AMMARTINEZ
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid7', '2014-01-01', 'My first project', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Cassandra', 'instuuid5', 'amtuuid1', 'conanc');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid8', '2014-01-01', 'Search Engine', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Searcher', 'instuuid6', 'amtuuid1', 'conanc');

--IZUBIZARRETA
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid9', '2014-01-01', 'My first project', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Cassandra', 'instuuid7', 'iztuuid1', 'conanc');
INSERT INTO project(uuid, creationdate, description, key, name, instance, owner, linux_group) VALUES ('projuuid10', '2014-01-01', 'Search Engine', 'IV0p34cDmXFCzA3p9V7/Hg==', 'Searcher', 'instuuid8', 'iztuuid1', 'conanc');

INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid1', 'useruuid3');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid1', 'useruuid4');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid1', 'useruuid1');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid2', 'useruuid5');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid4', 'useruuid1');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid4', 'useruuid5');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid5', 'useruuid2');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid5', 'useruuid4');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid5', 'useruuid5');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid6', 'useruuid1');

--AMMARTINEZ
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid7', 'useruuid1');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid7', 'useruuid3');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid8', 'useruuid5');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid8', 'useruuid4');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid8', 'useruuid1');

--IZUBIZARRETA
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid9', 'useruuid1');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid9', 'useruuid3');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid10', 'useruuid5');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid10', 'useruuid4');
INSERT INTO collaborators(projects_uuid, collaborators_uuid) VALUES ('projuuid10', 'useruuid1');

--AMMARTINEZ
INSERT INTO notification(uuid, subject, text, creationdate, type) VALUES ('notuuid1', 'Release of iMathCloud', 'The beta release of iMathCloud will soon be released', '2014-07-01', '0');
INSERT INTO notification(uuid, subject, text, creationdate, type) VALUES ('notuuid2', 'Contact with us', 'If you have any questions, requests or suggestions please contact us at info@imathresearch.com', '2014-05-01', '0');
INSERT INTO notification(uuid, subject, text, creationdate, type) VALUES ('notuuid3', 'New collaborators', 'Check your projects to see your new collaborators', '2014-08-01', '1');


INSERT INTO notifications_user(notification_uuid, notificationusers_uuid) VALUES ('notuuid3', 'amtuuid1');

--ADD A PRIVATE NOTIFICATION FOR IPINYOLTEST USER
INSERT INTO notifications_user(notification_uuid, notificationusers_uuid) VALUES ('notuuid3', 'useruuid1');

--ADD A PRIVATE NOTIFICATION FOR IZUBIZARRETA USER
INSERT INTO notifications_user(notification_uuid, notificationusers_uuid) VALUES ('notuuid3', 'iztuuid1');
