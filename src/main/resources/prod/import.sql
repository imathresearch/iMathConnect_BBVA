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

INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid1', 8, '2014-01-01', 8, 500, 'http://127.0.0.1:8080','Public Instance 1', null);
INSERT INTO instance(uuid, cpu, creationdate, ram, stg, url, name, owner_instance) VALUES ('instuuid4', 4, '2014-01-01', 8, 300, 'http://127.0.0.1:8080', 'Public Instance 2', null);

INSERT INTO notification(uuid, subject, text, creationdate, type) VALUES ('notuuid1', 'Welcome to iMath Cloud', 'Welcome to iMathCloud, the new high performance computation platform in the cloud', '2014-07-01', '0');
INSERT INTO notification(uuid, subject, text, creationdate, type) VALUES ('notuuid2', 'Contact with us', 'If you have any questions, requests or suggestions please contact us at info@imathresearch.com', '2014-05-01', '0');
INSERT INTO notification(uuid, subject, text, creationdate, type) VALUES ('notuuid3', 'New collaborators', 'Check your projects to see your new collaborators', '2014-08-01', '1');


