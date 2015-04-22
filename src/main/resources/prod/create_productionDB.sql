--
-- PostgreSQL database dump
--

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;

ALTER TABLE ONLY public.notifications_user DROP CONSTRAINT fk_qnqbahea6vyh66rcrvcrrxjjo;
ALTER TABLE ONLY public.project DROP CONSTRAINT fk_cyw1tck5kra9k3ffnmb9ep2bs;
ALTER TABLE ONLY public.collaborators DROP CONSTRAINT fk_csbnbc5hr3kjr5t7f1arrpvl2;
ALTER TABLE ONLY public.instance DROP CONSTRAINT fk_bpa4o8s5eqe079tn6ew3ywpqf;
ALTER TABLE ONLY public.project DROP CONSTRAINT fk_88195lfeao3lc1o02mxeu1uuk;
ALTER TABLE ONLY public.collaborators DROP CONSTRAINT fk_64ofpbgm51al66v64gr67wbsv;
ALTER TABLE ONLY public.notifications_user DROP CONSTRAINT fk_1jgjq2ifv5qqmp7wo8oltgrs2;
ALTER TABLE ONLY public.userjbossroles DROP CONSTRAINT userjbossroles_pkey;
ALTER TABLE ONLY public.userjboss DROP CONSTRAINT userjboss_pkey;
ALTER TABLE ONLY public.userconnect DROP CONSTRAINT userconnect_pkey;
ALTER TABLE ONLY public.useraccess DROP CONSTRAINT useraccess_pkey;
ALTER TABLE ONLY public.userconnect DROP CONSTRAINT uk_ex7cd5i7yhpdpgyl3mdchbtsd;
ALTER TABLE ONLY public.userconnect DROP CONSTRAINT uk_5ahi5chhosvmoqjai8rngcc13;
ALTER TABLE ONLY public.standardconfiguration DROP CONSTRAINT standardconfiguration_pkey;
ALTER TABLE ONLY public.project DROP CONSTRAINT project_pkey;
ALTER TABLE ONLY public.notifications_user DROP CONSTRAINT notifications_user_pkey;
ALTER TABLE ONLY public.notification DROP CONSTRAINT notification_pkey;
ALTER TABLE ONLY public.instance DROP CONSTRAINT instance_pkey;
ALTER TABLE ONLY public.collaborators DROP CONSTRAINT collaborators_pkey;
DROP TABLE public.userjbossroles;
DROP TABLE public.userjboss;
DROP TABLE public.userconnect;
DROP TABLE public.useraccess;
DROP TABLE public.standardconfiguration;
DROP TABLE public.project;
DROP TABLE public.notifications_user;
DROP TABLE public.notification;
DROP TABLE public.instance;
DROP TABLE public.collaborators;
DROP EXTENSION plpgsql;
DROP SCHEMA public;
--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: collaborators; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE collaborators (
    projects_uuid character varying(255) NOT NULL,
    collaborators_uuid character varying(255) NOT NULL
);


ALTER TABLE public.collaborators OWNER TO postgres;

--
-- Name: instance; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE instance (
    uuid character varying(255) NOT NULL,
    cpu bigint NOT NULL,
    creationdate timestamp without time zone NOT NULL,
    name character varying(255) NOT NULL,
    ram double precision NOT NULL,
    stg double precision NOT NULL,
    url character varying(255) NOT NULL,
    owner_instance character varying(255)
);


ALTER TABLE public.instance OWNER TO postgres;

--
-- Name: notification; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notification (
    uuid character varying(255) NOT NULL,
    creationdate timestamp without time zone NOT NULL,
    subject character varying(200) NOT NULL,
    text character varying(500) NOT NULL,
    type integer NOT NULL
);


ALTER TABLE public.notification OWNER TO postgres;

--
-- Name: notifications_user; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE notifications_user (
    notification_uuid character varying(255) NOT NULL,
    notificationusers_uuid character varying(255) NOT NULL
);


ALTER TABLE public.notifications_user OWNER TO postgres;

--
-- Name: project; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE project (
    uuid character varying(255) NOT NULL,
    creationdate timestamp without time zone NOT NULL,
    description character varying(255) NOT NULL,
    key character varying(200) NOT NULL,
    linux_group character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    instance character varying(255) NOT NULL,
    owner character varying(255) NOT NULL
);


ALTER TABLE public.project OWNER TO postgres;

--
-- Name: standardconfiguration; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE standardconfiguration (
    uuid character varying(255) NOT NULL,
    cpu bigint NOT NULL,
    ram double precision NOT NULL,
    stg double precision NOT NULL
);


ALTER TABLE public.standardconfiguration OWNER TO postgres;

--
-- Name: useraccess; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE useraccess (
    uuid character varying(255) NOT NULL,
    accesssource character varying(255) NOT NULL,
    password character varying(200)
);


ALTER TABLE public.useraccess OWNER TO postgres;

--
-- Name: userconnect; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE userconnect (
    uuid character varying(255) NOT NULL,
    creationdate timestamp without time zone NOT NULL,
    currentconnection timestamp without time zone,
    email character varying(255) NOT NULL,
    lastconnection timestamp without time zone,
    organization character varying(255) NOT NULL,
    phone1 character varying(15),
    phone2 character varying(15),
    photo bytea,
    username character varying(25)
);


ALTER TABLE public.userconnect OWNER TO postgres;

--
-- Name: userjboss; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE userjboss (
    username character varying(25) NOT NULL,
    password character varying(200)
);


ALTER TABLE public.userjboss OWNER TO postgres;

--
-- Name: userjbossroles; Type: TABLE; Schema: public; Owner: postgres; Tablespace: 
--

CREATE TABLE userjbossroles (
    username character varying(25) NOT NULL,
    role character varying(255),
    rolegroup character varying(255)
);


ALTER TABLE public.userjbossroles OWNER TO postgres;

--
-- Data for Name: collaborators; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY collaborators (projects_uuid, collaborators_uuid) FROM stdin;
\.


--
-- Data for Name: instance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY instance (uuid, cpu, creationdate, name, ram, stg, url, owner_instance) FROM stdin;
instuuid1	8	2014-01-01 00:00:00	Public Instance 1	8	500	http://127.0.0.1:8080	\N
instuuid4	4	2014-01-01 00:00:00	Public Instance 2	8	300	http://127.0.0.1:8080	\N
\.


--
-- Data for Name: notification; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notification (uuid, creationdate, subject, text, type) FROM stdin;
notuuid1	2014-07-01 00:00:00	Welcome to iMath Cloud	Welcome to iMathCloud, the new high performance computation platform in the cloud	0
notuuid2	2014-05-01 00:00:00	Contact with us	If you have any questions, requests or suggestions please contact us at info@imathresearch.com	0
notuuid3	2014-08-01 00:00:00	New collaborators	Check your projects to see your new collaborators	1
\.


--
-- Data for Name: notifications_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY notifications_user (notification_uuid, notificationusers_uuid) FROM stdin;
\.


--
-- Data for Name: project; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY project (uuid, creationdate, description, key, linux_group, name, instance, owner) FROM stdin;
\.


--
-- Data for Name: standardconfiguration; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY standardconfiguration (uuid, cpu, ram, stg) FROM stdin;
stda1	8	8	500
stda2	1	0.5	100
stda3	2	2	300
stda4	8	16	5000
\.


--
-- Data for Name: useraccess; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY useraccess (uuid, accesssource, password) FROM stdin;
\.


--
-- Data for Name: userconnect; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY userconnect (uuid, creationdate, currentconnection, email, lastconnection, organization, phone1, phone2, photo, username) FROM stdin;
\.


--
-- Data for Name: userjboss; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY userjboss (username, password) FROM stdin;
\.


--
-- Data for Name: userjbossroles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY userjbossroles (username, role, rolegroup) FROM stdin;
\.


--
-- Name: collaborators_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY collaborators
    ADD CONSTRAINT collaborators_pkey PRIMARY KEY (projects_uuid, collaborators_uuid);


--
-- Name: instance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY instance
    ADD CONSTRAINT instance_pkey PRIMARY KEY (uuid);


--
-- Name: notification_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notification
    ADD CONSTRAINT notification_pkey PRIMARY KEY (uuid);


--
-- Name: notifications_user_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY notifications_user
    ADD CONSTRAINT notifications_user_pkey PRIMARY KEY (notification_uuid, notificationusers_uuid);


--
-- Name: project_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY project
    ADD CONSTRAINT project_pkey PRIMARY KEY (uuid);


--
-- Name: standardconfiguration_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY standardconfiguration
    ADD CONSTRAINT standardconfiguration_pkey PRIMARY KEY (uuid);


--
-- Name: uk_5ahi5chhosvmoqjai8rngcc13; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY userconnect
    ADD CONSTRAINT uk_5ahi5chhosvmoqjai8rngcc13 UNIQUE (username);


--
-- Name: uk_ex7cd5i7yhpdpgyl3mdchbtsd; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY userconnect
    ADD CONSTRAINT uk_ex7cd5i7yhpdpgyl3mdchbtsd UNIQUE (email);


--
-- Name: useraccess_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY useraccess
    ADD CONSTRAINT useraccess_pkey PRIMARY KEY (uuid);


--
-- Name: userconnect_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY userconnect
    ADD CONSTRAINT userconnect_pkey PRIMARY KEY (uuid);


--
-- Name: userjboss_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY userjboss
    ADD CONSTRAINT userjboss_pkey PRIMARY KEY (username);


--
-- Name: userjbossroles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres; Tablespace: 
--

ALTER TABLE ONLY userjbossroles
    ADD CONSTRAINT userjbossroles_pkey PRIMARY KEY (username);


--
-- Name: fk_1jgjq2ifv5qqmp7wo8oltgrs2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notifications_user
    ADD CONSTRAINT fk_1jgjq2ifv5qqmp7wo8oltgrs2 FOREIGN KEY (notificationusers_uuid) REFERENCES userconnect(uuid);


--
-- Name: fk_64ofpbgm51al66v64gr67wbsv; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY collaborators
    ADD CONSTRAINT fk_64ofpbgm51al66v64gr67wbsv FOREIGN KEY (projects_uuid) REFERENCES project(uuid);


--
-- Name: fk_88195lfeao3lc1o02mxeu1uuk; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fk_88195lfeao3lc1o02mxeu1uuk FOREIGN KEY (owner) REFERENCES userconnect(uuid);


--
-- Name: fk_bpa4o8s5eqe079tn6ew3ywpqf; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY instance
    ADD CONSTRAINT fk_bpa4o8s5eqe079tn6ew3ywpqf FOREIGN KEY (owner_instance) REFERENCES userconnect(uuid);


--
-- Name: fk_csbnbc5hr3kjr5t7f1arrpvl2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY collaborators
    ADD CONSTRAINT fk_csbnbc5hr3kjr5t7f1arrpvl2 FOREIGN KEY (collaborators_uuid) REFERENCES userconnect(uuid);


--
-- Name: fk_cyw1tck5kra9k3ffnmb9ep2bs; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY project
    ADD CONSTRAINT fk_cyw1tck5kra9k3ffnmb9ep2bs FOREIGN KEY (instance) REFERENCES instance(uuid);


--
-- Name: fk_qnqbahea6vyh66rcrvcrrxjjo; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY notifications_user
    ADD CONSTRAINT fk_qnqbahea6vyh66rcrvcrrxjjo FOREIGN KEY (notification_uuid) REFERENCES notification(uuid);


--
-- Name: public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

