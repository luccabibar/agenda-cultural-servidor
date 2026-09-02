--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2026-09-01 22:40:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4871 (class 1262 OID 16389)
-- Name: agenda-cultural; Type: DATABASE; Schema: -; Owner: agenda-cultural-user
--

CREATE DATABASE "agenda-cultural" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';


ALTER DATABASE "agenda-cultural" OWNER TO "agenda-cultural-user";

\connect -reuse-previous=on "dbname='agenda-cultural'"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 229 (class 1259 OID 16553)
-- Name: atualizacao_evento; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.atualizacao_evento (
    id integer NOT NULL,
    evento integer NOT NULL,
    titulo character varying(24) NOT NULL,
    texto character varying(256) NOT NULL,
    imagem character varying(32),
    data_criacao date DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE public.atualizacao_evento OWNER TO "agenda-cultural-user";

--
-- TOC entry 228 (class 1259 OID 16552)
-- Name: atualizacao_evento_id_seq; Type: SEQUENCE; Schema: public; Owner: agenda-cultural-user
--

CREATE SEQUENCE public.atualizacao_evento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.atualizacao_evento_id_seq OWNER TO "agenda-cultural-user";

--
-- TOC entry 4872 (class 0 OID 0)
-- Dependencies: 228
-- Name: atualizacao_evento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: agenda-cultural-user
--

ALTER SEQUENCE public.atualizacao_evento_id_seq OWNED BY public.atualizacao_evento.id;


--
-- TOC entry 224 (class 1259 OID 16455)
-- Name: categoria_evento; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.categoria_evento (
    categoria character varying(16) NOT NULL
);


ALTER TABLE public.categoria_evento OWNER TO "agenda-cultural-user";

--
-- TOC entry 227 (class 1259 OID 16518)
-- Name: evento; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.evento (
    id integer NOT NULL,
    status character varying(12) NOT NULL,
    nome character varying(24) NOT NULL,
    descricao character varying(256) NOT NULL,
    categoria character varying(16) NOT NULL,
    imagem character varying(32),
    contato character varying(32),
    organizador integer NOT NULL,
    moderador integer,
    hora_ini timestamp without time zone NOT NULL,
    hora_fim timestamp without time zone NOT NULL,
    regiao character varying(24) NOT NULL,
    endereco character varying(64) NOT NULL,
    endereco_link character varying(48) NOT NULL,
    data_criacao date DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE public.evento OWNER TO "agenda-cultural-user";

--
-- TOC entry 226 (class 1259 OID 16517)
-- Name: evento_id_seq; Type: SEQUENCE; Schema: public; Owner: agenda-cultural-user
--

CREATE SEQUENCE public.evento_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.evento_id_seq OWNER TO "agenda-cultural-user";

--
-- TOC entry 4873 (class 0 OID 0)
-- Dependencies: 226
-- Name: evento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: agenda-cultural-user
--

ALTER SEQUENCE public.evento_id_seq OWNED BY public.evento.id;


--
-- TOC entry 223 (class 1259 OID 16443)
-- Name: moderador; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.moderador (
    id integer NOT NULL,
    cpf_cnpj character varying(14) NOT NULL
);


ALTER TABLE public.moderador OWNER TO "agenda-cultural-user";

--
-- TOC entry 222 (class 1259 OID 16431)
-- Name: organizador; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.organizador (
    id integer NOT NULL,
    cpf_cnpj character varying(14) NOT NULL
);


ALTER TABLE public.organizador OWNER TO "agenda-cultural-user";

--
-- TOC entry 221 (class 1259 OID 16421)
-- Name: pessoa; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.pessoa (
    id integer NOT NULL
);


ALTER TABLE public.pessoa OWNER TO "agenda-cultural-user";

--
-- TOC entry 225 (class 1259 OID 16479)
-- Name: regiao_evento; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.regiao_evento (
    regiao character varying(24) NOT NULL
);


ALTER TABLE public.regiao_evento OWNER TO "agenda-cultural-user";

--
-- TOC entry 217 (class 1259 OID 16397)
-- Name: status_evento; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.status_evento (
    status character varying(12) NOT NULL
);


ALTER TABLE public.status_evento OWNER TO "agenda-cultural-user";

--
-- TOC entry 218 (class 1259 OID 16402)
-- Name: status_usuario; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.status_usuario (
    status character varying(12) NOT NULL
);


ALTER TABLE public.status_usuario OWNER TO "agenda-cultural-user";

--
-- TOC entry 220 (class 1259 OID 16408)
-- Name: usuario; Type: TABLE; Schema: public; Owner: agenda-cultural-user
--

CREATE TABLE public.usuario (
    id integer NOT NULL,
    email character varying(48) NOT NULL,
    senha character varying(64) NOT NULL,
    nome character varying(32) NOT NULL,
    status character varying(12) NOT NULL,
    data_criacao date DEFAULT CURRENT_DATE NOT NULL
);


ALTER TABLE public.usuario OWNER TO "agenda-cultural-user";

--
-- TOC entry 219 (class 1259 OID 16407)
-- Name: usuario_id_seq; Type: SEQUENCE; Schema: public; Owner: agenda-cultural-user
--

CREATE SEQUENCE public.usuario_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.usuario_id_seq OWNER TO "agenda-cultural-user";

--
-- TOC entry 4874 (class 0 OID 0)
-- Dependencies: 219
-- Name: usuario_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: agenda-cultural-user
--

ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;


--
-- TOC entry 4683 (class 2604 OID 16556)
-- Name: atualizacao_evento id; Type: DEFAULT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.atualizacao_evento ALTER COLUMN id SET DEFAULT nextval('public.atualizacao_evento_id_seq'::regclass);


--
-- TOC entry 4681 (class 2604 OID 16521)
-- Name: evento id; Type: DEFAULT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.evento ALTER COLUMN id SET DEFAULT nextval('public.evento_id_seq'::regclass);


--
-- TOC entry 4679 (class 2604 OID 16411)
-- Name: usuario id; Type: DEFAULT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);


--
-- TOC entry 4710 (class 2606 OID 16559)
-- Name: atualizacao_evento atualizacao_evento_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.atualizacao_evento
    ADD CONSTRAINT atualizacao_evento_pkey PRIMARY KEY (id);


--
-- TOC entry 4704 (class 2606 OID 16459)
-- Name: categoria_evento categoria_evento_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.categoria_evento
    ADD CONSTRAINT categoria_evento_pkey PRIMARY KEY (categoria);


--
-- TOC entry 4708 (class 2606 OID 16526)
-- Name: evento evento_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT evento_pkey PRIMARY KEY (id);


--
-- TOC entry 4700 (class 2606 OID 16447)
-- Name: moderador moderador_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.moderador
    ADD CONSTRAINT moderador_pkey PRIMARY KEY (id);


--
-- TOC entry 4696 (class 2606 OID 16435)
-- Name: organizador organizador_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.organizador
    ADD CONSTRAINT organizador_pkey PRIMARY KEY (id);


--
-- TOC entry 4694 (class 2606 OID 16425)
-- Name: pessoa pessoa_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (id);


--
-- TOC entry 4706 (class 2606 OID 16483)
-- Name: regiao_evento regiao_evento_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.regiao_evento
    ADD CONSTRAINT regiao_evento_pkey PRIMARY KEY (regiao);


--
-- TOC entry 4686 (class 2606 OID 16401)
-- Name: status_evento status_evento_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.status_evento
    ADD CONSTRAINT status_evento_pkey PRIMARY KEY (status);


--
-- TOC entry 4688 (class 2606 OID 16406)
-- Name: status_usuario status_usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.status_usuario
    ADD CONSTRAINT status_usuario_pkey PRIMARY KEY (status);


--
-- TOC entry 4702 (class 2606 OID 16454)
-- Name: moderador unique_moderador; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.moderador
    ADD CONSTRAINT unique_moderador UNIQUE (cpf_cnpj);


--
-- TOC entry 4698 (class 2606 OID 16437)
-- Name: organizador unique_organizador; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.organizador
    ADD CONSTRAINT unique_organizador UNIQUE (cpf_cnpj);


--
-- TOC entry 4690 (class 2606 OID 16415)
-- Name: usuario unique_usuario; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT unique_usuario UNIQUE (email, nome);


--
-- TOC entry 4692 (class 2606 OID 16413)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);


--
-- TOC entry 4715 (class 2606 OID 16527)
-- Name: evento fk_categoria; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT fk_categoria FOREIGN KEY (categoria) REFERENCES public.categoria_evento(categoria) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4720 (class 2606 OID 16560)
-- Name: atualizacao_evento fk_evento; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.atualizacao_evento
    ADD CONSTRAINT fk_evento FOREIGN KEY (evento) REFERENCES public.evento(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4712 (class 2606 OID 16426)
-- Name: pessoa fk_id; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT fk_id FOREIGN KEY (id) REFERENCES public.usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4713 (class 2606 OID 16438)
-- Name: organizador fk_id; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.organizador
    ADD CONSTRAINT fk_id FOREIGN KEY (id) REFERENCES public.usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4714 (class 2606 OID 16448)
-- Name: moderador fk_id; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.moderador
    ADD CONSTRAINT fk_id FOREIGN KEY (id) REFERENCES public.usuario(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4716 (class 2606 OID 16532)
-- Name: evento fk_moderador; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT fk_moderador FOREIGN KEY (moderador) REFERENCES public.moderador(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4717 (class 2606 OID 16537)
-- Name: evento fk_organizador; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT fk_organizador FOREIGN KEY (organizador) REFERENCES public.organizador(id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4718 (class 2606 OID 16542)
-- Name: evento fk_regiao; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT fk_regiao FOREIGN KEY (regiao) REFERENCES public.regiao_evento(regiao) ON UPDATE CASCADE ON DELETE RESTRICT;


--
-- TOC entry 4711 (class 2606 OID 16416)
-- Name: usuario fk_status; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT fk_status FOREIGN KEY (status) REFERENCES public.status_usuario(status) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 4719 (class 2606 OID 16547)
-- Name: evento fk_status; Type: FK CONSTRAINT; Schema: public; Owner: agenda-cultural-user
--

ALTER TABLE ONLY public.evento
    ADD CONSTRAINT fk_status FOREIGN KEY (status) REFERENCES public.status_evento(status) ON UPDATE CASCADE ON DELETE RESTRICT;


-- Completed on 2026-09-01 22:40:48

--
-- PostgreSQL database dump complete
--

